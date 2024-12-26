package com.wiki.medieval.SistemaDeMidias;

import com.wiki.medieval.model.MidiaModel;
import com.wiki.medieval.repository.MidiaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BuscarMidiasTest {

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
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/search")
                        .param("query", "Filme Teste"))
                .andExpect(status().isOk());
    }

    @Test
    public void testBuscarMidiasPorAutor() throws Exception {
        String autorBusca = "Mel Gibson";

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/search")
                        .param("autorDiretor", autorBusca))
                .andExpect(status().isOk())
                .andExpect(view().name("midias/search"))
                .andExpect(model().attribute("resultados", hasItem(
                        hasProperty("autorDiretor", is(autorBusca)))));
    }

    @Test
    public void testBuscarMidiasPorAnoLancamento() throws Exception {
        Integer anoLancamentoBusca = 2001;
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/search")
                        .param("anoLancamento", String.valueOf(anoLancamentoBusca)))
                .andExpect(status().isOk())
                .andExpect(view().name("midias/search"))
                .andExpect(model().attribute("resultados", hasItem(
                        hasProperty("anoLancamento", is(anoLancamentoBusca)))));
    }


    @Test
    public void testBuscarMidiasPorTipoMidia() throws Exception {
        String tipoMidiaBusca = "Filme";
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/search")
                        .param("tipoMidia", tipoMidiaBusca))
                .andExpect(status().isOk())
                .andExpect(view().name("midias/search"))
                .andExpect(model().attribute("resultados", everyItem(
                        hasProperty("tipo", is(MidiaModel.TipoMidia.valueOf(tipoMidiaBusca))))));
    }
}