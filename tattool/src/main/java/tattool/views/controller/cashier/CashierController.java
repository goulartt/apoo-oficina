package tattool.views.controller.cashier;

import java.math.BigDecimal;
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
import com.sun.javafx.scene.control.skin.VirtualFlow;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;
import tattool.domain.model.Session;
import tattool.domain.modelfx.SessionCashierFX;
import tattool.rest.consume.SessionRest;
import tattool.util.ConvertModelToFX;

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
    private JFXTreeTableView<SessionCashierFX> cashierTable;
    
    private SessionRest rest = new SessionRest();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		cashierPanel.prefWidthProperty().bind(Bindings.selectDouble(cashierPanel.parentProperty(), "width").multiply(0.3));
		
		createNoteButton.managedProperty().bind(createNoteButton.visibleProperty());
		updateNoteButton.managedProperty().bind(updateNoteButton.visibleProperty());
		deleteNoteButton.managedProperty().bind(deleteNoteButton.visibleProperty());
		noteLabel.managedProperty().bind(noteLabel.visibleProperty());
		obs.managedProperty().bind(obs.visibleProperty());
		
		escondeNota();
		
		createTableColumns();
		populateTable();
		search();
		popup();
	}

	public void escondeNota() {
		createNoteButton.setVisible(false);
		updateNoteButton.setVisible(false);
		deleteNoteButton.setVisible(false);
		obs.setVisible(false);
	}
	
	/*
	 * 	##	CRIA UMA NOVA NOTA PARA A SESSÃO
	 */
	
	@FXML
    void createNote(ActionEvent event) {
		noteLabel.setVisible(false);
		createNoteButton.setVisible(false);
		obs.setText("");
		obs.setVisible(true);
		obs.requestFocus();
		updateNoteButton.setVisible(true);
		deleteNoteButton.setVisible(true);

    }
	
	/*
	 * 	##	APAGA A NOTA DA SESSÃO
	 */
	
    @FXML
    void deleteNote(ActionEvent event) {
    	SessionCashierFX cashierSession =  cashierTable.getSelectionModel().getSelectedItem().getValue();
		Session s = ConvertModelToFX.convertSessionCashierFXToSession(cashierSession);
		s.setObs("");
		rest.save(s);
		populateTable();
		escondeNota();
    }
    
    /*
     * 	##	ALTERA A NOTA DA SESSÃO
     */

    @FXML
    void updateNote(ActionEvent event) {
    	SessionCashierFX cashierSession =  cashierTable.getSelectionModel().getSelectedItem().getValue();
		Session s = ConvertModelToFX.convertSessionCashierFXToSession(cashierSession);
		s.setObs(obs.getText());
		rest.save(s);
		populateTable();
		escondeNota();
    }
	
	/*
	 * 	##	CRIA COLUNAS DA TABLE
	 */
	
	@SuppressWarnings("unchecked")
	void createTableColumns()
    {
		JFXTreeTableColumn<SessionCashierFX,String> date    = new JFXTreeTableColumn<>("Data");
		JFXTreeTableColumn<SessionCashierFX,String> service = new JFXTreeTableColumn<>("Serviço");
		JFXTreeTableColumn<SessionCashierFX,String> price   = new JFXTreeTableColumn<>("Preço");
		JFXTreeTableColumn<SessionCashierFX,String> paid    = new JFXTreeTableColumn<>("Pago");
		
    	
    	//Colunas com largura responsiva
    	
		date.prefWidthProperty().bind(cashierTable.widthProperty().multiply(0.2));
		service.prefWidthProperty().bind(cashierTable.widthProperty().multiply(0.4));
		price.prefWidthProperty().bind(cashierTable.widthProperty().multiply(0.2));
		paid.prefWidthProperty().bind(cashierTable.widthProperty().multiply(0.2));
    	
		date.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<SessionCashierFX, String>, ObservableValue<String>>()
    	{
			@Override
			public ObservableValue<String> call(CellDataFeatures<SessionCashierFX, String> param)
			{
				return param.getValue().getValue().date;
			}
    	});
		service.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<SessionCashierFX, String>, ObservableValue<String>>()
    	{
			@Override
			public ObservableValue<String> call(CellDataFeatures<SessionCashierFX, String> param)
			{
				return param.getValue().getValue().nomeServico;
			}
    	});
		price.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<SessionCashierFX, String>, ObservableValue<String>>()
    	{
			@Override
			public ObservableValue<String> call(CellDataFeatures<SessionCashierFX, String> param)
			{
				return param.getValue().getValue().preco;
			}
    	});
		paid.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<SessionCashierFX, String>, ObservableValue<String>>()
    	{
			@Override
			public ObservableValue<String> call(CellDataFeatures<SessionCashierFX, String> param)
			{
				return param.getValue().getValue().pago;
			}
    	});
	
	    paid.setCellFactory(new Callback<TreeTableColumn<SessionCashierFX, String>, TreeTableCell<SessionCashierFX, String>>() {
	       

			@Override
			public TreeTableCell<SessionCashierFX, String> call(TreeTableColumn<SessionCashierFX, String> param) {
				return new TreeTableCell<SessionCashierFX, String>() {
	                Rectangle deve = new Rectangle(10, 10, Color.RED);
	                {
	                    setGraphic(deve);
	                };
	                Rectangle pago = new Rectangle(10, 10, Color.GREEN);
	                {
	                    setGraphic(pago);
	                };
	                Rectangle pendente = new Rectangle(10, 10, Color.YELLOW);
	                {
	                    setGraphic(pendente);
	                };

	                @Override
	                protected void updateItem(String item, boolean empty) {
	                    super.updateItem(item, empty);
	                    if (item == null || empty) {
	                    	deve.setVisible(false);
	                        setText(null);
	                    } else {
	                    	if(new BigDecimal(item).intValueExact() > 0){
	                    		deve.setVisible(true);
	  	                        setText(item);
	  	                    }else {
	  	                    	pendente.setVisible(true);
		                        setText(item);
	  	                    }
	                    	
	                    	
	                    }
	                  
	                }
	            };
			}
	    });


				
		cashierTable.getColumns().setAll(date, service, price, paid);
		
		cashierTable.setRowFactory(table -> {
    		JFXTreeTableRow<SessionCashierFX> row = new JFXTreeTableRow<>();
    		
    		row.setOnMouseClicked(event -> {
    			if(event.getButton().equals(MouseButton.PRIMARY))
    			{
    				SessionCashierFX cashierSession =  cashierTable.getSelectionModel().getSelectedItem().getValue();
    				
    				if(cashierSession.getObs().equals("")) {
    					noteLabel.setVisible(false);
    					createNoteButton.setVisible(true);
    					obs.setText(cashierSession.getObs());
    					obs.setVisible(false);
    					updateNoteButton.setVisible(false);
    					deleteNoteButton.setVisible(false);
    				} else {
    					noteLabel.setVisible(false);
    					createNoteButton.setVisible(false);
    					obs.setText(cashierSession.getObs());
    					obs.setVisible(true);
    					obs.requestFocus();
    					updateNoteButton.setVisible(true);
    					deleteNoteButton.setVisible(true);
    				}
    				sessions.setText(cashierSession.getService().getQuantSessions().toString());
    				if(cashierSession.getPaid() == null) {
    					cashierSession.setPaid(new BigDecimal(0));
    				}
    				this.paid.setText("R$ "+cashierSession.getPaid().toString());
    				total.setText("R$ "+cashierSession.getPrice().toString());
    				BigDecimal balanco = cashierSession.getPrice().subtract(cashierSession.getPaid());
    				balance.setText("R$ "+balanco.toString());
    				
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
    	ObservableList<SessionCashierFX> cashierSessions = FXCollections.observableArrayList();
    	cashierSessions =  FXCollections.observableArrayList(ConvertModelToFX.convertSessinToSessionCashierFX(rest.findAll()));

  
    	final TreeItem<SessionCashierFX> root = new RecursiveTreeItem<SessionCashierFX>(cashierSessions, RecursiveTreeObject::getChildren);
    	
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
    		SessionCashierFX cashierSession =  cashierTable.getSelectionModel().getSelectedItem().getValue();
    		Session s = ConvertModelToFX.convertSessionCashierFXToSession(cashierSession);
    		if(s.getDateSession() != null) {
    			s.setStatus("PAGO");
        		s.setPaid(s.getPrice());
        		rest.save(s);
        		populateTable();
    		}else {
    			
    		}
    		
    		popup.hide();
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
				cashierTable.setPredicate(new Predicate<TreeItem<SessionCashierFX>>()
				{
					@Override
					public boolean test(TreeItem<SessionCashierFX> cashierSession)
					{
						//Compara o valor do TextInput com as colunas da table
						
						return cashierSession.getValue().date.getValue().toLowerCase().contains(newValue.toLowerCase())     ||
							   cashierSession.getValue().nomeServico.getValue().toLowerCase().contains(newValue.toLowerCase())  || 
							   cashierSession.getValue().preco.getValue().toLowerCase().contains(newValue.toLowerCase())    || 
							   cashierSession.getValue().pago.getValue().toLowerCase().contains(newValue.toLowerCase());
					}
				});
			}
    	});
    }
	

}