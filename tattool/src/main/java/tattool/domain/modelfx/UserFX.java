package tattool.domain.modelfx;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.SimpleStringProperty;

public class UserFX extends RecursiveTreeObject<UserFX>{
private Integer id;
	
	public SimpleStringProperty usuario;
	public SimpleStringProperty senha;
	public SimpleStringProperty nome;
	public SimpleStringProperty role;
	private SimpleStringProperty archived;

	public UserFX(String usuario, String senha, String nome, String role) {
		this.usuario = new SimpleStringProperty(usuario);
		this.senha = new SimpleStringProperty(senha);
		this.nome = new SimpleStringProperty(nome);
		this.role = new SimpleStringProperty(role);
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public SimpleStringProperty getUsuario() {
		return usuario;
	}
	public void setUsuario(SimpleStringProperty usuario) {
		this.usuario = usuario;
	}
	public SimpleStringProperty getSenha() {
		return senha;
	}
	public void setSenha(SimpleStringProperty senha) {
		this.senha = senha;
	}
	public SimpleStringProperty getNome() {
		return nome;
	}
	public void setNome(SimpleStringProperty nome) {
		this.nome = nome;
	}
	public SimpleStringProperty getRole() {
		return role;
	}
	public void setRole(SimpleStringProperty role) {
		this.role = role;
	}
	public SimpleStringProperty getArchived() {
		return archived;
	}
	public void setArchived(SimpleStringProperty archived) {
		this.archived = archived;
	}
	
	
	
}
