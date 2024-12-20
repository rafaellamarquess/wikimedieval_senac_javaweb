package com.wiki.medieval.controller;

import com.wiki.medieval.model.UserModel;
import com.wiki.medieval.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.wiki.medieval.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
public class UserController {

    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserController(UserDetailsServiceImpl userDetailsServiceImpl, AuthenticationManager authenticationManager) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.authenticationManager = authenticationManager;
    }

    // Metodo para exibir a página de login
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    // Metodo para processar o login
    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("senha") String senha,
                        Model model) {
        try {
            // Verifica as credenciais do usuário
            boolean authenticated = userDetailsServiceImpl.authenticateUser(email, senha);
            if (authenticated) {
                // Criação do token de autenticação
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, senha);
                // Autenticar o usuário
                Authentication authentication = authenticationManager.authenticate(authenticationToken);
                // Armazenar a autenticação no contexto de segurança
                SecurityContextHolder.getContext().setAuthentication(authentication);
                return "index";  // Redireciona para a página inicial após login bem-sucedido
            } else {
                model.addAttribute("errorMessage", "Credenciais inválidas");
                return "login";
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Erro ao tentar autenticar");
            return "login";
        }
    }


//    @PostMapping("/registro")
//    public String registerUser(@RequestParam String nome, @RequestParam String email,
//                               @RequestParam String senha, @RequestParam String tipoUsuario, Model model) {
//        if (userRepository.findByEmail(email) != null) {
//            model.addAttribute("errorMessage", "E-mail já cadastrado.");
//            return "registro";
//        }
//        UserModel.Role role;
//        try {
//            role = UserModel.Role.valueOf(tipoUsuario.toUpperCase()); // Converte para maiúsculas, garantindo compatibilidade com o enum
//        } catch (IllegalArgumentException e) {
//            model.addAttribute("errorMessage", "Tipo de usuário inválido.");
//            return "registro";
//        }
//        UserModel newUser = new UserModel();
//        newUser.setNome(nome);
//        newUser.setEmail(email);
//        newUser.setSenha(passwordEncoder.encode(senha));
//        newUser.setRole(role); // Define o tipo de usuário
//        userRepository.save(newUser);
//        return "redirect:/login";
//    }
}
