package Controllers;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.regex.Pattern;

import application.AlertBox;
import application.ConfirmationBox;
import application.ErrorBox;
import javafx.fxml.FXML;

import javafx.scene.control.TextField;

import javafx.scene.control.ToggleGroup;

import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;
import model.Address;
import model.AddressDAOImpl;
import model.Customer;
import model.CustomerDAOImpl;
import model.DAOFactory;
import model.Payment;
import model.PaymentDAOImpl;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;

public class updateUserController {
	@FXML
	private TextField fx_fname;
	@FXML
	private TextField fx_lname;
	@FXML
	private TextField fx_age;
	@FXML
	private ToggleGroup genderGroup;
	@FXML
	private TextField fx_user_id;
	@FXML
	private ImageView fx_search;
	@FXML
	private TextField fx_state;
	@FXML
	private TextField fx_city;
	@FXML
	private TextField fx_street;
	@FXML
	private TextField fx_mobile;
	@FXML
	private DatePicker fx_admission;
	@FXML
	private TextField fx_fees_paid;
	@FXML
	private TextField fx_total_fees;
	@FXML
	private RadioButton fx_male;
	@FXML
	private RadioButton fx_female;
	
	// DAO
	CustomerDAOImpl customerDAO = DAOFactory.getCustomerDAO();
	AddressDAOImpl addressDAO = DAOFactory.getAddressDAO();
	PaymentDAOImpl paymentDAO = DAOFactory.getPaymentDAO();
	// DTO
	Customer customer = new Customer();
	Address address =  new Address();
	Payment payment = new Payment();

	// Event Listener on ImageView[#fx_search].onMouseClicked
	@FXML
	public void checkUser(MouseEvent event) {
			String query = fx_user_id.getText();
			query = query.trim();
		if(Pattern.matches("[0-9]{2,}", query)) {
				int iQuery = Integer.parseInt(query);
				try {
					customer = customerDAO.getCustomerByID(iQuery);
					if(customer == null) {
						AlertBox.alertUser("OOPS NO SUCH USER !", "Check \"See All\" tab for ID.");
						
					}else { //user exits, fetch other DAO
						payment = paymentDAO.getPayement(customer.getId());
						address = addressDAO.getAddress(customer.getId());
						reflectDetails();
					}
				} catch (SQLException e) {
					ErrorBox.alertUser("Something went wrong!", e.getMessage());
					e.printStackTrace();
				}
			}else {
		AlertBox.alertUser("Enter Unique Numeric ID.", "If you dont know, look in See All tab.");
		}
	}
	private void reflectDetails() {
		fx_fname.requestFocus();
		fx_fname.setText(customer.getFname().trim());
		fx_lname.setText(customer.getLname().trim());
		fx_age.setText(Integer.toString(customer.getAge()).trim( ));
		fx_state.setText(address.getState().trim());
		fx_city.setText(address.getCity().trim());
		fx_street.setText(address.getStreet().trim());
		fx_mobile.setText(address.getMobile().trim());
		
		//date 
		String date  = customer.getAdmissionOn();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
		LocalDate localDate = LocalDate.parse(date, formatter);
		fx_admission.setValue(localDate);
		
		//payment
		String paid = Float.toString(payment.getPaid());
		paid.replace(".", "5");
		fx_fees_paid.setText(paid);
		fx_total_fees.setText(Float.toString(payment.getTotal()));
	
		//gender
		if(customer.getGender().trim().toUpperCase().equals("MALE"))
			fx_male.setSelected(true);
		else
			fx_female.setSelected(true);
			
		
	}
	// Event Listener on Button.onMouseClicked
	@FXML
	public void updateUser(MouseEvent event) {
		
		// make sure user has fetched details before updating
		if(customer.getId() == 0) {
			AlertBox.alertUser("Ohh no!", "You\'re updating details without searching the user above first.");
			return ;
		}
		
		boolean isValid = validateDetails();
		
		if(isValid) {
			boolean isSure = ConfirmationBox.alertUSer("You are about to update details!","Are you sure?");
			if(!isSure) {
				return;
			}
			//set customer DAO
			customer.setFname(fx_fname.getText().trim());
			customer.setLname(fx_lname.getText().trim());
			customer.setAge(Integer.parseInt(fx_age.getText().trim()));
			RadioButton selectedButton = (RadioButton) genderGroup.getSelectedToggle(); 
			customer.setGender(selectedButton.getText().toUpperCase().trim());
			LocalDate localDate = fx_admission.getValue();
			customer.setAdmissionOn(localDate.toString());
			//set address DAO
			address.setState(fx_state.getText().toUpperCase().trim());
			address.setCity(fx_city.getText().toUpperCase().trim());
			address.setMobile(fx_mobile.getText().trim());
			address.setStreet(fx_street.getText().trim());
			//set payment DAO
			payment.setPaid(Float.parseFloat(fx_fees_paid.getText().trim()));
			payment.setTotal(Float.parseFloat(fx_total_fees.getText().trim()));
			try {
				 customerDAO.updateCustomer(customer);
			} catch (SQLException e) {
				ErrorBox.alertUser("Can not update customer details.", e.getMessage());
			}
			try {
				addressDAO.updateAddress(address);
			} catch (SQLException e) {
				ErrorBox.alertUser("Can not update address details.", e.getMessage());
				e.printStackTrace();
			}
			try {
				paymentDAO.updatePayment(payment);
			} catch (SQLException e) {
				ErrorBox.alertUser("Can not update payment details.", e.getMessage());
			}
			
			AlertBox.alertUser("Details updated successfuly of.",customer.getFname()+" "+customer.getLname());
			
		}
	}
	private boolean validateDetails() {
		String fname = fx_fname.getText().trim();
		String lname = fx_lname.getText().trim();
		String age =  fx_age.getText().trim();
		String state  = fx_state.getText().trim();
		String city  = fx_city.getText().trim();
		String street  = fx_street.getText().trim();
		String mobile  = fx_mobile.getText().trim();
		String paid =  fx_fees_paid.getText().trim();
		String total =  fx_total_fees.getText().trim();
		
		
		
		if(Pattern.matches("[A-Za-z]{3,10}", fname) ) {
		}else {
			AlertBox.alertUser("First name field", "Character inputs of range 3-10 are allowed only.");
			return false;
		}
		if(Pattern.matches("[A-Za-z]{3,10}", lname) ) {
		}else {
			AlertBox.alertUser("Last name field", "Character inputs of range 3-10 are allowed only. ");
			return false;
		}
		if(Pattern.matches("[0-9]{2,2}", age) ) {
		}else {
			AlertBox.alertUser("Age field", "Can not be empty and\nneither exceed 99.");
			return false;
		}
		if(Pattern.matches("[A-Za-z]{3,12}", state) ) {
		}else {
			AlertBox.alertUser("State field", "Character inputs of range 3-12 are allowed only.");
			return false;
		}
		if(Pattern.matches("[A-Za-z]{3,12}", city) ) {
		}else {
			AlertBox.alertUser("City field", "Character inputs of range 3-12 are allowed only.");
			return false;
		}
		if(Pattern.matches("[A-Za-z_ ]{3,20}", street) ) {
		}else {
			AlertBox.alertUser("Street field", "Character inputs of range 3-20 are allowed only.");
			return false;
		}
		if(Pattern.matches("[0-9]{10,10}", mobile) ) {
		}else {
			AlertBox.alertUser("Mobile field", "Numeric inputs of range 10 are allowed only.");
			return false;
		}
		
//		if(Pattern.matches("[0-9_.-]{3,5}", paid) ) {
//		}else {
//			AlertBox.alertUser("Paid amount", "can not be empty.\nmust be of range 100-9,999");
//			return false;
//		}
//		if(Pattern.matches("[0-9_.-]{3,5}", total) ) {
//		}else {
//			AlertBox.alertUser("Total amount", "can not be empty.\nmust be of range 100-9,999");
//			return false;
//		}
		return true;
		
	}
	// Event Listener on Button.onMouseClicked
	@FXML
	public void deleteUser(MouseEvent event) {
			try {
				if(customer.getId()!=0) {
					customerDAO.deleteCustomer(customer);
				}
			} catch (SQLException e) {
				ErrorBox.alertUser("Cant delete user!", e.getMessage());
				e.printStackTrace();
			}
	}
}
