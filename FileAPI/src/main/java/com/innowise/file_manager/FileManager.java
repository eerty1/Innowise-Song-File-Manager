package com.innowise.file_manager;

import com.innowise.model.song.SongFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileManager {

    SongFile saveFile(MultipartFile songFile, SongFile song) throws IOException;
    void deleteFile(SongFile songFile);
}
