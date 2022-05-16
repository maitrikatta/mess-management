package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class MealDAOImpl implements MealDAO<Meal> {
	@Override
	public Meal getMeal(Meal meal) throws SQLException {
		// mutate new object only
		Meal newMeal = new Meal();
		String tableName = makeTableName(meal);
		String FETCH_MEAL = "SELECT LUNCH,DINNER FROM "+tableName+" WHERE DAY = ?";
		Connection conn = DBConnect.makeConnection();
		PreparedStatement ps = conn.prepareStatement(FETCH_MEAL);
		ps.setString(1, meal.getDate());
		ResultSet resultSet = ps.executeQuery();
		if(resultSet.next()) {
			newMeal.setDinner(resultSet.getInt("DINNER"));
			newMeal.setLunch(resultSet.getInt("LUNCH"));
			return newMeal;
		}
		conn.close();
		ps.close();
		return null; //no meal record found
	}

	private String makeTableName(Meal meal) {
		String name = meal.getCustomerName();
		int id = meal.getCustomerID();
		name = name.trim();
		String strID = Integer.toString(id);
		return (name.concat(strID));
	}

	@Override
	public boolean addNewMeal(Meal meal) throws SQLException {
		String tableName = makeTableName(meal);
		String ADD_NEW_MEAL = "INSERT INTO "+tableName+"(LUNCH,DINNER,DAY) VALUES(?,?,?)";
		Connection conn = DBConnect.makeConnection();
		PreparedStatement ps = conn.prepareStatement(ADD_NEW_MEAL);
		ps.setInt(1, meal.getLunch());
		ps.setInt(2, meal.getDinner());
		ps.setString(3, meal.getDate());
		int insertResult = ps.executeUpdate();
		conn.close();
		ps.close();
		if(insertResult>0) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public boolean updateMeal(Meal meal) throws SQLException {
		String tableName = makeTableName(meal);
		String UPDATE_MEAL = "UPDATE "+tableName+" SET LUNCH = ?, DINNER = ? WHERE DAY = ?";
		Connection conn =  DBConnect.makeConnection();
		PreparedStatement ps = conn.prepareStatement(UPDATE_MEAL);
		ps.setInt(1,meal.getLunch());
		ps.setInt(2,meal.getDinner());
		ps.setString(3,meal.getDate());
		int updateResult = ps.executeUpdate();
		conn.close();
		ps.close();
		if(updateResult>0) 
			return true;
		else
			return false;
	}
	

}
