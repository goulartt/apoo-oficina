package application;

import com.jfoenix.controls.JFXDecorator;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class LoginController {

    @FXML
    void closeApp(ActionEvent event) {
    	Platform.exit();
    }

    @FXML
    void login(ActionEvent event) {
    	
    }

}
