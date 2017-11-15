package tattool.domain.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tattool.domain.model.User;
import tattool.rest.consume.UserRest;

public class LoginController {
	
	@FXML Button btnLogin = new Button();
	@FXML Button btnSair = new Button();
	@FXML TextField lblUsuario = new TextField();
	@FXML Label lblErro = new Label();
	@FXML PasswordField lblSenha = new PasswordField();

	@FXML Label lblLoad = new Label();


	public User usuario = new User();
	private UserRest rest = new UserRest();
	
	@FXML
	public void initialize() {
		rest.verificaAdmin();
	}
	


	@FXML
	public void logar(ActionEvent event){
		
		
		this.usuario = rest.verificaLogin(lblUsuario.getText(), lblSenha.getText());
		if(usuario != null) {
			try {
				
				FXMLLoader loader = new FXMLLoader(DashboardController.class.getResource("/telas/Dashboard.fxml"));
		        Parent root = (Parent) loader.load();
		        DashboardController control = (DashboardController)loader.getController();
		        control.user(usuario);
		        control.lblNome.setText(usuario.getNome());
		        Scene scene = new Scene(root, 850, 600);
		        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		        stage.setTitle("Dashboard");
		        stage.setScene(scene);
		        stage.show();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			lblErro.setText("Login ou senha inválido");
		}
	}
	
	@FXML
	public void sair(ActionEvent event){
		
		System.exit(0);
	}



	
}
