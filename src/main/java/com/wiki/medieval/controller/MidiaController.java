package com.wiki.medieval.controller;

import com.wiki.medieval.model.MidiaModel;
import com.wiki.medieval.repository.MidiaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MidiaController {

    private final MidiaRepository midiaRepository;

    public MidiaController(MidiaRepository midiaRepository) {
        this.midiaRepository = midiaRepository;
    }

    // Lista Filmes
    @GetMapping("/filmes")
    public String listarFilmes(Model model) {
        List<MidiaModel> filmes = midiaRepository.findAllByTipo(MidiaModel.TipoMidia.Filme);
        model.addAttribute("filmes", filmes);
        filmes.forEach(filme -> System.out.println(filme));
        return "midias/filmes/listafilmes";
    }

    // Lista Jogos
    @GetMapping("/jogos")
    public String listarJogos(Model model) {
        List<MidiaModel> jogos = midiaRepository.findAllByTipo(MidiaModel.TipoMidia.Videogame);
        model.addAttribute("jogos", jogos);
        return "midias/jogos/listajogos";
    }

    // Lista Livros
    @GetMapping("/livros")
    public String listarLivros(Model model) {
        List<MidiaModel> livros = midiaRepository.findAllByTipo(MidiaModel.TipoMidia.Livro);
        model.addAttribute("livros", livros);
        return "midias/livros/listalivros";
    }

    // Exibi formulário para adicionar mídia
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/adicionarmidia")
    public String exibirFormularioMidia(Model model) {
        model.addAttribute("midia", new MidiaModel());
        return "midias/adicionarmidia";
    }

    // Adiciona midia
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/midias")
    public String adicionarMidia(@ModelAttribute("midia") MidiaModel midia) {
        midiaRepository.save(midia);
        return "index";
    }

    // Deletar mídia
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{tipo}/deletar/{id}")
    public String deletarMidia(@PathVariable Long id) {
        midiaRepository.deleteById(id);
        return "index";
    }

    // Exibi formulário para editar filme
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{tipo}/editarmidia/{id}")
    public String editarMidia(@PathVariable Long id, Model model) {
        MidiaModel midiaModel = midiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mídia não encontrada"));
        model.addAttribute("midia", midiaModel);
        return "midias/editarmidia"; // This should point to your template
    }

    // Salva os dados editados no banco
    @PostMapping("/midias/{tipo}/{id}")
    public String EditarMidia(@PathVariable Long id, @ModelAttribute("midia") MidiaModel midiaAtualizada) {
        MidiaModel livroExistente = midiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mídia não encontrada"));
        livroExistente.setTitulo(midiaAtualizada.getTitulo());
        livroExistente.setDescricao(midiaAtualizada.getDescricao());
        livroExistente.setAutorDiretor(midiaAtualizada.getAutorDiretor());
        livroExistente.setAnoLancamento(midiaAtualizada.getAnoLancamento());
        livroExistente.setTipo(midiaAtualizada.getTipo());
        midiaRepository.save(livroExistente);
        return "index";
    }
}
