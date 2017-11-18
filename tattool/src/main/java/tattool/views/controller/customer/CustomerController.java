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
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
//import tattool.domain.modelfx.UserFX;

public class CustomerController {

    @FXML
    private JFXTreeTableView<Costumer> customerTable;

    @FXML
    private JFXTextField search;
    
    JFXPopup popup = new JFXPopup();
    
    @FXML
    private OctIconView closeButton;
    
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
    	
    	
    	JFXTreeTableColumn<Costumer,String> cpf     = new JFXTreeTableColumn<>("CPF");
    	JFXTreeTableColumn<Costumer,String> name    = new JFXTreeTableColumn<>("Nome");
    	JFXTreeTableColumn<Costumer,String> contact = new JFXTreeTableColumn<>("Contato");
    	
    	//Colunas com largura responsiva
    	
    	cpf.prefWidthProperty().bind(customerTable.widthProperty().multiply(0.3));
    	name.prefWidthProperty().bind(customerTable.widthProperty().multiply(0.3));
    	contact.prefWidthProperty().bind(customerTable.widthProperty().multiply(0.4));
    	
    	cpf.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Costumer, String>, ObservableValue<String>>()
    	{
			@Override
			public ObservableValue<String> call(CellDataFeatures<Costumer, String> param)
			{
				return param.getValue().getValue().cpf;
			}
    	});
    	name.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Costumer, String>, ObservableValue<String>>()
    	{
			@Override
			public ObservableValue<String> call(CellDataFeatures<Costumer, String> param)
			{
				return param.getValue().getValue().name;
			}
    	});
    	contact.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Costumer, String>, ObservableValue<String>>()
    	{
			@Override
			public ObservableValue<String> call(CellDataFeatures<Costumer, String> param)
			{
				return param.getValue().getValue().contact;
			}
    	});
    	
    	customerTable.getColumns().setAll(cpf, name, contact);
    	
    	//Popup click event
    	customerTable.setRowFactory(table -> {
    		JFXTreeTableRow<Costumer> row = new JFXTreeTableRow<>();
    		
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
    	
    	ObservableList<Costumer> costumers = FXCollections.observableArrayList();
    	
    	costumers.add(new Costumer("123.456.789-10", "Ricardão", "(14) 99669 1426"));
    	costumers.add(new Costumer("659.148.235-56", "Jean Carlos", "(99) 65984 1596"));
    	costumers.add(new Costumer("648.215.623-84", "João Goulart", "joao@joao.com"));
    	costumers.add(new Costumer("496.154.914-62", "Breno Pikachu", "breno@pokemon.com"));
    	costumers.add(new Costumer("134.648.974-22", "Tudo Puta", "(99) 99999 9999"));
    	costumers.add(new Costumer("123.659.487-65", "Que Loucura", "(22) 1432 6458"));
    	costumers.add(new Costumer("148.517.321-54", "Catinguele", "(22) 1846-9844"));
    	
    	final TreeItem<Costumer> root = new RecursiveTreeItem<Costumer>(costumers, RecursiveTreeObject::getChildren);
    	
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
				customerTable.setPredicate(new Predicate<TreeItem<Costumer>>()
				{
					@Override
					public boolean test(TreeItem<Costumer> user)
					{
						//Compara o valor do TextInput com as colunas da table
						
						return user.getValue().cpf.getValue().toLowerCase().contains(newValue.toLowerCase())     ||
							   user.getValue().name.getValue().toLowerCase().contains(newValue.toLowerCase())    ||
							   user.getValue().contact.getValue().toLowerCase().contains(newValue.toLowerCase());
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
				
				//CLICOU EM SIM -> DELETA O CLIENTE DO SISTEMA
				
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
    
    /*
     * 	##	CLASSE PRA TESTAR A TABLE
     * 	##	RecursiveTreeObject -> clase do JFoenix pra popular a Table deles
     */
    
    class Costumer extends RecursiveTreeObject<Costumer> {
    	StringProperty cpf;
    	StringProperty name;
    	StringProperty contact;
    	
    	public Costumer(String cpf, String name, String contact) {
    		this.cpf     = new SimpleStringProperty(cpf);
    		this.name    = new SimpleStringProperty(name);
    		this.contact = new SimpleStringProperty(contact);
    	}
    }
}
