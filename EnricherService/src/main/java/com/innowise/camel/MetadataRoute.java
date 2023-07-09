package com.innowise.camel;

import com.innowise.camel.processor.ExceptionProcessor;
import com.innowise.camel.processor.MetadataProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class MetadataRoute extends RouteBuilder {
    private final MetadataProcessor metadataProcessor;
    private final ExceptionProcessor exceptionProcessor;
    private final String fromUri;
    private final String toUri;

    @Autowired
    public MetadataRoute(MetadataProcessor metadataProcessor,
                         ExceptionProcessor exceptionProcessor,
                         @Value("${camel.from-uri}") String fromUri,
                         @Value("${camel.to-uri}") String toUri)
    {
        this.metadataProcessor = metadataProcessor;
        this.exceptionProcessor = exceptionProcessor;
        this.fromUri = fromUri;
        this.toUri = toUri;
    }

    @Override
    public void configure() {
        onException(NoSuchElementException.class)
                .process(exceptionProcessor)
                .handled(true)
                .to(toUri);

        from(fromUri)
                .process(metadataProcessor)
                .marshal()
                .json(JsonLibrary.Jackson)
                .to(toUri);
    }
}
