package tattool.views.controller.session;

import java.util.function.Predicate;

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
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.input.MouseButton;
import javafx.util.Callback;

public class ServiceGridController {
	
	@FXML
    private JFXTextField search;

    @FXML
    private JFXTreeTableView<Service> serviceGrid;

    @FXML
    private OctIconView closeButton;
    
    @FXML
    SessionController sessionController;
	
    public ServiceGridController(SessionController sessionController){
		this.sessionController = sessionController;
	}
	public void initialize() {
		closeButton.setOnMouseClicked(event -> {
			sessionController.serviceModal.close();
		});
		createTableColumns();
		populateTable();
		search();
	}
	
	@SuppressWarnings("unchecked")
	void createTableColumns()
    {
		JFXTreeTableColumn<Service,String> name        = new JFXTreeTableColumn<>("Nome");
    	JFXTreeTableColumn<Service,String> customer    = new JFXTreeTableColumn<>("Cliente");
    	JFXTreeTableColumn<Service,String> status      = new JFXTreeTableColumn<>("Status");
    	
    	//Colunas com largura responsiva
    	
    	name.prefWidthProperty().bind(serviceGrid.widthProperty().multiply(0.3));
    	customer.prefWidthProperty().bind(serviceGrid.widthProperty().multiply(0.3));
    	status.prefWidthProperty().bind(serviceGrid.widthProperty().multiply(0.4));
    	
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
    	
    	serviceGrid.getColumns().setAll(name, customer, status);
    	
    	serviceGrid.setRowFactory(table -> {
    		JFXTreeTableRow<Service> row = new JFXTreeTableRow<>();
    		
    		row.setOnMouseClicked(event -> {
    			if(event.getButton().equals(MouseButton.PRIMARY))
    			{
    				sessionController.service.setText("SERVIÇO SELECIONADO");
    				sessionController.showSessionsButton.setDisable(false);
    				sessionController.serviceModal.close();
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
    	
    	services.add(new Service("SERVIÇO DAHORA", "Breno", "ATIVO"));
    	services.add(new Service("SERVIÇO BOSTA", "Goulart", "ATIVO"));
    	services.add(new Service("SERVIÇO NEGRO", "Jean", "ATIVO"));
    	services.add(new Service("SERVIÇO GAY", "Sotero", "ATIVO"));
  
    	final TreeItem<Service> root = new RecursiveTreeItem<Service>(services, RecursiveTreeObject::getChildren);
    	
    	serviceGrid.setRoot(root);
    	serviceGrid.setShowRoot(false);
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
				serviceGrid.setPredicate(new Predicate<TreeItem<Service>>()
				{
					@Override
					public boolean test(TreeItem<Service> service)
					{
						//Compara o valor do TextInput com as colunas da table
						
						return service.getValue().name.getValue().toLowerCase().contains(newValue.toLowerCase())      ||
								service.getValue().customer.getValue().toLowerCase().contains(newValue.toLowerCase()) ||
								service.getValue().status.getValue().toLowerCase().contains(newValue.toLowerCase());
					}
				});
			}
    	});
    }
    
    /*
     * 	##	CLASSE TESTE
     */
    
    class Service extends RecursiveTreeObject<Service> {
    	StringProperty name;
    	StringProperty customer;
    	StringProperty status;
    	
    	Service(String name, String customer, String status) {
    		this.name     = new SimpleStringProperty(name);
    		this.customer = new SimpleStringProperty(customer);
    		this.status   = new SimpleStringProperty(status);
    	}
    }
}
