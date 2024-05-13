package com.innowise.model.spotify_metadata.track;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Artist {

    @JsonProperty("external_urls")
    private ExternalUrls externalUrls;
    @JsonProperty("id")
    private String spotifyId;
    private String name;

}
