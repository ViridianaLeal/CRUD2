package dao;

import Conexion.conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Conexion.conexion;
import modelo.Producto;

public class daoProducto {
    conexion cx;
    Producto a;
    ArrayList<Producto> lista;

    public daoProducto() {
        cx = new conexion();
        lista = new ArrayList<Producto>();
        a = new Producto();
    }
   
    public boolean insertProducto(Producto a) {
        try {
            String sql = "INSERT INTO producto VALUES(null,?,?,?,?)";
            PreparedStatement ps = cx.conectar().prepareStatement(sql);
            ps.setString(1, a.getDescripcion());
            ps.setDouble(2, a.getPrecio());
            ps.setInt(3, a.getCantidad());
            ps.setInt(4, a.getCategoria());
            ps.executeUpdate();
            cx.desconectar();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
   
    public ArrayList<Producto> selectProductos() {
        ArrayList<Producto> listax = new ArrayList<Producto>();
        try {
            String sql="SELECT * FROM producto";
            PreparedStatement st = cx.conectar().prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Producto x = new Producto();
                x.setIdProducto(rs.getInt("idproducto"));
                x.setDescripcion(rs.getString("descripcion"));
                x.setPrecio(rs.getDouble("precio"));
                x.setCantidad(rs.getInt("cantidad"));
                x.setCategoria(rs.getInt("categoria"));
                listax.add(x);
            }
            cx.desconectar();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listax;
    }

    public boolean deleteProducto(int id) {
    	PreparedStatement ps = null;
		try {
			ps = cx.conectar().prepareStatement("DELETE FROM producto WHERE idproducto=?");
			ps.setInt(1,id);
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
    }

    public boolean editProducto(Producto x) {
    	PreparedStatement ps = null;
		try {
			ps = cx.conectar().prepareStatement("UPDATE producto SET descripcion=?,precio=?,cantidad=?,categoria=? WHERE idproducto=?");
			ps.setString(1, x.getDescripcion());
			ps.setDouble(2,x.getPrecio());
			ps.setInt(3, x.getCantidad());
			ps.setLong(4, x.getCategoria());
			ps.setInt(5, x.getIdProducto());
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
    }

   
}