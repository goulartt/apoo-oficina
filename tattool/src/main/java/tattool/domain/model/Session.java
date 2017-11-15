package tattool.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

public class Session implements Serializable{
	
	private static final long serialVersionUID = -8843063860683914321L;

	private Integer id;
	
	private Calendar dateSession;
	
	private BigDecimal price;
	
	private Integer status;
	
	private String obs;
	
	
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}


	
	
}
