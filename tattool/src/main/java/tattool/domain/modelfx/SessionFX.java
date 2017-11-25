package tattool.domain.modelfx;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.SimpleStringProperty;
import tattool.domain.model.Service;

public class SessionFX  extends RecursiveTreeObject<SessionFX> {
	
	private Integer id;
	public SimpleStringProperty price;
	public SimpleStringProperty date;
	public SimpleStringProperty duration;
	public SimpleStringProperty status;
	private Integer removed;
	private String obs;
	private Service service;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public SimpleStringProperty getPrice() {
		return price;
	}
	public void setPrice(SimpleStringProperty price) {
		this.price = price;
	}
	public SimpleStringProperty getDate() {
		return date;
	}
	public void setDate(SimpleStringProperty date) {
		this.date = date;
	}
	public SimpleStringProperty getDuration() {
		return duration;
	}
	public void setDuration(SimpleStringProperty duration) {
		this.duration = duration;
	}
	public SimpleStringProperty getStatus() {
		return status;
	}
	public void setStatus(SimpleStringProperty status) {
		this.status = status;
	}
	public Integer getRemoved() {
		return removed;
	}
	public void setRemoved(Integer removed) {
		this.removed = removed;
	}
	public String getObs() {
		return obs;
	}
	public void setObs(String obs) {
		this.obs = obs;
	}
	public Service getService() {
		return service;
	}
	public void setService(Service service) {
		this.service = service;
	}
	
	
	
	

}
