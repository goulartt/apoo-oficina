package tattool.views.controller.customer;

import java.io.IOException;
import java.util.function.Predicate;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableRow;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import de.jensd.fx.glyphs.octicons.OctIconView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import tattool.domain.model.Customer;
import tattool.domain.modelfx.CustomerFX;
import tattool.rest.consume.CustomerRest;
import tattool.util.ConvertModelToFX;

public class CustomerController {

    @FXML
    private JFXTreeTableView<CustomerFX> customerTable;

    @FXML
    private JFXTextField search;
    
    JFXPopup popup = new JFXPopup();
    
    @FXML
    private OctIconView closeButton;
    
    private CustomerRest rest = new CustomerRest();
    
    public void initialize()
    {
    	createTableColumns();
    	populateTable();
    	search();
    	popup();
    }
    
    /*
     * 	##	CADASTRAR CLIENTE
     */

    @FXML
    void create(ActionEvent event){
    	try {
    		FXMLLoader viewLoader = new FXMLLoader(getClass().getResource("/views/customers/create-edit.fxml"));
    		BorderPane main       = (BorderPane) ((Node) event.getSource()).getScene().lookup("#main");
    		viewLoader.setController(new CreateEditCustomerController(new Customer()));
    		viewLoader.setRoot(main);
    		main.getChildren().clear();
    		viewLoader.load();
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
    
    /*
     * 	##	CRIA COLUNAS DA TABLE
     */
    
    @SuppressWarnings("unchecked")
	void createTableColumns()
    {
    	
    	
    	JFXTreeTableColumn<CustomerFX,String> cpf     = new JFXTreeTableColumn<>("CPF");
    	JFXTreeTableColumn<CustomerFX,String> name    = new JFXTreeTableColumn<>("Nome");
    	JFXTreeTableColumn<CustomerFX,String> contact = new JFXTreeTableColumn<>("Contato");
    	
    	//Colunas com largura responsiva
    	
    	cpf.prefWidthProperty().bind(customerTable.widthProperty().multiply(0.3));
    	name.prefWidthProperty().bind(customerTable.widthProperty().multiply(0.3));
    	contact.prefWidthProperty().bind(customerTable.widthProperty().multiply(0.4));
    	
    	cpf.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<CustomerFX, String>, ObservableValue<String>>()
    	{
			@Override
			public ObservableValue<String> call(CellDataFeatures<CustomerFX, String> param)
			{
				return param.getValue().getValue().cpf;
			}
    	});
    	name.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<CustomerFX, String>, ObservableValue<String>>()
    	{
			@Override
			public ObservableValue<String> call(CellDataFeatures<CustomerFX, String> param)
			{
				return param.getValue().getValue().name;
			}
    	});
    	contact.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<CustomerFX, String>, ObservableValue<String>>()
    	{
			@Override
			public ObservableValue<String> call(CellDataFeatures<CustomerFX, String> param)
			{
				return param.getValue().getValue().contactSimple;
			}
    	});
    	
    	customerTable.getColumns().setAll(cpf, name, contact);
    	
    	//Popup click event
    	customerTable.setRowFactory(table -> {
    		JFXTreeTableRow<CustomerFX> row = new JFXTreeTableRow<>();
    		
    		row.setOnMouseClicked(event -> {
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
    	//Testando a populacao da table
	
    	
    	
    	ObservableList<CustomerFX> costumers = FXCollections.observableArrayList();
    	
    	costumers = FXCollections.observableArrayList(ConvertModelToFX.convertListCustomer(rest.findAll()));
  
    	final TreeItem<CustomerFX> root = new RecursiveTreeItem<CustomerFX>(costumers, RecursiveTreeObject::getChildren);
    	
    	customerTable.setRoot(root);
    	customerTable.setShowRoot(false);
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
				customerTable.setPredicate(new Predicate<TreeItem<CustomerFX>>()
				{
					@Override
					public boolean test(TreeItem<CustomerFX> user)
					{
						//Compara o valor do TextInput com as colunas da table
						
						return user.getValue().cpf.getValue().toLowerCase().contains(newValue.toLowerCase())     ||
							   user.getValue().name.getValue().toLowerCase().contains(newValue.toLowerCase());
					}
				});
			}
    	});
    }
    
    /*
     * 	##	TABLE POP-UP
     */
    
    void popup()
    {
    	JFXButton show   = new JFXButton("Visualizar");
    	JFXButton edit   = new JFXButton("Editar");
    	JFXButton delete = new JFXButton("Apagar");
    	
    	VBox vbox        = new VBox();
    	
    	//Popup Menu Events
    	
    	show.setOnMouseClicked(event -> {
    		popup.hide();
    		show((StackPane) customerTable.getScene().lookup("#mainStack"));
    	});
    	edit.setOnMouseClicked(event -> {
    		popup.hide();
    		edit();
    	});
    	delete.setOnMouseClicked(event -> {
    		popup.hide();
    		delete();
    	});
    	
    	show.setMaxWidth(Double.MAX_VALUE);
    	edit.setMaxWidth(Double.MAX_VALUE);
    	delete.setMaxWidth(Double.MAX_VALUE);
    	
    	vbox.setFillWidth(true);
    	vbox.getChildren().addAll(show, edit, delete);
    	
    	popup.setPopupContent(vbox);
    }
    
    /*
     * 	##	VISUALIZAR CLIENTE
     */
    
    void show(StackPane mainStack) {
		try {
			FXMLLoader customerLoader = new FXMLLoader(getClass().getResource("/views/customers/show-customer.fxml"));
			customerLoader.setController(new ShowCustomerController(ConvertModelToFX.convertCustomerFXtoCustomer(customerTable.getSelectionModel().getSelectedItem().getValue())));
			Region customerContent = customerLoader.load();
			JFXDialog customerModal = new JFXDialog(mainStack, customerContent, JFXDialog.DialogTransition.CENTER, false);
			OctIconView closeButton = (OctIconView) mainStack.getScene().lookup("#closeButton");
    		closeButton.setOnMouseClicked(event -> customerModal.close());
			customerModal.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /*
     * 	##	EDITAR CLIENTE
     */
    
    void edit()
    {
    	try {
    		FXMLLoader viewLoader = new FXMLLoader(getClass().getResource("/views/customers/create-edit.fxml"));
    		BorderPane main       = (BorderPane) customerTable.getScene().lookup("#main");
    		Customer customerCarregado = ConvertModelToFX.convertCustomerFXtoCustomer(customerTable.getSelectionModel().getSelectedItem().getValue());
    		viewLoader.setController(new CreateEditCustomerController(customerCarregado));
    		//control.customer =  ConvertModelToFX.convertCustomerFXtoCustomer(customerTable.getSelectionModel().getSelectedItem().getValue());
    		viewLoader.setRoot(main);
    		main.getChildren().clear();
    		viewLoader.load();
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
    
    /*
     * 	##	APAGAR CLIENTE
     */
    
    void delete()
    {
    	loadDialogDelete((StackPane) customerTable.getScene().lookup("#mainStack"));
    }
    
    /*
     * 	 DIALOGO DELETE
     */
    
    void loadDialogDelete(StackPane mainStack) {
    	
    	JFXDialogLayout dialogContent = new JFXDialogLayout();
    	JFXDialog dialog              = new JFXDialog(mainStack, dialogContent, JFXDialog.DialogTransition.CENTER);
    	JFXButton yes                 = new JFXButton("Sim");
    	JFXButton no                  = new JFXButton("Não");
    	
    	dialogContent.setHeading(new Text("Tem certeza que quer excluir este cliente?"));
    	dialogContent.setBody(new Text("Todos os dados deste cliente serão perdidos."));
    	
		yes.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				rest.deleteCustomer(customerTable.getSelectionModel().getSelectedItem().getValue().getId());
	    		dialog.close();
	    		populateTable();
			}
		});
		no.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				dialog.close();
			}
		});
		
		dialogContent.setActions(no, yes);
		dialog.setOverlayClose(false);
		dialog.show();
    }
    

    
}
