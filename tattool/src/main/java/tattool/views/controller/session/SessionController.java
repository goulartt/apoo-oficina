package tattool.views.controller.session;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import tattool.domain.model.Service;

public class SessionController implements Initializable{
	
	JFXDialog serviceModal;
	
	JFXDialog sessionModal;
	
	@FXML
    JFXTextField service;
	
	Service serviceCarregado = new Service();
	
	@FXML
    JFXButton showSessionsButton;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	}
	
	/*
	 * 	##	SELECIONE UM SERVICO
	 */
	
    @FXML
    void selectService(ActionEvent event) {
    	try {
			FXMLLoader serviceLoader = new FXMLLoader(getClass().getResource("/views/sessions/service-grid.fxml"));
			ServiceGridController control = new ServiceGridController(this);
			serviceLoader.setController(control);
			Region serviceContent    = serviceLoader.load();
			StackPane mainStack = (StackPane) ((Node) event.getSource()).getScene().lookup("#mainStack");
			serviceModal   = new JFXDialog(mainStack, serviceContent, JFXDialog.DialogTransition.CENTER, false);
			serviceModal.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /*
     * 	##	MOSTRA AS SESSOES DO SERVICO
     */

    @FXML
    void showSessions(ActionEvent event) {
    	try {
			FXMLLoader sessionLoader = new FXMLLoader(getClass().getResource("/views/sessions/session-grid.fxml"));
			SessionGridController control = new SessionGridController(this);
			sessionLoader.setController(control);
			Region sessionContent    = sessionLoader.load();
			StackPane mainStack = (StackPane) ((Node) event.getSource()).getScene().lookup("#mainStack");
			sessionModal   = new JFXDialog(mainStack, sessionContent, JFXDialog.DialogTransition.CENTER, false);
			sessionModal.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
