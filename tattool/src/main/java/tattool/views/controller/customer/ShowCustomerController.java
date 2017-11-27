package tattool.views.controller.customer;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import tattool.domain.model.Customer;
import tattool.domain.model.Service;
import tattool.report.jasper.UsuarioRel;
import tattool.rest.consume.ServiceRest;
import tattool.util.DateUtil;

public class ShowCustomerController implements Initializable {

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
	
	private ServiceRest rest = new ServiceRest();

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
		if (!customer.getContact().getEmail().equals(""))
			email.setText(customer.getContact().getEmail());
		else
			email.setText("Não possui email");

		if (!customer.getContact().getPhone().equals(""))
			phone.setText(customer.getContact().getPhone());
		else
			phone.setText("Não possui telefone");

		if (customer.getAddress().getZipCode() != null)
			zipCode.setText("CEP: " + customer.getAddress().getZipCode());
		else
			zipCode.setText("CEP não informado");

		if (!customer.getAddress().getStreet().equals(""))
			street.setText(customer.getAddress().getStreet() + ", " + customer.getAddress().getNumber());
		else
			street.setText("Rua não informada");

		if (!customer.getAddress().getCity().equals(""))
			city.setText(customer.getAddress().getCity() + "/" + customer.getAddress().getState());
		else
			city.setText("Cidade não informada");

		if (!customer.getAddress().getCity().equals(""))
			neighborhood.setText(customer.getAddress().getNeighborhood());
		else
			neighborhood.setText("Bairro não informado");
	}

	@FXML
	void historico(ActionEvent event) {
		UsuarioRel relatorio = new UsuarioRel("historicoCliente");
		try {
			List<Service> services = rest.geraRelatorio(customer.getId());
			relatorio.geraRelatorioHistorico(services);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
