package com.innowise.spotify_api.consumer;


import com.innowise.model.song_file.SongFile;
import com.innowise.model.spotify_data.spotify_metadata.Root;
import com.innowise.model.spotify_data.spotify_metadata.SpotifyMetadata;
import com.innowise.repository.SongFileRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

import static org.springframework.http.HttpMethod.GET;

@Component
public class MetadataConsumer {
    private final RestTemplate restTemplate;
    private final BearerTokenConsumer bearerTokenConsumer;
    private final SongFileRepositoryService songFileRepositoryService;
    private final HttpHeaders httpHeaders = new HttpHeaders();

    @Autowired
    public MetadataConsumer(RestTemplate restTemplate, BearerTokenConsumer bearerTokenConsumer, SongFileRepositoryService songFileRepositoryService) {
        this.restTemplate = restTemplate;
        this.bearerTokenConsumer = bearerTokenConsumer;
        this.songFileRepositoryService = songFileRepositoryService;
    }

    public SpotifyMetadata consumeSpotifyMetadata(Long songId) {
        SongFile songFile = songFileRepositoryService.getSongFileProperties(songId);
        try {
            return createApiSearchRequest(songFile.getSongName(), songFile.getArtist());
        } catch (HttpClientErrorException.Unauthorized httpClientErrorException) {
            bearerTokenConsumer.refreshBearerToken();
            return createApiSearchRequest(songFile.getSongName(), songFile.getArtist());
        }
    }

    private SpotifyMetadata createApiSearchRequest(String songName, String artistName) {
        httpHeaders.setBearerAuth(bearerTokenConsumer.getBearerToken());
        return Objects.requireNonNull(restTemplate.exchange(
                "https://api.spotify.com/v1/search?q=track:" + songName + "+artist:" + artistName + "&type=track&market=ES&limit=1&offset=1",
                GET,
                new HttpEntity<>(httpHeaders),
                Root.class).getBody()
        ).getSearchForItemResult().getSpotifyMetadata().get(0);
    }
}
