package com.wiki.medieval.controller;

import com.wiki.medieval.model.MidiaModel;
import com.wiki.medieval.repository.MidiaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/midias")
public class MidiaRestController {

    private final MidiaRepository midiaRepository;

    public MidiaRestController(MidiaRepository midiaRepository) {
        this.midiaRepository = midiaRepository;
    }

    @GetMapping
    public List<MidiaModel> listarTodos(MidiaModel.TipoMidia tipo) {
        return midiaRepository.findAllByTipo(tipo);
    }

    // 2. Buscar mídias por tipo (ex: Filme, Livro, etc.)
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<MidiaModel>> listarMidiasPorTipo(@PathVariable MidiaModel.TipoMidia tipo) {
        List<MidiaModel> midias = midiaRepository.findAllByTipo(tipo);
        if (midias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(midias);
    }

    // 3. Buscar mídia por ID
    @GetMapping("/{id}")
    public ResponseEntity<MidiaModel> buscarPorId(@PathVariable Long id) {
        Optional<MidiaModel> midia = midiaRepository.findById(id);
        return midia.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 4. Adicionar uma nova mídia
    @PostMapping
    public ResponseEntity<MidiaModel> adicionarMidia(@RequestBody MidiaModel novaMidia) {
        MidiaModel midiaSalva = midiaRepository.save(novaMidia);
        return ResponseEntity.status(201).body(midiaSalva);
    }

    // 5. Atualizar uma mídia existente
    @PutMapping("/{id}")
    public ResponseEntity<MidiaModel> atualizarMidia(@PathVariable Long id, @RequestBody MidiaModel midiaModelAtualizada) {
        return midiaRepository.findById(id).map(midiaModel -> {
            midiaModel.setTitulo(midiaModelAtualizada.getTitulo());
            midiaModel.setDescricao(midiaModelAtualizada.getDescricao());
            midiaModel.setTipo(midiaModelAtualizada.getTipo());
            midiaModel.setAutorDiretor(midiaModelAtualizada.getAutorDiretor());
            midiaModel.setAnoLancamento(midiaModelAtualizada.getAnoLancamento());
            MidiaModel midiaSalva = midiaRepository.save(midiaModel);
            return ResponseEntity.ok(midiaSalva);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 6. Deletar uma mídia por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarMidia(@PathVariable Long id) {
        if (midiaRepository.existsById(id)) {
            midiaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
