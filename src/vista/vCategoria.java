package vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import dao.daoCategoria;
import modelo.Categoria;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class vCategoria extends JFrame {

	private JPanel contentPane;
	private JTextField txtCategoria;
	private JTable tblCategoria;
	private JButton btnAgregar;
	private JButton btnEliminar;
	private JButton btnEditar;
	private JButton btnLimpiar;
	daoCategoria dao = new daoCategoria();
	DefaultTableModel modelo = new DefaultTableModel();
	ArrayList<Categoria> lista = new ArrayList<Categoria>();
	Categoria categoria;
	int fila = -1;
	private JScrollPane scrollPane;
	private JLabel lblIdCategoria;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					vCategoria frame = new vCategoria();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void limpiar() {
		txtCategoria.setText("");
		lblIdCategoria.setText("");

	}

	public vCategoria() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(vCategoria.class.getResource("/img/jyujyu.png")));
		setTitle("CATEGORIA");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 663, 433);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setBounds(25, 39, 46, 14);
		contentPane.add(lblNewLabel);

		lblIdCategoria = new JLabel("0");
		lblIdCategoria.setBounds(109, 39, 46, 14);
		contentPane.add(lblIdCategoria);

		JLabel lblNewLabel_2 = new JLabel("CATEGORIA");
		lblNewLabel_2.setBounds(25, 91, 130, 14);
		contentPane.add(lblNewLabel_2);

		txtCategoria = new JTextField();
		txtCategoria.setBounds(25, 117, 147, 20);
		contentPane.add(txtCategoria);
		txtCategoria.setColumns(10);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(182, 11, 440, 217);
		contentPane.add(scrollPane);

		tblCategoria = new JTable();
		tblCategoria.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				fila = tblCategoria.getSelectedRow();
				categoria = lista.get(fila);
				lblIdCategoria.setText("" + lista.get(fila).getIdcategoria());
				txtCategoria.setText(categoria.getCategoria());;
			}
		});
		tblCategoria.setModel(new DefaultTableModel(
				new Object[][] { { null, null }, { null, null }, { null, null }, { null, null }, { null, null }, },
				new String[] { "New column", "New column" }));

		scrollPane.setViewportView(tblCategoria);
		modelo.addColumn("ID");
		modelo.addColumn("CATEGORIA");
		tblCategoria.setModel(modelo);
		actualizarTabla();

		btnAgregar = new JButton("AGREGAR");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (txtCategoria.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "CAMPOS VACIOS ");
						return;
					}
					Categoria user = new Categoria();
					user.setCategoria(txtCategoria.getText());
					if (dao.insertCategoria(user)) {
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
		btnAgregar.setBounds(124, 291, 89, 23);
		contentPane.add(btnAgregar);

		btnEliminar = new JButton("ELIMINAR");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int opcion = JOptionPane.showConfirmDialog(null, "¿ESTA SEGURO DE ELIMINAR ESTA CATEGORIA?",
							"ELIMINAR CATEGORIA", JOptionPane.YES_NO_OPTION);
					if (opcion == 0) {
						if (dao.deleteCategoria(lista.get(fila).getIdcategoria())) {
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
		btnEliminar.setBounds(259, 291, 89, 23);
		contentPane.add(btnEliminar);

		btnEditar = new JButton("EDITAR");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (txtCategoria.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "CAMPOS VACIOS ");
						return;
					}
					categoria.setCategoria(txtCategoria.getText());
					if (dao.editCategoria(categoria)) {
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
		btnEditar.setBounds(412, 291, 89, 23);
		contentPane.add(btnEditar);

		btnLimpiar = new JButton("LIMPIAR");
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiar();
			}
		});
		btnLimpiar.setBounds(270, 347, 89, 23);
		contentPane.add(btnLimpiar);
	}

	public void actualizarTabla() {
		while (modelo.getRowCount() > 0) {
			modelo.removeRow(0);
		}
		lista = dao.selectCategoria();
		for (Categoria u : lista) {
			Object o[] = new Object[2];
			o[0] = u.getIdcategoria();
			o[1] = u.getCategoria();
			modelo.addRow(o);
		}
		tblCategoria.setModel(modelo);
	}

}
