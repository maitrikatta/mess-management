package Controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import model.Customer;
import model.CustomerDAOImpl;
import model.DAOFactory;
import model.DBConnect;

public class MainViewController {
	@FXML private AnchorPane mainView;
	@FXML private BorderPane borderPane;
	
	
	private AnchorPane root;

	// Event Listener on HBox.onKeyPressed
	@FXML
	public void addNewMenu()  {
		loadView();
	}	
	@FXML
	public void addAttendance() {
		loadAttendance();
	}
	@FXML 
	public void loadSeeAllMenu() {
		String fileName = "SeeAllCustomers";
		FxmlLoader loader = new FxmlLoader();
		root = loader.getFXML(fileName);		
		if(root == null) {
			System.out.println("Cant get SeeAllCustomers fxml, null");
		}
		BorderPane.setAlignment(root, Pos.CENTER);
		borderPane.setCenter(root);
	}
	@FXML
	public void loadRenew() {
		String fileName = "renew";
		FxmlLoader loader = new FxmlLoader();
		root = loader.getFXML(fileName);		
		if(root == null) {
			System.out.println("Cant get renew fxml, null");
		}
		BorderPane.setAlignment(root, Pos.CENTER);
		borderPane.setCenter(root);
	}
	@FXML
	public void updateUser() {
		String fileName = "updateUser";
		FxmlLoader loader = new FxmlLoader();
		root = loader.getFXML(fileName);		
		if(root == null) {
			System.out.println("Cant get update fxml, null");
		}
		BorderPane.setAlignment(root, Pos.CENTER);
		borderPane.setCenter(root);
	}
	
	private void loadAttendance() {
		String fileName = "Attendance";
		FxmlLoader loader = new FxmlLoader();
		root = loader.getFXML(fileName);
		if(root == null) {
			System.out.println("Cant get Attendace fxml, null");
		}
		BorderPane.setAlignment(root, Pos.TOP_LEFT);
		borderPane.setCenter(root);
	}
	private void loadView() {		
		String fileName = "AddNewUser";
		FxmlLoader loader =  new FxmlLoader();
		root =  loader.getFXML(fileName);
		if(root ==null) {
			System.out.println("Cant get AddNewUSer fxml, null");
		}
		BorderPane.setAlignment(root, Pos.TOP_LEFT); // locates UI to left, center is default
		borderPane.setCenter(root);
	}
	
}
