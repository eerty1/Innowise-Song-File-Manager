package com.innowise.web.user;

import com.innowise.crud_service.SongFileCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequiredArgsConstructor
public class SongFileUploadController {
    private final SongFileCrudService songFileCrudService;

    @PostMapping(path = "/file")
    public ResponseEntity<String> handleFileUpload(@RequestParam("song-file") MultipartFile songFile) throws IOException {
        return songFileCrudService.persistSongFile(songFile);
    }
}
