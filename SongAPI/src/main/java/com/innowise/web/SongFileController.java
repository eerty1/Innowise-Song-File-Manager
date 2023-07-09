package com.innowise.web;

import com.innowise.dto.SongFileDTO;
import com.innowise.repository.crud_service.SongFileCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/song-pool")
public class SongFileController {
    private final SongFileCrudService songFileCrudService;

    @Autowired
    public SongFileController(SongFileCrudService songFileCrudService) {
        this.songFileCrudService = songFileCrudService;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public Iterable<SongFileDTO> getSongFiles() {
        return songFileCrudService.getSongFiles();
    }

    @PatchMapping(path = "/update/{id}", consumes = APPLICATION_JSON_VALUE)
    public SongFileDTO updateSongFile(@PathVariable Long id, @RequestBody SongFileDTO songFileDTO) {
        return songFileCrudService.updateSongFile(id, songFileDTO);
    }

    @DeleteMapping(path = "/delete/{id}")
    public void deleteSongFile(@PathVariable Long id) {
        songFileCrudService.deleteSongFile(id);
    }
}
