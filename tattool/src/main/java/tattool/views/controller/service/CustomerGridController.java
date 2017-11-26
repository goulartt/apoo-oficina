package tattool.views.controller.service;

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
import tattool.domain.modelfx.CustomerFX;
import tattool.rest.consume.CustomerRest;
import tattool.util.ConvertModelToFX;

public class CustomerGridController {
	
	@FXML
    private JFXTextField search;
	
	@FXML
    private JFXTreeTableView<CustomerFX> customerGrid;
	
	@FXML
    private OctIconView closeButton;
	
	@FXML
	private CreateEditServiceController createEdit;
	
    private CustomerRest rest = new CustomerRest();
	
	public CustomerGridController(CreateEditServiceController createEdit){
		this.createEdit = createEdit;
	}
	public void initialize() {
		closeButton.setOnMouseClicked(event -> {
			createEdit.customerModal.close();
		});
		createTableColumns();
		populateTable();
		search();
	}
	
	@SuppressWarnings("unchecked")
	void createTableColumns()
    {
    	JFXTreeTableColumn<CustomerFX,String> cpf  = new JFXTreeTableColumn<>("CPF");
    	JFXTreeTableColumn<CustomerFX,String> name = new JFXTreeTableColumn<>("Nome");
    	
    	//Colunas com largura responsiva
    	
    	cpf.prefWidthProperty().bind(customerGrid.widthProperty().multiply(0.4));
    	name.prefWidthProperty().bind(customerGrid.widthProperty().multiply(0.6));
    	
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
    	
    	customerGrid.getColumns().setAll(cpf, name);
    	customerGrid.setRowFactory(table -> {
    		JFXTreeTableRow<CustomerFX> row = new JFXTreeTableRow<>();
    		
    		row.setOnMouseClicked(event -> {
    			if(event.getButton().equals(MouseButton.PRIMARY))
    			{
    				Customer c =  ConvertModelToFX.convertCustomerFXtoCustomer(customerGrid.getSelectionModel().getSelectedItem().getValue());
    				createEdit.customer.setText(c.getName());
    				createEdit.cliente = c;
    				createEdit.customerModal.close();
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
    	ObservableList<CustomerFX> customers = FXCollections.observableArrayList();
    	
    	customers = FXCollections.observableArrayList(ConvertModelToFX.convertListCustomer(rest.findAll()));
    	
  
    	final TreeItem<CustomerFX> root = new RecursiveTreeItem<CustomerFX>(customers, RecursiveTreeObject::getChildren);
    	
    	customerGrid.setRoot(root);
    	customerGrid.setShowRoot(false);
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
				customerGrid.setPredicate(new Predicate<TreeItem<CustomerFX>>()
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
    
}
