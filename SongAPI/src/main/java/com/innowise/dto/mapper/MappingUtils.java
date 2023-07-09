package com.innowise.dto.mapper;

import com.innowise.model.spotify_metadata.Artist;

import java.util.List;

public class MappingUtils {
    private final SpotifyMetadataMapper spotifyMetadataMapper = SpotifyMetadataMapper.spotifyMetadataMapperInstance;

    public List<Artist> updateArtists(List<Artist> artistsFromDTO, List<Artist> artistsToPatch) {
        if (artistsFromDTO != null) {
            for (int i = 0; i < artistsToPatch.size(); i++) {
                if (artistsFromDTO.size() == 0 || artistsFromDTO.get(i) == null) {
                    continue;
                }
                if (spotifyMetadataMapper.isNotEmpty(artistsFromDTO.get(i).getSpotifyArtistId())) {
                    artistsToPatch.get(i).setSpotifyArtistId(artistsFromDTO.get(i).getSpotifyArtistId());
                }
                if (spotifyMetadataMapper.isNotEmpty(artistsFromDTO.get(i).getName())) {
                    artistsToPatch.get(i).setName(artistsFromDTO.get(i).getName());
                }
                if (artistsFromDTO.get(i).getExternalUrls() != null) {
                    if (spotifyMetadataMapper.isNotEmpty(artistsFromDTO.get(i).getExternalUrls().getSpotifyUrl())) {
                        artistsToPatch.get(i).getExternalUrls().setSpotifyUrl(artistsFromDTO.get(i).getExternalUrls().getSpotifyUrl());
                    }
                }
            }
        }
        return artistsToPatch;
    }
}
