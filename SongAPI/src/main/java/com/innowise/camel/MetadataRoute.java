package com.innowise.camel;

import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class MetadataRoute extends RouteBuilder {

    private final Processor metadataProcessor;
    private final Processor exceptionLoggerProcessor;
    private final String fromUri;

    @Autowired
    public MetadataRoute(Processor metadataProcessor,
                         Processor exceptionLoggerProcessor,
                         @Value("${camel.song_metadata_song_api}") String fromUri)
    {
        this.metadataProcessor = metadataProcessor;
        this.exceptionLoggerProcessor = exceptionLoggerProcessor;
        this.fromUri = fromUri;
    }

    @Override
    public void configure() {
        onException(NoSuchElementException.class)
                .process(exceptionLoggerProcessor)
                .handled(true);

        from(fromUri)
                .process(metadataProcessor)
                .to("bean:com.innowise.crud_service.MetadataPersistenceService?method=saveMetadata(${body})");
    }
}
