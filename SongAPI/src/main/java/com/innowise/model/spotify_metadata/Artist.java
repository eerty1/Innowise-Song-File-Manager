package com.innowise.model.spotify_metadata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.innowise.model.attribute_converter.ExternalUrlsConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("artist_id")
    private Long id;

    @Convert(converter = ExternalUrlsConverter.class)
    @JsonProperty("external_urls")
    private ExternalUrls externalUrls;

    @JsonProperty("id")
    private String spotifyArtistId;

    @JsonProperty("name")
    private String name;
}
