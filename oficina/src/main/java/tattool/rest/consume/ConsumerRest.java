package tattool.rest.consume;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import tatool.util.Constantes;
import tattool.domain.model.Art;
import tattool.domain.model.Customer;
import tattool.domain.model.Tag;

public class ConsumerRest {
	private RestTemplate rest = new RestTemplate();

	public Customer[] findAll() {
		String url = Constantes.Api.URL_DEV + "/customers";
		rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		return rest.getForObject(url, Customer[].class);

	}

	public void save(Customer customer) {
		rest.postForObject(Constantes.Api.URL_DEV + "/customers", customer, Customer.class);
	}
	
	public void deleteImage(Integer id) {
		String url = Constantes.Api.URL_DEV+"/customers/{codigo}";
		
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
