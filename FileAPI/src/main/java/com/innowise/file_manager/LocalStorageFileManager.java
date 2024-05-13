package com.innowise.file_manager;

import com.innowise.model.song.SongFile;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.innowise.model.song.StorageType.LOCAL;

@Component("localStorageFileManager")
@RequiredArgsConstructor
public class LocalStorageFileManager implements FileManager {
    @Value("${aws.local-storage-path}")
    private String pathToLocalStorage;

    @Override
    public SongFile saveFile(MultipartFile songFile, SongFile song) throws IOException {
        File songFilePath = new File(pathToLocalStorage + songFile.getOriginalFilename());
        try (FileOutputStream fileOutputStream = new FileOutputStream(songFilePath)) {
            fileOutputStream.write(songFile.getBytes());
            song.setStoragePath(songFilePath.getAbsolutePath());
            song.setStorageType(LOCAL);
            return song;
        }
    }

    @Override
    public void deleteFile(SongFile songFile) {
        new File(songFile.getStoragePath()).delete();
    }
}
