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
            @RequestParam(value = "titulo", required = false) String titulo,
            @RequestParam(value = "autorDiretor", required = false) String autorDiretor,
            @RequestParam(value = "anoLancamento", required = false) Integer anoLancamento,
            @RequestParam(value = "tipoMidia", required = false) String tipoMidia,
            Model model) {

        List<MidiaModel> resultados;
        if ((tipoMidia != null && !tipoMidia.isEmpty())) {
            try {
                tipoMidia = String.valueOf(MidiaModel.TipoMidia.valueOf(tipoMidia));
            } catch (IllegalArgumentException e) {
                System.out.println("Valor inválido para tipo de mídia: " + tipoMidia);
            }
        }
        if (titulo != null && !titulo.isEmpty()) {
            resultados = midiaRepository.findByTituloContainingIgnoreCase(titulo);
        } else if (autorDiretor != null && !autorDiretor.isEmpty()) {
            resultados = midiaRepository.findByAutorDiretorContainingIgnoreCase(autorDiretor);
        } else if (anoLancamento != null) {
            resultados = midiaRepository.findByAnoLancamento(anoLancamento);
        } else if (tipoMidia != null) {
            resultados = midiaRepository.findByTipo(MidiaModel.TipoMidia.valueOf(tipoMidia));
        } else {
            resultados = new ArrayList<>();
        }

        model.addAttribute("resultados", resultados);
        model.addAttribute("titulo", titulo);
        model.addAttribute("autorDiretor", autorDiretor);
        model.addAttribute("anoLancamento", anoLancamento);
        model.addAttribute("tipoMidia", tipoMidia);
        return "midias/search";
    }
}