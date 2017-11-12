package com.utfpr.tattool.api.apitattool.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.utfpr.tattool.api.apitattool.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
