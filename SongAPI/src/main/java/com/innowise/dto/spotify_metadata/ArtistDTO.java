package com.innowise.dto.spotify_metadata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ArtistDTO {

    private String id;

    private ExternalUrlsDTO externalUrls;

    private String name;
}
