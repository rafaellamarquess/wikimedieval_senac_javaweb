package com.wiki.medieval.service;

import com.wiki.medieval.config.UserPrincipalImpl;
import com.wiki.medieval.model.UserModel;
import com.wiki.medieval.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserModel user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));
        System.out.println("Usuário encontrado: " + user.getEmail() + ", Role: " + user.getRole());
        System.out.println("Senha armazenada: " + user.getSenha());
        return new UserPrincipalImpl(user);
    }
}