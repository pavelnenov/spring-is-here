package com.pavel.spring.springishere.config;

import com.pavel.spring.springishere.dao.UserDao;
import com.pavel.spring.springishere.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@EnableWebSecurity
@ComponentScan("com.pavel.spring.springishere.jwt")
public class SecurityConfig {

    private final UserRepository userRepository;

    public SecurityConfig(UserRepository userDao) {
        this.userRepository = userDao;
    }

    private static final String[] AUTH_WHITELIST = {
            // -- Swagger UI v2
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/app/swagger-ui.html",
            "/webjars/**",
            "/v3/api-docs/**",
            "/v2/api-docs/**",
            "/swagger-ui/**",

            // Auth controller
            "/**/auth/**",

            //heartbeat
            "/heartbeat"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider());
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new DaoAuthenticationProvider() {{
            setUserDetailsService(userDetailsService());
            setPasswordEncoder(new BCryptPasswordEncoder());
//            setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        }};
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return (email) -> {
            UserDao user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

            return User
                    .withUsername(user.email())
                    .password(user.password())
                    .authorities("USER")
                    .build();
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
