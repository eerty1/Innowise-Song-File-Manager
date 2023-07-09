package com.innowise.model.spotify_data.spotify_metadata;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Root {
    @JsonProperty("tracks")
    private SearchForItemResult searchForItemResult;
}
