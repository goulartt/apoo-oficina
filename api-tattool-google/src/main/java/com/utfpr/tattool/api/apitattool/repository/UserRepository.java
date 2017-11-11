package com.utfpr.tattool.api.apitattool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.utfpr.tattool.api.apitattool.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	@Query("select u from User u where u.usuario = :username and u.senha = :senha")
    public User verificaLogin(@Param("username") String username, @Param("senha") String senha);
	
	@Query("select u from User u where u.usuario = :username")
	public User existeUsername(@Param("username") String username); 
	
	@Query("select u from User u where u.usuario = 'admin' and u.senha = '1'")
	public User verificaAdmin(); 
	
	
}
