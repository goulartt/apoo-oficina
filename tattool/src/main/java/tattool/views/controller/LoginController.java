package tattool.views.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import tattool.domain.model.User;
import tattool.rest.consume.UserRest;

public class LoginController implements Initializable {

	@FXML
	private JFXTextField txtUsuario;
	
	@FXML
	private JFXPasswordField txtSenha;
	
	@FXML
	private Label error;
	
	private UserRest rest = new UserRest();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		rest.verificaAdmin();
		
		Platform.runLater(new Runnable() {
	        @Override
	        public void run() {
	            txtUsuario.requestFocus();
	        }
	    });
		
		error.managedProperty().bind(error.visibleProperty());
		error.setVisible(false);
	}

	/*
	 * 	##	BOTAO LOGIN
	 */

	@FXML
	void login(ActionEvent event) {
		login((Stage) ((Node) event.getSource()).getScene().getWindow());
	}
	
	/*
	 * 	##	BOTAO ENCERRAR
	 */
	
	@FXML
	void closeApp(ActionEvent event) {
		closeApp();
	}
	
	/*
	 * 	##	TECLAS DE ATALHO
	 */
	
	@FXML
	void keyPressed(KeyEvent event)
	{
		switch(event.getCode())
		{
			case ENTER:
				login((Stage) ((Node) event.getSource()).getScene().getWindow());
				break;
			case ESCAPE:
				closeApp();
				break;
			default:
				break;
		}
	}
	
	/*
	 * 	##	LOGIN
	 */
	
	void login(Stage primaryStage) {
		
		error.setVisible(false);
		
		//Stage view login

		if (!txtUsuario.getText().isEmpty() && !txtSenha.getText().isEmpty()) {
			
			User user = rest.verificaLogin(txtUsuario.getText(), txtSenha.getText());
			
			if (user != null) {
				try {

					//New Stage dashboard

					Stage stage               = new Stage();
					FXMLLoader templateLoader = new FXMLLoader(getClass().getResource("/views/template/template.fxml"));
					Scene scene               = new Scene(new JFXDecorator(stage, templateLoader.load()), 1032, 612);
					FXMLLoader mainLoader     = new FXMLLoader(getClass().getResource("/views/home.fxml"));
					DashboardController control = (DashboardController) templateLoader.getController();
					if(user.getRole() != 1) control.btnUsers.setVisible(false);
					control.nome.setText(user.getNome());
					mainLoader.setRoot(templateLoader.getNamespace().get("main"));
					mainLoader.load();
					
					scene.getStylesheets().add("/css/application.css");

					stage.setScene(scene);
					primaryStage.hide();
					stage.setMinHeight(612);
				    stage.setMinWidth(1032);
					stage.show();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else {
				error.setText("Credenciais Inváldias");
				error.setVisible(true);
				txtUsuario.setText("");
				txtSenha.setText("");
				txtUsuario.requestFocus();
			}
		}else {
			error.setText("Por favor preencha os campos");
			error.setVisible(true);
			txtUsuario.requestFocus();
		}
	}
	
	void closeApp() {
		Platform.exit();
	}
}
