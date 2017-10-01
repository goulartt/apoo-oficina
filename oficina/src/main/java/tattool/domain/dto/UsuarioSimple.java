package tattool.domain.dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UsuarioSimple {
	private SimpleIntegerProperty id;
	private SimpleIntegerProperty role;
	private SimpleStringProperty usuario;
	private SimpleStringProperty nome;
	private SimpleStringProperty senha;
	
	
	
	public UsuarioSimple(Integer id, Integer role, String usuario, String nome,
			String senha) {
		this.id = new SimpleIntegerProperty(id);
		this.role = new SimpleIntegerProperty(role);
		this.usuario = new SimpleStringProperty(usuario);
		this.nome = new SimpleStringProperty(nome);
		this.senha = new SimpleStringProperty(senha);
	}

	public Integer getId() {
		return id.get();
	}
	public void setId(Integer id) {
		this.id.set(id);
	}
	public Integer getRole() {
		return role.get();
	}
	public void setRole(Integer role) {
		this.role.set(role);;
	}
	public String getUsuario() {
		return usuario.get();
	}
	public void setUsuario(String usuario) {
		this.usuario.set(usuario);
	}
	public String getNome() {
		return nome.get();
	}
	public void setNome(String nome) {
		this.nome.set(nome); 
	}
	public String getSenha() {
		return senha.get();
	}
	public void setSenha(String senha) {
		this.senha.set(senha);
	}
	
	
}
