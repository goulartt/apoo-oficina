package tattool.rest.consume;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import tattool.domain.model.User;
import tattool.util.Constantes;

public class UserRest {
	
	private RestTemplate rest = new RestTemplate();
	
	public boolean verificaAdmin() {
		if(rest.getForObject(Constantes.Api.URL_API+"/users/verify/", HttpStatus.class).is2xxSuccessful()) {
			return true;
		}
		return false;
	}
	
	
	
	public User[] findAllUsers() {
		User [] u =  rest.getForObject(Constantes.Api.URL_API+"/users/", User[].class);
		return u;
	}

	
	public User existeUsername(String usuario) {
		String url = Constantes.Api.URL_API+"/users/verify/username";

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
			return rest.postForObject(Constantes.Api.URL_API+"/users/verify", parametersMap, User.class);
		}catch(HttpClientErrorException e) {
			System.out.println("Usuario e senha invalido");
			return null;
		}
		
	}
	
	public User save(User user) {
		return rest.postForObject(Constantes.Api.URL_API+"/users", user, User.class);
	}
	
	public HttpStatus deleteUser(Integer codigo) {
		String url = Constantes.Api.URL_API+"/users/{codigo}";
		
		Map<String, Integer> params = new HashMap<String, Integer>();
	    params.put("codigo", codigo);
	     
	    RestTemplate restTemplate = new RestTemplate();
	    try {
	    	restTemplate.delete ( url,  params );
	    	return HttpStatus.OK;
	    }catch(Exception e) {
	    	e.printStackTrace();
	    	return HttpStatus.INTERNAL_SERVER_ERROR;
	    }
	}
	
	
	
}
