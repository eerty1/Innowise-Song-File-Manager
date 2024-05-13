package com.innowise.camel.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innowise.camel.supplier.Supplier;
import com.innowise.model.song_file.SongFile;
import com.innowise.model.spotify_metadata.track.TrackMetadata;
import com.innowise.song_file_metadata_collector.CollectorService;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("metadataProcessor")
@RequiredArgsConstructor
public class MetadataProcessor implements Processor {
    private final Supplier<Long, SongFile> songFileSupplier;
    private final Supplier<SongFile, TrackMetadata> spotifyMetadataSupplier;
    private final CollectorService collectorService;
    private final ObjectMapper objectMapper;

    @Override
    public void process(Exchange exchange) throws Exception {
        SongFile songFile = songFileSupplier.supply(exchange.getMessage().getBody(Long.class));
        Map<String, String> metadata = Map.of(
                "SongFileMetadata", objectMapper.writeValueAsString(collectorService.obtainMetadata(songFile)),
                "TrackMetadata", objectMapper.writeValueAsString(spotifyMetadataSupplier.supply(songFile))
        );
        exchange.getIn().setBody(metadata);
    }
}
