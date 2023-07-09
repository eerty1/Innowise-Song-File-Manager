package com.innowise.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SpotifyMetadataDTO {
    @JsonProperty("spotify_metadata_id")
    private Long id;

    @JsonProperty("artists")
    private List<ArtistDTO> artists = new ArrayList<>();
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
    private ExternalUrlsDTO externalUrls;
}
