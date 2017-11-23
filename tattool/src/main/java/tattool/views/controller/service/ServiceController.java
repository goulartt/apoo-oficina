package tattool.views.controller.service;

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
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class ServiceController {
	
	JFXPopup popup = new JFXPopup();
	
	@FXML
    private JFXTextField search;

    @FXML
    private JFXTreeTableView<Service> serviceTable;
	
	/*
	 * 	##	INITIALIZE
	 */
	
	public void initialize()
    {
    	createTableColumns();
    	populateTable();
    	search();
    	popup();
    }
	
	/*
	 * 	##	CADASTRAR SERVICO
	 */

    @FXML
    void create(ActionEvent event) {
    	try {
    		FXMLLoader viewLoader = new FXMLLoader(getClass().getResource("/views/services/create-edit.fxml"));
    		BorderPane main       = (BorderPane) ((Node) event.getSource()).getScene().lookup("#main");
    		viewLoader.setRoot(main);
    		main.getChildren().clear();
    		viewLoader.load();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    /*
     * 	##	CRIA COLUNAS DA TABLE
     */
    
    @SuppressWarnings("unchecked")
	void createTableColumns()
    {
    	JFXTreeTableColumn<Service,String> name        = new JFXTreeTableColumn<>("Nome");
    	JFXTreeTableColumn<Service,String> customer    = new JFXTreeTableColumn<>("Cliente");
    	JFXTreeTableColumn<Service,String> status      = new JFXTreeTableColumn<>("Estado");
    	
    	//Colunas com largura responsiva
    	
    	name.prefWidthProperty().bind(serviceTable.widthProperty().multiply(0.3));
    	customer.prefWidthProperty().bind(serviceTable.widthProperty().multiply(0.3));
    	status.prefWidthProperty().bind(serviceTable.widthProperty().multiply(0.4));
    	
    	name.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Service, String>, ObservableValue<String>>()
    	{
			@Override
			public ObservableValue<String> call(CellDataFeatures<Service, String> param)
			{
				return param.getValue().getValue().name;
			}
    	});
    	customer.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Service, String>, ObservableValue<String>>()
    	{
			@Override
			public ObservableValue<String> call(CellDataFeatures<Service, String> param)
			{
				return param.getValue().getValue().customer;
			}
    	});
    	status.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Service, String>, ObservableValue<String>>()
    	{
			@Override
			public ObservableValue<String> call(CellDataFeatures<Service, String> param)
			{
				return param.getValue().getValue().status;
			}
    	});
    	
    	serviceTable.getColumns().setAll(name, customer, status);
    	
    	//Popup click event
    	serviceTable.setRowFactory(table -> {
    		JFXTreeTableRow<Service> row = new JFXTreeTableRow<>();
    		
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
    	ObservableList<Service> services = FXCollections.observableArrayList();
    	
    	services.add(new Service("Tribal Africano", "Breno", "Ativo"));
    	services.add(new Service("Jean na Testa", "Jean", "Ativo"));
    	services.add(new Service("Goku Soltando Kamehamegaf", "Romerin", "Ativo"));
    	services.add(new Service("Pikachu na bunda", "Breno", "Cancelado"));
    	services.add(new Service("Ultra Fucking Speed Dog", "Renan", "Concluído"));
    	services.add(new Service("Abacate", "Gilmar", "Ativo"));
    	services.add(new Service("NINTENDO É UMA MERDA", "Breno", "Ativo"));
    	services.add(new Service("PC MASTER RACE", "Giovanni", "Concluído"));
  
    	final TreeItem<Service> root = new RecursiveTreeItem<Service>(services, RecursiveTreeObject::getChildren);
    	
    	serviceTable.setRoot(root);
    	serviceTable.setShowRoot(false);
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
				serviceTable.setPredicate(new Predicate<TreeItem<Service>>()
				{
					@Override
					public boolean test(TreeItem<Service> service)
					{
						//Compara o valor do TextInput com as colunas da table
						
						return service.getValue().name.getValue().toLowerCase().contains(newValue.toLowerCase())     ||
							   service.getValue().customer.getValue().toLowerCase().contains(newValue.toLowerCase()) ||
							   service.getValue().status.getValue().toLowerCase().contains(newValue.toLowerCase());
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
    		show((StackPane) serviceTable.getScene().lookup("#mainStack"));
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
     * 	##	VISUALIZAR SERVICO
     */
    
    void show(StackPane mainStack) {
		//
    }
    
    /*
     * 	##	EDITAR SERVICO
     */
    
    void edit()
    {
    	try {
    		FXMLLoader viewLoader = new FXMLLoader(getClass().getResource("/views/services/create-edit.fxml"));
    		BorderPane main       = (BorderPane) serviceTable.getScene().lookup("#main");
 
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
    	loadDialogDelete((StackPane) serviceTable.getScene().lookup("#mainStack"));
    }
    
    /*
     * 	 DIALOGO DELETE
     */
    
    void loadDialogDelete(StackPane mainStack) {
    	
    	JFXDialogLayout dialogContent = new JFXDialogLayout();
    	JFXDialog dialog              = new JFXDialog(mainStack, dialogContent, JFXDialog.DialogTransition.CENTER);
    	JFXButton yes                 = new JFXButton("Sim");
    	JFXButton no                  = new JFXButton("Não");
    	
    	dialogContent.setHeading(new Text("Tem certeza que quer excluir este serviço?"));
    	dialogContent.setBody(new Text("Todos os dados deste serviço serão perdidos."));
    	
		yes.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//REST.DELETE
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
     * 	##	CLASSE TESTE
     */
    
    private class Service extends RecursiveTreeObject<Service> {
    	StringProperty name;
    	StringProperty customer;
    	StringProperty status;
    	
    	public Service(String name, String customer, String status) {
    		this.name     = new SimpleStringProperty(name);
    		this.customer = new SimpleStringProperty(customer);
    		this.status   = new SimpleStringProperty(status);
    	}
    }
}
