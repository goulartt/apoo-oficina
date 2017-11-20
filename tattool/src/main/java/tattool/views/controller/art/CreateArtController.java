package tattool.views.controller.art;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.events.JFXDialogEvent;

import de.jensd.fx.glyphs.octicons.OctIcon;
import de.jensd.fx.glyphs.octicons.OctIconView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import tattool.domain.model.Art;
import tattool.domain.model.Tag;
import tattool.rest.consume.ArtRest;
import tattool.service.ArtService;

public class CreateArtController {
	
	@FXML
	private JFXTextField description;

    @FXML
    private JFXTextField fileName;

    @FXML
    private JFXTextField tagInput;
    
    @FXML
    private Label errorDescription;
    
    @FXML
    private Label errorImage;
    
    @FXML
    private Label errorTags;
    
    @FXML
    private HBox tags;
    
    private File imageFile;
    
    private ArtRest rest = new ArtRest();
    
    private ArtService service = new ArtService();
    
    /*
     * 	## INITIALIZE
     */
    
    public void initialize() {
    	loadValidationErrors();
    	Platform.runLater(new Runnable() {
	        @Override
	        public void run() {
	            description.requestFocus();
	        }
	    });
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
    
    /*
     * 	##	CADASTRA NOVA ARTE
     */

    @FXML
    void store(ActionEvent event) {
    	if(validate()) {
    		List<Tag> tag = new ArrayList<>();
        	tags.getChildren().forEach(t -> {
        		tag.add(new Tag(((Label)t).getText()));
        	});
        	Art art = new Art();
        	for(Tag t : tag) {
    			art.getTags().add(t);
    		}
    		art.setImage(service.convertFileToByte(imageFile));
    		rest.saveArt(art);
    		loadDialog((StackPane) ((Node) event.getSource()).getScene().lookup("#mainStack"));
    	}
    }
    
    /*
	 * 	##	DIALOG STORE
	 */
	
	void loadDialog(StackPane mainStack)
	{
		JFXDialogLayout dialogContent = new JFXDialogLayout();
		JFXDialog dialog              = new JFXDialog(mainStack, dialogContent, JFXDialog.DialogTransition.CENTER, false);
		JFXButton ok                  = new JFXButton("Ok");
		
		dialogContent.setHeading(new Text("Arte cadastrado com sucesso!"));
		
		ok.setCursor(Cursor.HAND);
		
		ok.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				dialog.close();
			}
		});
		
		dialogContent.setActions(ok);
		dialog.setOnDialogClosed(new EventHandler<JFXDialogEvent>() {
			@Override
			public void handle(JFXDialogEvent event) {
				back((BorderPane) mainStack.lookup("#main"));
			}
		});
		dialog.show();
	}
	
	/*
     * 	##	VALIDACAO FORM
     */
    
    boolean validate()
	{
		boolean validate = true;
		
		resetValidation();
		
		if(description.getText().isEmpty())
		{
			errorDescription.setText("Insira uma descrição para a arte");
			errorDescription.setVisible(true);
			validate = false;
		}
		if(fileName.getText().isEmpty())
		{
			errorImage.setText("Insira uma imagem da arte");
			errorImage.setVisible(true);
			validate = false;
		}
		if(tags.getChildren().isEmpty())
		{
			errorTags.setText("A arte deve possuir pelo menos uma tag");
			errorTags.setVisible(true);
			validate = false;
		}
		
		return validate;
	}
	
	/*
	 * 	##	VALIDATION ERRORS LABELS
	 */
	
	void loadValidationErrors()
	{
		errorDescription.managedProperty().bind(errorDescription.visibleProperty());
		errorImage.managedProperty().bind(errorImage.visibleProperty());
		errorTags.managedProperty().bind(errorTags.visibleProperty());
    	
    	resetValidation();
	}
	
	void resetValidation()
	{
		errorDescription.setVisible(false);
		errorImage.setVisible(false);
		errorTags.setVisible(false);
	}
    
    /*
     * 	##	CLASSE DO CHIP DA TAG
     */
    
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
