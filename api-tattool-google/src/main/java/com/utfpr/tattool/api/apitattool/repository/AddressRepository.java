package com.utfpr.tattool.api.apitattool.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.utfpr.tattool.api.apitattool.model.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {

}
