package tattool.views.controller.session;

import java.util.function.Predicate;

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
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.input.MouseButton;
import javafx.util.Callback;
import tattool.domain.model.Customer;
import tattool.domain.model.Service;
import tattool.domain.modelfx.ServiceFX;
import tattool.rest.consume.ServiceRest;
import tattool.util.ConvertModelToFX;

public class ServiceGridController {
	
	@FXML
    private JFXTextField search;

    @FXML
    private JFXTreeTableView<ServiceFX> serviceGrid;

    @FXML
    private OctIconView closeButton;
    
    @FXML
    SessionController sessionController;
    
    private ServiceRest rest = new ServiceRest();
	
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
		JFXTreeTableColumn<ServiceFX,String> name        = new JFXTreeTableColumn<>("Nome");
    	JFXTreeTableColumn<ServiceFX,String> customer    = new JFXTreeTableColumn<>("Cliente");
    	JFXTreeTableColumn<ServiceFX,String> status      = new JFXTreeTableColumn<>("Status");
    	
    	//Colunas com largura responsiva
    	
    	name.prefWidthProperty().bind(serviceGrid.widthProperty().multiply(0.3));
    	customer.prefWidthProperty().bind(serviceGrid.widthProperty().multiply(0.3));
    	status.prefWidthProperty().bind(serviceGrid.widthProperty().multiply(0.4));
    	
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
    	
    	serviceGrid.getColumns().setAll(name, customer, status);
    	
    	serviceGrid.setRowFactory(table -> {
    		JFXTreeTableRow<ServiceFX> row = new JFXTreeTableRow<>();
    		
    		row.setOnMouseClicked(event -> {
    			if(event.getButton().equals(MouseButton.PRIMARY))
    			{
    				Service s =  ConvertModelToFX.convertServiceFXtoService(serviceGrid.getSelectionModel().getSelectedItem().getValue());
    				sessionController.serviceCarregado = s;
    				sessionController.service.setText(s.getNameService());
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
    	ObservableList<ServiceFX> services = FXCollections.observableArrayList();
    	services = FXCollections.observableArrayList(ConvertModelToFX.convertListServicesToFx(rest.findAll()));
  
    	final TreeItem<ServiceFX> root = new RecursiveTreeItem<ServiceFX>(services, RecursiveTreeObject::getChildren);
    	
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
				serviceGrid.setPredicate(new Predicate<TreeItem<ServiceFX>>()
				{
					@Override
					public boolean test(TreeItem<ServiceFX> service)
					{
						//Compara o valor do TextInput com as colunas da table
						
						return service.getValue().name.getValue().toLowerCase().contains(newValue.toLowerCase())      ||
								service.getValue().customeName.getValue().toLowerCase().contains(newValue.toLowerCase()) ||
								service.getValue().status.getValue().toLowerCase().contains(newValue.toLowerCase());
					}
				});
			}
    	});
    }
   
}
