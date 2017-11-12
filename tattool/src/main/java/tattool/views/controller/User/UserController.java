package tattool.views.controller.User;

import java.util.function.Predicate;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.control.TreeTableColumn.CellEditEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import tattool.domain.modelfx.UserFX;
import tattool.rest.consume.UserRest;
import tattool.util.ConvertModelToFX;

public class UserController
{

    @FXML
    private JFXTreeTableView<UserFX> userTable;
    
    @FXML
    private JFXTextField search;
    
    @FXML
    private Label error;
    
    private UserRest rest = new UserRest();
    
    /*
     * 	##	INICIALIZAï¿½ï¿½O
     */
    public void initialize()
    {
    	createTableColumns();
    	populateTable();
    	search();
    	
    	error.managedProperty().bind(error.visibleProperty());
    	error.setVisible(false);
    }
    
    /*
     * 	##	CRIA AS COLUNAS DA TABLE DE USUï¿½RIOS (NECESSï¿½RIO PARA USAR O JFXTableTreeView)
     */
    
    @SuppressWarnings("unchecked")
	void createTableColumns()
    {
    	JFXTreeTableColumn<UserFX, String> name     = new JFXTreeTableColumn<>("Nome");
    	JFXTreeTableColumn<UserFX, String> username = new JFXTreeTableColumn<>("Login");
    	JFXTreeTableColumn<UserFX, String> role     = new JFXTreeTableColumn<>("Função");
    	
    	//Colunas com largura responsiva
    	
    	name.prefWidthProperty().bind(userTable.widthProperty().multiply(0.3));
    	username.prefWidthProperty().bind(userTable.widthProperty().multiply(0.3));
    	role.prefWidthProperty().bind(userTable.widthProperty().multiply(0.396));	//0.396 -> Gambiarra pra coluna nï¿½o atravessar a TableView
    	
    	name.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<UserFX, String>, ObservableValue<String>>()
    	{
			@Override
			public ObservableValue<String> call(CellDataFeatures<UserFX, String> param)
			{
				return param.getValue().getValue().nome;
			}
    	});
    	
    	username.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<UserFX, String>, ObservableValue<String>>()
    	{
			@Override
			public ObservableValue<String> call(CellDataFeatures<UserFX, String> param)
			{
				return param.getValue().getValue().usuario;
			}
    	});
    	
    	role.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<UserFX, String>, ObservableValue<String>>()
    	{
			@Override
			public ObservableValue<String> call(CellDataFeatures<UserFX, String> param)
			{
				return param.getValue().getValue().role;
			}
    	});
    	
    	//	TABLE EDITï¿½VEL
    	
    	name.setCellFactory((TreeTableColumn<UserFX, String> param) -> new GenericEditableTreeTableCell<>(new TextFieldEditorBuilder()));
        name.setOnEditCommit((CellEditEvent<UserFX, String> t) -> t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue().nome.set(t.getNewValue()));
        
        username.setCellFactory((TreeTableColumn<UserFX, String> param) -> new GenericEditableTreeTableCell<>(new TextFieldEditorBuilder()));
        username.setOnEditCommit((CellEditEvent<UserFX, String> t) -> t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue().usuario.set(t.getNewValue()));
        
        role.setCellFactory((TreeTableColumn<UserFX, String> param) -> new GenericEditableTreeTableCell<>(new TextFieldEditorBuilder()));
        role.setOnEditCommit((CellEditEvent<UserFX, String> t) -> t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue().role.set(t.getNewValue()));
    	
    	userTable.setEditable(true);
    	
    	userTable.getColumns().setAll(name, username, role);
    
    }
    
    /*
     * 	##	POPULANDO TABELA
     */
    
    public void populateTable()
    {
    	ObservableList<UserFX> users = FXCollections.observableArrayList();
    	
    	users = FXCollections.observableArrayList(ConvertModelToFX.convertListUser(rest.findAllUsers()));
    	
    	final TreeItem<UserFX> root = new RecursiveTreeItem<UserFX>(users, RecursiveTreeObject::getChildren);
    	
    	userTable.setRoot(root);
    	userTable.setShowRoot(false);
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
				userTable.setPredicate(new Predicate<TreeItem<UserFX>>()
				{
					@Override
					public boolean test(TreeItem<UserFX> user)
					{
						//Compara o valor do TextInput com as colunas da table
						
						return user.getValue().nome.getValue().toLowerCase().contains(newValue.toLowerCase())     ||
							   user.getValue().usuario.getValue().toLowerCase().contains(newValue.toLowerCase())  ||
							   user.getValue().role.getValue().toLowerCase().contains(newValue.toLowerCase());
					}
				});
			}
    	});
    }
    
    /*
     * 	##	CRIAR USUï¿½RIO
     */
    
    @FXML
    public void create(ActionEvent event)
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
    
    /*
     * 	##	DELETE USUARIO
     */
    
    @FXML
    public void delete(ActionEvent event)
    {   
    	error.setVisible(false);
    	
    	if(userTable.getSelectionModel().getSelectedItem() != null) {
    		
    		// Deleta usuario selecionado
    		
    	} else {
    		error.setText("Selecione um usuário para excluílo");
    		error.setVisible(true);
    	}
    }
 
}

