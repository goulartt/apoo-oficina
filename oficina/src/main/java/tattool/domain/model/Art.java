package tattool.domain.model;

public class Art {
	
	private Integer id;
	
	private String description;
	private byte image;
	private String tag;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public byte getImage() {
		return image;
	}
	public void setImage(byte image) {
		this.image = image;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	
}
