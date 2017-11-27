package tattool.views.controller.cashier;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableRow;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class CashierController implements Initializable {

	JFXPopup popup = new JFXPopup();
	
	@FXML
    private BorderPane cashierPanel;

    @FXML
    private JFXTextArea obs;

    @FXML
    private JFXTextField search;
    
    @FXML
    private JFXButton createNoteButton;

    @FXML
    private JFXButton updateNoteButton;

    @FXML
    private JFXButton deleteNoteButton;
    
    @FXML
    private Label noteLabel;
    
    @FXML
    private Label sessions;

    @FXML
    private Label paid;

    @FXML
    private Label total;

    @FXML
    private Label balance;

    @FXML
    private JFXTreeTableView<CashierSession> cashierTable;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		cashierPanel.prefWidthProperty().bind(Bindings.selectDouble(cashierPanel.parentProperty(), "width").multiply(0.3));
		
		createNoteButton.managedProperty().bind(createNoteButton.visibleProperty());
		updateNoteButton.managedProperty().bind(updateNoteButton.visibleProperty());
		deleteNoteButton.managedProperty().bind(deleteNoteButton.visibleProperty());
		noteLabel.managedProperty().bind(noteLabel.visibleProperty());
		obs.managedProperty().bind(obs.visibleProperty());
		
		createNoteButton.setVisible(false);
		updateNoteButton.setVisible(false);
		deleteNoteButton.setVisible(false);
		obs.setVisible(false);
		
		createTableColumns();
		populateTable();
		search();
		popup();
	}
	
	/*
	 * 	##	CRIA UMA NOVA NOTA PARA A SESSÃO
	 */
	
	@FXML
    void createNote(ActionEvent event) {

    }
	
	/*
	 * 	##	APAGA A NOTA DA SESSÃO
	 */
	
    @FXML
    void deleteNote(ActionEvent event) {

    }
    
    /*
     * 	##	ALTERA A NOTA DA SESSÃO
     */

    @FXML
    void updateNote(ActionEvent event) {

    }
	
	/*
	 * 	##	CRIA COLUNAS DA TABLE
	 */
	
	@SuppressWarnings("unchecked")
	void createTableColumns()
    {
		JFXTreeTableColumn<CashierSession,String> date    = new JFXTreeTableColumn<>("Data");
		JFXTreeTableColumn<CashierSession,String> service = new JFXTreeTableColumn<>("Serviço");
		JFXTreeTableColumn<CashierSession,String> price   = new JFXTreeTableColumn<>("Preço");
		JFXTreeTableColumn<CashierSession,String> paid    = new JFXTreeTableColumn<>("Pago");
		
    	
    	//Colunas com largura responsiva
    	
		date.prefWidthProperty().bind(cashierTable.widthProperty().multiply(0.2));
		service.prefWidthProperty().bind(cashierTable.widthProperty().multiply(0.4));
		price.prefWidthProperty().bind(cashierTable.widthProperty().multiply(0.2));
		paid.prefWidthProperty().bind(cashierTable.widthProperty().multiply(0.2));
    	
		date.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<CashierSession, String>, ObservableValue<String>>()
    	{
			@Override
			public ObservableValue<String> call(CellDataFeatures<CashierSession, String> param)
			{
				return param.getValue().getValue().date;
			}
    	});
		service.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<CashierSession, String>, ObservableValue<String>>()
    	{
			@Override
			public ObservableValue<String> call(CellDataFeatures<CashierSession, String> param)
			{
				return param.getValue().getValue().service;
			}
    	});
		price.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<CashierSession, String>, ObservableValue<String>>()
    	{
			@Override
			public ObservableValue<String> call(CellDataFeatures<CashierSession, String> param)
			{
				return param.getValue().getValue().price;
			}
    	});
		paid.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<CashierSession, String>, ObservableValue<String>>()
    	{
			@Override
			public ObservableValue<String> call(CellDataFeatures<CashierSession, String> param)
			{
				return param.getValue().getValue().paid;
			}
    	});
    	
		cashierTable.getColumns().setAll(date, service, price, paid);
		
		cashierTable.setRowFactory(table -> {
    		JFXTreeTableRow<CashierSession> row = new JFXTreeTableRow<>();
    		
    		row.setOnMouseClicked(event -> {
    			if(event.getButton().equals(MouseButton.PRIMARY))
    			{
    				CashierSession cashierSession =  cashierTable.getSelectionModel().getSelectedItem().getValue();
    				
    				if(cashierSession.obs.getValue() == null) {
    					noteLabel.setVisible(false);
    					createNoteButton.setVisible(true);
    					obs.setText(cashierSession.obs.getValue());
    					obs.setVisible(false);
    					updateNoteButton.setVisible(false);
    					deleteNoteButton.setVisible(false);
    				} else {
    					noteLabel.setVisible(false);
    					createNoteButton.setVisible(false);
    					obs.setText(cashierSession.obs.getValue());
    					obs.setVisible(true);
    					obs.requestFocus();
    					updateNoteButton.setVisible(true);
    					deleteNoteButton.setVisible(true);
    				}
    			}
    			if(event.getButton().equals(MouseButton.SECONDARY))
    			{
    				popup.show(row, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, event.getX(), event.getY());
    			}
    		});
    		
    		return row;
    	});
    }
    
    /*
     * 	##	POPULA A TABLE
     */
    
    void populateTable()
    {
    	ObservableList<CashierSession> cashierSessions = FXCollections.observableArrayList();
    	
    	cashierSessions.add(new CashierSession("17/12/2017", "Serviço loco", "R$200.00", "R$000.00", "Cliente filha da puta não pagou esta merda mas me deu um cachorro em troca."));
    	cashierSessions.add(new CashierSession("15/12/2017", "Serviço loco", "R$200.00", "R$000.00", null));
    	cashierSessions.add(new CashierSession("14/12/2017", "Serviço merda", "R$200.00", "R$000.00", null));
    	cashierSessions.add(new CashierSession("14/12/2017", "Serviço loco", "R$200.00", "R$000.00", "Cliente filha da puta não pagou esta merda mas me deu um cachorro em troca."));
    	cashierSessions.add(new CashierSession("13/12/2017", "Serviço merda", "R$400.00", "R$400.00", "Cliente filha da puta não pagou esta merda mas me deu um cachorro em troca."));
    	cashierSessions.add(new CashierSession("11/12/2017", "Serviço loco", "R$300.00", "R$200.00", null));
    	cashierSessions.add(new CashierSession("10/12/2017", "Serviço loco", "R$200.00", "R$200.00", null));
  
    	final TreeItem<CashierSession> root = new RecursiveTreeItem<CashierSession>(cashierSessions, RecursiveTreeObject::getChildren);
    	
    	cashierTable.setRoot(root);
    	cashierTable.setShowRoot(false);
    }
    
    /*
     * 	##	TABLE POP-UP
     */
    
    void popup()
    {
    	JFXButton setPaid  = new JFXButton("Pago");
    	JFXButton setCheck = new JFXButton("Acertado");
    	VBox vbox          = new VBox();
    	
    	//Popup Menu Events
    	
    	setPaid.setOnMouseClicked(event -> {
    		popup.hide();
    		
    		//COLOCA O PRECO DA SESSAO EM PAGO, PRO USUARIO NAO PRECISAR PREENCHER MANUALMENTE O VALOR
    	});
    	setCheck.setOnMouseClicked(event -> {
    		popup.hide();
    		
    		//MARCA COMO ACERTADO, NAO SEI COMO SERA CALCULADO O BALANCO DISSO ENTAO SE VIRA
    	});
    	
    	setPaid.setMaxWidth(Double.MAX_VALUE);
    	setCheck.setMaxWidth(Double.MAX_VALUE);
    	
    	vbox.setFillWidth(true);
    	vbox.getChildren().addAll(setPaid, setCheck);
    	
    	popup.setPopupContent(vbox);
    }
	
    /*
     * 	##	FILTRO DE BUSCA
     */
    
    public void search()

    {
    	search.textProperty().addListener(new ChangeListener<String>()
    	{
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
			{
				cashierTable.setPredicate(new Predicate<TreeItem<CashierSession>>()
				{
					@Override
					public boolean test(TreeItem<CashierSession> cashierSession)
					{
						//Compara o valor do TextInput com as colunas da table
						
						return cashierSession.getValue().date.getValue().toLowerCase().contains(newValue.toLowerCase())     ||
							   cashierSession.getValue().service.getValue().toLowerCase().contains(newValue.toLowerCase())  || 
							   cashierSession.getValue().price.getValue().toLowerCase().contains(newValue.toLowerCase())    || 
							   cashierSession.getValue().paid.getValue().toLowerCase().contains(newValue.toLowerCase());
					}
				});
			}
    	});
    }
	
	/*
	 * 	##	CLASSE TESTE
	 */
	
	class CashierSession extends RecursiveTreeObject<CashierSession> {
		StringProperty date;
		StringProperty service;
		StringProperty price;
		StringProperty paid;
		StringProperty obs;
		
		CashierSession(String date, String service, String price, String paid, String obs) {
			this.date    = new SimpleStringProperty(date);
			this.service = new SimpleStringProperty(service);
			this.price   = new SimpleStringProperty(price);
			this.paid    = new SimpleStringProperty(paid);
			this.obs    = new SimpleStringProperty(obs);
		}
	}
}