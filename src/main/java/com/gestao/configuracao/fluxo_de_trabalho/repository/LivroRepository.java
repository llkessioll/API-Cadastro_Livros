package com.gestao.configuracao.fluxo_de_trabalho.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestao.configuracao.fluxo_de_trabalho.model.Livro;



public interface LivroRepository extends JpaRepository<Livro, Long> {

}
