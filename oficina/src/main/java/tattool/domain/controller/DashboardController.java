package tattool.domain.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tattool.dao.UsuarioDao;
import tattool.domain.model.Usuario;

public class DashboardController implements Initializable{
	
	
	@FXML Label lblNome = new Label();
	@FXML MenuItem menuLogout = new MenuItem();
	public Usuario user = new Usuario();
	
	
	public void initialize(URL location, ResourceBundle resources) {

	
	}
	
	@FXML
	public void cadastrarUsuario(ActionEvent event){
		
		try {
		
			FXMLLoader loader = new FXMLLoader(UsuarioController.class.getResource("/telas/cadastroUsuario.fxml"));
	        Parent root = (Parent) loader.load();
	        UsuarioController control = (UsuarioController)loader.getController();
	        control.user(user);
	        if(user.getRole() != 1) {
	        	control.txtNome.setDisable(true);
	        	control.txtUsuario.setDisable(true);
	        	control.txtSenha.setDisable(true);
	        	control.lblErro.setText("Você não tem permissoes para cadastrar usuario");
	        	control.btnCadastrar.setDisable(true);
	        }
	        Scene scene = new Scene(root);
	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        stage.setTitle("Cadastrar Usuario");
	        stage.setScene(scene);
	        stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@FXML
	public void logout(ActionEvent event){
		try {
		    AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/telas/Login.fxml"));
			Scene scene = new Scene(root);
			Stage primaryStage = new Stage();
			/*scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());*/
			primaryStage .setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Login");
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void user(Usuario usuario) {
		this.user = usuario;
		
	}
}
