package tattool.rest.consume;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import tatool.util.Constantes;
import tattool.domain.model.Customer;

public class CustomerRest {
	private RestTemplate rest = new RestTemplate();

	public Customer[] findAll() {
		String url = Constantes.Api.URL_DEV + "/customers";
		rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		return rest.getForObject(url, Customer[].class);

	}

	public void save(Customer customer) {
		rest.postForObject(Constantes.Api.URL_DEV + "/customers", customer, Customer.class);
	}
	
	public void deleteCustomer(Integer id) {
		String url = Constantes.Api.URL_DEV+"/customers/{codigo}";
		
		Map<String, Integer> params = new HashMap<String, Integer>();
	    params.put("codigo", id);
	     
	    try {
	    	rest.delete ( url,  params );
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }
	}
	
	public void atualizaCustomer(Integer id, Customer customer) {
		String url = Constantes.Api.URL_DEV+"/customers/"+id;
		HttpEntity<Customer> entity = new HttpEntity<Customer>(customer);
		rest.exchange(url, HttpMethod.PUT, entity, Customer.class);
	}
	
	public static void main(String[] args) {
		Customer c = new Customer();
		c.setName("teste");
		c.setCpf("cpf");
		c.setBirthDate(new Date());
		c.getContact().setEmail("jv.goulart.almeida@hotmail.com");
		c.getContact().setPhone("18 996501306");
		c.getAddress().setCity("Assis");
		CustomerRest rest = new CustomerRest();
		rest.save(c);
		List<Customer> clientes = Arrays.asList(rest.findAll());
		clientes.forEach(a -> System.out.println(a.getName()));

		
		
	}
}
