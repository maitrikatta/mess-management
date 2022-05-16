package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressDAOImpl implements AddressDAO<Address> {
	private String INSERT_ADDRESS = "INSERT INTO ADDRESS(CUST_ID,STATE,CITY,STREET,MOBILE) VALUES(?,?,?,?,?)";
	private String UPDATE_ADDRESS = "UPDATE ADDRESS SET STATE =?, CITY=?, STREET=?, MOBILE=? WHERE CUST_ID=?";
	private String READ_ADDRESS = "SELECT CITY, CUST_ID, STATE, STREET,MOBILE FROM ADDRESS WHERE CUST_ID=?";
	
	@Override
	public Address getAddress(int id) throws SQLException {
		Address address =  new Address();
		Connection conn = DBConnect.makeConnection();
		PreparedStatement ps = conn.prepareStatement(READ_ADDRESS);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		rs.next();
		address.setId(rs.getInt("CUST_ID"));
		address.setCity(rs.getString("CITY"));
		address.setState(rs.getString("STATE"));
		address.setStreet(rs.getString("STREET"));
		address.setMobile(rs.getString("MOBILE"));
		ps.close();
		conn.close();
		return address;
	}

	@Override
	public int setAddress(Address address) throws SQLException {
		int result;
		Connection conn = DBConnect.makeConnection();
		PreparedStatement ps = conn.prepareStatement(INSERT_ADDRESS);
		ps.setInt(1, address.getId());
		ps.setString(2,address.getState());
		ps.setString(3, address.getCity());
		ps.setString(4, address.getStreet());
		ps.setString(5, address.getMobile());
		result = ps.executeUpdate();
		ps.close();
		conn.close();
		System.out.println(address);
		return result;
	}

	@Override
	public boolean updateAddress(Address address) throws SQLException {
		Connection conn = DBConnect.makeConnection();
		PreparedStatement ps = conn.prepareStatement(UPDATE_ADDRESS);
		ps.setString(1, address.getState());
		ps.setString(2, address.getCity());
		ps.setString(3, address.getStreet());
		ps.setString(4, address.getMobile());
		ps.setInt(5, address.getId());
		ps.executeUpdate();
		ps.close();
		conn.close();
		return false;
	}

	

}
