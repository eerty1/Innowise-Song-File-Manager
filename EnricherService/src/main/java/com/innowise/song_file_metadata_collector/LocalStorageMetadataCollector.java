package com.innowise.song_file_metadata_collector;

import com.innowise.model.SongFileMetadata;
import com.innowise.model.song_file.SongFile;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Component("localStorageMetadataCollector")
public class LocalStorageMetadataCollector implements SongFileMetadataCollector {
    @Override
    public SongFileMetadata obtainMetadata(SongFile songFile) throws IOException {
        File localStorageSongObject = new File(songFile.getStoragePath());
        return new SongFileMetadata(
                songFile.getArtist(),
                songFile.getSongName(),
                localStorageSongObject.length(),
                getLocalStorageFileContentType(songFile.getStoragePath()),
                new Date(localStorageSongObject.lastModified())
        );
    }

    private String getLocalStorageFileContentType(String storagePath) throws IOException {
        Path path = Paths.get(storagePath);
        return Files.probeContentType(path);
    }
}
