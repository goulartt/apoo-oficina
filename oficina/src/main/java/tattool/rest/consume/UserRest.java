package tattool.rest.consume;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import tattool.domain.model.User;

public class UserRest {
	
	private RestTemplate rest = new RestTemplate();
	
	public void verificaAdmin() {
		rest.getForObject("http://tattool-api.herokuapp.com/users/verify/", User.class);
	}
	
	
	public User[] findAllUsers() {
		return rest.getForObject("http://tattool-api.herokuapp.com/users/", User[].class);
	}

	
	public User existeUsername(String usuario) {
		String url = "http://tattool-api.herokuapp.com/users/verify/username";

		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
		        // Add query parameter
		        .queryParam("usuario", usuario);
		return rest.getForObject(builder.buildAndExpand().toUri(), User.class);  
   
	}
	
	public User verificaLogin(String usuario, String senha){
		MultiValueMap<String, String> parametersMap = new LinkedMultiValueMap<String, String>();
			parametersMap.add("usuario", usuario);
			parametersMap.add("senha", senha);
		try {
			return rest.postForObject("http://tattool-api.herokuapp.com/users/verify", parametersMap, User.class);
		}catch(HttpClientErrorException e) {
			System.out.println("Usuario e senha invalido");
			return null;
		}
		
	}
	
	public User save(User user) {
		return rest.postForObject("http://tattool-api.herokuapp.com/users", user, User.class);
	}
	
	
	
	
}
