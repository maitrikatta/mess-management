package Controllers;

import javafx.fxml.FXML;

import javafx.scene.layout.HBox;
import model.Customer;
import model.DAOFactory;
import model.DBConnect;
import model.Meal;
import model.MealDAOImpl;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import application.AlertBox;
import application.ErrorBox;
import javafx.scene.control.Label;

import javafx.scene.input.MouseEvent;

import javafx.scene.control.CheckBox;

import javafx.scene.control.DatePicker;

public class AttendanceController {
	@FXML
	private TextField fx_search_field;
	@FXML
	private HBox fx_query_result_view;
	@FXML
	private Label fx_first_name;
	@FXML
	private Label fx_last_name;
	@FXML
	private Label fx_gender;
	@FXML
	private Label fx_age;
	@FXML
	private Label fx_admission_date;
	@FXML
	private DatePicker fx_meal_date;
	@FXML
	private CheckBox fx_lunch;
	@FXML
	private CheckBox fx_dinner;
	@FXML
	private Label fx_total_meal_lunch;
	@FXML
	private Label fx_total_meal_dinner;
	@FXML
	private Label fx_expires_in;

	// customer - DTO
	Customer customer = new Customer();
	// meal -DAO
	MealDAOImpl mealDAO = DAOFactory.getMealDAO();
	// meal - DTO
	Meal myMeal = new Meal();

	// Event Listener when user searches new customer
	@FXML
	public void searchBtnClicked(MouseEvent event) {
	
		String query = fx_search_field.getText();
		boolean res = validateSearchQuery(query);
		if (res) {
			boolean fetchResult = fetchCustomer(query.trim()); // it also sets meal object
			if (fetchResult) {
				paintCustomer(); // display the users details
				aboutAccount();
				fx_query_result_view.setVisible(true); // make result visible.
				fx_meal_date.setValue(LocalDate.of(1997, 1, 1));
				fx_meal_date.setValue(LocalDate.now());
			}
		}
	}

	private void aboutAccount() {
		String tableName = myMeal.getCustomerName().trim() + Integer.toString(myMeal.getCustomerID()).trim();
		String FETCH_ACCOUNT = "SELECT SUM(LUNCH) AS LUNCH, SUM(DINNER) AS DINNER FROM "+tableName;
		Connection conn;
		try {
			conn = DBConnect.makeConnection();
			PreparedStatement ps = conn.prepareStatement(FETCH_ACCOUNT);
			ResultSet resultSet = ps.executeQuery();
			if(resultSet.next())
			{
				String lunch = resultSet.getString("LUNCH");
				String dinner =resultSet.getString("DINNER");
				
				// fresh table doesn't have single meal record.
				if(lunch!=null && dinner!=null) {
					fx_total_meal_lunch.setText(lunch+" Lunch");
					fx_total_meal_dinner.setText(dinner+" Dinner");
					//days left
					double iLunch = Double.parseDouble(lunch);
					double iDinner = Double.parseDouble(dinner);
					Double daysLeft = (iLunch+iDinner)/2;
					daysLeft = (double) Math.round(daysLeft);
					daysLeft = 30 - daysLeft;
					fx_expires_in.setText(Double.toString(daysLeft)+" Days");
				}else {
					fx_total_meal_lunch.setText(0+" Lunch");
					fx_total_meal_dinner.setText(0+" Dinner");
					fx_expires_in.setText(30+" Days");
				}
				
			}else {
				ErrorBox.alertUser("can\'t fetch about account details.","DATABASE:FETCH ACCOUNT ON MEAL TABLE.");
			}
			conn.close();
			ps.close();
			
		} catch (SQLException e) {
			System.out.println("ABOUT MEAL >> ");
			System.out.println("ERR MSG: "+e.getMessage());
//			e.printStackTrace();
		}
		
	}

	private void paintCustomer() {
		fx_first_name.setText(customer.getFname());
		fx_last_name.setText(customer.getLname());
		fx_age.setText(Integer.toString(customer.getAge()));
		fx_gender.setText(customer.getGender());
		fx_admission_date.setText(customer.getAdmissionOn());
	}

	private boolean fetchCustomer(String query) {
		StringTokenizer tokens = new StringTokenizer(query, " ");
		String fName = tokens.nextToken();
		String lName = tokens.nextToken();
		fName = fName.toUpperCase();
		lName = lName.toUpperCase();

		String FETCH_CUSTOMER = "SELECT CUST_ID,ADMISSION_ON,AGE,GENDER,FIRST_NAME,LAST_NAME FROM CUSTOMERS WHERE FIRST_NAME LIKE ? AND LAST_NAME LIKE ?";
		try {
			Connection conn = DBConnect.makeConnection();
			PreparedStatement ps = conn.prepareStatement(FETCH_CUSTOMER);
			ps.setString(1, "%" + fName + "%");
			ps.setString(2, "%" + lName + "%");
			ResultSet resultSet = ps.executeQuery();
			if (resultSet.next()) {
				customer.setFname(resultSet.getString("FIRST_NAME"));
				customer.setLname(resultSet.getString("LAST_NAME"));
				customer.setGender(resultSet.getString("GENDER"));
				customer.setAge(resultSet.getInt("AGE"));
				customer.setId(resultSet.getInt("CUST_ID"));
				customer.setAdmissionOn(resultSet.getString("ADMISSION_ON"));

				// set meal object meta data also
				myMeal.setCustomerID(resultSet.getInt("CUST_ID"));
				myMeal.setCustomerName(resultSet.getString("FIRST_NAME"));
				return true;
			} else {
				AlertBox.alertUser("No such user found","You entered: "+fName+" "+" "+lName);
				return false;
			}
		} catch (SQLException e) {
			ErrorBox.alertUser("CUSTOMER FETCH ERR",e.getMessage());
//			e.printStackTrace();
			return false;
		}

	}

	private boolean validateSearchQuery(String query) {
		query = query.trim();
		if (query == null || query == "") {
			AlertBox.alertUser("Please enter first and last name.", "if you dont know, look in \'See All\' tab");
			return false;
		} else {
			try {
				StringTokenizer tokens = new StringTokenizer(query, " ");
				tokens.nextToken(); // no use here
				tokens.nextToken(); // making to throw exception, if no space separated inputs

				if (tokens.hasMoreTokens() == true) {
					AlertBox.alertUser("Entered three words.", "please enter only first and last name");
					return false;
				}
			} catch (NoSuchElementException e) {
				AlertBox.alertUser("Entered single word.", "please enter first and also last name");
//				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	// Event Listener on DatePicker[#fx_meal_date].onAction
	@FXML
	public void dateSelected() {
		Meal dateMeal = new Meal();
		
		// set date here only
		myMeal.setDate(fx_meal_date.getValue().toString());

		// reset check box on every new date selection
		fx_dinner.setSelected(false);
		fx_lunch.setSelected(false);
		fx_dinner.setDisable(false);
		fx_lunch.setDisable(false);

		// check if row already exists if so then catch new details
		dateMeal = checkExistingMealRecord();
	
		if (dateMeal != null) {
//			System.out.println("Meal record already Exists.");
			if (dateMeal.getLunch() == 1 && dateMeal.getDinner() == 1) {
				fx_dinner.setSelected(true);
				fx_dinner.setDisable(true);
				fx_lunch.setDisable(true);
				fx_lunch.setSelected(true);
			}
			if (dateMeal.getLunch() == 1) {
				fx_lunch.setDisable(true);
				fx_lunch.setSelected(true);
			}
			if (dateMeal.getDinner() == 1) {
				fx_dinner.setSelected(true);
				fx_dinner.setDisable(true);
			}

		} else {
//			System.out.println("Meal doesnt Exists.");
		}

	}

	// Event Listener on Button.onAction
	@FXML
	public void submitAttendance() {
		boolean mealval = validateMealSnippet(); // validate and set myMeal
		Meal submitMeal = new Meal();
		if (mealval) {
			submitMeal = checkExistingMealRecord(); // this returns either null or object with only lunch and dinner
			// update record
			if (submitMeal != null) {
				// set name,id and day of myMeal super object
				submitMeal.setCustomerName(myMeal.getCustomerName());
				submitMeal.setCustomerID(myMeal.getCustomerID());
				submitMeal.setDate(myMeal.getDate());

				// MATCH THE DATABASE RESULTS WITH NEW INPUTS
				if (submitMeal.getLunch() != myMeal.getLunch()) {
					submitMeal.setLunch(myMeal.getLunch());
				}
				if (submitMeal.getDinner() != myMeal.getDinner()) {
					submitMeal.setDinner(myMeal.getDinner());
				}
				try {
					mealDAO.updateMeal(submitMeal);
					// reset my meal on every update
					myMeal.setLunch(0);
					myMeal.setDinner(0);
					// hide result result on update
					fx_query_result_view.setVisible(false);
				} catch (SQLException e) {
					System.out.print("Cant update meal >>");
					System.out.print("ERR MSG:" + e.getMessage());

				}
			} else {
				// insert new meal record
				try {
					boolean addNewMealResult = mealDAO.addNewMeal(myMeal);
					if (addNewMealResult) {
						// reset myMeal on every insertion
						myMeal.setLunch(0);
						myMeal.setDinner(0);
						// hide result result on insertion
						fx_query_result_view.setVisible(false);
					} else {
						System.out.println("cant add new meal.");
					}
				} catch (SQLException e) {
					System.out.print("Can't add new meal >> ");
					System.out.println("ERR MSG:" + e.getMessage());
					// e.printStackTrace();
				}
			}
		} else {
		}
	}

	// this method will check if the meal already exists or not and return fresh
	// object if exists.
	private Meal checkExistingMealRecord() {
		Meal checkMeal = new Meal();
		try {
			checkMeal = mealDAO.getMeal(myMeal);
			return checkMeal;
		} catch (SQLException e) {
			System.out.print("CANT FETCH MEAL >> ");
			System.out.println("ERR MSG:" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	private boolean validateMealSnippet() {
		if(fx_meal_date.getValue()!=null) {
			myMeal.setDate(fx_meal_date.getValue().toString());
		}else {
			AlertBox.alertUser("Please select date first", "");
			return false;
		}
		if (!fx_dinner.isSelected() && !fx_lunch.isSelected()) {
			AlertBox.alertUser("Select at least one meal type.", "Dinner or Lunch");
			return false;
		}
		
		if (fx_dinner.isSelected() && fx_lunch.isSelected()) {
			myMeal.setDinner(1);
			myMeal.setLunch(1);
			return true;
		}
		
		if (fx_dinner.isSelected()) {
			myMeal.setDinner(1);
			return true;
		}
		if (fx_lunch.isSelected()) {
			myMeal.setLunch(1);
			return true;
		}
		return false;
	}

}
