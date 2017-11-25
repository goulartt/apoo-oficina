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
import com.utfpr.tattool.api.apitattool.model.Address;
import com.utfpr.tattool.api.apitattool.model.Contact;
import com.utfpr.tattool.api.apitattool.model.Customer;
import com.utfpr.tattool.api.apitattool.repository.AddressRepository;
import com.utfpr.tattool.api.apitattool.repository.ContactRepository;
import com.utfpr.tattool.api.apitattool.repository.CustomerRepository;
import com.utfpr.tattool.api.apitattool.service.CustomerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/customers")
@Api(value="Customer Resource", description="Operações para controle de Clientes")
public class CustomerResource {

	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private ContactRepository contactRepository;
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private CustomerService service;
	
   @ApiOperation(value = "Ver todos os usuarios cadastrados no sistema",response = Customer[].class)
   @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso! Foi encontrado todos usuarios"),
            @ApiResponse(code = 204, message = "Deu certo! Porém, não tem conteudo para ser exibido"),
            @ApiResponse(code = 404, message = "Vish, nem sei o que aconteceu")
    })
	@GetMapping
	public ResponseEntity<?> findAll() {
		List<Customer> Customers = customerRepository.findAll();
		return !Customers.isEmpty() ? ResponseEntity.ok(Customers) : ResponseEntity.noContent().build();
	}
   		
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	@ApiOperation(value = "Salvar novo cliente na API",response = Customer.class)
	public ResponseEntity<Customer> save(@Valid @RequestBody Customer customers, HttpServletResponse response) {
		Contact contatoSalvo = contactRepository.save(customers.getContact());
		Address enderecoSalvo = addressRepository.save(customers.getAddress());
		customers.setContact(contatoSalvo);
		customers.setAddress(enderecoSalvo);
		customers.setRemoved(0);
		Customer customerSalva = customerRepository.save(customers);
		publisher.publishEvent(new RecursoCriadoEvento(this, response, customerSalva.getId().longValue()));
		return ResponseEntity.status(HttpStatus.CREATED).body(customerSalva);
	}

	@GetMapping("/{codigo}")
	@ApiOperation(value = "Buscar cliente pelo id dele",response = Customer.class)
	public ResponseEntity<?> buscarCodigo(@PathVariable Integer codigo) {
		Customer Customer = customerRepository.findOne(codigo);
		return Customer != null ? ResponseEntity.ok(Customer) : ResponseEntity.notFound().build();
	}
	


	@DeleteMapping("/{codigo}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Deletar o cliente na API pelo id dele")
	public void deletarCustomer(@PathVariable Integer codigo) {
		customerRepository.delete(codigo);
	}

	@PutMapping("/{codigo}")
	@ApiOperation(value = "Atualizar o cliente na API pelo id dele",response = Customer.class)
	public ResponseEntity<Customer> atualiza(@PathVariable Integer codigo, @Valid @RequestBody Customer Customer) {

		Customer CustomerSalva = service.customerAtualiza(codigo, Customer);
		return ResponseEntity.ok().body(CustomerSalva);

	}

}