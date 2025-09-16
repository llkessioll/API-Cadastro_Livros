package com.gestao.configuracao.fluxo_de_trabalho.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestao.configuracao.fluxo_de_trabalho.model.Livro;
import com.gestao.configuracao.fluxo_de_trabalho.repository.LivroRepository;


@RestController
@RequestMapping("/api/livros")
public class LivroController {

	private final LivroRepository livroRepository;
	
	public LivroController(LivroRepository livroRepository) {
		this.livroRepository = livroRepository;
	}
	
	@GetMapping
	public List<Livro> getLivros(){
		return livroRepository.findAll();
	}
	
	@PostMapping
	public Livro addLivro(@RequestBody Livro livro) {
		return livroRepository.save(livro);
	}
	
}