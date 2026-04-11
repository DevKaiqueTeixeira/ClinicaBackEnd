// package com.example.demo.config;

// import java.util.List;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
// import org.springframework.web.cors.CorsConfiguration;
// import org.springframework.web.cors.CorsConfigurationSource;
// import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
// import org.springframework.security.oauth2.core.user.OAuth2User;

// import com.example.demo.model.Cliente;
// import com.example.demo.services.ClienteService;

// @Configuration
// public class SecurityConfig {

//     private final ClienteService clienteService;

//     public SecurityConfig(ClienteService clienteService) {
//         this.clienteService = clienteService;
//     }

//     @Bean
//     public AuthenticationSuccessHandler successHandler() {
//         return (request, response, authentication) -> {

//             OAuth2User user = (OAuth2User) authentication.getPrincipal();

//             String email = user.getAttribute("email");
//             String nome = user.getAttribute("name");

//             Cliente cliente = clienteService.loginComGoogle(email, nome);

//             request.getSession().setAttribute("usuarioLogado", cliente);

//             response.sendRedirect("http://localhost:3000/dashboard");
//         };
//     }

//     @Bean
//     public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

//         http
//                 .cors(cors -> cors.configurationSource(corsConfigurationSource()))
//                 .csrf(csrf -> csrf.disable())
//                 .authorizeHttpRequests(auth -> auth
//                         .requestMatchers(
//                                 "/",
//                                 "/login",
//                                 "/auth/**",
//                                 "/oauth2/**",
//                                 "/login/oauth2/**",
//                                 "/clientes/user",
//                                 "/clientes")
//                         .permitAll()
//                         .anyRequest().authenticated())
//                 .oauth2Login(oauth -> oauth
//                         .successHandler(successHandler()));

//         return http.build();
//     }

//     @Bean
//     public CorsConfigurationSource corsConfigurationSource() {

//         CorsConfiguration config = new CorsConfiguration();

//         config.setAllowedOrigins(List.of("http://localhost:3000"));
//         config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//         config.setAllowedHeaders(List.of("*"));
//         config.setAllowCredentials(true);

//         UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//         source.registerCorsConfiguration("/**", config);

//         return source;
//     }
// }

package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import org.springframework.http.HttpMethod;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // Troquei para a porta 9000
        config.setAllowedOrigins(List.of("http://localhost:9000"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> {
                })
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                "/login",
                                "/auth/**",
                                "/oauth2/**",
                                "/clientes/user",
                                "/vueTJ/**",
                                "/tickets/**" // adiciona seus endpoints de cadastro
                        ).permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // permite preflight
                        .anyRequest().authenticated())
                .oauth2Login(oauth -> oauth
                        .defaultSuccessUrl("http://localhost:9000/dashboard", true));

        return http.build();
    }
}