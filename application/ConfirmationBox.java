/**
 * 
 */
package application;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/**
 * @author yogesh kakde
 *
 */
public class ConfirmationBox {
	public static boolean alertUSer(String alertH,String alertContent) {
		Alert alert =  new Alert(AlertType.CONFIRMATION);
		alert.setHeaderText(alertH);
		alert.setContentText(alertContent);
		Optional<ButtonType> result = alert.showAndWait();
		if(result.isPresent() && result.get() == ButtonType.OK) {
			return true;
		}else {
			return false;
		}
	}
	
}
