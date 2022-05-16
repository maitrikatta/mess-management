package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Customer;
import model.CustomerDAOImpl;
import model.DAOFactory;

public class SeeAllCustomersController implements Initializable {

	@FXML
    private TableColumn<Customer, String> fx_admission_on;

    @FXML 
    private TableColumn<Customer, Integer> fx_age;

    @FXML
    private TableColumn<Customer, String> fx_first_name;

    @FXML
    private TableColumn<Customer, Integer> fx_gender;

    @FXML
    private TableColumn<Customer, Integer> fx_id;

    @FXML
    private TableColumn<Customer, String> fx_last_name;

    @FXML
    private TableView<Customer> my_table;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		fetchCustomers();
	}

	private void fetchCustomers() {
		CustomerDAOImpl customerDAO =  DAOFactory.getCustomerDAO();
		ArrayList<Customer> customerList = new ArrayList<>();
		
		try {
			customerList = customerDAO.getAllCustomers();
			ObservableList<Customer> tableList  = FXCollections.observableArrayList(customerList); 
				fx_first_name.setCellValueFactory(new PropertyValueFactory<Customer,String>("fname"));
				fx_id.setCellValueFactory(new PropertyValueFactory<Customer,Integer>("id"));
				fx_last_name.setCellValueFactory(new PropertyValueFactory<Customer,String>("lname"));
				fx_age.setCellValueFactory(new PropertyValueFactory<Customer,Integer>("age"));
				fx_gender.setCellValueFactory(new PropertyValueFactory<Customer,Integer>("gender"));
				fx_admission_on.setCellValueFactory(new PropertyValueFactory<Customer,String>("admissionOn"));
				my_table.setItems(tableList);
				
		} catch (SQLException e) {
			System.out.println("ERR MSG: "+e.getMessage());
//			e.printStackTrace();
		}
		
	}
    
    
	
}
