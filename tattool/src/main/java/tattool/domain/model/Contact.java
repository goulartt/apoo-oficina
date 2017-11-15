package tattool.domain.model;

public class Contact {
	
	private Integer id;
	private String phone;
	private String email;
	
	public Contact() {
		this.id = 0;
		this.phone = "";
		this.email = "";
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

}
