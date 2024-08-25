package br.edu.ifpb.pweb2.armants.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationConfiguration authConfiguration;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Disable CSRF if needed for your case
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin
                        )
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/aluno/cadastro", "/registro", "/registrar").permitAll()
                        .requestMatchers("/webjars/**").permitAll() // Allow access to WebJars resources
                        .anyRequest().permitAll() // All other requests require authentication
                )
                .formLogin(form -> form
                        .loginPage("/aluno/cadastro") // Custom login page
                        .permitAll() // Allow everyone to access the login page
                        .defaultSuccessUrl("/", true) // Redirect after successful login
                        .failureUrl("/aluno/cadastro?error=true") // Redirect after failed login
                )
                .logout(LogoutConfigurer::permitAll // Allow everyone to access the logout
                );

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authConfiguration.getAuthenticationManager();
    }
}

