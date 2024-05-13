package com.innowise.model.spotify_metadata;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ExternalUrls {

    @Getter(onMethod_ = @JsonGetter("spotifyUrl"))
    @Setter(onMethod_ = @JsonSetter("spotify"))
    private String spotifyUrl;
}
