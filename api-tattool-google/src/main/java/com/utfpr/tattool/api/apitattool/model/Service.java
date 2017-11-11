package com.utfpr.tattool.api.apitattool.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Service {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String nameService;
	
	@OneToMany
	private List<Art> arts;
	
	@OneToMany
	private List<Session> sessions;
	
	@ManyToOne
	private Customer customer;
	
	private Integer status;
	
	private char archived;

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

	public List<Session> getSessions() {
		return sessions;
	}

	public void setSessions(List<Session> sessions) {
		this.sessions = sessions;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public char getArchived() {
		return archived;
	}

	public void setArchived(char archived) {
		this.archived = archived;
	}
	
	
	
}
