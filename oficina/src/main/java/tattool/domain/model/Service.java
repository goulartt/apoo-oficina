package tattool.domain.model;

import java.util.List;

public class Service {

	private Integer id;
	
	private String nameService;
	
	private List<Art> arts;
	
	private List<Session> sessions;
	
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
