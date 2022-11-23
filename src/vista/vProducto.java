package vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import dao.daoCategoria;
import dao.daoProducto;
import modelo.Categoria;
import modelo.Producto;

import javax.swing.JButton;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class vProducto extends JFrame {

	private JPanel contentPane;
	private JTextField txtDescripcion;
	private JTextField txtPrecio;
	private JTextField txtCantidad;
	private JComboBox cboCategoria;
	private JLabel lblIdProducto;
	private JTable tblProductos;
	private JScrollPane scrollpane;
	daoProducto dao = new daoProducto();
	DefaultTableModel modelo = new DefaultTableModel();
	ArrayList<Producto> lista = new ArrayList<Producto>();
	Producto producto;
	int fila = -1;
	Producto user = new Producto();
	private JButton btnAgregar;
	private JButton btnEditar;
	private JButton btnBorrar;
	private JButton btnEliminar;
	daoCategoria daocat = new daoCategoria();
	ArrayList<Categoria> listaCategorias = new ArrayList<Categoria>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					vProducto frame = new vProducto();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void limpiar() {
		txtDescripcion.setText("");
		txtPrecio.setText("");
		txtCantidad.setText("");
		cboCategoria.setSelectedItem("");
		lblIdProducto.setText("");
	}

	public void cargarCategorias() {
		listaCategorias = daocat.selectCategoria();
		DefaultComboBoxModel modelcombo = new DefaultComboBoxModel();
		for (Categoria categoria : listaCategorias) {
			modelcombo.addElement(categoria.getCategoria());
		}
		cboCategoria.setModel(modelcombo);
	}

	public vProducto() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(vProducto.class.getResource("/img/jyujyu.png")));
		setTitle("crub productos");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 582, 468);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setBounds(10, 30, 46, 14);
		contentPane.add(lblNewLabel);

		JPanel contentPane_1 = new JPanel();
		contentPane_1.setBounds(0, 0, 368, 191);
		contentPane_1.setLayout(null);
		contentPane_1.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.add(contentPane_1);

		JLabel lblNewLabel_2 = new JLabel("ID");
		lblNewLabel_2.setBounds(10, 30, 46, 14);
		contentPane_1.add(lblNewLabel_2);

		JLabel lblDescripciob = new JLabel("DESCRIPCION");
		lblDescripciob.setBounds(10, 55, 131, 14);
		contentPane_1.add(lblDescripciob);

		JLabel lblPrecio = new JLabel("PRECIO");
		lblPrecio.setBounds(10, 90, 46, 14);
		contentPane_1.add(lblPrecio);

		JLabel lblNewLabel_4_1 = new JLabel("CANTIDAD");
		lblNewLabel_4_1.setBounds(10, 115, 118, 14);
		contentPane_1.add(lblNewLabel_4_1);

		JLabel lblNewLabel_4_2 = new JLabel("CATEGORIA");
		lblNewLabel_4_2.setBounds(10, 149, 118, 14);
		contentPane_1.add(lblNewLabel_4_2);

		lblIdProducto = new JLabel("0");
		lblIdProducto.setBounds(106, 30, 46, 14);
		contentPane_1.add(lblIdProducto);

		txtDescripcion = new JTextField();
		txtDescripcion.setBounds(103, 52, 228, 20);
		contentPane_1.add(txtDescripcion);
		txtDescripcion.setColumns(10);

		txtPrecio = new JTextField();
		txtPrecio.setBounds(106, 87, 230, 20);
		contentPane_1.add(txtPrecio);
		txtPrecio.setColumns(10);

		txtCantidad = new JTextField();
		txtCantidad.setBounds(103, 112, 228, 20);
		contentPane_1.add(txtCantidad);
		txtCantidad.setColumns(10);

		cboCategoria = new JComboBox();
		cboCategoria.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				cargarCategorias();
			}
		});
		cboCategoria.setModel(new DefaultComboBoxModel(new String[] { "hhdhdhf" }));
		cboCategoria.setBounds(106, 145, 225, 22);
		contentPane_1.add(cboCategoria);

		btnAgregar = new JButton("AGREGAR");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (txtDescripcion.getText().equals("") || txtPrecio.getText().equals("")
							|| txtCantidad.getText().equals("") || cboCategoria.getSelectedItem().equals("")) {
						JOptionPane.showMessageDialog(null, "CAMPOS VACIOS ");
						return;
					}
					user.setDescripcion(txtDescripcion.getText());
					user.setPrecio(Double.parseDouble(txtPrecio.getText().toString()));
					user.setCantidad(Integer.parseInt(txtCantidad.getText().toString()));
					user.setCategoria(listaCategorias.get(cboCategoria.getSelectedIndex()).getIdcategoria());
					if (dao.insertProducto(user)) {
						actualizarTabla();
						limpiar();
						JOptionPane.showMessageDialog(null, "SE AGREGO CORRECTAMENTE");
					} else {
						JOptionPane.showMessageDialog(null, "ERROR");
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "ERROR");
				}
			}
		});
		btnAgregar.setBounds(414, 46, 89, 23);
		contentPane.add(btnAgregar);

		btnEditar = new JButton("EDITAR");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (txtDescripcion.getText().equals("") || txtPrecio.getText().equals("")
							|| txtCantidad.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "CAMPOS VACIOS ");
						return;
					}
					producto.setDescripcion(txtDescripcion.getText());
					producto.setPrecio(Double.parseDouble(txtPrecio.getText().toString()));
					producto.setCantidad(Integer.parseInt(txtCantidad.getText().toString()));
					producto.setCategoria(cboCategoria.getSelectedIndex());
					if (dao.editProducto(producto)) {
						actualizarTabla();
						limpiar();
						JOptionPane.showMessageDialog(null, "SE ACTUALIZO  CORRECTAMENTE");
					} else {
						JOptionPane.showMessageDialog(null, "ERROR");
					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "ERROR");
				}
			}
		});
		btnEditar.setBounds(414, 80, 89, 23);
		contentPane.add(btnEditar);

		btnBorrar = new JButton("BORRAR");
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiar();
			}
		});
		btnBorrar.setBounds(414, 114, 89, 23);
		contentPane.add(btnBorrar);

		btnEliminar = new JButton("ELIMINAR");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

					int opcion = JOptionPane.showConfirmDialog(null, "¿ESTA SEGURO DE ELIMINAR ESTE PRODUCTO?",
							"ELIMINAR PRODUCTO", JOptionPane.YES_NO_OPTION);
					if (opcion == 0) {
						if (dao.deleteProducto(lista.get(fila).getIdProducto())) {
							actualizarTabla();
							JOptionPane.showMessageDialog(null, "SE ELIMINO CORRECTAMENTE");
						} else {
							JOptionPane.showMessageDialog(null, "ERROR");
						}
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "ERROR");
				}
			}
		});
		btnEliminar.setBounds(414, 148, 89, 23);
		contentPane.add(btnEliminar);

		scrollpane = new JScrollPane();
		scrollpane.setBounds(10, 213, 529, 191);
		contentPane.add(scrollpane);

		tblProductos = new JTable();
		tblProductos.setModel(new DefaultTableModel(
				new Object[][] { { null, null, null, null, null }, { null, null, null, null, null },
						{ null, null, null, null, null }, },
				new String[] { "New column", "New column", "New column", "New column", "New column" }));
		scrollpane.setViewportView(tblProductos);
		modelo.addColumn("ID PRODUCTO");
		modelo.addColumn("DESCRIPCION");
		modelo.addColumn("PRECIO");
		modelo.addColumn("CANTIDAD");
		modelo.addColumn("CATEGORIA");
		tblProductos.setModel(modelo);

		actualizarTabla();

	}

	public String buscarCategoria(int idcategoria) {

		String ca = "";
		for (Categoria cat : listaCategorias) {
			if (cat.getIdcategoria() == idcategoria) {
				ca = cat.getCategoria();
			}
		}
		System.out.print("" + ca);
		return ca;
	}

	public void actualizarTabla() {
		cargarCategorias();
		while (modelo.getRowCount() > 0) {
			modelo.removeRow(0);
		}
		lista = dao.selectProductos();
		for (Producto u : lista) {
			Object o[] = new Object[5];
			o[0] = u.getIdProducto();
			o[1] = u.getDescripcion();
			o[2] = u.getPrecio();
			o[3] = u.getCantidad();
			o[4] = buscarCategoria(u.getCategoria());
			modelo.addRow(o);
		}
		tblProductos.setModel(modelo);

	}
}
