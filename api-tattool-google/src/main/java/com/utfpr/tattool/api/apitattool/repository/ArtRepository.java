package com.utfpr.tattool.api.apitattool.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.utfpr.tattool.api.apitattool.model.Art;
import com.utfpr.tattool.api.apitattool.model.User;

public interface ArtRepository extends JpaRepository<Art, Integer> {

	public List<Art> findByTags_TagContaining(String tag);
	
}
