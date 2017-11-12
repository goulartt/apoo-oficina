package tattool.views.controller.user;

import com.jfoenix.controls.JFXButton;

/*
 * 	##	NECESSï¿½RIO CRIAR UM CONTROLLER EXCLUSIVO POR HAVER DISPARO DE EXCESSï¿½O NA INICIALIZAï¿½ï¿½O DO USERCONTROLLER PELA TABELA DO USUï¿½RIO
 */

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.events.JFXDialogEvent;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
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
		
		Platform.runLater(new Runnable() {
	        @Override
	        public void run() {
	            name.requestFocus();
	        }
	    });
	}
	
	/*
	 * 	##	REGISTRA NOVO USUÁRIO
	 */
	
	void store(StackPane mainStack) {
		
		error.setVisible(false);
		errorName.setVisible(false);
		errorUsername.setVisible(false);
		errorPassword.setVisible(false);
		
		User usuario = userRest.existeUsername(username.getText());
		
		if (usuario == null) {
			if (validate(name.getText(), username.getText(), password.getText())) {
				User user = new User(username.getText(), password.getText(), name.getText(), 0);
				userRest.save(user);
				
				loadDialog(mainStack);
			}
		} else {
			errorUsername.setText("Este login já está em uso");
			errorUsername.setVisible(true);
		}
	}
	
	/*
	 * 	##	DIALOG STORE
	 */
	
	void loadDialog(StackPane mainStack)
	{
		JFXDialogLayout dialogContent = new JFXDialogLayout();
		JFXDialog dialog              = new JFXDialog(mainStack, dialogContent, JFXDialog.DialogTransition.CENTER, false);
		JFXButton ok                  = new JFXButton("Ok");
		
		dialogContent.setHeading(new Text("Usuário criado com sucesso"));
		
		ok.setCursor(Cursor.HAND);
		
		ok.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				dialog.close();
			}
		});
		
		dialogContent.setActions(ok);
		dialog.setOnDialogClosed(new EventHandler<JFXDialogEvent>() {
			@Override
			public void handle(JFXDialogEvent event) {
				back((BorderPane) mainStack.lookup("#main"));
			}
		});
		dialog.show();
	}
	
	/*
	 * 	##	VOLTAR
	 */
	
	void back(BorderPane main)
	{
		try {
			FXMLLoader viewLoader = new FXMLLoader(getClass().getResource("/views/users/users.fxml"));

			viewLoader.setRoot(main);
			main.getChildren().clear();
			viewLoader.load();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	void back(ActionEvent event) {
		back((BorderPane) ((Node) event.getSource()).getScene().lookup("#main"));
	}

	@FXML
	void store(ActionEvent event) {
		store((StackPane) ((Node) event.getSource()).getScene().lookup("#mainStack"));
	}

	public void limpaCampo() {
		username.setText("");
		name.setText("");
		password.setText("");
	}
	
	/*
	 * 	##	FORM VALIDATION
	 */

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
	
	/*
	 * 	##	TECLAS DE ATALHO
	 */
	
	@FXML
	void keyPressed(KeyEvent event)
	{
		switch(event.getCode())
		{
			case ENTER:
				store((StackPane) ((Node) event.getSource()).getScene().lookup("#mainStack"));
				break;
			case ESCAPE:
				back((BorderPane) ((Node) event.getSource()).getScene().lookup("#main"));
				break;
			default:
				break;
		}
	}
}
