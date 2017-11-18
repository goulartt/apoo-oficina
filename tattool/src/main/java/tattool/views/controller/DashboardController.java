package tattool.views.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.effects.JFXDepthManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DashboardController 
{
	@FXML
    private VBox nav;
	
	@FXML
	private BorderPane main;
	
    @FXML
    private BorderPane header;
    
    @FXML
    public JFXButton btnUsers = new JFXButton();
    
    public void initialize()
    {
    	depthManager();
    }
    
    @FXML
    void home(ActionEvent event)
    {
    	try
    	{
		    FXMLLoader viewLoader = new FXMLLoader(getClass().getResource("/views/home.fxml"));
		    
		    viewLoader.setRoot(main);
		    main.getChildren().clear();
		    viewLoader.load();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }
    
    @FXML
    void customers(ActionEvent event)
    {
		try
		{
		    FXMLLoader viewLoader = new FXMLLoader(getClass().getResource("/views/customers/customers.fxml"));
		    
		    viewLoader.setRoot(main);
		    main.getChildren().clear();
		    viewLoader.load();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }
    
    @FXML
    void services(ActionEvent event)
    {
    	try
    	{
		    FXMLLoader viewLoader = new FXMLLoader(getClass().getResource("/views/services/services.fxml"));
		    
		    viewLoader.setRoot(main);
		    main.getChildren().clear();
		    viewLoader.load();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }

    @FXML
    void sessions(ActionEvent event)
    {
    	try
    	{
		    FXMLLoader viewLoader = new FXMLLoader(getClass().getResource("/views/sessions/sessions.fxml"));
		    
		    viewLoader.setRoot(main);
		    main.getChildren().clear();
		    viewLoader.load();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }

    @FXML
    void gallery(ActionEvent event)
    {
    	try
    	{
		    FXMLLoader viewLoader = new FXMLLoader(getClass().getResource("/views/gallery/gallery.fxml"));
		    
		    viewLoader.setRoot(main);
		    main.getChildren().clear();
		    viewLoader.load();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }
    
    @FXML
    void cashier(ActionEvent event)
    {
    	try
    	{
		    FXMLLoader viewLoader = new FXMLLoader(getClass().getResource("/views/cashier/cashier.fxml"));
		    
		    viewLoader.setRoot(main);
		    main.getChildren().clear();
		    viewLoader.load();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }

    @FXML
    void users(ActionEvent event)
    {
    	try
    	{
		    FXMLLoader viewLoader = new FXMLLoader(getClass().getResource("/views/users/users.fxml"));
		    
		    viewLoader.setRoot(main);
		    main.getChildren().clear();
		    viewLoader.load();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }
    
    @FXML
    void profile(ActionEvent event)
    {
    	try
    	{
		    FXMLLoader viewLoader = new FXMLLoader(getClass().getResource("/views/users/profile.fxml"));
		    
		    viewLoader.setRoot(main);
		    main.getChildren().clear();
		    viewLoader.load();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }
    
    @FXML
    void settings(ActionEvent event)
    {
    	try
    	{
		    FXMLLoader viewLoader = new FXMLLoader(getClass().getResource("/views/settings.fxml"));
		    
		    viewLoader.setRoot(main);
		    main.getChildren().clear();
		    viewLoader.load();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }
    
    @FXML
    void help(ActionEvent event)
    {
    	try
    	{
		    FXMLLoader viewLoader = new FXMLLoader(getClass().getResource("/views/help.fxml"));
		    
		    viewLoader.setRoot(main);
		    main.getChildren().clear();
		    viewLoader.load();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }
    
    @FXML
    void logout(ActionEvent event)
    {
    	//Stage view dashboard
    	
		Stage primaryStage = (Stage) main.getScene().getWindow();
		
		try
		{
			//New Stage login
			
			Stage stage 			  = new Stage();	
			FXMLLoader templateLoader = new FXMLLoader(getClass().getResource("/views/login.fxml"));
			Scene scene 			  = new Scene(new JFXDecorator(stage, templateLoader.load()), 1032, 612);
			
			scene.getStylesheets().add("/css/application.css");
		    
		    stage.setScene(scene);
		    primaryStage.hide();
		    stage.setMinHeight(612);
		    stage.setMinWidth(1032);
		    stage.show();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }
    
    /*
     * 	## SOMBRAS
     */
    
    void depthManager()
    {
    	JFXDepthManager.setDepth(header, 1);
    	JFXDepthManager.setDepth(nav, 1);
    }
}
