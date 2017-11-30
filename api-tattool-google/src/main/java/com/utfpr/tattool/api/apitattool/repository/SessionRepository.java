package com.utfpr.tattool.api.apitattool.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.utfpr.tattool.api.apitattool.model.Service;
import com.utfpr.tattool.api.apitattool.model.Session;

public interface SessionRepository extends JpaRepository<Session, Integer> {

	List<Session> findByService(Service findOne);

	List<Session> findByStatus(String string);
	
	@Query("select s from Session s where s.dateSession BETWEEN concat(:inicio, ' 00:00:00') "
			+ "AND concat(:fim, ' 23:59:59') "
			+ "AND (:status is null or s.status = :status) order by s.dateSession")
	List<Session> filtroSession(@Param("status")String status, @Param("inicio")LocalDate inicio, @Param("fim")LocalDate fim);

}
