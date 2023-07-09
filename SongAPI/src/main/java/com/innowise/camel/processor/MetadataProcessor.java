package com.innowise.camel.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
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
