package tattool.views.controller.customer;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import de.jensd.fx.glyphs.octicons.OctIconView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import tattool.domain.model.Customer;
import tattool.rest.consume.CustomerRest;

/*
 * 	??	A IDEIA E USAR ESTE CONTROLLER PARA O CREATE E EDIT DO CLIENTE
 * 	??	PORQUE A VIEW E A MESMA
 */

public class CreateEditCustomerController {

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
    
    @FXML
    private Label error;
    
    /*
     * 	??	NAO SEI COMO SERA O PREENCHIMENTO DE ALGUNS ATRIBUTOS, MAS JA CRIEI UMA LABEL DE ERRO PRA TODOS
     */

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
	
    @FXML
    private Tab customerTab;
    
    @FXML
    private Tab contactTab;
    
    @FXML
    private Tab addressTab;
    
    
    private CustomerRest rest = new CustomerRest();
    public void initialize()
    {
    	loadValidationErrors();
    	
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
    	customer.setBirthDate(birthdate.getValue());
    	customer.getContact().setPhone(phone.getText());
    	customer.getContact().setEmail(email.getText());
    	customer.getAddress().setCity(city.getText());
    	customer.getAddress().setState(state.getText());
    	customer.getAddress().setNumber(number.getText());
    	customer.getAddress().setStreet(street.getText());
    	customer.getAddress().setNeighborhood(neighborhood.getText());
    	customer.getAddress().setZipCode(Integer.parseInt(zipCode.getText()));
 
    	
    	
    	rest.save(customer);
    	/*
  
    	    @FXML
    	    private JFXTextField zipCode;
    	    
    	    
    	    @FXML
    	    private JFXTextField number;
    	    
    	    @FXML
    	    private JFXTextField street;
    	    
    	    @FXML
    	    private JFXTextField neighborhood;
    	    */
    }
    
    /*
     * 	##	VALIDACAO FORM
     */
    
   
    /*
     * 	##	VOLTAR
     */
    
    void back(BorderPane main) {
    	try {
    		FXMLLoader viewLoader = new FXMLLoader(getClass().getResource("/views/customers/customers.fxml"));
    		
    		viewLoader.setRoot(main);
    		main.getChildren().clear();
    		viewLoader.load();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    @FXML
    void store(ActionEvent event) {
    	store();

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
	
	/*
	 * 	##	VALIDATION ERRORS LABELS
	 */
    
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
		
		
		if(birthdate.getValue() != null)
		{
			errorBirthdate.setText("Insira uma data de nascimento para o cliente");
			errorBirthdate.setVisible(true);
			validate = false;
		}
		
		return validate;
		
	}
	 
	
	
	void loadValidationErrors()
	{
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
    	
    	resetValidation();
	}
	
	void resetValidation()
	{
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
    	customerTab.setGraphic(null);
    	contactTab.setGraphic(null);
    	addressTab.setGraphic(null);
	}
	
	private class ErrorIcon extends OctIconView {
		
		ErrorIcon()
		{
			setGlyphName("ISSUE_OPENED");
			setGlyphSize(18);
			setFill(Color.WHITE);
		}
	}
}
