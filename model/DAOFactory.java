package model;

public class DAOFactory {
	private static CustomerDAOImpl customerDAO;
	private static AddressDAOImpl addressDAO;
	private static PaymentDAOImpl paymentDAO;
	private static MealDAOImpl mealDAO;
	//customer
	public static CustomerDAOImpl getCustomerDAO() {
		customerDAO  = new CustomerDAOImpl();
		return customerDAO;
	}
	//address
	public static AddressDAOImpl getAddressDAO() {
		addressDAO = new AddressDAOImpl();
		return addressDAO;
	}
	//payments
	public static PaymentDAOImpl getPaymentDAO() {
		paymentDAO = new PaymentDAOImpl();
		return paymentDAO;
	}
	//meal-table
	public static MealDAOImpl getMealDAO() {
		mealDAO = new MealDAOImpl();
		return mealDAO;
	}
}
