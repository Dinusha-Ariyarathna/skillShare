package com.skillshare.platform.config;

import com.skillshare.platform.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.web.SecurityFilterChain;
/**
 * @AUTHOR : Dinusha Ariyarathna
 * @DATE : 5/2/2025
 * @PROJECT : platform
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService) {
        this.customOAuth2UserService = customOAuth2UserService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/error").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/**").permitAll() // allow reading data
                        .requestMatchers(HttpMethod.POST, "/api/**").authenticated() // restrict creation
                        .requestMatchers(HttpMethod.DELETE, "/api/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/**").authenticated()
                        .requestMatchers(HttpMethod.GET,  "/api/plans/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/plans").authenticated()
                        .requestMatchers(HttpMethod.PUT,  "/api/plans/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE,"/api/plans/**").authenticated()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)
                        )
                )
                .csrf().disable(); // ❗ Disable CSRF ONLY in dev/testing

        return http.build();
    }

}
