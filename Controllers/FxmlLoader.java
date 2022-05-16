package Controllers;

import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public final class FxmlLoader {
	private AnchorPane anchorPane;
	
	public AnchorPane getFXML(String fileName) {
		try {
			URL urlPath = FxmlLoader.class.getResource("/views/"+fileName+".fxml");
			if(urlPath == null) {
				throw new java.io.FileNotFoundException("**FXML not found**");
			}
			anchorPane = FXMLLoader.load(urlPath);
		} catch(Exception e) {
			e.getMessage();
		}
		return anchorPane;
	}
	
}
