package com.innowise.storage_service;

import com.innowise.file_manager.FileManager;
import com.innowise.model.song.SongFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.exception.SdkClientException;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class StorageService {
    private final FileManager s3FileManager;
    private final FileManager localStorageFileManager;

    public SongFile saveFile(MultipartFile songFile, SongFile song) throws IOException {
        try {
            return s3FileManager.saveFile(songFile, song);
        } catch (SdkClientException sdkClientException) {
            return localStorageFileManager.saveFile(songFile, song);
        }
    }

    public void deleteFile(SongFile songFile) {
        switch (songFile.getStorageType()) {
            case S3:
                s3FileManager.deleteFile(songFile);
                break;
            case LOCAL:
                localStorageFileManager.deleteFile(songFile);
                break;
        }
    }
}
