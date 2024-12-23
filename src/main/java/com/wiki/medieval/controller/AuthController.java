package com.wiki.medieval.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String login(@RequestParam String email, @RequestParam String senha, Model model) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, senha)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return "index";
        } catch (AuthenticationException e) {
            model.addAttribute("errorMessage", "E-mail ou senha inválidos.");
            return "login";
        }
    }

}