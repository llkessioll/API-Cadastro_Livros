package com.gestao.configuracao.fluxo_de_trabalho;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.gestao.configuracao.fluxo_de_trabalho.model.Livro;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FluxoDeTrabalhoApplicationTests {

	 @LocalServerPort
	    private int port;

	    @Autowired
	    private TestRestTemplate restTemplate;

	    private String urlBase() {
	        return "http://localhost:" + port + "/api/livros";
	    }

	    @Test
	    void deveRetornarListaVaziaDeLivros() {
	        ResponseEntity<String> response = restTemplate.getForEntity(urlBase(), String.class);

	        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	        assertThat(response.getBody()).isEqualTo("[]");
	    }

	    @Test
	    void deveAdicionarLivroComPost() {
	        Livro livro = new Livro("O Hobbit", "J.R.R. Tolkien", "1937");

	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);

	        HttpEntity<Livro> request = new HttpEntity<>(livro, headers);
	        
	        // POST
	        ResponseEntity<String> postResponse = restTemplate.postForEntity(urlBase(), request, String.class);
	        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	        assertThat(postResponse.getBody()).isEqualTo("Livro adicionado com sucesso");
	        
	        /*/ GET
	        ResponseEntity<Livro[]> getResponse = restTemplate.getForEntity(urlBase(), Livro[].class);
	        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
	        assertThat(getResponse.getBody()).isNotEmpty();
	        assertThat(getResponse.getBody()[0].getTitulo()).isEqualTo("O Hobbit");*/
	        
	    }
}
