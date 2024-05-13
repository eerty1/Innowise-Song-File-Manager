package com.innowise.web.admin;

import com.innowise.crud_service.CrudService;
import com.innowise.dto.spotify_metadata.TrackMetadataDTO;
import com.innowise.model.spotify_metadata.TrackMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin/track-metadata")
@RequiredArgsConstructor
public class TrackMetadataAdminController {

    private final CrudService<TrackMetadata, TrackMetadataDTO, String> trackMetadataCrudService;

    @PatchMapping(path = "/{id}")
    public void updateTrackMetadata(@PathVariable String id, @RequestBody TrackMetadataDTO trackMetadataDTO) {
        trackMetadataCrudService.updateEntity(id, trackMetadataDTO);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteTrackMetadata(@PathVariable String id) {
        trackMetadataCrudService.deleteEntity(id);
    }
}
