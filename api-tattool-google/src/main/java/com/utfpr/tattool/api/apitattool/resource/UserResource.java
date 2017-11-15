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
import com.utfpr.tattool.api.apitattool.model.User;
import com.utfpr.tattool.api.apitattool.repository.UserRepository;
import com.utfpr.tattool.api.apitattool.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/users")
@Api(value="User Resource", description="Operações para controle de Usuario")
public class UserResource {

	@Autowired
	private ApplicationEventPublisher publisher;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserService service;
	
   @ApiOperation(value = "Ver todos os usuarios cadastrados no sistema",response = User[].class)
   @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucesso! Foi encontrado todos usuarios"),
            @ApiResponse(code = 204, message = "Deu certo! Porém, não tem conteudo para ser exibido"),
            @ApiResponse(code = 404, message = "Vish, nem sei o que aconteceu")
    })
	@GetMapping
	public ResponseEntity<?> findAll() {
		List<User> Users = userRepository.findAll();
		return !Users.isEmpty() ? ResponseEntity.ok(Users) : ResponseEntity.noContent().build();
	}
   		
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	@ApiOperation(value = "Salvar novo usuario na API",response = User.class)
	public ResponseEntity<User> save(@Valid @RequestBody User Users, HttpServletResponse response) {
		User UserSalva = userRepository.save(Users);
		publisher.publishEvent(new RecursoCriadoEvento(this, response, UserSalva.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(UserSalva);
	}

	@GetMapping("/{codigo}")
	@ApiOperation(value = "Buscar usuario pelo id dele",response = User.class)
	public ResponseEntity<?> buscarCodigo(@PathVariable Long codigo) {
		User User = userRepository.findOne(codigo);
		return User != null ? ResponseEntity.ok(User) : ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{codigo}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Deletar o usuario na API pelo id dele")
	public void deletarUser(@PathVariable Long codigo) {
		userRepository.delete(codigo);
	}

	@PutMapping("/{codigo}")
	@ApiOperation(value = "Atualizar o usuario na API pelo id dele",response = User.class)
	public ResponseEntity<User> atualiza(@PathVariable Long codigo, @Valid @RequestBody User User) {

		User UserSalva = service.userAtualiza(codigo, User);
		return ResponseEntity.ok().body(UserSalva);

	}

	@PostMapping("/verify")
	@ApiOperation(value = "Verifica se o usuario e senha estão corretos no bando de dados",response = User.class)
	public ResponseEntity<User> verificaLogin(String usuario, String senha) {
		User usuarioVerificado = userRepository.verificaLogin(usuario, senha);
		if (usuarioVerificado != null) {
			return ResponseEntity.ok().body(usuarioVerificado);
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/verify")
	@ApiOperation(value = "Verifica se existe o ADMIN no sistema, se não tiver ele cria",response = User.class)
	public HttpStatus start(){
		User usuarioVerificado = userRepository.verificaAdmin();
		if(usuarioVerificado == null){
			userRepository.save(new User("Admin", "1", "Administrador", 1, 0));
			return HttpStatus.CREATED;
		}
		return HttpStatus.OK;
	}
	
	@GetMapping("/verify/username")
	@ApiOperation(value = "Verfica se o usuario ja existe no sistema, usado quando estiver criando um novo usuario",response = User.class)
	public ResponseEntity<?> verificaUsuario(String usuario){
		User usuarioVerificado = userRepository.existeUsername(usuario);
		if(usuarioVerificado == null){
			return ResponseEntity.ok(null);
		}
		return ResponseEntity.status(HttpStatus.IM_USED).body(usuarioVerificado);
	}
}
