package com.gestao.configuracao.fluxo_de_trabalho;

import com.gestao.configuracao.fluxo_de_trabalho.model.Livro;
import com.gestao.configuracao.fluxo_de_trabalho.repository.LivroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class LivroControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LivroRepository livroRepository;

    @BeforeEach
    void setup() {
        // limpar o banco antes de cada teste
        livroRepository.deleteAll();

        // inserir dados iniciais
        livroRepository.save(new Livro("Dom Casmurro", "Machado de Assis", "1899"));
        livroRepository.save(new Livro("Memórias Póstumas", "Machado de Assis", "1881"));
    }

    @Test
    void deveRetornarListaDeLivros() throws Exception {
        mockMvc.perform(get("/api/livros"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].titulo").value("Dom Casmurro"))
                .andExpect(jsonPath("$[1].autor").value("Machado de Assis"));
    }

    @Test
    void deveAdicionarNovoLivro() throws Exception {
        String novoLivroJson = """
            {
                "titulo": "O Alienista",
                "autor": "Machado de Assis",
                "dataLancamento": "1882"
            }
            """;

        mockMvc.perform(post("/api/livros")
                        .contentType("application/json")
                        .content(novoLivroJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("O Alienista"))
                .andExpect(jsonPath("$.isbn").exists());
    }
}
