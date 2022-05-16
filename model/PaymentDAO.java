package model;

import java.sql.SQLException;

public interface PaymentDAO<T> {
	public int addPayment(T t) throws SQLException;
	public int deletePayement(int id) throws SQLException;
	public boolean updatePayment(T t) throws SQLException;
	public T getPayement(int id) throws SQLException;
}
