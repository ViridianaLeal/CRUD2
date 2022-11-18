package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Conexion.conexion;
import modelo.Categoria;
import modelo.Producto;

public class daoCategoria {
		    conexion cx;
		    Producto a;
		    ArrayList<Categoria> lista;
	
		    public daoCategoria() {
		        cx = new conexion();
		        lista = new ArrayList<Categoria>();
		        a = new Producto();
		    }
		   
		    public boolean insertCategoria(Categoria a) {
		        try {
		            String sql = "INSERT INTO categoria VALUES(null,?)";
		            PreparedStatement ps = cx.conectar().prepareStatement(sql);
		            ps.setString(1, a.getCategoria());		       
		            ps.executeUpdate();
		            cx.desconectar();
		            return true;
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		            return false;
		        }
		    }
		   
		    public ArrayList<Categoria> selectCategoria() {
		        ArrayList<Categoria> listax = new ArrayList<Categoria>();
		        try {
		            String sql="SELECT * FROM categoria";
		            PreparedStatement st = cx.conectar().prepareStatement(sql);
		            ResultSet rs = st.executeQuery();
		            while (rs.next()) {
		            	Categoria x = new Categoria();
		                x.setIdcategoria(rs.getInt("idcategoria"));
		                x.setCategoria(rs.getString("categoria"));
		                listax.add(x);
		            }
		            cx.desconectar();
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }
		        return listax;
		    }

		    public boolean deleteCategoria(int Id) {
				PreparedStatement ps = null;
				try {
					ps = cx.conectar().prepareStatement("DELETE FROM categoria WHERE idcategoria=?");
					ps.setInt(1, Id);
					ps.executeUpdate();
					return true;
				} catch (SQLException e) {
					e.printStackTrace();
					return false;
				}

			}

		    public boolean editCategoria(Categoria user) {
				PreparedStatement ps = null;
				try {
					ps = cx.conectar().prepareStatement("UPDATE categoria SET categoria=? WHERE idcategoria=?");
					ps.setString(1, user.getCategoria());	
					ps.setInt(2,user.getIdcategoria() );
					ps.executeUpdate();
					return true;
				} catch (SQLException e) {
					e.printStackTrace();
					return false;
				}

			}

		   

	}


