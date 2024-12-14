package com.wiki.medieval.controller;

import com.wiki.medieval.model.MidiaModel;
import com.wiki.medieval.repository.MidiaRepository;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MidiaController {

    private final MidiaRepository midiaRepository;

    public MidiaController(MidiaRepository midiaRepository) {
        this.midiaRepository = midiaRepository;
    }

    @GetMapping("/filmes")
    public String listarFilmes(Model model) {
        List<MidiaModel> filmes = midiaRepository.findAllByTipo(MidiaModel.TipoMidia.Filme);
        model.addAttribute("filmes", filmes);
        filmes.forEach(filme -> System.out.println(filme));
        return "midias/filmes/listafilmes";
    }

    @GetMapping("/jogos")
    public String listarJogos(Model model) {
        List<MidiaModel> jogos = midiaRepository.findAllByTipo(MidiaModel.TipoMidia.Videogame);
        model.addAttribute("jogos", jogos);
        return "midias/jogos/listajogos";
    }

    @GetMapping("/livros")
    public String listarLivros(Model model) {
        List<MidiaModel> livros = midiaRepository.findAllByTipo(MidiaModel.TipoMidia.Livro);
        model.addAttribute("livros", livros);
        return "midias/livros/listalivros";
    }


}
