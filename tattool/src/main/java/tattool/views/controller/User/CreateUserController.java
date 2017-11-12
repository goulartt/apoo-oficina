package tattool.views.controller.User;

/*
 * 	##	NECESSï¿½RIO CRIAR UM CONTROLLER EXCLUSIVO POR HAVER DISPARO DE EXCESSï¿½O NA INICIALIZAï¿½ï¿½O DO USERCONTROLLER PELA TABELA DO USUï¿½RIO
 */

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
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
	private Label error;
	
	@FXML
	private Label errorName;
	
	@FXML
	private Label errorUsername;
	
	@FXML
	private Label errorPassword;

	@FXML
	private JFXComboBox<?> role;

	private UserRest userRest = new UserRest();
	
	public void initialize()
	{
		error.managedProperty().bind(error.visibleProperty());
		error.setVisible(false);
		errorName.managedProperty().bind(errorName.visibleProperty());
		errorName.setVisible(false);
		errorUsername.managedProperty().bind(errorUsername.visibleProperty());
		errorUsername.setVisible(false);
		errorPassword.managedProperty().bind(errorPassword.visibleProperty());
		errorPassword.setVisible(false);
	}

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
		
		error.setVisible(false);
		errorName.setVisible(false);
		errorUsername.setVisible(false);
		errorPassword.setVisible(false);
		
		User usuario = userRest.existeUsername(username.getText());
		
		if (usuario == null) {
			if (validate(name.getText(), username.getText(), password.getText())) {
				User user = new User(username.getText(), password.getText(), name.getText(), 0);
				userRest.save(user);
				limpaCampo();

				System.out.println("Usuario salvo com sucesso!");
			}
		} else {
			errorUsername.setText("Este login já está em uso");
			errorUsername.setVisible(true);
		}
	}

	public void limpaCampo() {
		username.setText("");
		name.setText("");
		password.setText("");
	}

	boolean validate(String name, String username, String password)
	{
		boolean validate = true;
		
		if(name.isEmpty())
		{
			errorName.setText("Insira um nome para o usuário");
			errorName.setVisible(true);
			validate = false;
		}
		if(username.isEmpty())
		{
			errorUsername.setText("Insira um login para o usuário");
			errorUsername.setVisible(true);
			validate = false;
		}
		if(password.isEmpty())
		{
			errorPassword.setText("Insira a senha do usuário");
			errorPassword.setVisible(true);
			validate = false;
		}
		
		return validate;
	}
}
