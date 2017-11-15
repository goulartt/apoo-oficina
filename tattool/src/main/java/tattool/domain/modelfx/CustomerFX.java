package tattool.domain.modelfx;

import java.util.Date;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.SimpleStringProperty;
import tattool.domain.model.Address;
import tattool.domain.model.Contact;

public class CustomerFX extends RecursiveTreeObject<UserFX> {
	public SimpleStringProperty cpf;
	public SimpleStringProperty name;
	public SimpleStringProperty contact;
	public SimpleStringProperty email;
	public Integer idContato;
	private Date birthDate;
	private Address address;
	private char archived;
	public SimpleStringProperty getCpf() {
		return cpf;
	}
	public void setCpf(SimpleStringProperty cpf) {
		this.cpf = cpf;
	}
	public SimpleStringProperty getName() {
		return name;
	}
	public void setName(SimpleStringProperty name) {
		this.name = name;
	}
	public SimpleStringProperty getContact() {
		return contact;
	}
	public void setContact(SimpleStringProperty contact) {
		this.contact = contact;
	}
	public SimpleStringProperty getEmail() {
		return email;
	}
	public void setEmail(SimpleStringProperty email) {
		this.email = email;
	}
	public Integer getIdContato() {
		return idContato;
	}
	public void setIdContato(Integer idContato) {
		this.idContato = idContato;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public char getArchived() {
		return archived;
	}
	public void setArchived(char archived) {
		this.archived = archived;
	}
	
	

}
