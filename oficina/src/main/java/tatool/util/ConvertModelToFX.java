package tatool.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.util.Callback;
import tatool.domain.modelfx.UserFX;
import tattool.domain.model.User;

public class ConvertModelToFX {
	
	public static User convertUser(UserFX u) {
		User user = new User();
		user.setNome(u.getNome().toString());
		user.setRole(Integer.parseInt(u.getRole().toString()));
		user.setSenha(u.getSenha().toString());
		user.setUsuario(u.getUsuario().toString());
		return user;
		
	}
	
	public static UserFX convertUserFX(User u) {
		UserFX user = new UserFX(u.getUsuario(), u.getSenha(), u.getNome(), u.getRole().toString());

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
}
