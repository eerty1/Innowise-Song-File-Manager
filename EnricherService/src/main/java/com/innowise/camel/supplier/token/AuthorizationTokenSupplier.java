package com.innowise.camel.supplier.token;

import com.innowise.camel.supplier.Supplier;
import lombok.Getter;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;

public abstract class AuthorizationTokenSupplier extends Supplier<String, String> {

    @Getter
    private String authorizationToken;

    protected AuthorizationTokenSupplier(ConsumerTemplate consumerTemplate, ProducerTemplate producerTemplate, String fromUri, String toUri) {
        super(consumerTemplate, producerTemplate, fromUri, toUri);
    }

    public void refreshToken() {
        this.authorizationToken = supply(null);
    }
}
