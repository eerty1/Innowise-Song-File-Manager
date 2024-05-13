package com.innowise.web.admin;

import com.innowise.crud_service.CrudService;
import com.innowise.dto.song_file.SongFileDTO;
import com.innowise.model.song.SongFile;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/song-file")
@RequiredArgsConstructor
public class SongFileAdminController {

    private final CrudService<SongFile, SongFileDTO, Long> songFileCrudService;

    @PatchMapping(path = "/{id}")
    public void updateSongFile(@PathVariable Long id, @RequestBody SongFileDTO songFileDTO) {
        songFileCrudService.updateEntity(id, songFileDTO);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteSong(@PathVariable Long id) {
        songFileCrudService.deleteEntity(id);
    }
}
