package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexion {
	Connection cx = null;

	public Connection conectar() {
		try {
			Class.forName("org.sqlite.JDBC");
			cx = DriverManager.getConnection("jdbc:sqlite:sistema.db");
			//Class.forName("com.mysql.cj.jdbc.Driver");
			//cx = DriverManager.getConnection("jdbc:mysql://localhost:3306/sistema","root","");
			System.out.print("Conexión exitosa");

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();
		}
		return cx;
	}
	
	 public void desconectar() {
	        try {
	            cx.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	public static void main(String[] args) {
		conexion cx = new conexion();
		cx.conectar();

	}

}