package com.utfpr.tattool.api.apitattool.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String usuario;
	private String senha;
	private String nome;
	private Integer role;
	private char archived;
	
	public User() {
	}
	public User(String usuario, String senha, String nome, Integer role, char archived) {
		this.usuario = usuario;
		this.senha = senha;
		this.nome = nome;
		this.role = role;
		this.archived = archived;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Integer getRole() {
		return role;
	}
	public void setRole(Integer role) {
		this.role = role;
	}
	public char getArchived() {
		return archived;
	}
	public void setArchived(char archived) {
		this.archived = archived;
	}
	
	
}
