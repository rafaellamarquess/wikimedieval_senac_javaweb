package com.wiki.medieval.service;

import com.wiki.medieval.model.UserModel;
import com.wiki.medieval.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
        return User.builder()
                .username(user.getEmail())
                .password(user.getSenha())
                .roles(user.getRole().name())
                .build();
    }

    public boolean authenticateUser(String email, String senha) {
        UserModel user = userRepository.findByEmail(email);
        if (user != null) {
            return passwordEncoder.matches(senha, user.getSenha());
        }
        return false;
    }
}
