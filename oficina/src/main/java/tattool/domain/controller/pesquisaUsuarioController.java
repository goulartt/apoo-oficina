package tattool.domain.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import tattool.domain.model.User;
import tattool.rest.consume.UserRest;

public class pesquisaUsuarioController implements Initializable{
	@FXML TableView tabelaPesquisa;	
	@FXML TableColumn colunaUsuario;
	
	@FXML TableColumn colunaNome;
	
	@FXML  Label lblFiltrar = new Label();
	@FXML  TextField txtFiltrar = new TextField();
	
	private List<User> usuarios = new ArrayList<>();
	
	private User user = new User();
	private UserRest dao = new UserRest();
	public ObservableList<User> observableUsuario;
	
    private ObservableList<User> masterData = FXCollections.observableArrayList();
    private ObservableList<User> filteredData = FXCollections.observableArrayList();
   
    
    public void carregarTable() {

		colunaUsuario.setCellValueFactory(new PropertyValueFactory<User, String>("usuario"));
		colunaNome.setCellValueFactory(new PropertyValueFactory<User, String>("nome"));
		observableUsuario = FXCollections.observableArrayList(dao.findAllUsers());
		tabelaPesquisa.setItems(observableUsuario);
		
	}


	public void initialize(URL location, ResourceBundle resources) {
		carregarTable();
		masterData.addAll(usuarios);
		filteredData.addAll(usuarios);
		
		masterData.addListener(new ListChangeListener<User>()
		{
			public void onChanged(ListChangeListener.Change<? extends User> change)
			{
				updateFilteredData();
			}
		});
		
		
		
		 colunaNome.setCellValueFactory(
	     new PropertyValueFactory<User, String>("colunaNome"));
		 colunaUsuario.setCellValueFactory(
		 new PropertyValueFactory<User, String>("colunaUsuario"));
		 
		 tabelaPesquisa.setItems(filteredData);
		 
		 txtFiltrar.textProperty().addListener(new ChangeListener<String> ()
		 {
			 public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
			 {
				 updateFilteredData();
			 }
		 });
		 
		 
	}
	
	private void updateFilteredData()
	{

		filteredData.clear();
		for(User u: masterData)
		{
			if(matchesFilter(u))
			{
				filteredData.add(u);
			}
		}
		reapplyTableSortOrder();
	}
	
	private boolean matchesFilter(User user)
	{
		String filterString = txtFiltrar.getText();
		if(filterString == null || filterString.isEmpty())
		{
			return true;
		}
		
		String lowerCaseFilterString = filterString.toLowerCase();
		if(user.getNome().toLowerCase().indexOf(lowerCaseFilterString) != -1)
		{
			return true;
		}
		return false;
	}
	

	private void reapplyTableSortOrder()
	{
		ArrayList<TableColumn<User, ?>> sortOrder = new ArrayList<>(tabelaPesquisa.getSortOrder());
		tabelaPesquisa.getSortOrder().clear();
		tabelaPesquisa.getSortOrder().addAll(sortOrder);
		
	}
		
	
	public static User selectUser(MouseEvent event) {
	    if (event.getClickCount()>1) {
	    	TableRow<User> row = new TableRow<>();
	    	User rowData = row.getItem();
	  
	    	return rowData;
	    }
	    return null;
	   
	}
	
}
