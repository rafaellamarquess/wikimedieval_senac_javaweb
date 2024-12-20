package com.wiki.medieval.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "usuarios")
public class UserModel  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_usuario")
    private Role role;

    public enum Role {
        ADMIN,
        USER,
    }
}
