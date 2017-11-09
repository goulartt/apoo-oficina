package application;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tattool.domain.controller.LoginController;
import tattool.rest.consume.ArtRest;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		ArtRest rest = new ArtRest();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/telas/Login.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			LoginController control = (LoginController) loader.getController();
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			
			primaryStage.getIcons().add(SwingFXUtils.toFXImage(rest.findImg("1509581158_tattool.png"), null));
			primaryStage.setTitle("TATTOOL - Software de Gerenciamento de Estúdios de Tatuagens");
			
			primaryStage.show();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);

	}
}
