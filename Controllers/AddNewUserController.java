package Controllers;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Random;
import java.util.regex.Pattern;

import application.AlertBox;
import application.ConfirmationBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.ToggleGroup;
import model.Address;
import model.AddressDAOImpl;
import model.CreateMealTable;
import model.Customer;
import model.CustomerDAOImpl;
import model.DAOFactory;
import model.Payment;
import model.PaymentDAOImpl;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;

public class AddNewUserController {
//Personal information
	@FXML
	private TextField fx_fname;
	@FXML
	private TextField fx_lname;
	@FXML
	private TextField fx_age;
	@FXML
	private DatePicker fx_admission;
	@FXML
	private TextField fx_fees_paid;
	@FXML
	private TextField fx_total_fees;
	@FXML
	private ToggleGroup genderGroup;
//Address
	@FXML
	private TextField fx_mobile;
	@FXML
	private TextField fx_state;
	@FXML
	private TextField fx_street;
	@FXML
	private TextField fx_city;
//Data Transfer Object -- Customer
	private Customer customer = new Customer();
//Data Transfer Object -- Address
	private Address address = new Address();
//Data Transfer Object -- Payment
	private Payment payment = new Payment(); 
// create meal table instance for each user
	private CreateMealTable mealTable =  new CreateMealTable();
	@FXML
	public boolean validateFormData() {
		//validate personal info first
		boolean boo1 = validatePersonalInfo();
		if(boo1==false) {
			return false; //terminate now
		}
		//validate Address second
		boolean boo2 = validateAddress();
		if(boo2==false) {
			return false; // terminate now
		}
		//validate Payment third
		boolean boo3 = validatePayment();
		if(boo3==false) {
			return false; // terminate now
		}
		//confirming user first
		boolean isAddUser = ConfirmationBox.alertUSer("Do you want to procced?", "You're about to create new user.");
		if(isAddUser) {
			
			// making DAO, setting database data, if validation satisfies
			setCustomerDAO(); // Sequence matter here
			setAddressDAO(); // don't insert address(child) before customer table(Parent)
			setPaymentDAO(); // 3rd to insert, sequentially 
			
			// meal table for each
			mealTable.createTable(customer.getFname(), customer.getId()); 
			// clear inputs fields after submit
			clearPersonal_Information();
			clearAddress_Information();
			return true;
		}else {
			return false;
		}
		
		
	}
	
	private boolean validatePayment() {
		//setting id of Payment table which references Customer table
		payment.setId(customer.getId());
		
		//paid_amount
		try {
				if(fx_fees_paid.getText()==null || fx_fees_paid.getText()=="") {
					System.out.println();
					AlertBox.alertUser("Empty paid amount field.","Please enter paid amount.");
					return false;
				}else {
					int paid = Integer.parseInt(fx_fees_paid.getText());
					float fPaid = (float)paid;
					payment.setPaid(fPaid);
				}
		} catch(NumberFormatException e) {
			AlertBox.alertUser("Wrong inputs.", "Paid amount should be numeric");
			return false;
		}
		//total_amount
		try {
				if(fx_total_fees.getText()==null || fx_total_fees.getText()=="") {
					AlertBox.alertUser("Empty total amount field.","Please enter total amount.");
					return false;
				}else {
					int total = Integer.parseInt(fx_total_fees.getText());
					float fTotal = (float)total;
					payment.setTotal(fTotal);
				}
		}catch(NumberFormatException e) {
			AlertBox.alertUser("Wrong inputs.", "Total amount should be numeric");
			return false;
		}
			
		return true; //if everything is okay
	}
	private boolean validatePersonalInfo() {
		//setting ID
		Random random = new Random();
		int id = random.nextInt(25990);
		customer.setId(id);
				
		//admission date
		if(fx_admission.getValue()!=null){
				LocalDate localDate = fx_admission.getValue();
				customer.setAdmissionOn(localDate.toString());
		}else {
				AlertBox.alertUser("Please Select Date.", "Admission date is mandatory!");
				System.out.println("Please choose date");
				return false;
			}
		//first name
		if(fx_fname.getText()!=null && fx_fname.getText().length()>=3) {
			customer.setFname(fx_fname.getText().toUpperCase());
		}else {
			AlertBox.alertUser("Oops check first name.", "Fast name must be more than 3 characters!");
			System.out.println("First name should be more than 3 characters!");
			fx_fname.requestFocus();
			return false;
		}
		//last name
		if(fx_lname.getText()!=null && fx_lname.getText().length()>=3) {
			customer.setLname(fx_lname.getText().toUpperCase());
		}else {
			AlertBox.alertUser("Oops check last name.", "Last name must be more than 3 characters!");
			fx_lname.requestFocus();
			return false;
		}
				
		//gender
		RadioButton selectedRadioButton = (RadioButton) genderGroup.getSelectedToggle();
		if(selectedRadioButton==null) {
			AlertBox.alertUser("Please Select Gender.", "Click on radio button.");
			return false;
		}else {
			String togleGroupValue = selectedRadioButton.getText();
			customer.setGender(togleGroupValue.toUpperCase().trim());
		}
		// age
		try {	
			if(fx_age==null || fx_age.getText()==""){
				AlertBox.alertUser("Please Select Age.", "Age field can't be empty.");
				fx_age.requestFocus();
				return false;
				}
			int age = Integer.parseInt(fx_age.getText()); 
			customer.setAge(age); //if no exception
						
			} catch(NumberFormatException e) {
					AlertBox.alertUser("Wrong Age", "Enter numeric value only");
					fx_age.requestFocus();
					return false;
			}
		return true;
	}
	private boolean validateAddress() {
		//setting id of Address table which references Customer table
		address.setId(customer.getId());
		
		// state		
		if(fx_state.getText()==null || fx_state.getText()=="") {
			AlertBox.alertUser("Empty state field.", " Plase fill state field.");
			fx_state.requestFocus();
			return false;
		}else {
			address.setState(fx_state.getText().toUpperCase().trim());
		}
		// city
		if(fx_city.getText()==null || fx_city.getText()=="") {
			AlertBox.alertUser("Empty city field.", " Plase fill city field.");
			fx_city.requestFocus();
			return false;
		}else {
			address.setCity(fx_city.getText().toUpperCase().trim());
		}
		// street
		if(fx_street.getText()==null || fx_street.getText()=="") {
			address.setStreet("Not Available");
		}
		//mobile
		address.setStreet(fx_street.getText());
		String mobile = fx_mobile.getText();
		boolean isMobile = Pattern.matches("[0-9]{10}", mobile);
		
		if(fx_mobile.getText()==null ||fx_mobile.getText()=="") {
			AlertBox.alertUser("Plase enter contact. ", "A 10 digit number.");
			fx_mobile.requestFocus();
			return false;
		}else if(isMobile) {
			address.setMobile(fx_mobile.getText());
			return true;
		}else {
			AlertBox.alertUser("You entered:"+mobile, "Please enter valid 10 digit mobile number!");
			fx_mobile.requestFocus();
			return false;
		}
	}
	
	private void setAddressDAO() {
		AddressDAOImpl addressDAO = DAOFactory.getAddressDAO();
		try {
			int result = addressDAO.setAddress(address);
			if(result!=0) {
				System.out.println("Address Info Saved");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			System.out.println("Cant fix Address DAO SQL");
		}
	}
	private void setCustomerDAO() {
		CustomerDAOImpl customerDAO  = DAOFactory.getCustomerDAO();
		try {
			int result = customerDAO.saveCustomer(customer);
			if(result!=0) {
				System.out.println("Personal Info saved");
			}
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}		
		
	}
	private void setPaymentDAO() {
		PaymentDAOImpl paymentDAO = DAOFactory.getPaymentDAO();
		try {
			int result  = paymentDAO.addPayment(payment);
			if(result !=0) {
				System.out.println("Payment Info saved");
			}
		} catch(SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("Error in SQL while payment insertion.");
		}
		
	}
	 @FXML void clearPersonal_Information() {
		 fx_fname.setText(null);
		 fx_lname.setText(null);
		 fx_age.setText(null);
			 RadioButton radioButton = (RadioButton) genderGroup.getSelectedToggle();
			 if(radioButton!=null) {radioButton.setSelected(false);}
			 
	    }
	 @FXML void clearAddress_Information() {
		 fx_mobile.setText(null);
		 fx_city.setText(null);
		 fx_street.setText(null);
		 fx_state.setText(null);
	 }
}
