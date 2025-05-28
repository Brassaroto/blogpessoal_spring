package com.generation.blogpessoal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.repository.PostagemRepository;
import com.generation.blogpessoal.repository.TemaRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/postagens")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostagemController {

	@Autowired
	private PostagemRepository postagemRepository;
	
	@Autowired
	private TemaRepository temaRepository;
	
	@GetMapping
	public ResponseEntity<List<Postagem>> getAll() {
		
		// SELECT * FROM tb_postagens;
		return ResponseEntity.ok(postagemRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Postagem> getById(@PathVariable Long id) {
		return postagemRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Postagem>> getAllbyTitulo(@PathVariable String titulo) {
		
		return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo));
		
		// SELECT * FROM tb_postagens;
	}
	
	@PostMapping
	public ResponseEntity<Postagem> post(@Valid @RequestBody Postagem postagem) {

	    if (postagem.getTema() == null || postagem.getTema().getId() == null) {
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O tema é obrigatório");
	    }

	    temaRepository.findById(postagem.getTema().getId())
	        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "O tema não existe"));

	    Postagem novaPostagem = postagemRepository.save(postagem);

	    return ResponseEntity.status(HttpStatus.CREATED).body(novaPostagem);
	}

	
	@PutMapping
	public ResponseEntity<Postagem> put(@Valid @RequestBody Postagem postagem) {
	    if (postagem.getId() == null) {
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O ID da postagem é obrigatório");
	    }

	    if (postagem.getTema() == null || postagem.getTema().getId() == null || !temaRepository.existsById(postagem.getTema().getId())) {
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O Tema não existe!");
	    }

	    return postagemRepository.findById(postagem.getId())
	        .map(p -> ResponseEntity.ok(postagemRepository.save(postagem)))
	        .orElse(ResponseEntity.notFound().build());
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		postagemRepository.findById(id)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		
		postagemRepository.deleteById(id);
		
		// DELETE FROM tb_postagens WHERE id =?;
	}
}
