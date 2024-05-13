package com.innowise.camel.supplier.token;

import com.innowise.model.spotify_metadata.SpotifyAuthToken;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("spotifyAuthTokenSupplier")
public class SpotifyAuthTokenSupplier extends AuthorizationTokenSupplier {

    private final String clientId;
    private final String clientSecret;

    @Autowired
    public SpotifyAuthTokenSupplier(ConsumerTemplate consumerTemplate,
                                    ProducerTemplate producerTemplate,
                                    @Value("${camel.spotify.bearer.from-uri}") String fromUri,
                                    @Value("${camel.spotify.bearer.to-uri}") String toUri,
                                    @Value("${spotify.client-id}") String clientId,
                                    @Value("${spotify.client-secret}") String clientSecret)
    {
        super(consumerTemplate, producerTemplate, fromUri, toUri);
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    @Override
    public String supply(String parameter) {
        producerTemplate.send(fromUri, exchange -> exchange.getIn().setBody(
                "grant_type=client_credentials&client_id=" + clientId + "&client_secret=" + clientSecret)
        );
        return consumerTemplate.receiveBody(toUri, SpotifyAuthToken.class).getAccessToken();
    }
}
