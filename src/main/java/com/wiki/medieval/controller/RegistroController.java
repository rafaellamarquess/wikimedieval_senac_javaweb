package com.wiki.medieval.controller;

import com.wiki.medieval.model.UserModel;
import com.wiki.medieval.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegistroController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public RegistroController(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/registro")
    public String home() {
        return "registro";
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@RequestParam String nome,  @RequestParam String email, @RequestParam String senha, @RequestParam UserModel.Role role) {
        System.out.println("Recebendo dados de registro: email=" + email + ", senha=" + senha);
        try {
            if (userRepository.findByEmail(email).isPresent()) {
                return ResponseEntity.badRequest().body("Usuário já cadastrado");
            }
            UserModel novoUsuario = new UserModel();
            novoUsuario.setNome(nome);
            novoUsuario.setEmail(email);
            novoUsuario.setSenha(passwordEncoder.encode(senha));
            novoUsuario.setRole(role);

            userRepository.save(novoUsuario);

            return ResponseEntity.ok("Usuário cadastrado com sucesso");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao registrar usuário: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno ao tentar registrar o usuário. Tente novamente mais tarde.");
        }
    }
}
