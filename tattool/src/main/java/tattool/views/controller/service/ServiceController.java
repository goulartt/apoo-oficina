package tattool.views.controller.service;

import java.io.IOException;
import java.util.List;
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
import tattool.domain.model.Service;
import tattool.domain.model.Session;
import tattool.domain.modelfx.ServiceFX;
import tattool.rest.consume.ServiceRest;
import tattool.rest.consume.SessionRest;
import tattool.util.ConvertModelToFX;

public class ServiceController {
	
	JFXPopup popup = new JFXPopup();
	
	@FXML
    private JFXTextField search;

    @FXML
    private JFXTreeTableView<ServiceFX> serviceTable;
    
    private ServiceRest rest = new ServiceRest();
	
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
    		viewLoader.setController(new CreateEditServiceController(new Service()));
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
    	JFXTreeTableColumn<ServiceFX,String> name        = new JFXTreeTableColumn<>("Nome");
    	JFXTreeTableColumn<ServiceFX,String> customer    = new JFXTreeTableColumn<>("Cliente");
    	JFXTreeTableColumn<ServiceFX,String> status      = new JFXTreeTableColumn<>("Status");
    	
    	//Colunas com largura responsiva
    	
    	name.prefWidthProperty().bind(serviceTable.widthProperty().multiply(0.3));
    	customer.prefWidthProperty().bind(serviceTable.widthProperty().multiply(0.3));
    	status.prefWidthProperty().bind(serviceTable.widthProperty().multiply(0.4));
    	
    	name.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ServiceFX, String>, ObservableValue<String>>()
    	{
			@Override
			public ObservableValue<String> call(CellDataFeatures<ServiceFX, String> param)
			{
				return param.getValue().getValue().name;
			}
    	});
    	customer.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ServiceFX, String>, ObservableValue<String>>()
    	{
			@Override
			public ObservableValue<String> call(CellDataFeatures<ServiceFX, String> param)
			{
				return param.getValue().getValue().customeName;
			}
    	});
    	status.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ServiceFX, String>, ObservableValue<String>>()
    	{
			@Override
			public ObservableValue<String> call(CellDataFeatures<ServiceFX, String> param)
			{
				return param.getValue().getValue().status;
			}
    	});
    	
    	serviceTable.getColumns().setAll(name, customer, status);
    	
    	//Popup click event
    	serviceTable.setRowFactory(table -> {
    		JFXTreeTableRow<ServiceFX> row = new JFXTreeTableRow<>();
    		
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
    	ObservableList<ServiceFX> services = FXCollections.observableArrayList();
    	
    	
    	services = FXCollections.observableArrayList(ConvertModelToFX.convertListServicesToFx(rest.findAll()));
  
    	final TreeItem<ServiceFX> root = new RecursiveTreeItem<ServiceFX>(services, RecursiveTreeObject::getChildren);
    	
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
				serviceTable.setPredicate(new Predicate<TreeItem<ServiceFX>>()
				{
					@Override
					public boolean test(TreeItem<ServiceFX> service)
					{
						//Compara o valor do TextInput com as colunas da table
						
						return service.getValue().name.getValue().toLowerCase().contains(newValue.toLowerCase())     ||
							   service.getValue().customeName.getValue().toLowerCase().contains(newValue.toLowerCase()) ||
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
    	try {
			FXMLLoader serviceLoader = new FXMLLoader(getClass().getResource("/views/services/show-service.fxml"));
			ServiceFX service = serviceTable.getSelectionModel().getSelectedItem().getValue();
			List<Session> sessions = new SessionRest().findByService(service.getId());
			serviceLoader.setController(new ShowServiceController(service, sessions));
			Region serviceContent = serviceLoader.load();
			JFXDialog customerModal = new JFXDialog(mainStack, serviceContent, JFXDialog.DialogTransition.CENTER, false);
			OctIconView closeButton = (OctIconView) mainStack.getScene().lookup("#closeButton");
    		closeButton.setOnMouseClicked(event -> customerModal.close());
			customerModal.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /*
     * 	##	EDITAR SERVICO
     */
    
    void edit()
    {
    	try {
    		FXMLLoader viewLoader = new FXMLLoader(getClass().getResource("/views/services/create-edit.fxml"));
    		BorderPane main       = (BorderPane) serviceTable.getScene().lookup("#main");
    		Service serviceCarregado = ConvertModelToFX.convertServiceFXtoService(serviceTable.getSelectionModel().getSelectedItem().getValue());
    		viewLoader.setController(new CreateEditServiceController(serviceCarregado));
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
				rest.deleteService(serviceTable.getSelectionModel().getSelectedItem().getValue().getId());
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
