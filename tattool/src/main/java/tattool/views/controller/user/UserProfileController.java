package tattool.views.controller.user;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class UserProfileController implements Initializable{

    @FXML
    private Label userName;

    @FXML
    private Label userLogin;

    @FXML
    private Label userFunction;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		userName.setText("<Nome do usuario>");
		userLogin.setText("<Login do usuario>");
		userFunction.setText("<Funcao do usuario>");
	}

}
