package com.innowise.dto.spotify_metadata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TrackMetadataDTO {

    private String id;

    private List<ArtistDTO> artists = new ArrayList<>();

    private String name;

    private Long durationMs;

    private Integer popularity;

    private String previewUrl;

    private ExternalUrlsDTO externalUrls;

}
