package com.wiki.medieval.SistemaDeMidias;

import com.wiki.medieval.model.MidiaModel;
import com.wiki.medieval.repository.MidiaRepository;
import org.junit.jupiter.api.*;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CadastroMidiaTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MidiaRepository midiaRepository;

    private MidiaModel midiaModel;

    @BeforeEach
    public void setUp() {
        // Inicialização dos objetos necessários para os testes
        MockitoAnnotations.initMocks(this);
        midiaModel = new MidiaModel();
        midiaModel.setTitulo("Aventuras no Espaço");
        midiaModel.setDescricao("Um livro sobre viagens espaciais.");
        midiaModel.setAutorDiretor("João Silva");
        midiaModel.setAnoLancamento(2023);
        midiaModel.setTipo(MidiaModel.TipoMidia.valueOf("Livro"));
    }

    // Testando o cadastro de uma nova mídia
    @Test
    @WithMockUser(username = "email", password = "senha", roles = {"ADMIN"})  // Simula um usuário autenticado
    void testarCadastroDeMidia() throws Exception {
        // Submetendo os dados do formulário de cadastro de mídia
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/midias")
                        .param("titulo", "O Senhor dos Anéis")
                        .param("descricao", "Filme de fantasia épica")
                        .param("autorDiretor", "J.R.R. Tolkien")
                        .param("anoLancamento", "2001")
                        .param("tipoMidia", "Filme")
                        .with(csrf())) // Adiciona o token CSRF à requisição
                .andExpect(status().is3xxRedirection()) // Espera redirecionamento
                .andExpect(redirectedUrl("/midias/listafilmes")); // Ajuste para o URL de redirecionamento esperado

        // Verifica se a mídia foi salva no banco de dados diretamente
        MidiaModel midia = midiaRepository.findByTitulo("O Senhor dos Anéis");
        assertNotNull(midia);  // Verifica se a mídia foi encontrada no banco
        assertEquals("O Senhor dos Anéis", midia.getTitulo());  // Verifica o título
        assertEquals("Filme de fantasia épica", midia.getDescricao());  // Verifica a descrição
        assertEquals("J.R.R. Tolkien", midia.getAutorDiretor());  // Verifica o autor/diretor
        assertEquals(2001, midia.getAnoLancamento());  // Verifica o ano de lançamento
        assertEquals("Filme", midia.getTipo());  // Verifica o tipo de mídia
    }
}