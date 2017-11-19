package tattool.domain.modelfx;

import java.util.Date;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.SimpleStringProperty;
import tattool.domain.model.Address;
import tattool.domain.model.Contact;

public class CustomerFX extends RecursiveTreeObject<CustomerFX> {
	private Integer id;
	public SimpleStringProperty cpf;
	public SimpleStringProperty name;
	public SimpleStringProperty contactSimple;
	private SimpleStringProperty email;
	
	private Date birthDate;
	
	private Contact contact;
	
	private Address address;
	
	private Integer removed;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
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
	public SimpleStringProperty getEmail() {
		return email;
	}
	public void setEmail(SimpleStringProperty email) {
		this.email = email;
	}
	public SimpleStringProperty getContactSimple() {
		return contactSimple;
	}
	public void setContactSimple(SimpleStringProperty contactSimple) {
		this.contactSimple = contactSimple;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public Contact getContact() {
		return contact;
	}
	public void setContact(Contact contact) {
		this.contact = contact;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public Integer getRemoved() {
		return removed;
	}
	public void setRemoved(Integer removed) {
		this.removed = removed;
	}
	
	
	


}
