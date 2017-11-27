package com.utfpr.tattool.api.apitattool.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.utfpr.tattool.api.apitattool.model.Service;
import com.utfpr.tattool.api.apitattool.model.Session;

public interface SessionRepository extends JpaRepository<Session, Integer> {

	List<Session> findByService(Service findOne);

	List<Session> findByStatus(String string);

}
