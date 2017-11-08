package application;
	
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application
{
	@Override
	public void start(Stage primaryStage)
	{
		try
		{
			FXMLLoader template = new FXMLLoader(getClass().getResource("/view/template/template.fxml"));

		    Scene scene = new Scene(template.load());

		    URL content = getClass().getResource("/view/home.fxml");

		    FXMLLoader main = new FXMLLoader(content);
		    
		    main.setRoot(template.getNamespace().get("main"));

		    main.load();

		    primaryStage.setScene(scene);
		    primaryStage.initStyle(StageStyle.UNDECORATED);
		    primaryStage.show();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}
}
