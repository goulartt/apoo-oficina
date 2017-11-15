package com.utfpr.tattool.api.apitattool.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.utfpr.tattool.api.apitattool.model.Art;

public interface ArtRepository extends JpaRepository<Art, Integer> {

	public List<Art> findByTags_TagContaining(String tag);
	
}
