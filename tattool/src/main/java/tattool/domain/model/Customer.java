package tattool.domain.model;

import java.time.LocalDate;
import java.util.Date;

public class Customer {
	
	private Integer id;
	private String cpf;
	private String name;
	
	private LocalDate birthDate;
	
	private Contact contact;
	
	private Address address;
	
	private char[] archived;
	
	public Customer() {
		super();
		this.id = 0;
		this.cpf = "";
		this.name = "";
		this.birthDate = null;
		this.contact = new Contact();
		this.address = new Address();
		this.archived = new char['N'];
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
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

	public char[] getArchived() {
		return archived;
	}

	public void setArchived(char[] archived) {
		this.archived = archived;
	}
	
	
}
