package com.innowise.web.user;

import com.innowise.crud_service.CrudService;
import com.innowise.dto.song_file.SongFileDTO;
import com.innowise.model.song.SongFile;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/song-file")
@RequiredArgsConstructor
public class SongFileController {

    private final CrudService<SongFile, SongFileDTO, Long> songFileCrudService;

    @GetMapping(path = "/{id}")
    public SongFileDTO getSongFile(@PathVariable Long id) {
        return songFileCrudService.getEntity(id);
    }

    @GetMapping
    public List<SongFileDTO> getAllSongFiles() {
        return songFileCrudService.getEntities();
    }
}

