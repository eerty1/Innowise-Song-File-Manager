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
public class Tracks {

    @JsonProperty("items")
    private List<TrackMetadata> trackMetadata;
}
