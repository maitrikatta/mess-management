package model;

import java.sql.SQLException;
import java.util.ArrayList;


public interface CustomerDAO<T> {
	 T getCustomerByID(int id) throws SQLException;
	 T getCustomerByName(String name) throws SQLException;
	 ArrayList<T> getAllCustomers() throws SQLException;
	 int saveCustomer(T t) throws SQLException;
	 boolean updateCustomer(T t) throws SQLException;
	 boolean deleteCustomer(T t) throws SQLException;
}
