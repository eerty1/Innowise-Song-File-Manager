package com.innowise.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component("metadataProcessor")
public class MetadataProcessor implements Processor {

    @Override
    public void process(Exchange exchange) {
        String enricherServiceMessage = exchange.getIn().getBody(String.class);
        if (enricherServiceMessage.contains("404")) {
            throw new NoSuchElementException();
        } else {
            exchange.getIn().setBody(enricherServiceMessage);
        }
    }
}
