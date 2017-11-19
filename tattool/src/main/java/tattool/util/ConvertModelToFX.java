package tattool.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import tattool.domain.model.Customer;
import tattool.domain.model.User;
import tattool.domain.modelfx.CustomerFX;
import tattool.domain.modelfx.UserFX;

public class ConvertModelToFX {
	
	public static User convertUser(UserFX u) {
		User user = new User();
		if(u.getId() != null) user.setId(u.getId());
		user.setNome(u.getNome().get());
		String role = u.getRole().get();
		user.setRole(Integer.parseInt(role));
		user.setSenha(u.getSenha().get());
		user.setUsuario(u.getUsuario().get());
		return user;
		
	}
	
	public static UserFX convertUserFX(User u) {
		UserFX user = new UserFX(u.getUsuario(), u.getSenha(), u.getNome(), u.getRole().toString());
		user.setId(u.getId());
		return user;
	}

	public static List<UserFX> convertListUser(User[] findAllUsers) {
	  	List<UserFX> userFX = new ArrayList<>();
    	List<User> user = Arrays.asList(findAllUsers);
    	for(User u : user) {
    		userFX.add(ConvertModelToFX.convertUserFX(u));
    	}
    	return userFX;
	}
	
	public static List<User> convertListUserFX(List<UserFX> user){
		List<User> userConvertido = new ArrayList<>();
		for(UserFX u : user) {
			userConvertido.add(ConvertModelToFX.convertUser(u));
    	}
    	return userConvertido;
	}

	public static List<CustomerFX> convertListCustomer(Customer[] findAll) {
		List<Customer> customers = Arrays.asList(findAll);
		List<CustomerFX> clientes = new ArrayList<>();
		if(!customers.isEmpty()) {
			customers.forEach(c -> {
				CustomerFX conversao = new CustomerFX();
				conversao.setId(c.getId());
				conversao.setCpf(new SimpleStringProperty(c.getCpf()));
				conversao.setBirthDate(c.getBirthDate());
				conversao.setAddress(c.getAddress());
				conversao.setContact(c.getContact());
				if(c.getContact().getEmail().equals("") && !c.getContact().getPhone().equals("")) {
					conversao.setContactSimple(new SimpleStringProperty(c.getContact().getPhone()));
				}
				if(!c.getContact().getEmail().equals("") && c.getContact().getPhone().equals("")) {
					conversao.setContactSimple(new SimpleStringProperty(c.getContact().getEmail()));
				}
				if(!c.getContact().getEmail().equals("") && !c.getContact().getPhone().equals("")) {
					conversao.setContactSimple(new SimpleStringProperty(c.getContact().getPhone()));
				}
				conversao.setName(new SimpleStringProperty(c.getName()));
				conversao.setRemoved(c.getRemoved());
				clientes.add(conversao);
			});
		}
		return clientes;
	}

	public static Customer convertCustomerFXtoCustomer(CustomerFX value) {
		Customer customer = new Customer();
		customer.setAddress(value.getAddress());
		customer.setContact(value.getContact());
		customer.setId(value.getId());
		customer.setName(value.getName().get());
		customer.setCpf(value.getCpf().get());
		customer.setBirthDate(value.getBirthDate());
		customer.setRemoved(value.getRemoved());
		
		return customer;
	}
}
