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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tattool.dao.UsuarioDao;
import tattool.domain.model.Usuario;

public class LoginController implements Initializable{
	
	@FXML Button btnLogin = new Button();
	@FXML Button btnSair = new Button();
	@FXML TextField lblUsuario = new TextField();
	@FXML Label lblErro = new Label();
	@FXML PasswordField lblSenha = new PasswordField();
	
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	@FXML
	public void logar(ActionEvent event){
		
		UsuarioDao dao = new UsuarioDao();
		Usuario usuario = dao.verificaLogin(lblUsuario.getText(), lblSenha.getText());
		if(usuario != null) {
			try {
				Parent dash = FXMLLoader.load(getClass().getResource("/telas/Dashboard.fxml"));
				Scene cadastroCena = new Scene(dash);
				Stage cadastroTela = (Stage) ((Node) event.getSource()).getScene().getWindow();
			    cadastroTela.setScene(cadastroCena);
			    cadastroTela.show();
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
