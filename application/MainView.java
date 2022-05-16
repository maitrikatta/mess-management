package application;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainView {
	private Stage stage;
	private String style = "/stylesheets/main-view-style.css";
	private String view = "/views/MainView.fxml";
	public MainView(Stage stage) {
		this.stage = stage;
		buildStage();
	}

	private void buildStage() {
		try {
		Parent root =  FXMLLoader.load(getClass().getResource(view));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource(style).toExternalForm());
		customizeProperties();
		stage.setScene(scene);
		stage.show();		
		} catch(IOException e) {
			e.setStackTrace(null);
		}
	}

	private void customizeProperties() {
		stage.setTitle("Dev. Yogesh Kakde");
		stage.setMaximized(true);
		stage.setMinHeight(520);
		stage.setMinWidth(520);
		Image icon = new Image("/assets/food.png");
		stage.getIcons().add(icon);
		
	}

}
