package tattool.rest.consume;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import tatool.util.Constantes;
import tattool.domain.model.Customer;
import tattool.domain.model.User;

public class ConsumerRest {
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
	
	public Customer existeCustomer(String nome) {

		String url = Constantes.Api.URL_API+"/users/verify/username";

		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
		        // Add query parameter
		        .queryParam("nome", nome);
		return rest.getForObject(builder.buildAndExpand().toUri(), Customer.class);  
   
	}
	
	
}
