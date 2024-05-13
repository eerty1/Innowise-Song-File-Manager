package com.innowise.web.user;

import com.innowise.crud_service.CrudService;
import com.innowise.dto.SongFileMetadataDTO;
import com.innowise.model.SongFileMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(path = "/song-file-metadata")
@RequiredArgsConstructor
public class SongFileMetadataController {

    private final CrudService<SongFileMetadata, SongFileMetadataDTO, String> songFileMetadataCrudService;

    @GetMapping(path = "/{id}")
    public SongFileMetadataDTO getSongFileMetadata(@PathVariable String id) {
        return songFileMetadataCrudService.getEntity(id);
    }

    @GetMapping
    public List<SongFileMetadataDTO> getAllSongFileMetadata() {
        return songFileMetadataCrudService.getEntities();
    }
}
