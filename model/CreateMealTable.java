package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import application.ErrorBox;

public class CreateMealTable {
	private String tableName;
	public boolean createTable(String personName, int id) {
		tableName = personName+id;
		String CREATE_TABLE = "CREATE TABLE "+tableName+"(Day date not null,lunch int not null default 0,dinner int not null default 0)";
		
		try {
			Connection conn =  DBConnect.makeConnection();
			PreparedStatement ps = conn.prepareStatement(CREATE_TABLE);
			ps.executeUpdate();
			ps.close();
			conn.close();		
		} catch(SQLException e) {
			System.out.println();
			return false;
		}
		return true;
	}
	public static boolean renewMeal(String personName, int id) {
		String tableName = personName+id;
		String DELETE_TABLE = "DELETE FROM "+tableName;
		try {
			Connection conn =  DBConnect.makeConnection();
			PreparedStatement ps = conn.prepareStatement(DELETE_TABLE);
			ps.executeUpdate();
			ps.close();
			conn.close();		
		} catch(SQLException e) {
			ErrorBox.alertUser("DATABASE ERROR.", e.getMessage());
			return false;
		}
		return true;
	}
}
