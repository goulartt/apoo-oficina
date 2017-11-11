package com.utfpr.tattool.api.apitattool.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import com.utfpr.tattool.api.apitattool.repository.CustomerRepository;
import com.utfpr.tattool.api.apitattool.model.Customer;

@Service
public class CustomerService {

	@Autowired private CustomerRepository customerRepository;
	
	
	public Customer customerAtualiza(Integer codigo, Customer customer) {
		Customer customerSalvo = customerRepository.findOne(codigo);
		if(customerSalvo == null) {
			throw new EmptyResultDataAccessException(1);
		}
		BeanUtils.copyProperties(customer, customerSalvo, "id");
		
		return customerRepository.save(customerSalvo);
	}


	
}
