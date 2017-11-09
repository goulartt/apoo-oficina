package application;

import javafx.fxml.FXML;

import javafx.scene.layout.VBox;

import com.jfoenix.effects.JFXDepthManager;

public class HomeController {

    @FXML
    private VBox block;
    
    public void initialize()
    {
    	JFXDepthManager.setDepth(block, 1);
    }

}
