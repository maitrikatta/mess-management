package model;

import java.sql.SQLException;

public interface AddressDAO<T> {
	public T getAddress(int id) throws SQLException;
	public int setAddress(T t) throws SQLException;
	public boolean updateAddress(T t) throws SQLException;
}
