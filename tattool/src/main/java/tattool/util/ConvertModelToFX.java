package tattool.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tattool.domain.model.User;
import tattool.domain.modelfx.UserFX;

public class ConvertModelToFX {
	
	public static User convertUser(UserFX u) {
		User user = new User();
		if(u.getId() != null) user.setId(u.getId());
		user.setNome(u.getNome().get());
		String role = u.getRole().get();
		user.setRole(Integer.parseInt(role));
		user.setSenha(u.getSenha().get());
		user.setUsuario(u.getUsuario().get());
		return user;
		
	}
	
	public static UserFX convertUserFX(User u) {
		UserFX user = new UserFX(u.getUsuario(), u.getSenha(), u.getNome(), u.getRole().toString());
		user.setId(u.getId());
		return user;
	}

	public static List<UserFX> convertListUser(User[] findAllUsers) {
	  	List<UserFX> userFX = new ArrayList<>();
    	List<User> user = Arrays.asList(findAllUsers);
    	for(User u : user) {
    		userFX.add(ConvertModelToFX.convertUserFX(u));
    	}
    	return userFX;
	}
	
	public static List<User> convertListUserFX(List<UserFX> user){
		List<User> userConvertido = new ArrayList<>();
		for(UserFX u : user) {
			userConvertido.add(ConvertModelToFX.convertUser(u));
    	}
    	return userConvertido;
	}
}
