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
public class SearchForItemResult {
    @JsonProperty("items")
    List<SpotifyMetadata> spotifyMetadata = new ArrayList<>();
}
