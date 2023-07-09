package com.innowise.model.spotify_metadata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.innowise.model.attribute_converter.ExternalUrlsConverter;
import lombok.*;

import javax.persistence.Convert;
import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
public class ExternalUrls implements Serializable {
    @JsonProperty("spotify")
    private String spotifyUrl;
}
