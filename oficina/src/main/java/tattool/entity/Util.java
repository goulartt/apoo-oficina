package tattool.entity;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import tattool.domain.dto.UsuarioSimple;
import tattool.domain.model.Usuario;

public class Util {
	public List<UsuarioSimple> parseUsuario(List<Usuario> u) {
		List<UsuarioSimple> usuariosSimples = new ArrayList<>();
		for(Usuario usuario : u) {
			UsuarioSimple simple = new UsuarioSimple(usuario.getId(), usuario.getRole(), usuario.getUsuario(), usuario.getNome(), usuario.getSenha());
			
			usuariosSimples.add(simple);
		}
		
		return usuariosSimples;
	}
}
