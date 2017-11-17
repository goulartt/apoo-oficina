package tattool.domain.model;

public class User {
	
	private Integer id;
	
	private String usuario;
	private String senha;
	private String nome;
	private Integer role;
	private Integer removed;
	
	public User() {
	}
	public User(String usuario, String senha, String nome, Integer role) {
		this.usuario = usuario;
		this.senha = senha;
		this.nome = nome;
		this.role = role;
		this.removed = 0;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
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
	public Integer getRemoved() {
		return removed;
	}
	public void setRemoved(Integer removed) {
		this.removed = removed;
	}
	
	
	
}
