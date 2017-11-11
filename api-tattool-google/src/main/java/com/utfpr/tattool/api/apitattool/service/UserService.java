package com.utfpr.tattool.api.apitattool.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.utfpr.tattool.api.apitattool.model.User;
import com.utfpr.tattool.api.apitattool.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired UserRepository userRepository;
	
	
	public User userAtualiza(Long codigo, User User) {
		User UserSalva = userRepository.findOne(codigo);
		if(UserSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		BeanUtils.copyProperties(User, UserSalva, "id");
		
		return userRepository.save(UserSalva);
	}
}
