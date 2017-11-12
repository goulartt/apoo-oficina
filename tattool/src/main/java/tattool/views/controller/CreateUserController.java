package tattool.views.controller;

/*
 * 	##	NECESS�RIO CRIAR UM CONTROLLER EXCLUSIVO POR HAVER DISPARO DE EXCESS�O NA INICIALIZA��O DO USERCONTROLLER PELA TABELA DO USU�RIO
 */

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import tattool.domain.model.User;
import tattool.rest.consume.UserRest;

public class CreateUserController {

	@FXML
	private JFXTextField name;

	@FXML
	private JFXTextField username;

	@FXML
	private JFXPasswordField password;

	@FXML
	private JFXComboBox<?> role;

	private UserRest userRest = new UserRest();

	@FXML
	void back(ActionEvent event) {
		try {
			FXMLLoader viewLoader = new FXMLLoader(getClass().getResource("/views/users/users.fxml"));
			BorderPane main = (BorderPane) ((Node) event.getSource()).getScene().lookup("#main");

			viewLoader.setRoot(main);
			main.getChildren().clear();
			viewLoader.load();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void store(ActionEvent event) {
		User usuario = userRest.existeUsername(username.getText());
		if (usuario == null) {
			if (!username.getText().isEmpty() && !password.getText().isEmpty() && !name.getText().isEmpty()) {
				User user = new User(username.getText(), password.getText(), name.getText(), 0);
				userRest.save(user);
				limpaCampo();

				System.out.println("Usuario salvo com sucesso!");
			} else {
				System.out.println("Preenche todos os campos por favor");
			}
		} else {
			System.out.println("Usuario ja existe");
		}
	}

	public void limpaCampo() {
		username.setText("");
		name.setText("");
		password.setText("");
	}

}
