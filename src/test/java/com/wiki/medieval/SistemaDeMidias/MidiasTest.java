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


import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MidiasTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MidiaRepository midiaRepository;

    private MidiaModel midiaModel;

    @BeforeEach
    public void setUp() {
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
    @WithMockUser(username = "email", password = "senha", roles = {"ADMIN"})
    void testarCadastroDeMidia() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/midias")
                        .param("titulo", "O Senhor dos Anéis")
                        .param("descricao", "Filme de fantasia épica")
                        .param("autorDiretor", "J.R.R. Tolkien")
                        .param("anoLancamento", "2001")
                        .param("tipoMidia", "Filme")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/index"));

        // Verifica se a mídia foi salva no banco de dados diretamente
        MidiaModel midia = midiaRepository.findByTitulo("O Senhor dos Anéis");
        assertNotNull(midia);
        assertEquals("O Senhor dos Anéis", midia.getTitulo());
        assertEquals("Filme de fantasia épica", midia.getDescricao());
        assertEquals("J.R.R. Tolkien", midia.getAutorDiretor());
        assertEquals(2001, midia.getAnoLancamento());
        assertEquals("Filme", midia.getTipo());
    }

    // Testando atualização de uma mídia
    @Test
    @WithMockUser(username = "email", password = "senha", roles = {"ADMIN"})
    void testarAtualizacaoDeMidia() throws Exception {
        MidiaModel midiaExistente = new MidiaModel();
        midiaExistente.setTitulo("O Senhor dos Anéis");
        midiaExistente.setDescricao("Filme de fantasia épica");
        midiaExistente.setAutorDiretor("J.R.R. Tolkien");
        midiaExistente.setAnoLancamento(2001);
        midiaExistente.setTipo(MidiaModel.TipoMidia.valueOf("Filme"));
        midiaRepository.save(midiaExistente);

        // Atualizando a mídia
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/midias/" + midiaExistente.getTipo() + "/" + midiaExistente.getId())
                        .param("titulo", "O Hobbit")
                        .param("descricao", "Filme de aventura e fantasia")
                        .param("autorDiretor", "J.R.R. Tolkien")
                        .param("anoLancamento", "2012")
                        .param("tipoMidia", "Filme")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/midias/listafilmes"));

        // Verificando se a mídia foi atualizada no banco de dados
        MidiaModel midiaAtualizada = midiaRepository.findById(midiaExistente.getId()).orElse(null);
        assertNotNull(midiaAtualizada);
        assertEquals("O Hobbit", midiaAtualizada.getTitulo());
        assertEquals("Filme de aventura e fantasia", midiaAtualizada.getDescricao());
        assertEquals("J.R.R. Tolkien", midiaAtualizada.getAutorDiretor());
        assertEquals(2012, midiaAtualizada.getAnoLancamento());
        assertEquals("Filme", midiaAtualizada.getTipo());
    }

    @Test
    @WithMockUser(username = "email", password = "senha", roles = {"ADMIN"})
    void testarExclusaoDeMidia() throws Exception {
        // Criar uma mídia de exemplo
        MidiaModel midiaExistente = new MidiaModel();
        midiaExistente.setTitulo("Harry Potter");
        midiaExistente.setDescricao("Filme de magia e aventura");
        midiaExistente.setAutorDiretor("J.K. Rowling");
        midiaExistente.setAnoLancamento(2001);
        midiaExistente.setTipo(MidiaModel.TipoMidia.valueOf("Filme"));
        midiaRepository.save(midiaExistente);

        // Verificar que a mídia foi salva corretamente
        assertNotNull(midiaRepository.findById(midiaExistente.getId()).orElse(null));

        // Excluindo a mídia
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/" + midiaExistente.getTipo() + "/deletar/" + midiaExistente.getId())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/midias/listafilmes"));

        // Verificar se a mídia foi excluída do banco de dados
        MidiaModel midiaExcluida = midiaRepository.findById(midiaExistente.getId()).orElse(null);
        assertNull(midiaExcluida);
    }

    // Testando visualização de mídias
    @Test
    @WithMockUser(username = "email", password = "senha", roles = {"ADMIN"})
    void testarVisualizacaoListaMidias() throws Exception {
        MidiaModel midia1 = new MidiaModel();
        midia1.setTitulo("O Senhor dos Anéis");
        midia1.setDescricao("Filme de fantasia épica");
        midia1.setAutorDiretor("J.R.R. Tolkien");
        midia1.setAnoLancamento(2001);
        midia1.setTipo(MidiaModel.TipoMidia.valueOf("Filme"));
        midiaRepository.save(midia1);

        MidiaModel midia2 = new MidiaModel();
        midia2.setTitulo("Harry Potter");
        midia2.setDescricao("Filme de magia e aventura");
        midia2.setAutorDiretor("J.K. Rowling");
        midia2.setAnoLancamento(2001);
        midia2.setTipo(MidiaModel.TipoMidia.valueOf("Filme"));
        midiaRepository.save(midia2);

        // Acessando a página de lista de filmes
        mockMvc.perform(MockMvcRequestBuilders.get("/filmes"))
                .andExpect(status().isOk())  // Espera um status de sucesso (200)
                .andExpect(view().name("midias/filmes/listafilmes"))  // Verifica a view
                .andExpect(model().attributeExists("filmes"))  // Verifica se o atributo "filmes" existe
                .andExpect(content().string(containsString("O Senhor dos Anéis")))  // Verifica se o conteúdo contém o título
                .andExpect(content().string(containsString("Harry Potter")))  // Verifica se o conteúdo contém o título
                .andExpect(content().string(containsString("Filme")));
    }
}