package tattool.domain.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tattool.domain.model.Customer;
import tattool.domain.model.User;
import tattool.rest.consume.CustomerRest;

public class CadastroClienteController implements Initializable{
	@FXML TextField txtName = new TextField();
	@FXML TextField txtCpf = new TextField();
	@FXML TextField txtCel = new TextField();
	@FXML TextField txtPhone = new TextField();
	@FXML TextField txtEmail = new TextField();
	@FXML TextField txtStreet = new TextField();
	@FXML TextField txtNeigh = new TextField();
	@FXML TextField txtCity = new TextField();
	@FXML TextField txtNumber = new TextField();
	@FXML TextField txtZip = new TextField();
	@FXML TextField txtState = new TextField();
	@FXML DatePicker dateNasc = new DatePicker();
	@FXML TableView<Customer> tableCustomer;
	@FXML TableColumn<Customer, String> colunaNome;
	@FXML TableColumn<Customer, String> colunaCpf;
	@FXML  Button btnCadastrar = new Button();
	@FXML  Button btnVoltar = new Button();
	@FXML Label lblSucesso = new Label();
	@FXML Label lblErro = new Label();
	private List<Customer> clientes = new ArrayList<>();
	private Customer customer = new Customer();
	private CustomerRest customerRest = new CustomerRest();
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		carregarTable();
	}
	
	public void carregarTable() {

		colunaNome.setCellValueFactory(new PropertyValueFactory<User, String>("nome"));
		colunaCpf.setCellValueFactory(new PropertyValueFactory<User, String>("cpf"));
		
		
	}

	@FXML
	public void cadastrar(ActionEvent event){
		Customer customer = customerRest.existeCostumer(txtName.getText());
		if(customer == null)
		{
			if(!txtName.getText().isEmpty() && !txtCpf.getText().isEmpty() && !txtName.getText().isEmpty() && !txtCel.getText().isEmpty() && !txtStreet.getText().isEmpty() &&
				!txtNeigh.getText().isEmpty() && !txtNumber.getText().isEmpty() && !txtCity.getText().isEmpty() && !txtZip.getText().isEmpty() && !txtState.getText().isEmpty())
			{
				customerRest.save(customer);
				limpaCampo();
				carregarTable();
				lblSucesso.setText("Usuario salvo com sucesso!");
			}
			else {
				lblErro.setText("Preenche todos os campos por favor");
			}
		}else {
			lblErro.setText("Usuario ja existe");
		}	
	}
	
	@FXML
	public void voltar(ActionEvent event){
		
		try {
		    FXMLLoader loader = new FXMLLoader(DashboardController.class.getResource("/telas/Dashboard.fxml"));
	        Parent root = (Parent) loader.load();
	        DashboardController control = (DashboardController)loader.getController();
	        control.customer(customer);
	        control.lblNome.setText(customer.getName());
	        Scene scene = new Scene(root);
	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        stage.setTitle("Dashboard");
	        stage.setScene(scene);
	        stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void deletar(ActionEvent event){
		carregarTable();
	}
	
	public void limpaCampo() {
		txtName.setText("");
	    txtCpf.setText("");
	    dateNasc.setValue(null);
		txtCel.setText("");
		txtPhone.setText("");
	    txtEmail.setText("");
		txtStreet.setText("");
		txtNeigh.setText("");
		txtNumber.setText("");
		txtCity.setText("");
		txtState.setText("");
		txtZip.setText("");
		lblErro.setText("");
	}

	public void customer(Customer customer) {
		this.customer = customer;
		
	}
	
}
