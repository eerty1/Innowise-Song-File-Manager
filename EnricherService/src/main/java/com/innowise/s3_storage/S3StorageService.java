package com.innowise.s3_storage;

import com.innowise.model.song_file.SongFile;
import com.innowise.model.s3_metadata.S3Metadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Utilities;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import static com.innowise.model.song_file.StorageType.S3;

@Service
public class S3StorageService {
    private final S3Client s3Client;
    private final S3Utilities s3Utilities;

    @Autowired
    public S3StorageService(S3Client s3Client) {
        this.s3Client = s3Client;
        s3Utilities = s3Client.utilities();
    }

    public S3Metadata obtainSongMetadata(SongFile songFile) throws IOException {
        if (songFile.getStorageType() == S3) {
            GetObjectResponse s3SongObject = getSongFileFromS3(songFile);
            return new S3Metadata(
                    s3SongObject.contentLength(),
                    s3SongObject.contentType(),
                    Date.from(s3SongObject.lastModified())
            );
        } else {
            File localStorageSongObject = new File(songFile.getStoragePath());
            return new S3Metadata(
                    localStorageSongObject.length(),
                    getLocalStorageFileContentType(songFile.getStoragePath()),
                    new Date(localStorageSongObject.lastModified())
            );
        }
    }

    private GetObjectResponse getSongFileFromS3(SongFile songFile) {
        URI s3StoragePath = URI.create(songFile.getStoragePath());
        return s3Client.getObject(GetObjectRequest.builder()
                .key(s3Utilities.parseUri(s3StoragePath).key().orElseThrow())
                .bucket(s3Utilities.parseUri(s3StoragePath).bucket().orElseThrow())
                .build()
        ).response();
    }

    private String getLocalStorageFileContentType(String storagePath) throws IOException {
        Path path = Paths.get(storagePath);
        return Files.probeContentType(path);
    }
}
