package com.innowise.model.spotify_data.spotify_metadata;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SpotifyMetadata {
    @JsonProperty("artists")
    List<Artist> artists = new ArrayList<>();

    @JsonProperty("id")
    private String trackId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("duration_ms")
    private int durationMs;

    @JsonProperty("popularity")
    private int popularity;

    @JsonProperty("preview_url")
    private String previewUrl;

    @JsonProperty("external_urls")
    private ExternalUrls externalUrls;
}
