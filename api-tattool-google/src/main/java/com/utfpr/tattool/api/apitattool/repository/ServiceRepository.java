package com.utfpr.tattool.api.apitattool.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.utfpr.tattool.api.apitattool.model.Customer;
import com.utfpr.tattool.api.apitattool.model.Service;

public interface ServiceRepository extends JpaRepository<Service, Integer> {

	List<Service> findByCustomer(Customer cliente);

}
