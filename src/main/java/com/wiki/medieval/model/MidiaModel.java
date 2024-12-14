package com.wiki.medieval.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@Table(name = "midias")
public class MidiaModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @Enumerated(EnumType.STRING)
    private TipoMidia tipo;

    private String descricao;

    @Column(name = "autor_diretor")
    private String autorDiretor;

    @Column(name = "ano_lancamento")
    private Integer anoLancamento;

    public enum TipoMidia {
        Livro,
        Filme,
        Videogame,
        ;
    }
}

