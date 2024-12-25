package com.wiki.medieval.controller;

import com.wiki.medieval.model.MidiaModel;
import com.wiki.medieval.repository.MidiaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchController {

    private final MidiaRepository midiaRepository;

    public SearchController(MidiaRepository midiaRepository) {
        this.midiaRepository = midiaRepository;
    }

    @GetMapping
    public String buscarMidia(
            @RequestParam(value = "query", required = false) String query,
            Model model) {
        List<MidiaModel> resultados;
        if (query != null && !query.isEmpty()) {
            resultados = midiaRepository.findByTituloContainingIgnoreCaseOrTipoOrAutorDiretorContainingIgnoreCaseOrAnoLancamento(
                    query, null, query, null); // 'query' vai buscar em título e autorDiretor
        } else {
            resultados = new ArrayList<>();
        }
        model.addAttribute("resultados", resultados);
        model.addAttribute("query", query);
        return "midias/search"; // Renderiza a página de resultados
    }
}

