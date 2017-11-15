package com.utfpr.tattool.api.apitattool.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

import com.utfpr.tattool.api.apitattool.model.Service;
import com.utfpr.tattool.api.apitattool.repository.ServiceRepository;

@org.springframework.stereotype.Service
public class ServicesService {

	@Autowired 
	private ServiceRepository serviceRepository;

	
	public Service serviceAtualiza(Integer codigo, Service service) {
		Service serviceSalvo = serviceRepository.findOne(codigo);
		if(serviceSalvo == null) {
			throw new EmptyResultDataAccessException(1);
		}
		BeanUtils.copyProperties(service, serviceSalvo, "id");
		return serviceRepository.save(serviceSalvo);
	}


	
}
