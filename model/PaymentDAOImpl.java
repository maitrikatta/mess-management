package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class PaymentDAOImpl implements PaymentDAO<Payment> {
	private String INSERT_PAYMENT = "INSERT INTO PAYMENTS(CUST_ID,TOTAL,PAID,DATE_OF_PAYMENT) VALUES(?,?,?,CURRENT_TIMESTAMP)";
	private String GET_PAYMENT = "SELECT TOTAL,CUST_ID, PAID, DATE_OF_PAYMENT FROM PAYMENTS WHERE CUST_ID=?";
	private String UPDATE_PAYMENT = "UPDATE PAYMENTS SET TOTAL=?, PAID=?, DATE_OF_PAYMENT=CURRENT_TIMESTAMP WHERE CUST_ID=? ";
	@Override
	public int addPayment(Payment payment) throws SQLException {
		Connection conn = DBConnect.makeConnection();
		PreparedStatement ps = conn.prepareStatement(INSERT_PAYMENT);
		ps.setInt(1, payment.getId());
		ps.setFloat(2, payment.getTotal());
		ps.setFloat(3, payment.getPaid());
		int res = ps.executeUpdate();
		System.out.println(payment);
		ps.close();
		conn.close();
		return res;
	}

	@Override
	public int deletePayement(int id) throws SQLException {
		return 0;
	}

	@Override
	public boolean updatePayment(Payment payment) throws SQLException {
		Connection conn = DBConnect.makeConnection();
		PreparedStatement ps =  conn.prepareStatement(UPDATE_PAYMENT);
		ps.setFloat(1,payment.getTotal());
		ps.setFloat(2,payment.getPaid());
		ps.setFloat(3,payment.getId());
		ps.executeUpdate();
		ps.close();
		conn.close();
		return true;
	}

	@Override
	public Payment getPayement(int id) throws SQLException {
		Payment payment = new Payment();
		Connection conn = DBConnect.makeConnection();
		PreparedStatement ps =  conn.prepareStatement(GET_PAYMENT);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		rs.next();
		payment.setId(rs.getInt("CUST_ID"));
		payment.setPaid(rs.getInt("PAID"));
		payment.setTotal(rs.getInt("TOTAL"));
		Date newDate  = rs.getDate("DATE_OF_PAYMENT");
		payment.setPaymentDate(newDate.toString());
		return payment;
	}

}
