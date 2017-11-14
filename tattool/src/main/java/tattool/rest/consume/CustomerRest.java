package tattool.rest.consume;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import tattool.domain.model.Customer;
import tattool.util.Constantes;

public class CustomerRest {
	private RestTemplate rest = new RestTemplate();

	public Customer[] findAll() {
		String url = Constantes.Api.URL_API + "/customers";
		rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		return rest.getForObject(url, Customer[].class);

	}

	public void save(Customer customer) {
		rest.postForObject(Constantes.Api.URL_API + "/customers", customer, Customer.class);
	}
	
	public void deleteImage(Integer id) {
		String url = Constantes.Api.URL_API+"/customers/{codigo}";
		
		Map<String, Integer> params = new HashMap<String, Integer>();
	    params.put("codigo", id);
	     
	    RestTemplate restTemplate = new RestTemplate();
	    try {
	    	restTemplate.delete ( url,  params );
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }
	}
}
