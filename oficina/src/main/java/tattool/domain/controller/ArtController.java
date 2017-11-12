package tattool.domain.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tattool.domain.model.Art;
import tattool.domain.model.Tag;
import tattool.rest.consume.ArtRest;

public class ArtController implements Initializable{
	
	@FXML ImageView img = new ImageView();
	@FXML TextField txtDescricao = new TextField();
	@FXML TextField txtTag = new TextField();
	@FXML TextField txtKey = new TextField();
	@FXML Label lblErro = new Label();
	@FXML TableView<Tag> tableTag;
	@FXML TableColumn<Tag, String> tagColumn;
	List<Tag> tags = new ArrayList<>();
	File imagem;
	public ArtRest rest = new ArtRest();
	public ObservableList<Tag> observableTag;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	

	@FXML
	public void adicionarTag(ActionEvent event){
		if(!txtTag.getText().equals("")) {
			tagColumn.setCellValueFactory(new PropertyValueFactory<Tag, String>("tag"));
			tags.add(new Tag(txtTag.getText()));
			observableTag = FXCollections.observableArrayList(tags);
			tableTag.setItems(observableTag);
			lblErro.setText("");
		}else {
			lblErro.setText("Valor invalido");
		}
	}
	
	@FXML
	public void uploadImagem(ActionEvent event) throws IOException {
		FileChooser fc = new FileChooser();
		FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
		FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
		FileChooser.ExtensionFilter extFilterJPEG = new FileChooser.ExtensionFilter("JPEG files (*.jpeg)", "*.JPEG");
		fc.getExtensionFilters().addAll(extFilterJPG, extFilterPNG,extFilterJPEG);

		imagem = fc.showOpenDialog(null);
	}
	
	@FXML
	public void salvarArt(ActionEvent event){
		if(imagem != null && !txtDescricao.getText().equals("") && !tags.isEmpty()) {
			try {
				rest.save(imagem, new Art(txtDescricao.getText()), tags);
				limparCampos();
			} catch (IOException e) {
				e.printStackTrace();
				lblErro.setText("Deu ruim");
			}
		}else {
			lblErro.setText("Preencha todos os campos e ao menos uma tag");
		}
	}
	
	public void limparCampos() {
		txtDescricao.setText("");
		tags = new ArrayList<>();
		tagColumn.setCellValueFactory(new PropertyValueFactory<Tag, String>("tag"));
		tags.add(new Tag(txtTag.getText()));
		observableTag = FXCollections.observableArrayList(tags);
		tableTag.setItems(observableTag);
		
		lblErro.setText("");
	}
	
	@FXML
	public void deletarArt(ActionEvent event){
	
	}
	
	@FXML
	public void buscarImagem(ActionEvent event){
	
		
	}
	@FXML
	public void abrirGaleria(ActionEvent event) throws IOException{
		Parent root = (Parent) FXMLLoader.load(getClass().getResource("/telas/GaleriaArt.fxml"));
		Scene scene = new Scene(root);
	
		 
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Galeria de Arte");
		stage.show();
		
	}



}
