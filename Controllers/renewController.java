package Controllers;

import java.sql.SQLException;
import java.util.regex.Pattern;

import application.AlertBox;
import application.ConfirmationBox;
import application.ErrorBox;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.CreateMealTable;
import model.Customer;
import model.CustomerDAOImpl;
import model.DAOFactory;

public class renewController {
	@FXML
	 private Label fx_first_name;

	@FXML
	 private Label fx_id;

	@FXML
	 private Label fx_last_name;
	 
	@FXML
	private TextField fx_search_field;
	
	@FXML
	private ImageView fx_renew_btn;

	// Event Listener on ImageView.onMouseClicked
	@FXML
	CustomerDAOImpl customerDAO = DAOFactory.getCustomerDAO(); //DAO
	Customer customer = new Customer(); //DTO
	
	
	public void searchCustomer(MouseEvent event) {		
		String query = fx_search_field.getText();
		query = query.trim();
		if(Pattern.matches("[0-9]{2,}", query)) {
			int iQuery = Integer.parseInt(query);
			try {
				customer = customerDAO.getCustomerByID(iQuery);
				if(customer == null) {
					AlertBox.alertUser("OOPS NO SUCH USER !", "Check \"See All\" tab for ID.");
					fx_renew_btn.setVisible(false);
				}else {
					fx_id.setText(Integer.toString(customer.getId()));
					fx_first_name.setText(customer.getFname().trim());
					fx_last_name.setText(customer.getLname().trim());
					fx_renew_btn.setVisible(true);
				}
			} catch (SQLException e) {
				System.out.println("ERR MSG: "+e.getMessage());
				ErrorBox.alertUser("Something went wrong!", e.getMessage());
				fx_renew_btn.setVisible(false);
			}
		}else {
			AlertBox.alertUser("Enter Unique Numeric ID.", "If you dont know, look in See All tab.");
			fx_renew_btn.setVisible(false);
		}
	}
	
	
	@FXML
    void handleRenewButton(MouseEvent event) {
	 boolean isConf =	ConfirmationBox.alertUSer("About to renew this account.","Are you sure? This action can't be undone!");
	 if(isConf) {
		int id = customer.getId();
		String fname = customer.getFname().trim();
		boolean isRenew = CreateMealTable.renewMeal(fname, id);
		if(isRenew) {
			AlertBox.alertUser("Successful!", customer.getId()+" Account renewed.");
		}
	 }else {
		 System.out.println("cancle");
	 }
    }
}

