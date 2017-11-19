package tattool.views.controller.customer;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import tattool.domain.model.Customer;
import tattool.report.jasper.UsuarioRel;
import tattool.util.DateUtil;

public class ShowCustomerController implements Initializable{
	
	private Customer customer = new Customer();
	
	@FXML
    private Label name;
    
    @FXML
    private Label cpf;
    
    @FXML
    private Label birthdate;
    
    @FXML
    private Label email;
    
    @FXML
    private Label phone;
    
    @FXML
    private Label zipCode;
    
    @FXML
    private Label city;
    
    @FXML
    private Label state;

    @FXML
    private Label street;
    
    @FXML
    private Label neighborhood;
	
	public ShowCustomerController(Customer customer) {
		this.customer = customer;
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		carregaCampo();
	}
	private void carregaCampo() {
		name.setText(customer.getName());
		cpf.setText(customer.getCpf());
		birthdate.setText(DateUtil.asLocalDate(customer.getBirthDate()).toString());
		if(!customer.getContact().getEmail().equals(""))
			email.setText(customer.getContact().getEmail());
		else
			email.setText("Não possui email");
		if(!customer.getContact().getPhone().equals(""))
			phone.setText(customer.getContact().getPhone());
		else
			phone.setText("Não possui telefone");
		zipCode.setText("CEP: "+customer.getAddress().getZipCode());
		street.setText(customer.getAddress().getStreet()+", "+customer.getAddress().getNumber());
		city.setText(customer.getAddress().getCity()+"/"+customer.getAddress().getState());
		neighborhood.setText(customer.getAddress().getNeighborhood());
	}
	
	 @FXML
	    void historico(ActionEvent event){
		 UsuarioRel relatorio = new UsuarioRel();
		 try {
			relatorio.geraRelatorio();
		} catch (Exception e) {
			e.printStackTrace();
		}
	 }

}
