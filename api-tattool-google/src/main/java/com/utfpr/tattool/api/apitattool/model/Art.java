package com.utfpr.tattool.api.apitattool.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity(name="art")
@Table(name = "art")
public class Art {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_art", unique = true, nullable = false)
	private Integer id;
	
	 @Column(name = "description", length=65)
	private String description;
	
	 @Lob
	 @Column(name="image", nullable=false, columnDefinition="mediumblob")
	 private byte[] image;
    
    @ManyToMany
    @JoinTable(name = "art_has_tag", joinColumns = @JoinColumn(name = "art_id_art", referencedColumnName = "id_art"), inverseJoinColumns = @JoinColumn(name = "tag_id_tag", referencedColumnName = "id_tag"))
    private Set<Tag> tags = new HashSet<Tag>(0);

	
	
	public Art() {
		
	}

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

	

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}
	
	

	
}
