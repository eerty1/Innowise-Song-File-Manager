package com.innowise.song_file_metadata_collector;

import com.innowise.model.SongFileMetadata;
import com.innowise.model.song_file.SongFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CollectorService {

    private final SongFileMetadataCollector s3MetadataCollector;
    private final SongFileMetadataCollector localStorageMetadataCollector;

    public SongFileMetadata obtainMetadata(SongFile songFile) throws IOException {
        switch (songFile.getStorageType()) {
            case S3:
                return s3MetadataCollector.obtainMetadata(songFile);
            case LOCAL:
                return localStorageMetadataCollector.obtainMetadata(songFile);
        }
        throw new NoSuchElementException();
    }
}