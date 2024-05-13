package com.innowise.camel.processor.auth_token_refresher;

import com.innowise.camel.supplier.token.AuthorizationTokenSupplier;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component("spotifyAuthTokenRefresherProcessor")
@RequiredArgsConstructor
public class SpotifyAuthTokenRefresherProcessor implements Processor {

    private final AuthorizationTokenSupplier spotifyAuthTokenSupplier;

    @Override
    public void process(Exchange exchange) {
        spotifyAuthTokenSupplier.refreshToken();
        exchange.setProperty("bearerToken", "Bearer " + spotifyAuthTokenSupplier.getAuthorizationToken());
    }
}
