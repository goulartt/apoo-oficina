package tattool.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;


public class Session implements Serializable{
	
	private static final long serialVersionUID = -8843063860683914321L;

	private Integer id;
	
	private Calendar dateSession;
	
	private BigDecimal price;
	
	private String status;
	
	private String obs;
	
	private Service service;
	
	private Integer duration;
	
	private Integer removed;
	
	
	public Session() {
		this.id = null;
		this.dateSession = null;
		this.price = null;
		this.status = "PENDENTE";
		this.obs = "";
		this.service = new Service();
		this.duration = 0;
		this.removed = 0;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public Calendar getDateSession() {
		return dateSession;
	}

	public void setDateSession(Calendar dateSession) {
		this.dateSession = dateSession;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Integer getRemoved() {
		return removed;
	}

	public void setRemoved(Integer removed) {
		this.removed = removed;
	}
	
	

	
	
}
