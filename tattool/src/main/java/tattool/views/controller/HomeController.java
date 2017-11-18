package tattool.views.controller;

import java.net.URL;
import java.text.DateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class HomeController  implements Initializable{
	
	@FXML Label lblData = new Label();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lblData.setText("Hoje é dia " + pegaData() + ".");
	}
	
	@FXML
    void createCustomer(ActionEvent event) {
    	try {
		    FXMLLoader viewLoader = new FXMLLoader(getClass().getResource("/views/customers/create-edit.fxml"));
		    BorderPane main       = (BorderPane) ((Node) event.getSource()).getScene().lookup("#main");
		    
		    viewLoader.setRoot(main);
		    main.getChildren().clear();
		    viewLoader.load();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void createService(ActionEvent event) {
    	//
    }
	
	private String pegaData() {
		java.util.Date d = new Date();
		return java.text.DateFormat.getDateInstance(DateFormat.LONG).format(d);
	}

}
