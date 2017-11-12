package application;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;

import java.util.function.Predicate;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.control.TreeTableColumn.CellEditEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

public class UserController
{

    @FXML
    private JFXTreeTableView<User> userTable;
    
    @FXML
    private JFXTextField search;
    
    /*
     * 	##	INICIALIZAÇÃO
     */
    public void initialize()
    {
    	createTableColumns();
    	populateTable();
    	search();
    }
    
    /*
     * 	##	CRIA AS COLUNAS DA TABLE DE USUÁRIOS (NECESSÁRIO PARA USAR O JFXTableTreeView)
     */
    
    @SuppressWarnings("unchecked")
	void createTableColumns()
    {
    	JFXTreeTableColumn<User, String> name     = new JFXTreeTableColumn<>("Nome");
    	JFXTreeTableColumn<User, String> username = new JFXTreeTableColumn<>("Login");
    	JFXTreeTableColumn<User, String> role     = new JFXTreeTableColumn<>("Função");
    	
    	//Colunas com largura responsiva
    	
    	name.prefWidthProperty().bind(userTable.widthProperty().multiply(0.3));
    	username.prefWidthProperty().bind(userTable.widthProperty().multiply(0.3));
    	role.prefWidthProperty().bind(userTable.widthProperty().multiply(0.396));	//0.396 -> Gambiarra pra coluna não atravessar a TableView
    	
    	name.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<User, String>, ObservableValue<String>>()
    	{
			@Override
			public ObservableValue<String> call(CellDataFeatures<User, String> param)
			{
				return param.getValue().getValue().name;
			}
    	});
    	
    	username.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<User, String>, ObservableValue<String>>()
    	{
			@Override
			public ObservableValue<String> call(CellDataFeatures<User, String> param)
			{
				return param.getValue().getValue().username;
			}
    	});
    	
    	role.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<User, String>, ObservableValue<String>>()
    	{
			@Override
			public ObservableValue<String> call(CellDataFeatures<User, String> param)
			{
				return param.getValue().getValue().role;
			}
    	});
    	
    	//	TABLE EDITÁVEL
    	
    	name.setCellFactory((TreeTableColumn<User, String> param) -> new GenericEditableTreeTableCell<>(new TextFieldEditorBuilder()));
        name.setOnEditCommit((CellEditEvent<User, String> t) -> t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue().name.set(t.getNewValue()));
        
        username.setCellFactory((TreeTableColumn<User, String> param) -> new GenericEditableTreeTableCell<>(new TextFieldEditorBuilder()));
        username.setOnEditCommit((CellEditEvent<User, String> t) -> t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue().username.set(t.getNewValue()));
        
        role.setCellFactory((TreeTableColumn<User, String> param) -> new GenericEditableTreeTableCell<>(new TextFieldEditorBuilder()));
        role.setOnEditCommit((CellEditEvent<User, String> t) -> t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue().role.set(t.getNewValue()));
    	
    	userTable.setEditable(true);
    	
    	userTable.getColumns().setAll(name, username, role);
    }
    
    /*
     * 	##	POPULANDO TABELA
     */
    
    void populateTable()
    {
    	ObservableList<User> users = FXCollections.observableArrayList();
    	
    	users.add(new User("Renan", "renan", "Administrador"));
    	users.add(new User("João", "goulart", "Gerenciador"));
    	users.add(new User("Breno", "pokemon", "Gerenciador"));
    	users.add(new User("Jean", "jean", "Gerenciador"));
    	users.add(new User("Vaga", "bunda", "Gerenciador"));
    	
    	final TreeItem<User> root = new RecursiveTreeItem<User>(users, RecursiveTreeObject::getChildren);
    	
    	userTable.setRoot(root);
    	userTable.setShowRoot(false);
    }
    
    /*
     * 	##	FILTRO DE BUSCA
     */
    
    void search()

    {
    	search.textProperty().addListener(new ChangeListener<String>()
    	{
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
			{
				userTable.setPredicate(new Predicate<TreeItem<User>>()
				{
					@Override
					public boolean test(TreeItem<User> user)
					{
						//Compara o valor do TextInput com as colunas da table
						
						return user.getValue().name.getValue().toLowerCase().contains(newValue.toLowerCase())     ||
							   user.getValue().username.getValue().toLowerCase().contains(newValue.toLowerCase()) ||
							   user.getValue().role.getValue().toLowerCase().contains(newValue.toLowerCase());
					}
				});
			}
    	});
    }
    
    /*
     * 	##	CRIAR USUÁRIO
     */
    
    @FXML
    void create(ActionEvent event)
    {
    	try
    	{
		    FXMLLoader viewLoader = new FXMLLoader(getClass().getResource("/views/users/create.fxml"));
		    BorderPane main       = (BorderPane) ((Node) event.getSource()).getScene().lookup("#main");
		    
		    viewLoader.setRoot(main);
		    main.getChildren().clear();
		    viewLoader.load();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }
    
    //RecursiveTreeObject -> NECESSÁRIO PARA USAR O FILTRO DE BUSCA
    
    class User extends RecursiveTreeObject<User>
    {
    	StringProperty name;
    	StringProperty username;
    	StringProperty role;
    	
    	public User(String name, String username, String role)
    	{
    		this.name     = new SimpleStringProperty(name);
    		this.username = new SimpleStringProperty(username);
    		this.role     = new SimpleStringProperty(role);
    	}
    }
}

