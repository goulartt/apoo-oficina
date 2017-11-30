package tattool.views.controller.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import com.jfoenix.controls.events.JFXDialogEvent;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
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
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import tattool.domain.model.Customer;
import tattool.domain.model.Service;
import tattool.domain.model.Session;
import tattool.rest.consume.ServiceRest;
import tattool.rest.consume.SessionRest;
import tattool.util.DateUtil;
import tattool.util.MaskFieldUtil;

public class CreateEditServiceController {
	
	private JFXPopup popup = new JFXPopup();

    @FXML
    private JFXTabPane tabPane;

    @FXML
    private Tab serviceTab;

    @FXML
    private JFXCheckBox priceCheckbox;
    
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
    private JFXDatePicker firstDate;

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
    
    @FXML
    JFXListView<ArtListItem> artsList;
    
    public JFXDialog customerModal;
    
    public JFXDialog artModal;
    
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
    	popup();
    	
    	artsList.setCellFactory(value -> {
    		JFXListCell<ArtListItem> cell = new JFXListCell<>();
    		
    		cell.setOnMouseClicked(event -> {
    			if(event.getButton().equals(MouseButton.SECONDARY)) {
    				popup.show(cell, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, event.getX(), event.getY());
    			}
    		});
    		
    		return cell;
    	});
    	
    	firstBegin.setIs24HourView(true);
    	Platform.runLater(new Runnable() {
	        @Override
	        public void run() {
	            name.requestFocus();
	        }
	    });
    	MaskFieldUtil.monetaryField(price);
    	MaskFieldUtil.numericField(numberSessions);
    	numberSessions.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if(newValue.equals("0")) {
					sessionsTab.setDisable(true);
					sessionsTab.setTooltip(new Tooltip("Adicione um valor maior que zero para habilitar essa tab"));
				}else {
					sessionsTab.setDisable(false);
					sessionsTab.setTooltip(new Tooltip("Cadastre rapidamente a primeira sessão"));
				}
			}
    	});
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
    		if(!numberSessions.getText().equals("0"))
    		{
    			Session session = new Session();
        		Integer cont = serviceSalvo.getQuantSessions();
    			session.setDateSession(DateUtil.asDate(firstBegin.getValue().atDate(firstDate.getValue())));
    			session.setPrice(new BigDecimal(price.getText()));
    			session.setService(serviceSalvo);
    			session.setDuration(Integer.parseInt(firstTime.getText()));
    			session.setStatus("AGENDADO");
    			BigDecimal preco = null;
    			if(priceCheckbox.isSelected())
    				preco = new BigDecimal(price.getText());
    			
    			sessionRest.save(session);
    			System.out.println(session.getPrice());
    			cont--;
        		for(int i = 0; i < cont; i++) {
        			Session sessionNew = new Session();
        			sessionNew.setService(serviceSalvo);
        			sessionNew.setPrice(preco);
        			sessionRest.save(sessionNew);
        		}
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
			errorNumberSessions.setText("Por favor informe o número de sessões");
			errorNumberSessions.setVisible(true);
			validate = false;
			service = true;
		}
		else if(numberSessions.getText().equals("0")) {
			
			
			
		}
		else
		{
			if(!numberSessions.getText().matches("['0-9']") && price.getText().matches("[a-Z]"))
			{
				errorNumberSessions.setText("Número de sessões pode conter apenas números");
				errorNumberSessions.setVisible(true);
				validate = false;
				service = true;
			}
	    	firstDate.setEditable(false);
	    	firstBegin.setEditable(false);
			price.setDisable(false);
			firstDate.setDisable(false);
			firstTime.setDisable(false);
			firstBegin.setDisable(false);
			if(price.getText().isEmpty())
			{
				errorPrice.setText("É necessário informar o valor da sessão");
				errorPrice.setVisible(true);
				validate = false;
				sessions = true;
			}else
			{
				if(!price.getText().matches("['0-9']")  && !price.getText().contains("."))
				{
					errorPrice.setText("Preço inválido");
					errorPrice.setVisible(true);
					validate = false;
					sessions = true;
				}
			}
			
			
			if(firstDate.getValue() == null) {
				errorFirstDate.setText("Informe a data da primeira sessão");
				errorFirstDate.setVisible(true);
				validate = false;
				sessions = true;
			}
			
			if(firstDate.getValue().isBefore(LocalDate.now())) {
				errorFirstDate.setText("Informe uma data que não seja retroativa ");
				errorFirstDate.setVisible(true);
				validate = false;
				sessions = true;
			}
			
	
			if(firstBegin.getValue() == null)
			{
				errorFirstBegin.setText("Informe o horário da sessão");
				errorFirstBegin.setVisible(true);
				validate = false;
				sessions = true;
			}
			if(firstTime.getText().isEmpty())
			{
				errorFirstTime.setText("Informe a duração da sessão");
				errorFirstTime.setVisible(true);
				validate = false;
				sessions = true;
			}else
			{
				if(!firstTime.getText().matches("\\d+"))
				{
					errorFirstTime.setText("Informe a duração em minutos");
					errorFirstTime.setVisible(true);
					validate = false;
					sessions = true;
				}
			}
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
     * 	##	GRID SELECT ARTS
     */
    
    void artGrid(StackPane mainStack) {
    	try {
			FXMLLoader artLoader = new FXMLLoader(getClass().getResource("/views/services/art-grid.fxml"));
			ArtGridController control = new ArtGridController(this);
			artLoader.setController(control);
			Region artContent    = artLoader.load();
			artModal   = new JFXDialog(mainStack, artContent, JFXDialog.DialogTransition.CENTER, false);
			artModal.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /*
     * 	POPUP-ART-LIST
     */
    
    void popup()
    {
    	JFXButton remove   = new JFXButton("Remover");
    	
    	VBox vbox        = new VBox();
    	
    	//Popup Menu Events
    	
    	remove.setOnMouseClicked(event -> {
    		popup.hide();
    		
    		//REMOVE A ARTE DA LISTA
    	});
    	
    	remove.setMaxWidth(Double.MAX_VALUE);
    	
    	vbox.setFillWidth(true);
    	vbox.getChildren().addAll(remove);
    	
    	popup.setPopupContent(vbox);
    }
    
    /*
     * 	##	CLASSE DA LISTA DE ARTES
     */
    
    public ArtListItem addArtItem(String text) {
    	return new ArtListItem(text);
    }
    
    private class ArtListItem extends Label {
    	
    	ArtListItem(String text) {
    		setText(text);
    		MaterialDesignIconView icon = new MaterialDesignIconView(MaterialDesignIcon.IMAGE);
    		icon.setGlyphSize(22);
    		setGraphic(icon);
    		setGraphicTextGap(10);
    		
    		getStyleClass().add("art-list-item");
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
     * 	## GRID PARA SELECIONAR A ARTE    
     */
    
    @FXML
    void artGrid(ActionEvent event) {
    	artGrid((StackPane) ((Node) event.getSource()).getScene().lookup("#mainStack"));
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
    	if(event.getSource().equals(numberSessions)){
    		if(numberSessions.getText().equals("0"))
    		{
    			price.clear();
    			firstDate.setValue(null);
    			firstBegin.setValue(null);
    			firstTime.clear();
    		}
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
