package com.innowise.model.spotify_metadata.track;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TrackMetadata {

    private List<Artist> artists;

    @JsonProperty("id")
    private String spotifyId;

    private String name;

    @JsonProperty("duration_ms")
    private Integer durationMs;

    private Integer popularity;

    @JsonProperty("preview_url")
    private String previewUrl;

    @JsonProperty("external_urls")
    private ExternalUrls externalUrls;
}
