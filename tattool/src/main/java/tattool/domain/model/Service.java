package tattool.domain.model;

import java.io.Serializable;
import java.util.List;

public class Service implements Serializable {

	private static final long serialVersionUID = -5680202725613141522L;

	private Integer id;
	
	private String nameService;
	
	private List<Art> arts;
	
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

	
	
	
}
