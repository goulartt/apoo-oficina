package com.utfpr.tattool.api.apitattool.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.utfpr.tattool.api.apitattool.model.Session;

public interface SessionRepository extends JpaRepository<Session, Integer> {

}
