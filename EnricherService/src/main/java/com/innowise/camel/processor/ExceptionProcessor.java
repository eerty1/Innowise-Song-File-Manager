package com.innowise.camel.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Component
public class ExceptionProcessor implements Processor {

    @Override
    public void process(Exchange exchange) {
        exchange.getIn().setBody(NOT_FOUND.value(), Integer.class);
    }
}
