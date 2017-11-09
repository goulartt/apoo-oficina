package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import com.jfoenix.controls.JFXDecorator;


public class Main extends Application
{
	@Override
	public void start(Stage primaryStage)
	{
		try
		{
			FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/view/home.fxml"));
			
			JFXDecorator decorator = new JFXDecorator(primaryStage, loginLoader.load());
			
			decorator.setCustomMaximize(true);
			
			Scene scene = new Scene(decorator, 1024, 576);

		    primaryStage.setScene(scene);
		    
		    primaryStage.show();
			
			/*FXMLLoader templateLoader = new FXMLLoader(getClass().getResource("/view/template/template.fxml"));
			
			JFXDecorator decorator = new JFXDecorator(primaryStage, templateLoader.load());
			
			decorator.setCustomMaximize(false);
			
			Scene scene = new Scene(decorator, 1024, 576);

		    FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/view/home.fxml"));
		    
		    mainLoader.setRoot(templateLoader.getNamespace().get("main"));

		    mainLoader.load();

		    primaryStage.setScene(scene);
		    
		    primaryStage.show();*/
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
