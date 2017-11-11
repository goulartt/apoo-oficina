package com.utfpr.tattool.api.apitattool.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.utfpr.tattool.api.apitattool.model.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {

	
	public Tag findByTag(String tag);

	
	
}
