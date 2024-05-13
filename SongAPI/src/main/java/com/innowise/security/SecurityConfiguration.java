package com.innowise.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

import static com.innowise.model.user.Role.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final RsaKeyProperties rsaKeyProperties;
    private final JwtAuthenticationConverterAdapter jwtAuthenticationConverterAdapter;
    private final ExceptionResolverAuthenticationEntryPoint exceptionResolverAuthenticationEntryPoint;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeRequests(request ->
                        request
                                .antMatchers("/admin/**")
                                .hasRole(ADMIN.name())
                                .antMatchers("/user/**")
                                .hasAnyRole(MICROSERVICE.name())
                                .antMatchers("/swagger-ui/**", "/v3/**")
                                .permitAll()
                                .antMatchers("/**")
                                .hasAnyRole(ADMIN.name(), MICROSERVICE.name(), USER.name())
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .oauth2ResourceServer(oauth -> oauth
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverterAdapter))
                        .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                        .authenticationEntryPoint(exceptionResolverAuthenticationEntryPoint)
                );
        return httpSecurity.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(rsaKeyProperties.getPublicKey()).build();
    }

}

