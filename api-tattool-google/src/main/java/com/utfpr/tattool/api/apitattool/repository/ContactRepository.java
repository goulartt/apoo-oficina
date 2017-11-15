package com.utfpr.tattool.api.apitattool.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.utfpr.tattool.api.apitattool.model.Contact;

public interface ContactRepository extends JpaRepository<Contact, Integer> {

}
