package tattool.views.controller.art;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import tattool.domain.model.Art;
import tattool.domain.model.Tag;
import tattool.rest.consume.ArtRest;

public class ArtViewController implements Initializable{
	@FXML public ImageView image = new ImageView();
	@FXML public TextField txtDescricao = new TextField();
	@FXML TableColumn<Tag, String> tagColumn;
	@FXML public TableView<Tag> tableTag;
	@FXML public Button btnFechar = new Button();
	@FXML public Label lblSucesso = new Label();
	public ObservableList<Tag> observableTag;
	public Art art = new Art();
	List<Tag> tags = new ArrayList<>();
	private ArtRest rest = new ArtRest();
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	 
	}
	
	@FXML
	public void salvarArt(ActionEvent event){
		art.setDescription(txtDescricao.getText());
		for(Tag tag : tags) {
			art.getTags().add(tag);
		}
		rest.saveArt(art);
		lblSucesso.setText("Imagem Salva com sucesso");
	}
	
	@FXML
	public void deletarArt(ActionEvent event){
		if(rest.deleteImage(art.getId()).is2xxSuccessful()) {
			 Stage stage = (Stage) btnFechar.getScene().getWindow();
			 stage.close();
		}
	}

}
