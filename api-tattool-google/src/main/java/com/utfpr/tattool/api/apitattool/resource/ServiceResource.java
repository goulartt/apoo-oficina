package com.utfpr.tattool.api.apitattool.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.utfpr.tattool.api.apitattool.event.RecursoCriadoEvento;
import com.utfpr.tattool.api.apitattool.model.Customer;
import com.utfpr.tattool.api.apitattool.model.Service;
import com.utfpr.tattool.api.apitattool.model.Session;
import com.utfpr.tattool.api.apitattool.repository.CustomerRepository;
import com.utfpr.tattool.api.apitattool.repository.ServiceRepository;
import com.utfpr.tattool.api.apitattool.repository.SessionRepository;
import com.utfpr.tattool.api.apitattool.service.ServicesService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
@RequestMapping("/services")
@Api(value="Service Resource", description="Operações para controle de Serviços")
public class ServiceResource {

	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private ServiceRepository serviceRepository;
	
	@Autowired
	private SessionRepository sessionRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private ServicesService service;
	
   @ApiOperation(value = "Ver todos os Serviços cadastrados no sistema",response = Service[].class)
   @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso! Foi encontrado todos serviços"),
            @ApiResponse(code = 204, message = "Deu certo! Porém, não tem conteudo para ser exibido"),
            @ApiResponse(code = 404, message = "Vish, nem sei o que aconteceu")
    })
	@GetMapping
	public ResponseEntity<?> findAll() {
		List<Service> services = serviceRepository.findAll();
		return !services.isEmpty() ? ResponseEntity.ok(services) : ResponseEntity.noContent().build();
	}
   		
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	@ApiOperation(value = "Salvar novo serviço na API",response = Service.class)
	public ResponseEntity<Service> save(@Valid @RequestBody Service service, HttpServletResponse response) {
		Service serviceSalvo = serviceRepository.save(service);
		publisher.publishEvent(new RecursoCriadoEvento(this, response, serviceSalvo.getId().longValue()));
		return ResponseEntity.status(HttpStatus.CREATED).body(serviceSalvo);
	}

	@GetMapping("/{codigo}")
	@ApiOperation(value = "Buscar serviço pelo id dele",response = Service.class)
	public ResponseEntity<?> buscarCodigo(@PathVariable Integer codigo) {
		Service Service = serviceRepository.findOne(codigo);
		return Service != null ? ResponseEntity.ok(Service) : ResponseEntity.notFound().build();
	}
	
	@GetMapping("/relatorio/{codigo}")
	@ApiOperation(value = "Gerar relatorio de serviços daquele cliente",response = Service.class)
	public ResponseEntity<?> relatorioHistorico(@PathVariable Integer codigo) {
		Customer cliente = customerRepository.findOne(codigo);
		if(cliente != null) {
			List<Service> serviços = serviceRepository.findByCustomer(cliente);
			return serviços != null ? ResponseEntity.ok(serviços) : ResponseEntity.notFound().build();
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{codigo}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Deletar o serviço na API pelo id dele")
	public void deletarService(@PathVariable Integer codigo) {
		
		List<Session> sessoes = sessionRepository.findByService(serviceRepository.findOne(codigo));
		sessoes.forEach(s ->{
			sessionRepository.delete(s);
		});
		serviceRepository.delete(codigo);
	}

	@PutMapping("/{codigo}")
	@ApiOperation(value = "Atualizar o serviço na API pelo id dele",response = Service.class)
	public ResponseEntity<Service> atualiza(@PathVariable Integer codigo, @Valid @RequestBody Service Service) {

		Service ServiceSalva = service.serviceAtualiza(codigo, Service);
		return ResponseEntity.ok().body(ServiceSalva);

	}

}