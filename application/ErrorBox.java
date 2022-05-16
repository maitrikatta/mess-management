/**
 * 
 */
package application;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;

/**
 * @author yogesh kakde
 *
 */
public class ErrorBox {
	public static void alertUser(String alertH,String alertContent)  {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error...");
		alert.setContentText(alertContent);
		AlertBox ab =  new AlertBox();
		ImageView icon = new ImageView(ab.getClass().getResource("/assets/unhappy.png").toString());
		icon.setFitWidth(50);
		icon.setFitHeight(50);
	    alert.setGraphic(icon);
		alert.setHeaderText(alertH);
		alert.showAndWait();
}
}
