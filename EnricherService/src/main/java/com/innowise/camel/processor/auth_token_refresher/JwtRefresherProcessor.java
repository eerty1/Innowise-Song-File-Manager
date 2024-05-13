package com.innowise.camel.processor.auth_token_refresher;

import com.innowise.camel.supplier.token.JwtSupplier;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component("jwtRefresherProcessor")
@RequiredArgsConstructor
public class JwtRefresherProcessor implements Processor {
    private final JwtSupplier jwtSupplier;

    @Override
    public void process(Exchange exchange) {
        jwtSupplier.refreshToken();
        exchange.setProperty("jwt", "Bearer " + jwtSupplier.getAuthorizationToken());
    }
}
