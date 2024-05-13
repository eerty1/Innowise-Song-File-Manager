package com.innowise.camel.supplier.token;

import com.innowise.model.User;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("jwtSupplier")
public class JwtSupplier extends AuthorizationTokenSupplier {

    private final String username;
    private final String password;

    @Autowired
    public JwtSupplier(ConsumerTemplate consumerTemplate,
                       ProducerTemplate producerTemplate,
                       @Value("${camel.jwt.from-uri}") String fromUri,
                       @Value("${camel.jwt.to-uri}") String toUri,
                       @Value("${auth-credentials.username}") String username,
                       @Value("${auth-credentials.password}") String password)
    {
        super(consumerTemplate, producerTemplate, fromUri, toUri);
        this.username = username;
        this.password = password;
    }

    @Override
    public String supply(String parameter) {
        producerTemplate.send(fromUri, exchange -> exchange.getIn().setBody(new User(username, password)));
        return consumerTemplate.receiveBody(toUri, String.class);
    }
}
