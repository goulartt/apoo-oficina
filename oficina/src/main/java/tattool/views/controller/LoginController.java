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
import javafx.stage.Stage;
import tattool.domain.model.User;
import tattool.rest.consume.UserRest;

public class LoginController implements Initializable {

	@FXML
	private JFXTextField txtUsuario = new JFXTextField();
	@FXML
	private JFXPasswordField txtSenha = new JFXPasswordField();
	private UserRest rest = new UserRest();

	@FXML
	void closeApp(ActionEvent event) {
		Platform.exit();
	}

	@FXML
	void login(ActionEvent event) {
		/*
		 * ## Stage view login
		 */
		if (!txtUsuario.getText().isEmpty() && !txtSenha.getText().isEmpty()) {
			User user = rest.verificaLogin(txtUsuario.getText(), txtSenha.getText());
			if (user != null) {
				Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

				try {

					/*
					 * ## New Stage dashboard
					 */

					Stage stage = new Stage();
					FXMLLoader templateLoader = new FXMLLoader(getClass().getResource("/views/template/template.fxml"));
					Scene scene = new Scene(new JFXDecorator(stage, templateLoader.load()), 1032, 612);
					FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/views/home.fxml"));

					mainLoader.setRoot(templateLoader.getNamespace().get("main"));
					mainLoader.load();

					stage.setScene(scene);
					primaryStage.hide();
					stage.show();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else {
				System.out.println("Usuario e senha invalidos");
			}

		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		rest.verificaAdmin();
		
	}
}
