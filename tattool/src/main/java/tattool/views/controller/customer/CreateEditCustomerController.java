package tattool.views.controller.customer;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.events.JFXDialogEvent;

import de.jensd.fx.glyphs.octicons.OctIconView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import tattool.domain.model.Customer;
import tattool.rest.consume.CustomerRest;
import tattool.util.DateUtil;

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
    private JFXTabPane tabPane;
    
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
    	loadTab();
    	
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
    
    void store(StackPane mainStack)
    {
    	if(validate())
    	{
    		Customer customer = new Customer();
        	customer.setName(name.getText());
        	customer.setCpf(cpf.getText());
        	customer.setBirthDate(DateUtil.asDate(birthdate.getValue()));
        	customer.getContact().setPhone(phone.getText());
        	customer.getContact().setEmail(email.getText());
        	customer.getAddress().setCity(city.getText());
        	customer.getAddress().setState(state.getText());
        	customer.getAddress().setNumber(number.getText());
        	customer.getAddress().setStreet(street.getText());
        	customer.getAddress().setNeighborhood(neighborhood.getText());
        	customer.getAddress().setZipCode(Integer.parseInt(zipCode.getText()));
        	
        	rest.save(customer);
        	
        	loadDialog(mainStack);
    	}
    }
    
    /*
     * 	##	STORE DIALOG
     */
    
    /*
	 * 	##	DIALOG STORE
	 */
	
	void loadDialog(StackPane mainStack)
	{
		JFXDialogLayout dialogContent = new JFXDialogLayout();
		JFXDialog dialog              = new JFXDialog(mainStack, dialogContent, JFXDialog.DialogTransition.CENTER, false);
		JFXButton ok                  = new JFXButton("Ok");
		
		dialogContent.setHeading(new Text("Cliente cadastrado com sucesso!"));
		
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
     * 	##	VALIDACAO FORM
     */
    
    boolean validate()
	{
		boolean validate = true;
		boolean customer = false, contact = false, address = false;
		
		resetValidation();
		
		if(name.getText().isEmpty())
		{
			errorName.setText("Insira um nome para o cliente");
			errorName.setVisible(true);
			validate = false;
			customer = true;
		}
		if(cpf.getText().isEmpty())
		{
			errorCpf.setText("Insira um CPF para o cliente");
			errorCpf.setVisible(true);
			validate = false;
			customer  = true;
		}
		if(birthdate.getValue() == null)
		{
			errorBirthdate.setText("Insira uma data de nascimento para o cliente");
			errorBirthdate.setVisible(true);
			validate = false;
			customer = true;
		}
		
		//Adicionar icone de erro nas Tabs
		if(customer)
			customerTab.setGraphic(new ErrorIcon());
		if(contact)
			customerTab.setGraphic(new ErrorIcon());
		if(address)
			customerTab.setGraphic(new ErrorIcon());
		
		return validate;
	}
   
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
    	store((StackPane) ((Node) event.getSource()).getScene().lookup("#mainStack"));
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
				store((StackPane) ((Node) event.getSource()).getScene().lookup("#mainStack"));
				break;
			case ESCAPE:
				back((BorderPane) ((Node) event.getSource()).getScene().lookup("#main"));
				break;
			case TAB:
				if(((Control) event.getSource()).getId().equals("birthdate"))
					tabPane.getSelectionModel().select(2);
				if(((Control) event.getSource()).getId().equals("phone"))
					tabPane.getSelectionModel().select(2);
				if(((Control) event.getSource()).getId().equals("neighborhood"))
					tabPane.getSelectionModel().select(0);
			default:
				break;
		}
	}
	
	/*
	 * 	##	VALIDATION ERRORS LABELS
	 */
	
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
	
	/*
	 * 	##	TAB
	 */
	
	void loadTab()
	{
		tabPane.setOnMouseClicked(event -> {
			if(tabPane.getSelectionModel().getSelectedIndex() == 0)
				name.requestFocus();
			if(tabPane.getSelectionModel().getSelectedIndex() == 1)
				email.requestFocus();
			if(tabPane.getSelectionModel().getSelectedIndex() == 2)
				zipCode.requestFocus();
		});
		tabPane.setOnKeyPressed(event -> {
			if(tabPane.getSelectionModel().getSelectedIndex() == 0)
				email.requestFocus();
			if(tabPane.getSelectionModel().getSelectedIndex() == 1)
				zipCode.requestFocus();
			if(tabPane.getSelectionModel().getSelectedIndex() == 2)
				name.requestFocus();
		});
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
