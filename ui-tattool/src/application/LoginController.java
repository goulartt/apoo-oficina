package application;

import com.jfoenix.controls.JFXDecorator;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginController
{

    @FXML
    void closeApp(ActionEvent event)
    {
    	Platform.exit();
    }
    
    @FXML
    void login(ActionEvent event)
    {	 
    	/*
    	 * 	##	Stage view login
    	 */
    	
		Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		
		try {
			
			/*
			 * 	##	New Stage dashboard
			 */
			
			Stage stage 			  = new Stage();	
			FXMLLoader templateLoader = new FXMLLoader(getClass().getResource("/views/template/template.fxml"));
			Scene scene 			  = new Scene(new JFXDecorator(stage, templateLoader.load()), 1032, 612);
		    FXMLLoader mainLoader 	  = new FXMLLoader(getClass().getResource("/views/home.fxml"));
		    
		    String style = getClass().getResource("application.css").toExternalForm();
			scene.getStylesheets().add(style);
			
		    mainLoader.setRoot(templateLoader.getNamespace().get("main"));
		    mainLoader.load();
		    
		    stage.setScene(scene);
		    primaryStage.hide();
		    stage.show();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }
}
