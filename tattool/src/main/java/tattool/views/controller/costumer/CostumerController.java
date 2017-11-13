package tattool.views.controller.costumer;

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
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
//import tattool.domain.modelfx.UserFX;

public class CostumerController {

    @FXML
    private JFXTreeTableView<Costumer> costumerTable;

    @FXML
    private JFXTextField search;
    
    JFXPopup popup = new JFXPopup();
    
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
    		FXMLLoader viewLoader = new FXMLLoader(getClass().getResource("/views/costumers/create-edit.fxml"));
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
    	
    	cpf.prefWidthProperty().bind(costumerTable.widthProperty().multiply(0.3));
    	name.prefWidthProperty().bind(costumerTable.widthProperty().multiply(0.3));
    	contact.prefWidthProperty().bind(costumerTable.widthProperty().multiply(0.39));	//0.39 -> Gambiarra pra coluna nao atravessar a TableView
    	
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
    	
    	costumerTable.getColumns().setAll(cpf, name, contact);
    	
    	//Popup click event
    	costumerTable.setRowFactory(table -> {
    		JFXTreeTableRow<Costumer> row = new JFXTreeTableRow<>();
    		
    		row.setOnMouseClicked(event -> popup.show(row, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, event.getX(), event.getY()));
    		
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
    	
    	costumerTable.setRoot(root);
    	costumerTable.setShowRoot(false);
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
				costumerTable.setPredicate(new Predicate<TreeItem<Costumer>>()
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
    	JFXButton view   = new JFXButton("Visualizar");
    	JFXButton edit   = new JFXButton("Editar");
    	JFXButton delete = new JFXButton("Apagar");
    	
    	VBox vbox        = new VBox();
    	
    	//Popup Menu Events
    	
    	view.setOnMouseClicked(event -> {
    		popup.hide();
    		view();
    	});
    	edit.setOnMouseClicked(event -> {
    		popup.hide();
    		edit();
    	});
    	delete.setOnMouseClicked(event -> {
    		popup.hide();
    		delete();
    	});
    	
    	view.setMaxWidth(Double.MAX_VALUE);
    	edit.setMaxWidth(Double.MAX_VALUE);
    	delete.setMaxWidth(Double.MAX_VALUE);
    	
    	vbox.setFillWidth(true);
    	vbox.getChildren().addAll(view, edit, delete);
    	
    	popup.setPopupContent(vbox);
    }
    
    /*
     * 	##	VISUALIZAR CLIENTE
     */
    
    void view()
    {
    	// view.fxml
    }
    
    /*
     * 	##	EDITAR CLIENTE
     */
    
    void edit()
    {
    	try {
    		FXMLLoader viewLoader = new FXMLLoader(getClass().getResource("/views/costumers/create-edit.fxml"));
    		BorderPane main       = (BorderPane) costumerTable.getScene().lookup("#main");
    		
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
    	loadDialogDelete((StackPane) costumerTable.getScene().lookup("#mainStack"));
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
    	
    	yes.setCursor(Cursor.HAND);
    	no.setCursor(Cursor.HAND);
    	
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
