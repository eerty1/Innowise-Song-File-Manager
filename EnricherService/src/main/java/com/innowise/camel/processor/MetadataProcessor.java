package com.innowise.camel.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innowise.repository.SongFileRepositoryService;
import com.innowise.s3_storage.S3StorageService;
import com.innowise.spotify_api.consumer.MetadataConsumer;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MetadataProcessor implements Processor {
    private final S3StorageService s3StorageService;
    private final SongFileRepositoryService songFileRepositoryService;
    private final MetadataConsumer metadataConsumer;

    @Autowired
    public MetadataProcessor(S3StorageService s3StorageService, SongFileRepositoryService songFileRepositoryService, MetadataConsumer metadataConsumer) {
        this.s3StorageService = s3StorageService;
        this.songFileRepositoryService = songFileRepositoryService;
        this.metadataConsumer = metadataConsumer;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        Long songId = exchange.getMessage().getBody(Long.class);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> metadata = Map.of(
                "S3Metadata", objectMapper.writeValueAsString(s3StorageService.obtainSongMetadata(songFileRepositoryService.getSongFileProperties(songId))),
                "SpotifyMetadata", objectMapper.writeValueAsString(metadataConsumer.consumeSpotifyMetadata(songId))
        );
        exchange.getIn().setBody(metadata);
    }
}
