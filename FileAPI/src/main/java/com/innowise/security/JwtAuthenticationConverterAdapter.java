package com.innowise.security;

import com.innowise.dto.user.CheckUserOutDTO;
import com.innowise.model.user.User;
import com.innowise.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

import static com.innowise.model.user.Role.MICROSERVICE;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationConverterAdapter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    @Value("${auth-api.get-user-endpoint}")
    private String getUserEndpoint;

    /*
     jwt.getId() is intended to return the ID of the token itself, but in my case it returns the UUID of a user, that this token was issued to.
     this ID is used to avoid desynchronization (which can happen due to update failure) between user instances from AuthAPI and FileAPI databases
     because of the risk, that the username or any other property could be completely lost, I decided to work with user's ID, which is not changed at all
     ofc I could create a custom claim, but imho it's overkill + redundant code
     */

    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
        AbstractAuthenticationToken tokenContent = jwtAuthenticationConverter.convert(jwt);
        try {
            String rolePrefix = "ROLE_";

            if (isInternalRequest(tokenContent)) {
                return new JwtAuthenticationToken(jwt, List.of(new SimpleGrantedAuthority(rolePrefix + MICROSERVICE.name())));
            }
            User user = userRepository.findById(jwt.getId()).orElseGet(
                    () -> restTemplate.postForObject(getUserEndpoint, new CheckUserOutDTO(jwt.getId()), User.class)
            );
            return new JwtAuthenticationToken(jwt, List.of(new SimpleGrantedAuthority(rolePrefix + Objects.requireNonNull(user).getRole())));
        } catch (HttpClientErrorException.NotFound notFound) {
            throw new UsernameNotFoundException("User " + tokenContent.getName() + " not found");
        }
    }

    private boolean isInternalRequest(AbstractAuthenticationToken abstractAuthenticationToken) {
        return abstractAuthenticationToken
                .getAuthorities()
                .stream()
                .anyMatch(grantedAuthority -> grantedAuthority
                        .getAuthority()
                        .contains(MICROSERVICE.name())
                );
    }
}