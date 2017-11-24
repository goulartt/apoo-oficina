package tattool.rest.consume;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import tattool.domain.model.Cep;
import tattool.domain.model.Customer;
import tattool.util.Constantes;

public class CustomerRest {
	private RestTemplate rest = new RestTemplate();

	public Customer[] findAll() {
		String url = Constantes.Api.URL_API + "/customers";
		rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		return rest.getForObject(url, Customer[].class);

	}
	
	public Customer findById(Integer id) {
		String url = Constantes.Api.URL_API + "/customers/{codigo}";
		
		Map<String, Integer> params = new HashMap<String, Integer>();
	    params.put("codigo", id); 
	    return rest.getForObject(url, Customer.class);
	}

	public Customer save(Customer customer) {
		return rest.postForObject(Constantes.Api.URL_API + "/customers", customer, Customer.class);
	}
	
	public void deleteCustomer(Integer id) {
		String url = Constantes.Api.URL_API+"/customers/{codigo}";
		
		Map<String, Integer> params = new HashMap<String, Integer>();
	    params.put("codigo", id);
	     
	    try {
	    	rest.delete ( url,  params );
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }
	}
	
	public void atualizaCustomer(Integer id, Customer customer) {
		String url = Constantes.Api.URL_API+"/customers/"+id;
		HttpEntity<Customer> entity = new HttpEntity<Customer>(customer);
		rest.exchange(url, HttpMethod.PUT, entity, Customer.class);
	}
	
	public Cep buscaCep(String cep ) {
		return rest.getForObject("http://viacep.com.br/ws/"+cep+"/json", Cep.class);
	}
	
	public static void main(String[] args) {
		Customer c = new Customer();
		c.setName("teste2");
		c.setCpf("cpf2");
		//c.setBirthDate(new Date());
		c.getContact().setEmail("jv.goulart.almeida@hotmail.com");
		c.getContact().setPhone("18 996501306");
		c.getAddress().setCity("Assis");
		CustomerRest rest = new CustomerRest();
		Customer customerSalvo = rest.save(c);
		List<Customer> clientes = Arrays.asList(rest.findAll());
		clientes.forEach(a -> System.out.println(a.getName()));
		customerSalvo.setCpf("opa atualizou");
		customerSalvo.getContact().setEmail("emailteste");
		customerSalvo.getAddress().setCity("assis");
		rest.atualizaCustomer(customerSalvo.getId(), customerSalvo);
		//rest.deleteCustomer(customerSalvo.getId());
		
		
		
	}
}
