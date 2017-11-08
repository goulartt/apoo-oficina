package application;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class WindowController {

    @FXML
    private BorderPane main;

    @FXML
    void closeApp(MouseEvent event)
    {
    	Platform.exit();
    }

    @FXML
    void maximize(MouseEvent event)
    {

    }

    @FXML
    void minimize(MouseEvent event)
    {

    }

}