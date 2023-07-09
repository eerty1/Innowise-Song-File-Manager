package com.innowise.dto.mapper;

import com.innowise.dto.ArtistDTO;
import com.innowise.dto.SpotifyMetadataDTO;
import com.innowise.model.spotify_metadata.Artist;
import com.innowise.model.spotify_metadata.SpotifyMetadata;
import org.mapstruct.Condition;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SpotifyMetadataMapper {
    SpotifyMetadataMapper spotifyMetadataMapperInstance = Mappers.getMapper(SpotifyMetadataMapper.class);

    MappingUtils mappingUtils = new MappingUtils();

    SpotifyMetadataDTO toSpotifyMetadataDTO(SpotifyMetadata spotifyMetadata);

    List<Artist> toArtistList(List<ArtistDTO> list);

    default SpotifyMetadata updateSpotifyMetadataFromDTO(SpotifyMetadataDTO spotifyMetadataDTO, SpotifyMetadata spotifyMetadataToPatch) {
        if (spotifyMetadataDTO == null) {
            return spotifyMetadataToPatch;
        }

        List<Artist> artistsFromDTO = toArtistList(spotifyMetadataDTO.getArtists());
        List<Artist> artistsToPatch = spotifyMetadataToPatch.getArtists();

        if (spotifyMetadataToPatch.getArtists() != null) {
            spotifyMetadataToPatch.setArtists(mappingUtils.updateArtists(artistsFromDTO, artistsToPatch));
        } else {
            if (artistsFromDTO != null) {
                spotifyMetadataToPatch.setArtists(artistsFromDTO);
            }
        }

        if (isNotEmpty(spotifyMetadataDTO.getTrackId())) {
            spotifyMetadataToPatch.setTrackId(spotifyMetadataDTO.getTrackId());
        }
        if (isNotEmpty(spotifyMetadataDTO.getName())) {
            spotifyMetadataToPatch.setName(spotifyMetadataDTO.getName());
        }
        if (isNotZero(spotifyMetadataDTO.getDurationMs())) {
            spotifyMetadataToPatch.setDurationMs(spotifyMetadataDTO.getDurationMs());
        }
        if (isNotZero(spotifyMetadataDTO.getPopularity())) {
            spotifyMetadataToPatch.setPopularity(spotifyMetadataDTO.getPopularity());
        }
        if (isNotEmpty(spotifyMetadataDTO.getPreviewUrl())) {
            spotifyMetadataToPatch.setPreviewUrl(spotifyMetadataDTO.getPreviewUrl());
        }
        if (spotifyMetadataDTO.getExternalUrls() != null) {
            if (isNotEmpty(spotifyMetadataDTO.getExternalUrls().getSpotifyUrl())) {
                spotifyMetadataToPatch.getExternalUrls().setSpotifyUrl(spotifyMetadataDTO.getExternalUrls().getSpotifyUrl());
            }
        }
        return spotifyMetadataToPatch;
    }

    @Condition
    default boolean isNotEmpty(String value) {
        return value != null && !value.isEmpty();
    }

    @Condition
    default boolean isNotZero(int value) {
        return value != 0;
    }
}
