package com.wiki.medieval.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final AuthenticationManager authenticationManager;

    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    // Metodo para exibir a página de login
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    // Metodo para autenticar o usuário
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String senha) {
        System.out.println("Recebendo dados de login: email=" + email + ", senha=" + senha); // Log para verificação
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            email,
                            senha
                    )
            );
            authentication.getPrincipal();
            return ResponseEntity.ok().body("Login bem-sucedido!");
        } catch (BadCredentialsException e) {
            System.out.println("Credenciais inválidas: " + e.getMessage());  // Log de erro
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
        }
    }

}