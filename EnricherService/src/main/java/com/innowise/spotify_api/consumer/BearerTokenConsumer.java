package com.innowise.spotify_api.consumer;

import com.innowise.model.spotify_data.bearer_token.BearerToken;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;

@Component
public class BearerTokenConsumer {
    private final RestTemplate restTemplate;
    private final String clientId;
    private final String clientSecret;
    private final String accessTokenUrl;
    private final HttpHeaders httpHeaders = new HttpHeaders();
    @Getter
    private String bearerToken;

    @Autowired
    public BearerTokenConsumer(RestTemplate restTemplate,
                               @Value("${spotify.client-id}") String clientId,
                               @Value("${spotify.client-secret}") String clientSecret,
                               @Value("${spotify.access-token-url}") String accessTokenUrl)
    {
        this.restTemplate = restTemplate;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.accessTokenUrl = accessTokenUrl;
    }

    public String consumeBearerToken() {
        return Objects.requireNonNull(
                restTemplate.postForObject(
                        accessTokenUrl,
                        prepareHttpEntity(),
                        BearerToken.class)
        ).getAccessToken();
    }

    private HttpEntity<String> prepareHttpEntity() {
        httpHeaders.setContentType(APPLICATION_FORM_URLENCODED);
        return new HttpEntity<>(
                "grant_type=client_credentials&client_id=" + clientId + "&client_secret=" + clientSecret,
                httpHeaders
        );
    }

    public void refreshBearerToken() {
        this.bearerToken = consumeBearerToken();
    }
}
