package com.innowise.web.user;

import com.innowise.crud_service.CrudService;
import com.innowise.dto.spotify_metadata.TrackMetadataDTO;
import com.innowise.model.spotify_metadata.TrackMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/track-metadata")
@RequiredArgsConstructor
public class TrackMetadataController {

    private final CrudService<TrackMetadata, TrackMetadataDTO, String> trackMetadataCrudService;

    @GetMapping(path = "/{id}")
    public TrackMetadataDTO getTrackMetadata(@PathVariable String id) {
        return trackMetadataCrudService.getEntity(id);
    }

    @GetMapping
    public List<TrackMetadataDTO> getAllTrackMetadata() {
        return trackMetadataCrudService.getEntities();
    }
}
