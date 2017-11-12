package com.utfpr.tattool.api.apitattool.resource;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.utfpr.tattool.api.apitattool.model.Art;
import com.utfpr.tattool.api.apitattool.model.Tag;
import com.utfpr.tattool.api.apitattool.repository.ArtRepository;
import com.utfpr.tattool.api.apitattool.repository.TagRepository;
import com.utfpr.tattool.api.apitattool.service.FileArchiveService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/art")
@Api(value="Art Resource", description="Operações para controle de Artes")
public class ArtResource {
	@Autowired
	private FileArchiveService fileArchiveService; 
	
	@Autowired TagRepository tagDao;
	
	@Autowired ArtRepository dao;

	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	@ApiOperation(value = "Salvar nova art no bd",response = HttpStatus.class)
	public ResponseEntity<Art> saveImage(@RequestBody Art  art) throws IOException {
		for(Tag tag : art.getTags()){
			Tag tagBuscada = tagDao.findByTag(tag.getTag());
			if(tagBuscada != null) {
				tag.setId(tagBuscada.getId());
			}
			tagDao.save(tag);
		}
		dao.save(art);
		return ResponseEntity.ok(art);
	}
	
	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	@ApiOperation(value = "Puxar arte do banco de dados", response = Art.class)
	public ResponseEntity<?> getArt(String tag){
		List<Art> arts = dao.findByTags_TagContaining(tag);
		return !arts.isEmpty() ? ResponseEntity.ok(arts) : ResponseEntity.noContent().build();
	}
	
	@GetMapping("/all")
	@ResponseStatus(code = HttpStatus.OK)
	@ApiOperation(value = "Puxar arte do banco de dados", response = Art.class)
	public ResponseEntity<?> getArt(){
		List<Art> arts = dao.findAll();
		return !arts.isEmpty() ? ResponseEntity.ok(arts) : ResponseEntity.noContent().build();
	}
	

	
	@DeleteMapping("/img/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Deletar imagem do banco de dados", response = HttpStatus.class)
	@ResponseBody
	public void deleteImage(@PathVariable("id") Integer id){
		try{
			Art art = dao.findOne(id);
			dao.delete(art);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	

}
