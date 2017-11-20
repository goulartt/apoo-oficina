//package tattool.views.controller.art;
//
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.ResourceBundle;
//
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.TextField;
//import javafx.scene.image.ImageView;
//import javafx.stage.Stage;
//import tattool.domain.model.Art;
//import tattool.domain.model.Tag;
//import tattool.rest.consume.ArtRest;
//
//public class ArtViewController implements Initializable{
//	@FXML public ImageView image = new ImageView();
//	@FXML public TextField txtDescricao = new TextField();
//	@FXML TableColumn<Tag, String> tagColumn;
//	@FXML public TableView<Tag> tableTag;
//	@FXML public Button btnFechar = new Button();
//	@FXML public Label lblSucesso = new Label();
//	public ObservableList<Tag> observableTag;
//	public Art art = new Art();
//	List<Tag> tags = new ArrayList<>();
//	private ArtRest rest = new ArtRest();
//	
//	
//	@Override
//	public void initialize(URL location, ResourceBundle resources) {
//		// TODO Auto-generated method stub
//	 
//	}
//	
//	@FXML
//	public void salvarArt(ActionEvent event){
//		art.setDescription(txtDescricao.getText());
//		for(Tag tag : tags) {
//			art.getTags().add(tag);
//		}
//		rest.saveArt(art);
//		lblSucesso.setText("Imagem Salva com sucesso");
//	}
//	
//	@FXML
//	public void deletarArt(ActionEvent event){
//		if(rest.deleteImage(art.getId()).is2xxSuccessful()) {
//			 Stage stage = (Stage) btnFechar.getScene().getWindow();
//			 stage.close();
//		}
//	}
//
//}

package tattool.views.controller.art;

import java.io.File;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;

import de.jensd.fx.glyphs.octicons.OctIcon;
import de.jensd.fx.glyphs.octicons.OctIconView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import tattool.domain.modelfx.UserFX;

public class ShowArtController {

    @FXML
    private StackPane imageStack;

    @FXML
    private ImageView image;
    
    @FXML
    private JFXTextField description;
    
    @FXML
    private JFXTextField fileName;

    @FXML
    private JFXButton imageButton;
    
    @FXML
    private JFXTextField tagInput;
    
    @FXML
    private HBox tags;

    @FXML
    private OctIconView closeButton;
    
    private File imageFile;
    
    /*
     * 	##	ATUALIZA DADOS
     */
    @FXML
    void update(ActionEvent event) {
    	
    	//if(atualizou) {
    	loadDialogUpdate((StackPane) ((Node) event.getSource()).getScene().lookup("#mainStack"), new Text("As altera��es foram salvas!"));
    	//} else {
    		
    		//DESATIVA O BOT�O DE SALVAR L� QUE FICA DAHORA
    }
    
    /*
     * 	##	DELETA ARTE
     */
    @FXML
    void delete(ActionEvent event) {
    	loadDialogDelete((StackPane) ((Node) event.getSource()).getScene().lookup("#mainStack"));
    }
    
    /*
     * 	##	INSERE TAG
     */
    @FXML
    void insertTag(KeyEvent event) {
    	if(event.getCode().equals(KeyCode.ENTER)) {
    		tags.getChildren().add(new Chip(tagInput.getText()));
    		tagInput.setText("");
    		tagInput.requestFocus();
    	}
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
    
    /*
     * 	##	DELETE DIALOG
     */
    
    void loadDialogDelete(StackPane mainStack) {
    	
    	JFXDialogLayout dialogContent = new JFXDialogLayout();
    	JFXDialog dialog              = new JFXDialog(mainStack, dialogContent, JFXDialog.DialogTransition.CENTER);
    	JFXButton yes                 = new JFXButton("Sim");
    	JFXButton no                  = new JFXButton("N�o");
    	
    	dialogContent.setHeading(new Text("Tem certeza que quer excluir esta arte?"));
    	dialogContent.setBody(new Text("Todos os dados sobre esta arte ser�o perdidos."));
    	
    	yes.setCursor(Cursor.HAND);
    	no.setCursor(Cursor.HAND);
    	
		yes.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				//REST.DELETE
				
			}
		});
		no.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				dialog.close();
			}
		});
		
		dialogContent.setActions(no, yes);
		dialog.setOverlayClose(false);
		dialog.show();
    }
    
    /*
     * 	##	DIALOG UPDATE
     */
    
    void loadDialogUpdate(StackPane mainStack, Text text) {
    	
    	JFXDialogLayout dialogContent = new JFXDialogLayout();
    	JFXDialog dialog              = new JFXDialog(mainStack, dialogContent, JFXDialog.DialogTransition.CENTER);
    	JFXButton ok                 = new JFXButton("Ok");
    	
    	dialogContent.setHeading(text);
    	
    	ok.setCursor(Cursor.HAND);
    	
		ok.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
	    		dialog.close();
			}
		});
		
		dialogContent.setActions(ok);
		dialog.show();
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
