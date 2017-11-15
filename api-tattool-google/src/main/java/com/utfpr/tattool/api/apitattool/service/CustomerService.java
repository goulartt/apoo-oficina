package com.utfpr.tattool.api.apitattool.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.utfpr.tattool.api.apitattool.model.Address;
import com.utfpr.tattool.api.apitattool.model.Contact;
import com.utfpr.tattool.api.apitattool.model.Customer;
import com.utfpr.tattool.api.apitattool.repository.AddressRepository;
import com.utfpr.tattool.api.apitattool.repository.ContactRepository;
import com.utfpr.tattool.api.apitattool.repository.CustomerRepository;

@Service
public class CustomerService {

	@Autowired 
	private CustomerRepository customerRepository;

	public Customer customerAtualiza(Integer codigo, Customer customer) {
		Customer customerSalvo = customerRepository.findOne(codigo);
		if(customerSalvo == null) {
			throw new EmptyResultDataAccessException(1);
		}
		BeanUtils.copyProperties(customer, customerSalvo, "id");
		return customerRepository.save(customerSalvo);
	}


	
}
