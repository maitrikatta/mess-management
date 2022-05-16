package model;

import java.sql.SQLException;

public interface MealDAO<T> {
	public Meal getMeal(T t) throws SQLException;
	public boolean addNewMeal(T t) throws SQLException;
	public boolean updateMeal(T t) throws SQLException;
}
