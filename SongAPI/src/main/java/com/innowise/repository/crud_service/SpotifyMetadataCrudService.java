package com.innowise.repository.crud_service;

import com.innowise.dto.SpotifyMetadataDTO;
import com.innowise.dto.mapper.SpotifyMetadataMapper;
import com.innowise.model.spotify_metadata.SpotifyMetadata;
import com.innowise.repository.SpotifyMetadataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpotifyMetadataCrudService {
    private final SpotifyMetadataRepository spotifyMetadataRepository;
    private final SpotifyMetadataMapper spotifyMetadataMapper = SpotifyMetadataMapper.spotifyMetadataMapperInstance;

    @Autowired
    public SpotifyMetadataCrudService(SpotifyMetadataRepository spotifyMetadataRepository) {
        this.spotifyMetadataRepository = spotifyMetadataRepository;
    }

    public SpotifyMetadataDTO getSpotifyMetadata(Long id) {
        return spotifyMetadataMapper.toSpotifyMetadataDTO(
                spotifyMetadataRepository.findById(id).orElseThrow()
        );
    }

    public SpotifyMetadataDTO updateSpotifyMetadata(Long id, SpotifyMetadataDTO spotifyMetadataDTO) {
        SpotifyMetadata spotifyMetadataToPatch = spotifyMetadataRepository.findById(id).orElseThrow();
        return spotifyMetadataMapper.toSpotifyMetadataDTO(
                spotifyMetadataRepository.save(
                        spotifyMetadataMapper.updateSpotifyMetadataFromDTO(spotifyMetadataDTO, spotifyMetadataToPatch)
                )
        );
    }

    public void deleteSpotifyMetadata(Long id) {
        spotifyMetadataRepository.deleteById(id);
    }
}
