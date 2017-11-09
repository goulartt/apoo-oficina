package application;

import javafx.fxml.FXML;

import javafx.scene.layout.BorderPane;

import com.jfoenix.effects.JFXDepthManager;

public class DashboardController {

    @FXML
    private BorderPane header;

    public void initialize()
    {
    	JFXDepthManager.setDepth(header, 1);
    }
    
}
