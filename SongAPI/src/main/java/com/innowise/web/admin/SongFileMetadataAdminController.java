package com.innowise.web.admin;

import com.innowise.crud_service.CrudService;
import com.innowise.dto.SongFileMetadataDTO;
import com.innowise.model.SongFileMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/song-file-metadata")
@RequiredArgsConstructor
public class SongFileMetadataAdminController {

    private final CrudService<SongFileMetadata, SongFileMetadataDTO, String> songFileMetadataCrudService;

    @PatchMapping(path = "/{id}")
    public void updateSongFileMetadata(@PathVariable String id, @RequestBody SongFileMetadataDTO songFileMetadataDTO) {
        songFileMetadataCrudService.updateEntity(id, songFileMetadataDTO);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteSongFileMetadata(@PathVariable String id) {
        songFileMetadataCrudService.deleteEntity(id);
    }
}
