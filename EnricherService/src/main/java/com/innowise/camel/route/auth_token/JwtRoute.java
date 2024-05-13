package com.innowise.camel.route.auth_token;

import com.innowise.camel.route.DestinationedRouteBuilder;
import com.innowise.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtRoute extends DestinationedRouteBuilder {
    private final String receiveJwtUri;

    @Autowired
    public JwtRoute(@Value("${camel.jwt.from-uri}") String fromUri,
                    @Value("${camel.jwt.to-uri}") String toUri,
                    @Value("${camel.jwt.receive-jwt-uri}")  String receiveJwtUri)
    {
        super(fromUri, toUri);
        this.receiveJwtUri = receiveJwtUri;
    }

    @Override
    public void configure() {
        from(fromUri)
                .marshal()
                .json(User.class)
                .to(receiveJwtUri)
                .to(toUri);
    }
}
