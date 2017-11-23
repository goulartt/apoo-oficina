package tattool.views.controller.service;

import java.util.function.Predicate;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
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
import javafx.scene.control.TreeTablePosition;
import javafx.util.Callback;

public class CustomerGridController {
	
	@FXML
    private JFXTextField search;
	
	@FXML
    private JFXTreeTableView<Customer> customerGrid;
	
	@FXML
    private OctIconView closeButton;
	
	JFXDialog dialog      = new JFXDialog();
	JFXTextField customer = new JFXTextField();
	
	public void initialize() {
		closeButton.setOnMouseClicked(event -> {
			if(!customerGrid.getSelectionModel().getSelectedItems().isEmpty()) {
				TreeTablePosition position = customerGrid.getSelectionModel().getSelectedCells().get(0);
		        int row                    = position.getRow();
		        TreeItem item              = customerGrid.getTreeItem(row);
		        TreeTableColumn column     = position.getTableColumn();
				customer.setText(column.getCellObservableValue(item).getValue().toString());
			}
			dialog.close();
		});
		createTableColumns();
		populateTable();
		search();
	}
	
	@SuppressWarnings("unchecked")
	void createTableColumns()
    {
    	JFXTreeTableColumn<Customer,String> cpf  = new JFXTreeTableColumn<>("CPF");
    	JFXTreeTableColumn<Customer,String> name = new JFXTreeTableColumn<>("Nome");
    	
    	//Colunas com largura responsiva
    	
    	cpf.prefWidthProperty().bind(customerGrid.widthProperty().multiply(0.4));
    	name.prefWidthProperty().bind(customerGrid.widthProperty().multiply(0.6));
    	
    	cpf.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Customer, String>, ObservableValue<String>>()
    	{
			@Override
			public ObservableValue<String> call(CellDataFeatures<Customer, String> param)
			{
				return param.getValue().getValue().cpf;
			}
    	});
    	name.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Customer, String>, ObservableValue<String>>()
    	{
			@Override
			public ObservableValue<String> call(CellDataFeatures<Customer, String> param)
			{
				return param.getValue().getValue().name;
			}
    	});
    	
    	customerGrid.getColumns().setAll(cpf, name);
    }
    
    /*
     * 	##	POPULA A TABLE
     */
    
    void populateTable()
    {
    	ObservableList<Customer> customers = FXCollections.observableArrayList();
    	
    	customers.add(new Customer("400.404.505-65", "Breno"));
    	customers.add(new Customer("652.154.654-05", "Goulart"));
    	customers.add(new Customer("123.656.845-22", "Jean"));
    	customers.add(new Customer("654.123.454-12", "Renan"));
    	customers.add(new Customer("615.541.236-32", "Gabriel"));
    	customers.add(new Customer("142.324.895-45", "Mateus"));
    	customers.add(new Customer("645.512.323-65", "Alfredo"));
  
    	final TreeItem<Customer> root = new RecursiveTreeItem<Customer>(customers, RecursiveTreeObject::getChildren);
    	
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
				customerGrid.setPredicate(new Predicate<TreeItem<Customer>>()
				{
					@Override
					public boolean test(TreeItem<Customer> user)
					{
						//Compara o valor do TextInput com as colunas da table
						
						return user.getValue().cpf.getValue().toLowerCase().contains(newValue.toLowerCase())     ||
							   user.getValue().name.getValue().toLowerCase().contains(newValue.toLowerCase());
					}
				});
			}
    	});
    }
    
    /*
     * 	##	CLASSE DE TESTE
     */
    
    class Customer extends RecursiveTreeObject<Customer> {
    	StringProperty cpf;
    	StringProperty name;
    	
    	public Customer(String name, String cpf) {
    		this.cpf  = new SimpleStringProperty(cpf);
    		this.name = new SimpleStringProperty(name);
    	}
    	
    	public String getName() {
    		return name.toString();
    	}
    }
}
