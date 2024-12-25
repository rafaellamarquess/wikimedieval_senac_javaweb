package com.wiki.medieval.config;

import com.wiki.medieval.repository.UserRepository;
import com.wiki.medieval.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserRepository userRepository;
    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(UserRepository userRepository, CustomUserDetailsService customUserDetailsService) {
        this.userRepository = userRepository;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    UserDetailsService userDetailsService() {
        return new CustomUserDetailsService(this.userRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(this.customUserDetailsService);
        authenticationProvider.setPasswordEncoder(this.passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/css/**", "/js/**", "/img/**", "/login", "/registro").permitAll()
                                .requestMatchers(HttpMethod.POST, "/adicionarmidia").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/delete").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/editarmidia").hasAuthority("ADMIN")
                                .anyRequest().authenticated()
                )
                .formLogin(form -> {
                            form
                                    .loginPage("/login")
                                    .usernameParameter("email")
                                    .passwordParameter("senha")
                                    .defaultSuccessUrl("/")
                                    .failureUrl("/login?error=true")
                                    .permitAll();
                        }
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                )
                .build();
    }
}