package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
	private static final String JDBC_URL = "jdbc:derby:MESS_DATABASE;create=true";
	public static Connection makeConnection() throws SQLException {
			Connection conn = DriverManager.getConnection(JDBC_URL);
			if(conn==null) {
				System.out.println("Connection is null");
			}
			return conn;
		}
}
