package com.utfpr.tattool.api.apitattool.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.utfpr.tattool.api.apitattool.model.Service;

public interface ServiceRepository extends JpaRepository<Service, Integer> {

}
