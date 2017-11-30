package application;

import com.jfoenix.controls.JFXDecorator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application
{
	@Override
	public void start(Stage primaryStage)
	{
		
		try
		{
			Parent root = (Parent) FXMLLoader.load(getClass().getResource("/views/login.fxml"));
	 
			Scene scene = new Scene(new JFXDecorator(primaryStage, root), 1280, 750);
			primaryStage.setTitle("TATTOOL - Software de Gerenciamento de Estúdios de Tatuagens");
			scene.getStylesheets().add("/css/application.css");
		    primaryStage.setScene(scene);
		    primaryStage.setMinHeight(750);
		    primaryStage.setMinWidth(1280);
		    primaryStage.show();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}
}
