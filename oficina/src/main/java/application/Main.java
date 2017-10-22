package application;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import tattool.domain.controller.LoginController;
import tattool.domain.controller.UsuarioController;
import tattool.rest.consume.UserRest;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/telas/Login.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			LoginController control = (LoginController) loader.getController();
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			/*primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/telas/barber.png")));*/
			primaryStage.setTitle("Login");
			
			primaryStage.show();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);

	}
}
