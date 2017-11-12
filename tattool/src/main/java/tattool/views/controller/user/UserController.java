package tattool.views.controller.user;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.control.TreeTableColumn.CellEditEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Callback;
import tattool.domain.model.User;
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
    
    private List<User> userUpdate = new ArrayList<>();
    
    private UserRest rest = new UserRest();
    private List<UserFX> userTest = new ArrayList<>();
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
    	role.prefWidthProperty().bind(userTable.widthProperty().multiply(0.39));	//0.396 -> Gambiarra pra coluna nï¿½o atravessar a TableView
    	
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
        name.setOnEditCommit(new EventHandler<TreeTableColumn.CellEditEvent<UserFX, String>>() { 
			

			@Override
			public void handle(CellEditEvent<UserFX, String> event) {
				event.getTreeTableView().getTreeItem(event.getTreeTablePosition().getRow()).getValue().nome.set(event.getNewValue());
				TreeItem<UserFX> item = event.getRowValue();
                UserFX userOld = item.getValue();
                String newer = event.getNewValue();
                userOld.setNome(new SimpleStringProperty(newer));
                userTest.add(userOld);
              
			}
        });
        
        username.setCellFactory((TreeTableColumn<UserFX, String> param) -> new GenericEditableTreeTableCell<>(new TextFieldEditorBuilder()));
        username.setOnEditCommit(new EventHandler<TreeTableColumn.CellEditEvent<UserFX, String>>() { 
			@Override
			public void handle(CellEditEvent<UserFX, String> event) {
				event.getTreeTableView().getTreeItem(event.getTreeTablePosition().getRow()).getValue().usuario.set(event.getNewValue());
				TreeItem<UserFX> item = event.getRowValue();
                UserFX userOld = item.getValue();
                String newer = event.getNewValue();
                userOld.setUsuario(new SimpleStringProperty(newer));
                userTest.add(userOld);
				
			}
        });
        
        //role.setCellFactory((TreeTableColumn<UserFX, String> param) -> new GenericEditableTreeTableCell<>(new TextFieldEditorBuilder()));
        //role.setOnEditCommit((CellEditEvent<UserFX, String> t) -> t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue().role.set(t.getNewValue()));
    	
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
    		loadDialogDelete((StackPane) ((Node) event.getSource()).getScene().lookup("#mainStack"));
    	} else {
    		error.setText("Selecione um usuário para excluílo");
    		error.setVisible(true);
    	}
    }
    
    /*
     * 	##	DIALOG DELETE
     */
    
    void loadDialogDelete(StackPane mainStack) {
    	
    	JFXDialogLayout dialogContent = new JFXDialogLayout();
    	JFXDialog dialog              = new JFXDialog(mainStack, dialogContent, JFXDialog.DialogTransition.CENTER);
    	JFXButton yes                 = new JFXButton("Sim");
    	JFXButton no                  = new JFXButton("Não");
    	
    	dialogContent.setHeading(new Text("Tem certeza que quer excluir este usuário?"));
    	dialogContent.setBody(new Text("\n Todos os dados deste usuário serão perdidos.\n"));
    	
    	yes.setCursor(Cursor.HAND);
    	no.setCursor(Cursor.HAND);
    	
		yes.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				UserFX u = userTable.getSelectionModel().getSelectedItem().getValue();
	    		rest.deleteUser(u.getId());
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
    
    @FXML
    public void update(ActionEvent event) {
    	userUpdate = ConvertModelToFX.convertListUserFX(userTest);
    	
    	for(User u : userUpdate) {
    		rest.save(u);
    	}
    	
    	if(userTest.isEmpty())
    	{
    		loadDialogUpdate((StackPane) ((Node) event.getSource()).getScene().lookup("#mainStack"), new Text("Não há nenhum alteração para salvar!"));
    	}else {
    		loadDialogUpdate((StackPane) ((Node) event.getSource()).getScene().lookup("#mainStack"), new Text("As atualizações foram salvas!"));
    	}
    }
    
    /*
     * 	##	DIALOG DELETE
     */
    
    void loadDialogUpdate(StackPane mainStack, Text text) {
    	
    	JFXDialogLayout dialogContent = new JFXDialogLayout();
    	JFXDialog dialog              = new JFXDialog(mainStack, dialogContent, JFXDialog.DialogTransition.CENTER);
    	JFXButton ok                 = new JFXButton("Ok");
    	
    	dialogContent.setHeading(text);
    	
    	ok.setCursor(Cursor.HAND);
    	
		ok.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
	    		dialog.close();
			}
		});
		
		dialogContent.setActions(ok);
		dialog.show();
    }
}

