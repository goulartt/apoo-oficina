package tattool.views.controller.art;

import java.io.File;

import com.jfoenix.controls.JFXTextField;

import de.jensd.fx.glyphs.octicons.OctIcon;
import de.jensd.fx.glyphs.octicons.OctIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

public class CreateArtController {

    @FXML
    private JFXTextField fileName;

    @FXML
    private JFXTextField tagInput;

    @FXML
    private HBox tags;
    
    private File imageFile;
    
    /*
     * 	##	CADASTRA ARTE NOVA
     */
    
    void store() {
    	// REST.SAVE
    }
    
    /*
     * 	##	VOLTA
     */

    void back(BorderPane main) {
    	try {
    		FXMLLoader viewLoader = new FXMLLoader(getClass().getResource("/views/gallery/gallery.fxml"));
    		
    		viewLoader.setRoot(main);
    		main.getChildren().clear();
    		viewLoader.load();
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
    
    @FXML
    void back(ActionEvent event) {
    	back((BorderPane) ((Node) event.getSource()).getScene().lookup("#main"));
    }

    @FXML
    void fileChooser(ActionEvent event) {
    	FileChooser chooser = new FileChooser();
    	
    	chooser.setTitle("Selecionar imagem");
    	FileChooser.ExtensionFilter extensionFilters = new FileChooser.ExtensionFilter("Imagens (.jpeg, .png, .bmp)", "*.jpeg", "*.jpg", "*.png", "*.bmp");
    	chooser.getExtensionFilters().add(extensionFilters);
    	
    	imageFile = chooser.showOpenDialog(null);
    	
    	if(imageFile != null) {
    		fileName.setText(imageFile.getName());
    	}
    }

    @FXML
    void insertTag(KeyEvent event) {
    	if(event.getCode().equals(KeyCode.ENTER)) {
    		tags.getChildren().add(new Chip(tagInput.getText()));
    		tagInput.setText("");
    		tagInput.requestFocus();
    	}
    }

    @FXML
    void store(ActionEvent event) {
    	store();
    }
    
    private class Chip extends Label {
    	
    	Chip(String text) {
    		setText(text);
    		
    		OctIconView icon = new OctIconView(OctIcon.X);
    		icon.setFill(Color.DARKGRAY);
    		icon.setCursor(Cursor.HAND);
    		icon.setOnMouseClicked(event -> {
    			tags.getChildren().remove(this);
    		});
    		setGraphic(icon);
    		setContentDisplay(ContentDisplay.RIGHT);
    		setGraphicTextGap(8);
    		getStyleClass().add("chip");
    	}
    }
}
