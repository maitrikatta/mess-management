package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;


public class CustomerDAOImpl implements CustomerDAO<Customer>{
	private String INSERT_NEW_USER = "INSERT INTO CUSTOMERS(CUST_ID,FIRST_NAME,LAST_NAME,GENDER,CREATED_ON,ADMISSION_ON,AGE) values(?,?,?,?,CURRENT_TIMESTAMP,?,?)";
	private String GET_CUSTOMER_BY_NAME = "SELECT CUST_ID,FIRST_NAME, LAST_NAME,GENDER,ADMISSION_ON,AGE FROM CUSTOMERS WHERE FIRST_NAME LIKE ? AND LAST_NAME LIKE ?";
	private String GET_ALL_CUSTOMERS = "SELECT CUST_ID,FIRST_NAME, LAST_NAME,GENDER,ADMISSION_ON,AGE FROM CUSTOMERS";
	private String GET_CUSTOMER_BY_ID = "SELECT CUST_ID, FIRST_NAME, LAST_NAME, GENDER, CUST_ID, ADMISSION_ON, AGE FROM CUSTOMERS WHERE CUST_ID=?";
	private String UPDATE_CUSTOMER = "UPDATE CUSTOMERS SET FIRST_NAME=?, LAST_NAME =?, GENDER=?,ADMISSION_ON=?,AGE=? WHERE CUST_ID=? ";
	private String DELETE_BY_ID = "DELETE FROM CUSTOMERS WHERE CUST_ID=?";
	@Override
	public Customer getCustomerByID(int id) throws SQLException {
		Connection conn = DBConnect.makeConnection();
		PreparedStatement ps =  conn.prepareStatement(GET_CUSTOMER_BY_ID);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		//if match found; meaning has row.
		if(rs.next()) {
			Customer customer = new Customer();
			customer.setId(rs.getInt("CUST_ID"));
			customer.setFname(rs.getString("FIRST_NAME"));
			customer.setLname(rs.getString("LAST_NAME"));
			customer.setAge(rs.getInt("AGE"));
			customer.setGender(rs.getString("GENDER"));
			customer.setAdmissionOn(rs.getString("ADMISSION_ON"));
			rs.close();
			ps.close();
			conn.close();
			return customer;
		}else {  // nothing to return.
			rs.close();
			ps.close();
			conn.close();
			return null;
		}
		
	}

	@Override
	public Customer getCustomerByName(String name) throws SQLException {
		Connection conn;
		PreparedStatement ps;
		ResultSet resultSet;
		String fName=null;
		String lName=null;
		Customer customer = new Customer();
		//get first and last name from string
		StringTokenizer tokens = new StringTokenizer(name," ");
			fName = tokens.nextToken();
			lName = tokens.nextToken();
//			 System.out.print(fName);
//			 System.out.print(lName);
		
		// Querying database
		conn = DBConnect.makeConnection();
		ps = conn.prepareStatement(GET_CUSTOMER_BY_NAME);
		ps.setString(1, "%" + fName.toUpperCase() + "%");	
		ps.setString(2, "%" + lName.toUpperCase() + "%");	
		resultSet = ps.executeQuery();
		resultSet.next();	// move cursor to first row, i don't care about next row/search
		//set the customer DTO object and return it
		customer.setId(resultSet.getInt("CUST_ID"));
		customer.setFname(resultSet.getString("FIRST_NAME"));
		customer.setLname(resultSet.getString("LAST_NAME"));;
		customer.setAge(resultSet.getInt("AGE"));
		customer.setGender(resultSet.getString("GENDER"));
		customer.setAdmissionOn(resultSet.getString("ADMISSION_ON"));
		resultSet.close();
		ps.close();
		conn.close();
		return customer;
	}

	@Override
	public ArrayList<Customer> getAllCustomers() throws SQLException {
		Connection conn;
		PreparedStatement ps;
		ResultSet rs;
		conn = DBConnect.makeConnection();
		ps = conn.prepareStatement(GET_ALL_CUSTOMERS);
		rs = ps.executeQuery();
		//make array list object of type Customer
		ArrayList<Customer> customerList = new ArrayList<>();
		
		while(rs.next()) {
			Customer customer = new Customer();
			customer.setId(rs.getInt("CUST_ID"));
			customer.setFname(rs.getString("FIRST_NAME"));
			customer.setLname(rs.getString("LAST_NAME"));
			customer.setGender(rs.getString("GENDER"));
			customer.setAge(rs.getInt("AGE"));
			customer.setAdmissionOn(rs.getString("ADMISSION_ON"));
			customerList.add(customer);			
		}
		
		rs.close();
		ps.close();
		conn.close();
		
		return customerList;
	}

	@Override
	public int saveCustomer(Customer customer) throws SQLException {
		Connection conn = DBConnect.makeConnection();
		PreparedStatement ps = conn.prepareStatement(INSERT_NEW_USER);
		ps.setInt(1, customer.getId());
		ps.setString(2, customer.getFname());
		ps.setString(3, customer.getLname());
		ps.setString(4,customer.getGender());
		ps.setDate(5, java.sql.Date.valueOf(customer.getAdmissionOn()));
		ps.setInt(6,customer.getAge());
		int result = ps.executeUpdate();
		ps.close();
		conn.close();
		System.out.println(customer);
		return result;
	}

	@Override
	public boolean updateCustomer(Customer customer) throws SQLException {
		Connection conn = DBConnect.makeConnection();
		PreparedStatement ps = conn.prepareStatement(UPDATE_CUSTOMER);
		ps.setString(1, customer.getFname());
		ps.setString(2, customer.getLname());
		ps.setString(3, customer.getGender());
		ps.setDate(4, java.sql.Date.valueOf(customer.getAdmissionOn()));
		ps.setInt(5, customer.getAge());
		ps.setInt(6,customer.getId());
		ps.executeUpdate();
		ps.close();
		conn.close();
		return true;
	}

	@Override
	public boolean deleteCustomer(Customer customer) throws SQLException {
		
		Connection conn = DBConnect.makeConnection();
		PreparedStatement ps = conn.prepareStatement(DELETE_BY_ID);
		ps.setInt(1, customer.getId());
		ps.executeUpdate();
		ps.close();
		conn.close();
		return true;
	}

}
