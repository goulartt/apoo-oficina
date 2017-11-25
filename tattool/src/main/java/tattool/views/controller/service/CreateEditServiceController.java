package tattool.views.controller.service;

import java.io.IOException;
import java.math.BigDecimal;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import com.jfoenix.controls.events.JFXDialogEvent;

import de.jensd.fx.glyphs.octicons.OctIconView;
import javafx.application.Platform;
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
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import tattool.domain.model.Customer;
import tattool.domain.model.Service;
import tattool.domain.model.Session;
import tattool.rest.consume.ServiceRest;
import tattool.rest.consume.SessionRest;
import tattool.util.DateUtil;

public class CreateEditServiceController {

    @FXML
    private JFXTabPane tabPane;

    @FXML
    private Tab serviceTab;

    @FXML
    private Tab sessionsTab;

    @FXML
    private JFXTextField name;

    @FXML
    public JFXTextField customer;

    @FXML
    private JFXTextField numberSessions;

    @FXML
    private JFXTextField price;


    @FXML
    private JFXDatePicker firstsubDate;

    @FXML
    private JFXTimePicker firstBegin;
    
    @FXML
    private JFXTextField firstTime;
    
    @FXML
    private Label errorName;

    @FXML
    private Label errorCustomer;

    @FXML
    private Label errorNumberSessions;

    @FXML
    private Label errorPrice;

    @FXML
    private Label errorFirstDate;

    @FXML
    private Label errorFirstBegin;

    @FXML
    private Label errorFirstTime;
    
    public JFXDialog customerModal;
    
    private ServiceRest rest = new ServiceRest();
    private SessionRest sessionRest = new SessionRest();
    
    public Customer cliente = new Customer();
    
    private Service serviceCarregado = new Service();
    
    /*
     * 	## INITIALIZE
     */
    
    public CreateEditServiceController(Service serviceCarregado) {
    	this.serviceCarregado = serviceCarregado;
    }

	public void initialize() {
		carregaCampos();
    	loadValidationErrors();
    	loadTab();
    	firstBegin.setIs24HourView(true);
    	Platform.runLater(new Runnable() {
	        @Override
	        public void run() {
	            name.requestFocus();
	        }
	    });
    	firstBegin.setDisable(true);
    	firstTime.setDisable(true);
    }
    
    private void carregaCampos() {
    	if(serviceCarregado.getId() != null) {
    		name.setText(serviceCarregado.getNameService());
        	customer.setText(serviceCarregado.getCustomer().getName());
        	cliente = serviceCarregado.getCustomer();
        	numberSessions.setText(serviceCarregado.getQuantSessions().toString());
        	sessionsTab.setDisable(true);
        	
    	}
    	
		
	}

	/*
     * 	## CADASTRA NOVO SERVIÇO
     */
    
    void store(StackPane mainStack) {
    	
    	if(validate()) {
    		loadDialog(mainStack);
    		Service service = new Service();
    		
    		service.setCustomer(cliente);  //verifica se tem cliente
    		service.setNameService(name.getText()); //obrigatorio
    		
    		service.setQuantSessions(Integer.parseInt(numberSessions.getText())); //tenta entender minha logica ai embaixo e faz validação
    		Service serviceSalvo = rest.save(service);
    		Session session = new Session();
    		Integer cont = serviceSalvo.getQuantSessions();
			session.setDateSession(DateUtil.asDate(firstBegin.getValue().atDate(firstsubDate.getValue())));
			session.setPrice(new BigDecimal(price.getText()));
			session.setService(serviceSalvo);
			session.setDuration(Integer.parseInt(firstTime.getText()));
			sessionRest.save(session);
			cont--;
    		for(int i = 0; i <= cont; i++) {
    			Session sessionNew = new Session();
    			sessionNew.setService(serviceSalvo);
    			sessionRest.save(sessionNew);
    		}
    		
    		
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
		
		dialogContent.setHeading(new Text("Serviço cadastrado com sucesso!"));
		
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
		boolean service = false, sessions = false;
		
		resetValidation();
		
		if(name.getText().isEmpty())
		{
			errorName.setText("Insira um nome para o serviço");
			errorName.setVisible(true);
			validate = false;
			service = true;
		}
		if(customer.getText().isEmpty())
		{
			errorCustomer.setText("Informe o cliente do serviço");
			errorCustomer.setVisible(true);
			validate = false;
			service  = true;
		}
		if(numberSessions.getText().isEmpty())
		{
			errorNumberSessions.setText("Obrigatório");
			errorNumberSessions.setVisible(true);
			validate = false;
			sessions = true;
		}
		else
		{
			if(price.getText().isEmpty())
			{
				errorPrice.setText("É necessário informar o valor de cada sessão");
				errorPrice.setVisible(true);
				validate = true;
				service = false;
			}
			if(!firstBegin.getValue().equals(""))
			{
				errorFirstBegin.setText("É necessário informar a data da sessão inicial");
				errorFirstBegin.setVisible(true);
				validate = true;
				service = false;
			}
			/*if(!firstTime.get().equals(""))
			{
				
			}
			*/
			
			
		}
		
		
		//Adicionar icone de erro nas Tabs
		if(service)
			serviceTab.setGraphic(new ErrorIcon());
		if(sessions)
			sessionsTab.setGraphic(new ErrorIcon());
		
		return validate;
	}
    
    /*
     * 	##	GRID SELECT CUSTOMER
     */
    
    void customerGrid(StackPane mainStack) {
    	try {
			FXMLLoader customerLoader = new FXMLLoader(getClass().getResource("/views/services/customer-grid.fxml"));
			CustomerGridController control = new CustomerGridController(this);
			customerLoader.setController(control);
			Region customerContent    = customerLoader.load();
			customerModal   = new JFXDialog(mainStack, customerContent, JFXDialog.DialogTransition.CENTER, false);
			customerModal.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /*
     * 	##	VOLTA
     */
    
    void back(BorderPane main) {
    	try {
    		FXMLLoader viewLoader = new FXMLLoader(getClass().getResource("/views/services/services.fxml"));
    		
    		viewLoader.setRoot(main);
    		main.getChildren().clear();
    		viewLoader.load();
    	}catch(Exception e) {
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

    /*
     * 	## GRID PARA SELECIONAR O CLIENTE    
     */
    
    @FXML
    void customerGrid(ActionEvent event) {
    	customerGrid((StackPane) ((Node) event.getSource()).getScene().lookup("#mainStack"));
    }

    /*
     * 	##	TECLAS DE ATALHO
     */
    
    @FXML
    void keyPressed(KeyEvent event) {
    	switch(event.getCode()) {
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
	
	void loadValidationErrors()
	{
		errorName.managedProperty().bind(errorName.visibleProperty());
		errorCustomer.managedProperty().bind(errorCustomer.visibleProperty());
		errorNumberSessions.managedProperty().bind(errorNumberSessions.visibleProperty());
		errorPrice.managedProperty().bind(errorPrice.visibleProperty());
		errorFirstDate.managedProperty().bind(errorFirstDate.visibleProperty());
		errorFirstBegin.managedProperty().bind(errorFirstBegin.visibleProperty());
		errorFirstTime.managedProperty().bind(errorFirstTime.visibleProperty());
    	
    	resetValidation();
	}
	
	void resetValidation()
	{
		errorName.setVisible(false);
		errorCustomer.setVisible(false);
		errorNumberSessions.setVisible(false);
		errorPrice.setVisible(false);
		errorFirstDate.setVisible(false);
		errorFirstBegin.setVisible(false);
		errorFirstTime.setVisible(false);
    	serviceTab.setGraphic(null);
    	sessionsTab.setGraphic(null);
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
				numberSessions.requestFocus();
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
