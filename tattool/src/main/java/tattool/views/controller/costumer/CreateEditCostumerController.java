package tattool.views.controller.costumer;

import java.util.Date;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import tattool.domain.model.Address;
import tattool.domain.model.Contact;
import tattool.domain.model.Customer;
import tattool.domain.model.User;
import tattool.rest.consume.CustomerRest;
import tattool.util.ConvertModelToFX;

/*
 * 	??	A IDEIA E USAR ESTE CONTROLLER PARA O CREATE E EDIT DO CLIENTE
 * 	??	PORQUE A VIEW E A MESMA
 */

public class CreateEditCostumerController {

    @FXML
    private ScrollPane scrollPane;
    
    @FXML
    private JFXTextField name;
    
    @FXML
    private JFXTextField cpf;
    
    @FXML
    private JFXDatePicker birthdate;
    
    @FXML
    private JFXTextField email;
    
    @FXML
    private JFXTextField phone;
    
    @FXML
    private JFXTextField zipCode;
    
    @FXML
    private JFXTextField city;
    
    @FXML
    private JFXTextField state;
    
    @FXML
    private JFXTextField number;
    
    @FXML
    private JFXTextField street;
    
    @FXML
    private JFXTextField neighborhood;
    
    /*
     * 	??	NAO SEI COMO SERA O PREENCHIMENTO DE ALGUNS ATRIBUTOS, MAS JA CRIEI UMA LABEL DE ERRO PRA TODOS
     */

    @FXML
    private Label error;

    @FXML
    private Label errorName;

    @FXML
    private Label errorCpf;

    @FXML
    private Label errorBirthdate;

    @FXML
    private Label errorEmail;

    @FXML
    private Label errorPhone;

    @FXML
    private Label errorZipCode;
    
    @FXML
    private Label errorStreet;
    
    @FXML
    private Label errorNumber;

    @FXML
    private Label errorCity;

    @FXML
    private Label errorState;

    @FXML
    private Label errorNeighborhood;
    
    
    private CustomerRest rest = new CustomerRest();
    public void initialize()
    {
    	scrollPane.setFitToWidth(true);
    	
    	error.managedProperty().bind(error.visibleProperty());
    	errorName.managedProperty().bind(errorName.visibleProperty());
    	errorCpf.managedProperty().bind(errorCpf.visibleProperty());
    	errorBirthdate.managedProperty().bind(errorBirthdate.visibleProperty());
    	errorEmail.managedProperty().bind(errorEmail.visibleProperty());
    	errorPhone.managedProperty().bind(errorPhone.visibleProperty());
    	errorZipCode.managedProperty().bind(errorZipCode.visibleProperty());
    	errorStreet.managedProperty().bind(errorStreet.visibleProperty());
    	errorNumber.managedProperty().bind(errorNumber.visibleProperty());
    	errorCity.managedProperty().bind(errorCity.visibleProperty());
    	errorState.managedProperty().bind(errorState.visibleProperty());
    	errorNeighborhood.managedProperty().bind(errorNeighborhood.visibleProperty());
    	
    	error.setVisible(false);
    	errorName.setVisible(false);
    	errorCpf.setVisible(false);
    	errorBirthdate.setVisible(false);
    	errorEmail.setVisible(false);
    	errorPhone.setVisible(false);
    	errorZipCode.setVisible(false);
    	errorStreet.setVisible(false);
    	errorNumber.setVisible(false);
    	errorCity.setVisible(false);
    	errorState.setVisible(false);
    	errorNeighborhood.setVisible(false);
    	
    	Platform.runLater(new Runnable() {
	        @Override
	        public void run() {
	            name.requestFocus();
	        }
	    });
    }
    
    /*
     * 	##	REGISTRA O CLINTE
     */
    
    void store()
    {
    	Customer customer = new Customer();
    	customer.setName(name.getText());
    	customer.setCpf(cpf.getText());
    	customer.getContact().setPhone(phone.getText());
    	customer.getContact().setEmail(email.getText());
    	System.out.println(name.getText());
    	System.out.println(cpf.getText());
    	System.out.println(phone.getText());
    	System.out.println(email.getText());
    	
    	//rest.save(customer);
    	// REST SAVE
    }
    
    /*
     * 	##	VOLTAR
     */
    
    void back(BorderPane main) {
    	try {
    		FXMLLoader viewLoader = new FXMLLoader(getClass().getResource("/views/costumers/costumers.fxml"));
    		
    		viewLoader.setRoot(main);
    		main.getChildren().clear();
    		viewLoader.load();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    @FXML
    void store(ActionEvent event) {
    
    	
    	
    	
    	/* private JFXTextField name;
    
	    
	    private JFXTextField cpf;
	    private JFXDatePicker birthdate;
	    private JFXTextField email;
	    private JFXTextField phone;
	    private JFXTextField zipCode;
	    private JFXTextField city;
	    private JFXTextField state;
	    private JFXTextField number;
	    private JFXTextField street;
	    private JFXTextField neighborhood;
    */ 
    	}
    
    @FXML
    void back(ActionEvent event) {
    	back((BorderPane) ((Node) event.getSource()).getScene().lookup("#main"));
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
				store();
				break;
			case ESCAPE:
				back((BorderPane) ((Node) event.getSource()).getScene().lookup("#main"));
				break;
			default:
				break;
		}
	}
    
	boolean validate()
	{
		boolean validate = true;
		
		if(name.getText().isEmpty())
		{
			errorName.setText("Insira um nome para o cliente");
			errorName.setVisible(true);
			validate = false;
		}
		
		if(cpf.getText().isEmpty())
		{
			errorCpf.setText("Insira um CPF para o cliente");
			errorCpf.setVisible(true);
			validate = false;
		}
	
	}

	 
	
	
}
