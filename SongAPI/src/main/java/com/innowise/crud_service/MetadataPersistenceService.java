package com.innowise.crud_service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.innowise.model.SongFileMetadata;
import com.innowise.model.spotify_metadata.Artist;
import com.innowise.model.spotify_metadata.TrackMetadata;
import com.innowise.repository.spotify_metadata.ArtistRepository;
import com.innowise.repository.spotify_metadata.TrackMetadataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MetadataPersistenceService {

    private final TrackMetadataRepository trackMetadataRepository;
    private final ArtistRepository artistRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public void saveMetadata(String enricherServiceMessage) throws IOException {
        Map<String, String> metadataMap = deserializeJsonMap(enricherServiceMessage);
        TrackMetadata trackMetadata = objectMapper.readValue(metadataMap.get("TrackMetadata"), TrackMetadata.class);
        SongFileMetadata songFileMetadata = objectMapper.readValue(metadataMap.get("SongFileMetadata"), SongFileMetadata.class);
        assignTrackMetadataToArtist(trackMetadata);
        assignTrackMetadataToSongFileMetadata(trackMetadata, songFileMetadata);
        trackMetadataRepository.save(trackMetadata);
    }

    private void assignTrackMetadataToArtist(TrackMetadata trackMetadata) {
        List<Artist> existingArtists = artistRepository.findAllByIdIn(
                trackMetadata.getArtists()
                        .stream()
                        .map(Artist::getId)
                        .collect(Collectors.toList())
        );

        existingArtists.forEach(existingArtist -> trackMetadata.getArtists()
                .stream()
                .filter(artist -> artist.equals(existingArtist))
                .findFirst()
                .orElseThrow()
                .setTrackMetadata(existingArtist.getTrackMetadata()));
        trackMetadata.getArtists().forEach(artist -> artist.addTrackMetadata(trackMetadata));
    }

    private void assignTrackMetadataToSongFileMetadata(TrackMetadata trackMetadata, SongFileMetadata songFileMetadata) {
        songFileMetadata.setTrackMetadata(trackMetadata);
        trackMetadata.setSongFileMetadata(songFileMetadata);
        songFileMetadata.setId(trackMetadata.getId());
    }

    private Map<String, String> deserializeJsonMap(String enricherServiceMessage) throws JsonProcessingException {
        return objectMapper.readValue(
                enricherServiceMessage,
                objectMapper.getTypeFactory().constructMapType(HashMap.class, String.class, String.class)
        );
    }
}