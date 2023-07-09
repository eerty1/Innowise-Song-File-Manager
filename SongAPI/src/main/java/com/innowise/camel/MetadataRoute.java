package com.innowise.camel;

import com.innowise.camel.processor.MetadataProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MetadataRoute extends RouteBuilder {
    private final MetadataProcessor metadataProcessor;
    private final String fromUri;

    @Autowired
    public MetadataRoute(MetadataProcessor metadataProcessor,
                         @Value("${camel.from-uri}") String fromUri)
    {
        this.metadataProcessor = metadataProcessor;
        this.fromUri = fromUri;
    }

    @Override
    public void configure() {
        from(fromUri)
                .process(metadataProcessor)
                .to("bean:com.innowise.repository.MetadataPersistenceService?method=saveMetadata(${body})");
    }
}
