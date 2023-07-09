package com.innowise.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.innowise.model.s3_metadata.S3Metadata;
import com.innowise.model.spotify_metadata.SpotifyMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class MetadataPersistenceService {
    private final S3MetadataRepository s3MetadataRepository;
    private final SpotifyMetadataRepository spotifyMetadataRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public MetadataPersistenceService(S3MetadataRepository s3MetadataRepository, SpotifyMetadataRepository spotifyMetadataRepository) {
        this.s3MetadataRepository = s3MetadataRepository;
        this.spotifyMetadataRepository = spotifyMetadataRepository;
    }

    public void saveMetadata(String enricherServiceMessage) throws IOException {
        s3MetadataRepository.save(objectMapper.readValue(deserializeJsonMap(enricherServiceMessage).get("S3Metadata"), S3Metadata.class));
        spotifyMetadataRepository.save(objectMapper.readValue(deserializeJsonMap(enricherServiceMessage).get("SpotifyMetadata"), SpotifyMetadata.class));
    }

    private Map<String, String> deserializeJsonMap(String enricherServiceMessage) throws JsonProcessingException {
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        MapType mapType = typeFactory.constructMapType(HashMap.class, String.class, String.class);
        return objectMapper.readValue(enricherServiceMessage, mapType);
    }
}