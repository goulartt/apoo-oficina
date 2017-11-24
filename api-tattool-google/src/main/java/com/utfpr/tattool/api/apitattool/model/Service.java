package com.utfpr.tattool.api.apitattool.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Service implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 5381743236953194235L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="name_service")
	private String nameService;
	
	@Column(name="quant_sessions")
	private Integer quantSessions;
	
	@ManyToMany
	@JoinTable(name = "service_has_art", joinColumns = @JoinColumn(name = "service_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "art_id_art", referencedColumnName = "id_art"))
	private List<Art> arts;
	
	@OneToOne
	@JoinColumn(name = "customer_id", unique=true)
	private Customer customer;
	
	private String status;
	
	private Integer removed;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNameService() {
		return nameService;
	}

	public void setNameService(String nameService) {
		this.nameService = nameService;
	}

	public List<Art> getArts() {
		return arts;
	}

	public void setArts(List<Art> arts) {
		this.arts = arts;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getRemoved() {
		return removed;
	}

	public void setRemoved(Integer removed) {
		this.removed = removed;
	}

	public Integer getQuantSessions() {
		return quantSessions;
	}

	public void setQuantSessions(Integer quantSessions) {
		this.quantSessions = quantSessions;
	}

	
	
	
}
