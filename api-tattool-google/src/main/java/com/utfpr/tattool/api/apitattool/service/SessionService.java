package com.utfpr.tattool.api.apitattool.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.utfpr.tattool.api.apitattool.model.Session;
import com.utfpr.tattool.api.apitattool.repository.AddressRepository;
import com.utfpr.tattool.api.apitattool.repository.ContactRepository;
import com.utfpr.tattool.api.apitattool.repository.SessionRepository;

@Service
public class SessionService {

	@Autowired 
	private SessionRepository sessionRepository;

	
	public Session sessionAtualiza(Integer codigo, Session session) {
		Session sessionSalvo = sessionRepository.findOne(codigo);
		if(sessionSalvo == null) {
			throw new EmptyResultDataAccessException(1);
		}
		BeanUtils.copyProperties(session, sessionSalvo, "id");
		return sessionRepository.save(sessionSalvo);
	}


	
}
