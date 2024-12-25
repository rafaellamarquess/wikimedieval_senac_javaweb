package com.wiki.medieval.SistemaDeMidias;

import com.wiki.medieval.model.MidiaModel;
import com.wiki.medieval.repository.MidiaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class BuscarMidiaTest {

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

    @Test
    public void testBuscarMidiasPorTitulo() throws Exception {
        // Definir o critério de busca (ex: título)
        String tituloBusca = "O Senhor dos Anéis";
        // Submetendo os dados de busca (parâmetro de título)
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/midias")
                        .param("titulo", tituloBusca)) // Envia o parâmetro de busca
                .andExpect(status().isOk()) // Espera um status 200 OK
                .andExpect(view().name("resultadoBusca")) // Espera que a página de resultados de busca seja renderizada
                .andExpect(model().attributeExists("midias")) // Verifica se o atributo "midias" está presente no modelo
                .andExpect(model().attribute("midias", hasSize(greaterThan(0)))) // Verifica se há mídias retornadas
                .andExpect(model().attribute("midias", hasItem(
                        hasProperty("titulo", is(tituloBusca))))) // Verifica se a mídia retornada contém o título correto
        ;
    }

    @Test
    public void testBuscarMidiasPorAutor() throws Exception {
        // Definir o critério de busca (ex: autor)
        String autorBusca = "J.R.R. Tolkien";
        // Submetendo os dados de busca (parâmetro de autor)
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/midias")
                        .param("autorDiretor", autorBusca)) // Envia o parâmetro de busca
                .andExpect(status().isOk()) // Espera um status 200 OK
                .andExpect(view().name("resultadoBusca")) // Espera que a página de resultados de busca seja renderizada
                .andExpect(model().attributeExists("midias")) // Verifica se o atributo "midias" está presente no modelo
                .andExpect(model().attribute("midias", hasSize(greaterThan(0)))) // Verifica se há mídias retornadas
                .andExpect(model().attribute("midias", hasItem(
                        hasProperty("autorDiretor", is(autorBusca))))) // Verifica se a mídia retornada contém o autor correto
        ;
    }

    @Test
    public void testBuscarMidiasPorAnoLancamento() throws Exception {
        // Definir o critério de busca (ex: ano de lançamento)
        String anoLancamentoBusca = "2001";
        // Submetendo os dados de busca (parâmetro de ano de lançamento)
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/midias")
                        .param("anoLancamento", anoLancamentoBusca)) // Envia o parâmetro de busca
                .andExpect(status().isOk()) // Espera um status 200 OK
                .andExpect(view().name("resultadoBusca")) // Espera que a página de resultados de busca seja renderizada
                .andExpect(model().attributeExists("midias")) // Verifica se o atributo "midias" está presente no modelo
                .andExpect(model().attribute("midias", hasSize(greaterThan(0)))) // Verifica se há mídias retornadas
                .andExpect(model().attribute("midias", hasItem(
                        hasProperty("anoLancamento", is(anoLancamentoBusca))))) // Verifica se a mídia retornada contém o ano de lançamento correto
        ;
    }

    @Test
    public void testBuscarMidiasPorTipoMidia() throws Exception {
        // Definir o critério de busca (ex: tipo de mídia)
        String tipoMidiaBusca = "Filme";
        // Submetendo os dados de busca (parâmetro de tipo de mídia)
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/midias")
                        .param("tipoMidia", tipoMidiaBusca)) // Envia o parâmetro de busca
                .andExpect(status().isOk()) // Espera um status 200 OK
                .andExpect(view().name("resultadoBusca")) // Espera que a página de resultados de busca seja renderizada
                .andExpect(model().attributeExists("midias")) // Verifica se o atributo "midias" está presente no modelo
                .andExpect(model().attribute("midias", hasSize(greaterThan(0)))) // Verifica se há mídias retornadas
                .andExpect(model().attribute("midias", hasItem(
                        hasProperty("tipoMidia", is(tipoMidiaBusca))))) // Verifica se a mídia retornada contém o tipo de mídia correto
        ;
    }
}
