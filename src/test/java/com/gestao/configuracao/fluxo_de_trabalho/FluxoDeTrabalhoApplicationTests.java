package com.gestao.configuracao.fluxo_de_trabalho;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

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
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>("\"O Hobbit\"", headers);

        // POST
        ResponseEntity<String> postResponse = restTemplate.postForEntity(urlBase(), request, String.class);
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(postResponse.getBody()).isEqualTo("Livro adicionado com sucesso");

        // GET
        ResponseEntity<String> getResponse = restTemplate.getForEntity(urlBase(), String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).contains("O Hobbit");
    }
}
