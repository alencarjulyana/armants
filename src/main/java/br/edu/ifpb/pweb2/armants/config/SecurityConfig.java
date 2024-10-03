package br.edu.ifpb.pweb2.armants.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import static org.springframework.security.core.userdetails.User.withUsername;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception{

        http
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/css/**", "/imagens/**", "/", "/empresas/novo", "/aluno/novo", "auth/**").permitAll()
//                    .requestMatchers("/coordenador/ofertas-estagio")
//                    .hasAnyRole("COORDENADOR", "EMPRESA")
//                    .requestMatchers("/**").hasRole("COORDENADOR")
                    .anyRequest().authenticated())
                .formLogin((form) -> form
                        .loginPage("/auth/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll())
                .logout((logout) -> logout.logoutUrl("auth/logout"));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails teste = withUsername("teste").password(passwordEncoder().encode("teste")).roles("COORDENADOR").build();
        UserDetails empresa = User.withUsername("empresa").password(passwordEncoder().encode("empresa")).roles("EMPRESA").build();
        UserDetails aluno = User.withUsername("aluno").password(passwordEncoder().encode("aluno")).roles("ALUNO").build();

        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);

        if(!users.userExists(teste.getUsername())){
            users.createUser(teste);
            users.createUser(empresa);
            users.createUser(aluno);
        }

        return users;
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
}

