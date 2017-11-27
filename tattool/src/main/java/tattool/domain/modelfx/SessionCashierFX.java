package tattool.domain.modelfx;

import java.math.BigDecimal;
import java.util.Date;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.SimpleStringProperty;
import tattool.domain.model.Service;

public class SessionCashierFX extends RecursiveTreeObject<SessionCashierFX> {
	private Integer id;
	public SimpleStringProperty date;
	public SimpleStringProperty nomeServico;
	public SimpleStringProperty preco;
	public SimpleStringProperty pago;
	
	private Date dateSession;
	
	private BigDecimal price;
	
	private BigDecimal paid;
	
	private String status;
	
	private String obs;
	
	private Service service;
	
	private Integer duration;
	
	private Integer removed;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public SimpleStringProperty getDate() {
		return date;
	}

	public void setDate(SimpleStringProperty date) {
		this.date = date;
	}

	public SimpleStringProperty getNomeServico() {
		return nomeServico;
	}

	public void setNomeServico(SimpleStringProperty nomeServico) {
		this.nomeServico = nomeServico;
	}

	public SimpleStringProperty getPreco() {
		return preco;
	}

	public void setPreco(SimpleStringProperty preco) {
		this.preco = preco;
	}

	public SimpleStringProperty getPago() {
		return pago;
	}

	public void setPago(SimpleStringProperty pago) {
		this.pago = pago;
	}

	public Date getDateSession() {
		return dateSession;
	}

	public void setDateSession(Date dateSession) {
		this.dateSession = dateSession;
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

	public BigDecimal getPaid() {
		return paid;
	}

	public void setPaid(BigDecimal paid) {
		this.paid = paid;
	}
	
	
	
}
