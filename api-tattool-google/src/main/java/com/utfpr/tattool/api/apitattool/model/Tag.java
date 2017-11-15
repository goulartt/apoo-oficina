package com.utfpr.tattool.api.apitattool.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity(name="tag")
@Table(name = "tag")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Tag implements Serializable{
	 	
		@Id
	    @GeneratedValue(strategy=GenerationType.AUTO)
		@Column(name = "id_tag", unique = true, nullable = false)
	    private long id;
	 
	 	
	    @Column(name = "tag", nullable = false, length=45)
	    @NotNull
	    private String tag;
	    
	    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "tags")
	    @JsonIgnore
	    private Set<Art> arts  = new HashSet<Art>(0);

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getTag() {
			return tag;
		}

		public void setTag(String tag) {
			this.tag = tag;
		}

		public Set<Art> getArts() {
			return arts;
		}

		public void setArts(Set<Art> arts) {
			this.arts = arts;
		}
	    
	    
}
