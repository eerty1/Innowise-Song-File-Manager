package com.innowise.camel.supplier;

import com.innowise.camel.supplier.token.AuthorizationTokenSupplier;
import com.innowise.model.song_file.SongFile;
import com.innowise.model.spotify_metadata.track.SpotifyMetadataRoot;
import com.innowise.model.spotify_metadata.track.TrackMetadata;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("spotifyMetadataSupplier")
public class SpotifyMetadataSupplier extends Supplier<SongFile, TrackMetadata> {

    private final AuthorizationTokenSupplier spotifyAuthTokenSupplier;

    @Autowired
    public SpotifyMetadataSupplier(ConsumerTemplate consumerTemplate,
                                   ProducerTemplate producerTemplate,
                                   @Value("${camel.spotify.track.from-uri}") String fromUri,
                                   @Value("${camel.spotify.track.to-uri}") String toUri,
                                   AuthorizationTokenSupplier spotifyAuthTokenSupplier)
    {
        super(consumerTemplate, producerTemplate, fromUri, toUri);
        this.spotifyAuthTokenSupplier = spotifyAuthTokenSupplier;
    }

    @Override
    public TrackMetadata supply(SongFile parameter) {
        producerTemplate.send(fromUri, exchange -> {
            exchange.setProperty("songName", parameter.getSongName());
            exchange.setProperty("artist", parameter.getArtist());
            exchange.setProperty("bearerToken", "Bearer " + spotifyAuthTokenSupplier.getAuthorizationToken());
        });
        return consumerTemplate.receiveBody(toUri, SpotifyMetadataRoot.class).getTracks().getTrackMetadata().get(0);
    }
}
