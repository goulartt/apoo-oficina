package com.utfpr.tattool.api.apitattool.event;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

public class RecursoCriadoEvento extends ApplicationEvent{

	private static final long serialVersionUID = 2638386144871666598L;
	private HttpServletResponse respose;
	private Long codigo;

	public RecursoCriadoEvento(Object source, HttpServletResponse response, Long codigo) {
		super(source);
		this.respose = response;
		this.codigo = codigo;
	}

	public HttpServletResponse getRespose() {
		return respose;
	}


	public Long getCodigo() {
		return codigo;
	}

	
}
