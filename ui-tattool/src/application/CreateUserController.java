package application;

/*
 * 	##	NECESSÁRIO CRIAR UM CONTROLLER EXCLUSIVO POR HAVER DISPARO DE EXCESSÃO NA INICIALIZAÇÃO DO USERCONTROLLER PELA TABELA DO USUÁRIO
 */

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

public class CreateUserController {

    @FXML
    private JFXTextField name;

    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXComboBox<?> role;

    @FXML
    void back(ActionEvent event)
    {
    	try
    	{
		    FXMLLoader viewLoader = new FXMLLoader(getClass().getResource("/views/users/users.fxml"));
		    BorderPane main       = (BorderPane) ((Node) event.getSource()).getScene().lookup("#main");
		    
		    viewLoader.setRoot(main);
		    main.getChildren().clear();
		    viewLoader.load();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }

    @FXML
    void store(ActionEvent event)
    {
    	//Registra o usuário
    }

}

