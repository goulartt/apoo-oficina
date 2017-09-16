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

public class UsuarioController implements Initializable{
	
	@FXML Button btnCadastrar = new Button();
	@FXML Button btnVoltar = new Button();
	@FXML TextField txtUsuario = new TextField();
	@FXML TextField txtNome = new TextField();
	@FXML Label lblErro = new Label();
	@FXML Label lblSucesso = new Label();
	@FXML PasswordField txtSenha = new PasswordField();
	Usuario user = new Usuario();
	
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	@FXML
	public void cadastrar(ActionEvent event){
		
		UsuarioDao dao = new UsuarioDao();
		Usuario usuario = dao.existeUsuario(txtUsuario.getText());
		if(user.getRole() == 1) {
			if(usuario == null) {
				if(!txtUsuario.getText().isEmpty() && !txtSenha.getText().isEmpty() && !txtNome.getText().isEmpty()) {
					Usuario user = new Usuario(txtUsuario.getText(), txtSenha.getText(), txtNome.getText(), 0);
					dao.save(user);
					limpaCampo();
					lblSucesso.setText("Usuario salvo com sucesso!");
				}else {
					lblErro.setText("Preenche todos os campos por favor");
				}
			}else {
				lblErro.setText("Usuario ja existe");
			}
		}else {
			lblErro.setText("Você não tem permissoes para cadastrar usuario");
			txtUsuario.setDisable(true);
		}
			
	}
	@FXML
	public void voltar(ActionEvent event){
		
		try {
			
		    
		    FXMLLoader loader = new FXMLLoader(DashboardController.class.getResource("/telas/Dashboard.fxml"));
	        Parent root = (Parent) loader.load();
	        DashboardController control = (DashboardController)loader.getController();
	        control.user(user);
	        control.lblNome.setText(user.getNome());
	        Scene scene = new Scene(root);
	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        stage.setTitle("Dashboard");
	        stage.setScene(scene);
	        stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void limpaCampo() {
		txtUsuario.setText("");
	    txtNome.setText("");
		txtSenha.setText("");
		lblErro.setText("");
	}

	public void user(Usuario user) {
		this.user = user;
		
	}
}
