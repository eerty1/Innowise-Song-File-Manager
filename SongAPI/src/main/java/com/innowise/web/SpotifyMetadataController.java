package com.innowise.web;

import com.innowise.dto.SpotifyMetadataDTO;
import com.innowise.repository.crud_service.SpotifyMetadataCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class SpotifyMetadataController {
    private final SpotifyMetadataCrudService spotifyMetadataCrudService;

    @Autowired
    public SpotifyMetadataController(SpotifyMetadataCrudService spotifyMetadataCrudService) {
        this.spotifyMetadataCrudService = spotifyMetadataCrudService;
    }

    @GetMapping(path = "/get-spotifymetadata/{id}", produces = APPLICATION_JSON_VALUE)
    public SpotifyMetadataDTO getSpotifyMetadata(@PathVariable Long id) {
        return spotifyMetadataCrudService.getSpotifyMetadata(id);
    }

    @PatchMapping(path = "/update-spotifymetadata/{id}", consumes = APPLICATION_JSON_VALUE)
    public SpotifyMetadataDTO updateS3Metadata(@PathVariable Long id, @RequestBody SpotifyMetadataDTO spotifyMetadataDTO) {
        return spotifyMetadataCrudService.updateSpotifyMetadata(id, spotifyMetadataDTO);
    }

    @DeleteMapping(path = "/delete-spotifymetadata/{id}")
    public void deleteS3Metadata(@PathVariable Long id) {
        spotifyMetadataCrudService.deleteSpotifyMetadata(id);
    }
}
