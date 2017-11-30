package com.utfpr.tattool.api.apitattool.resource;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.utfpr.tattool.api.apitattool.event.RecursoCriadoEvento;
import com.utfpr.tattool.api.apitattool.model.Service;
import com.utfpr.tattool.api.apitattool.model.Session;
import com.utfpr.tattool.api.apitattool.repository.ServiceRepository;
import com.utfpr.tattool.api.apitattool.repository.SessionRepository;
import com.utfpr.tattool.api.apitattool.service.SessionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/sessions")
@Api(value="Sessions Resource", description="Operações para controle de sessões")
public class SessionResource {

	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private SessionRepository sessionRepository;
	
	@Autowired
	private ServiceRepository serviceRepository;
	
	@Autowired
	private SessionService service;

	
   @ApiOperation(value = "Ver todos os Sessões cadastrados no sistema",response = Session[].class)
   @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso! Foi encontrado todos sessãos"),
            @ApiResponse(code = 204, message = "Deu certo! Porém, não tem conteudo para ser exibido"),
            @ApiResponse(code = 404, message = "Vish, nem sei o que aconteceu")
    })
	@GetMapping
	public ResponseEntity<?> findAll() {
		List<Session> services = sessionRepository.findAll();
		
		return !services.isEmpty() ? ResponseEntity.ok(services) : ResponseEntity.noContent().build();
	}
   
	@GetMapping("/agendado")
	public ResponseEntity<?> findAgendados() {
		List<Session> services = sessionRepository.findByStatus("AGENDADO");
		return !services.isEmpty() ? ResponseEntity.ok(services) : ResponseEntity.noContent().build();
	}
   		
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	@ApiOperation(value = "Salvar novo sessão na API",response = Session.class)
	public ResponseEntity<Session> save(@Valid @RequestBody Session service, HttpServletResponse response) {
		Session serviceSalvo = sessionRepository.save(service);
		publisher.publishEvent(new RecursoCriadoEvento(this, response, serviceSalvo.getId().longValue()));
		return ResponseEntity.status(HttpStatus.CREATED).body(serviceSalvo);
	}

	@GetMapping("/{codigo}")
	@ApiOperation(value = "Buscar sessão pelo id dele",response = Session.class)
	public ResponseEntity<?> buscarCodigo(@PathVariable Integer codigo) {
		Session Session = sessionRepository.findOne(codigo);
		return Session != null ? ResponseEntity.ok(Session) : ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{codigo}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Deletar o sessão na API pelo id dele")
	public void deletarSession(@PathVariable Integer codigo) {
		sessionRepository.delete(codigo);
	}

	@PutMapping("/{codigo}")
	@ApiOperation(value = "Atualizar o sessão na API pelo id dele",response = Session.class)
	public ResponseEntity<Session> atualiza(@PathVariable Integer codigo, @Valid @RequestBody Session Session) {

		Session SessionSalva = service.sessionAtualiza(codigo, Session);
		return ResponseEntity.ok().body(SessionSalva);

	}
	
	@GetMapping("/service/{codigo}")
	@ApiOperation(value = "Busca as sessoes daquela serviço",response = Session[].class)
	public ResponseEntity<?> relatorioHistorico(@PathVariable Integer codigo) {
		Service servico = serviceRepository.findOne(codigo);
		if(servico != null) {
			List<Session> sessoes = sessionRepository.findByService(servico);
			return sessoes != null ? ResponseEntity.ok(sessoes) : ResponseEntity.notFound().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/filtro")
	public ResponseEntity<?> consultarArquivosErrosPorFiltro(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
	  @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim, @RequestParam(required = false) String status) {
		List<Session> services = sessionRepository.filtroSession(status, inicio, fim);
		return !services.isEmpty() ? ResponseEntity.ok(services) : ResponseEntity.noContent().build();
	}

}