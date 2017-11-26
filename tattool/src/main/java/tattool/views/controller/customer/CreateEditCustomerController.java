package tattool.views.controller.customer;

import java.time.LocalDate;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.events.JFXDialogEvent;

import de.jensd.fx.glyphs.octicons.OctIconView;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import tattool.domain.model.Cep;
import tattool.domain.model.Customer;
import tattool.rest.consume.CustomerRest;
import tattool.util.DateUtil;
import tattool.util.MaskFieldUtil;
import tattool.util.ValidaCPF;

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
    
    private Customer customer = new Customer();
    
    
    public CreateEditCustomerController(Customer customer) { 
        this.customer = customer;
    }
    
    public void initialize() {
    	carregaCampos();
    	loadValidationErrors();
    	loadTab();
    
    	Platform.runLater(new Runnable() {
	        @Override
	        public void run() {
	            name.requestFocus();
	        }
	    });
    	
    	zipCode.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if(newValue.length() == 9) {
					String valor = newValue.replace("-", "");
					System.out.println(valor);
					Cep cep = rest.buscaCep(valor);
					street.setText(cep.getLogradouro());
					state.setText(cep.getUf());
					city.setText(cep.getLocalidade());
					neighborhood.setText(cep.getBairro());
					
				}
			}
		});
    	
    	MaskFieldUtil.cpfField(cpf);
    	MaskFieldUtil.foneField(phone);
    	MaskFieldUtil.cepField(zipCode);
	
    }
    
    private void carregaCampos() {
    	if(customer.getId() != null) {
    		name.setText(customer.getName());
    		cpf.setText(customer.getCpf());
    		birthdate.setValue(DateUtil.asLocalDate(customer.getBirthDate()));
    		phone.setText(customer.getContact().getPhone());
    		email.setText(customer.getContact().getEmail());
    		city.setText(customer.getAddress().getCity());
    		state.setText(customer.getAddress().getState());
    		number.setText(customer.getAddress().getNumber());
    		street.setText(customer.getAddress().getStreet());
    		neighborhood.setText(customer.getAddress().getNeighborhood());
    		zipCode.setText(customer.getAddress().getZipCode().toString());
    	}
	}

	/*
     * 	##	REGISTRA O CLINTE
     */
    
    void store(StackPane mainStack) {
    	if(this.customer.getId() == null) {
    		if(validate()){
        		Customer customer = MontaCustomer();
            	
            	rest.save(customer);
            	
            	loadDialog(mainStack);
        	}
    	}else {
    		Customer customer = MontaCustomer();
    		customer.setId(this.customer.getId());
    		rest.atualizaCustomer(customer.getId(), customer);
    		loadDialog(mainStack);
    	}
    	
    }

	public Customer MontaCustomer() {
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
		customer.getAddress().setZipCode(!zipCode.getText().equals("") ? Integer.parseInt(zipCode.getText().replaceAll("-", "")) : null);
		return customer;
	}
    
    /*
	 * 	##	DIALOG STORE
	 */
	
	void loadDialog(StackPane mainStack) {
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
    
    boolean validate() {
		boolean validate = true;
		boolean customer = false, contact = false, address = false;
		
		resetValidation();
		
		if(name.getText().equals(""))
		{
			errorName.setText("Insira um nome para o cliente");
			errorName.setVisible(true);
			validate = false;
			customer = true;
		}
		if(ValidaCPF.isCPF(MaskFieldUtil.onlyDigitsValue(cpf)) == false)
		{
			errorCpf.setText("Insira um CPF válido");
			errorCpf.setVisible(true);
			validate = false;
			customer  = true;
		}
		
		if(birthdate.getValue().equals(""))
		{
			errorBirthdate.setText("Insira uma data de nascimento para o cliente");
			errorBirthdate.setVisible(true);
			validate = false;
			customer = true;
		}else {
			if(birthdate.getValue().getYear() > LocalDate.now().getYear())
			{
				errorBirthdate.setText("Insira uma data de nascimento menor que " + LocalDate.now().getYear());
				errorBirthdate.setVisible(true);
				validate = false;
				customer = true;
			}
		}
		
		if(email.getText().equals("") && phone.getText().equals(""))
		{
			errorEmail.setText("Por favor, insira ao menos uma forma de contato");
			errorPhone.setText("Por favor, insira ao menos uma forma de contato");
			errorPhone.setVisible(true);
			errorEmail.setVisible(true);
			validate = false;
			contact = true;
		}

	
		
		//Adicionar icone de erro nas Tabs
		if(customer)
			customerTab.setGraphic(new ErrorIcon());
		if(contact)
			contactTab.setGraphic(new ErrorIcon());
		if(address)
			addressTab.setGraphic(new ErrorIcon());
		
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
	void keyPressed(KeyEvent event) {
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
	
	/*
	 * 	##	VALIDATION ERRORS LABELS
	 */
	
	void loadValidationErrors() {
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
	
	void resetValidation() {
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
	
	void loadTab() {
		tabPane.setOnMouseClicked(event -> {
			if(tabPane.getSelectionModel().getSelectedIndex() == 0)
				name.requestFocus();
			if(tabPane.getSelectionModel().getSelectedIndex() == 1)
				email.requestFocus();
			if(tabPane.getSelectionModel().getSelectedIndex() == 2)
				zipCode.requestFocus();
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
