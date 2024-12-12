package com.wiki.medieval.controller;

import com.wiki.medieval.model.MidiaModel;
import com.wiki.medieval.repository.MidiaRepository;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MidiaController {

    private MidiaRepository midiaRepository;

    public MidiaController(MidiaRepository midiaRepository) {
        this.midiaRepository = midiaRepository;
    }

    @GetMapping("/livros")
    public String exibirLivros(Model model) {
        model.addAttribute("midias", midiaRepository.findByTipo(MidiaModel.TipoMidia.LIVRO));
        return "livros";
    }

    @GetMapping("/filmes")
    public String exibirFilmes(Model model) {
        model.addAttribute("midias", midiaRepository.findByTipo(MidiaModel.TipoMidia.FILME));
        return "filmes";
    }

    @GetMapping("/jogos")
    public String exibirJogos(Model model) {
        model.addAttribute("midias", midiaRepository.findByTipo(MidiaModel.TipoMidia.VIDEOGAME));
        return "jogos";
    }
}
