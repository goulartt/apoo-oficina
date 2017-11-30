package tattool.rest.consume;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import tattool.domain.model.Session;
import tattool.util.Constantes;

public class SessionRest {
	private RestTemplate rest = new RestTemplate();

	public Session[] findAll() {
		String url = Constantes.Api.URL_API + "/sessions";
		rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		return rest.getForObject(url, Session[].class);

	}
	public Session[] findAllAgendados() {
		String url = Constantes.Api.URL_API + "/sessions/agendado";
		rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		return rest.getForObject(url, Session[].class);

	}
	
	public Session[] filtro(String status, LocalDate inicio, LocalDate fim) {
		String url = Constantes.Api.URL_DEV + "/sessions/filtro";
	
	    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
	            .queryParam("status", status)
	            .queryParam("inicio", inicio)
			    .queryParam("fim", fim);
	    HttpHeaders header = new HttpHeaders();
	    header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
	    rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
	    HttpEntity<String> entity = new HttpEntity<String>(header);
	    return rest.exchange(builder.build().encode().toUriString() , HttpMethod.GET,
	            entity, Session[].class).getBody();
		

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
		/*Session c = new Session();
		ServiceRest restC = new ServiceRest();
		SessionRest rest = new SessionRest();
		List<Service> customer = Arrays.asList(restC.findAll());
		c.setObs("ELE É ALERGICO CARALGO");
		c.setPrice(new BigDecimal(350.54));
		c.setStatus("PENDENTE");
		Date in = new Date();
		LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());
		Date out = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
		c.setDateSession(out);
		c.setService(customer.get(0));
		c.setDuration(55);
		Session customerSalvo = rest.save(c);*/
		new SessionRest().filtro("PENDENTE", LocalDate.now(), LocalDate.now());
		/*List<Session> clientes = Arrays.asList(rest.findAll());
		customerSalvo.setObs("era zuera");
		rest.atualizaSession(customerSalvo.getId(), customerSalvo);*/
		//rest.deleteSession(customerSalvo.getId());

	}

	public List<Session> findByService(Integer id) {
		String url = Constantes.Api.URL_API+"/sessions/service/{codigo}";
		Map<String, Integer> params = new HashMap<String, Integer>();
	    params.put("codigo", id);
	    rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		return Arrays.asList(rest.getForObject(url, Session[].class, params));
	}
}
