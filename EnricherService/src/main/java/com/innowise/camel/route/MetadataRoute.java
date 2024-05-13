package com.innowise.camel.route;

import org.apache.camel.Processor;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Component
public class MetadataRoute extends DestinationedRouteBuilder {
    private final Processor metadataProcessor;

    @Autowired
    public MetadataRoute(@Value("${camel.sqs.from-uri}") String fromUri,
                         @Value("${camel.sqs.to-uri}") String toUri,
                         Processor metadataProcessor)
    {
        super(fromUri, toUri);
        this.metadataProcessor = metadataProcessor;
    }

    @Override
    public void configure() {
        onException(NoSuchElementException.class)
                .process(exchange -> exchange.getIn().setBody(NOT_FOUND.value(), Integer.class))
                .handled(true)
                .to(toUri);

        from(fromUri)
                .process(metadataProcessor)
                .marshal()
                .json(JsonLibrary.Jackson)
                .to(toUri);
    }
}
