package tattool.rest.consume;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import tattool.domain.model.Customer;
import tattool.domain.model.Service;
import tattool.util.Constantes;

public class ServiceRest {
	private RestTemplate rest = new RestTemplate();

	public Service[] findAll() {
		String url = Constantes.Api.URL_API + "/services";
		rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		return rest.getForObject(url, Service[].class);

	}

	public Service save(Service customer) {
		return rest.postForObject(Constantes.Api.URL_API + "/services", customer, Service.class);
	}
	
	public void deleteService(Integer id) {
		String url = Constantes.Api.URL_API+"/services/{codigo}";
		
		Map<String, Integer> params = new HashMap<String, Integer>();
	    params.put("codigo", id);
	     
	    try {
	    	rest.delete ( url,  params );
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }
	}
	
	public void atualizaService(Integer id, Service customer) {
		String url = Constantes.Api.URL_API+"/services/"+id;
		HttpEntity<Service> entity = new HttpEntity<Service>(customer);
		rest.exchange(url, HttpMethod.PUT, entity, Service.class);
	}
	
	public static void main(String[] args) {
		Service c = new Service();
		CustomerRest restC = new CustomerRest();
		List<Customer> customer = Arrays.asList(restC.findAll());
		c.setCustomer(customer.get(0));
		c.setNameService("Serviço de qualidade");
		c.setRemoved(0);
		c.setStatus("ATIVO");
		ServiceRest rest = new ServiceRest();
		Service customerSalvo = rest.save(c);
		List<Service> clientes = Arrays.asList(rest.findAll());
		customerSalvo.setNameService("Serviço de qualidade atualizado");
		rest.atualizaService(customerSalvo.getId(), customerSalvo);
		//rest.deleteService(customerSalvo.getId());

	}
}
