package tattool.domain.modelfx;

import java.util.List;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import tattool.domain.model.Art;
import tattool.domain.model.Customer;

public class ServiceFX extends RecursiveTreeObject<ServiceFX> {
	
	private Integer id;
	public SimpleStringProperty name;
	public SimpleStringProperty customeName;
	public SimpleStringProperty status;
	private List<Art> arts;
	
	private Customer customer;
	
	private Integer removed;
	
	private Integer quantSessions;
	
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public SimpleStringProperty getName() {
		return name;
	}

	public void setName(SimpleStringProperty name) {
		this.name = name;
	}

	public StringProperty getCustomeName() {
		return customeName;
	}

	public void setCustomeName(SimpleStringProperty customeNamer) {
		this.customeName = customeNamer;
	}

	public SimpleStringProperty getStatus() {
		return status;
	}

	public void setStatus(SimpleStringProperty status) {
		this.status = status;
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
