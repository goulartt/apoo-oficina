package tattool.rest.consume;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import tattool.domain.model.Service;
import tattool.domain.model.Session;
import tattool.util.Constantes;

public class SessionRest {
	private RestTemplate rest = new RestTemplate();

	public Session[] findAll() {
		String url = Constantes.Api.URL_API + "/sessions";
		rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		return rest.getForObject(url, Session[].class);

	}

	public Session save(Session customer) {
		return rest.postForObject(Constantes.Api.URL_API + "/sessions", customer, Session.class);
	}
	
	public void deleteSession(Integer id) {
		String url = Constantes.Api.URL_API+"/sessions/{codigo}";
		
		Map<String, Integer> params = new HashMap<String, Integer>();
	    params.put("codigo", id);
	     
	    try {
	    	rest.delete ( url,  params );
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }
	}
	
	public void atualizaSession(Integer id, Session customer) {
		String url = Constantes.Api.URL_API+"/sessions/"+id;
		HttpEntity<Session> entity = new HttpEntity<Session>(customer);
		rest.exchange(url, HttpMethod.PUT, entity, Session.class);
	}
	
	public static void main(String[] args) {
		Session c = new Session();
		ServiceRest restC = new ServiceRest();
		SessionRest rest = new SessionRest();
		List<Service> customer = Arrays.asList(restC.findAll());
		c.setObs("ELE É ALERGICO CARALGO");
		c.setPrice(new BigDecimal(350.54));
		c.setStatus("PENDENTE");
		c.setDateSession(Calendar.getInstance());
		c.setService(customer.get(0));
		c.setDuration(55);
		Session customerSalvo = rest.save(c);
		List<Session> clientes = Arrays.asList(rest.findAll());
		customerSalvo.setObs("era zuera");
		rest.atualizaSession(customerSalvo.getId(), customerSalvo);
		//rest.deleteSession(customerSalvo.getId());

	}
}
