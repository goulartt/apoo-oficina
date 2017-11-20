package tattool.views.controller.service;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

public class ServiceController {

    @FXML
    void create(ActionEvent event) {
    	try {
    		FXMLLoader viewLoader = new FXMLLoader(getClass().getResource("/views/services/create-edit.fxml"));
    		BorderPane main       = (BorderPane) ((Node) event.getSource()).getScene().lookup("#main");
    		viewLoader.setRoot(main);
    		main.getChildren().clear();
    		viewLoader.load();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
}
