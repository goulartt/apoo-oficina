package tattool.rest.consume;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import tattool.domain.model.User;

public class UserRest {
	
	private RestTemplate rest = new RestTemplate();
	
	public boolean verificaAdmin() {
		if(rest.getForObject("http://api-tattool.us-east-2.elasticbeanstalk.com/users/verify/", HttpStatus.class).is2xxSuccessful()) {
			return true;
		}
		return false;
	}
	
	
	public User[] findAllUsers() {
		return rest.getForObject("http://api-tattool.us-east-2.elasticbeanstalk.com/users/", User[].class);
	}

	
	public User existeUsername(String usuario) {
		String url = "http://api-tattool.us-east-2.elasticbeanstalk.com/users/verify/username";

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
			return rest.postForObject("http://api-tattool.us-east-2.elasticbeanstalk.com/users/verify", parametersMap, User.class);
		}catch(HttpClientErrorException e) {
			System.out.println("Usuario e senha invalido");
			return null;
		}
		
	}
	
	public User save(User user) {
		return rest.postForObject("http://api-tattool.us-east-2.elasticbeanstalk.com/users", user, User.class);
	}
	
	
	
	
}
