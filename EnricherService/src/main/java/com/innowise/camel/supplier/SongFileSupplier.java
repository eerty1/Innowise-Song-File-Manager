package com.innowise.camel.supplier;

import com.innowise.camel.supplier.token.AuthorizationTokenSupplier;
import com.innowise.model.song_file.SongFile;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("songFileSupplier")
public class SongFileSupplier extends Supplier<Long, SongFile> {

    private final AuthorizationTokenSupplier jwtSupplier;

    @Autowired
    public SongFileSupplier(ConsumerTemplate consumerTemplate,
                            ProducerTemplate producerTemplate,
                            @Value("${camel.song-file.from-uri}") String fromUri,
                            @Value("${camel.song-file.to-uri}") String toUri,
                            AuthorizationTokenSupplier jwtSupplier)
    {
        super(consumerTemplate, producerTemplate, fromUri, toUri);
        this.jwtSupplier = jwtSupplier;
    }

    @Override
    public SongFile supply(Long parameter) {
        producerTemplate.send(fromUri, exchange -> {
                exchange.setProperty("id", parameter);
                exchange.setProperty("jwt", "Bearer " + jwtSupplier.getAuthorizationToken());
        });
        return consumerTemplate.receiveBody(toUri, SongFile.class);
    }
}