package com.innowise.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ArtistDTO {
    @JsonProperty("artist_id")
    private Long id;

    @JsonProperty("external_urls")
    private ExternalUrlsDTO externalUrls;

    @JsonProperty("id")
    private String spotifyArtistId;

    @JsonProperty("name")
    private String name;
}
