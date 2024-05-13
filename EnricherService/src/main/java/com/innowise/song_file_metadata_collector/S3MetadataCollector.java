package com.innowise.song_file_metadata_collector;

import com.innowise.model.song_file.SongFile;
import com.innowise.model.SongFileMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Utilities;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.net.URI;
import java.util.Date;

@Component("s3MetadataCollector")
public class S3MetadataCollector implements SongFileMetadataCollector {
    private final S3Client s3Client;
    private final S3Utilities s3Utilities;

    @Autowired
    public S3MetadataCollector(S3Client s3Client) {
        this.s3Client = s3Client;
        s3Utilities = s3Client.utilities();
    }

    @Override
    public SongFileMetadata obtainMetadata(SongFile songFile) {
        GetObjectResponse s3SongObject = getSongFileFromS3(songFile);
        return new SongFileMetadata(
                songFile.getArtist(),
                songFile.getSongName(),
                s3SongObject.contentLength(),
                s3SongObject.contentType(),
                Date.from(s3SongObject.lastModified())
        );
    }

    private GetObjectResponse getSongFileFromS3(SongFile songFile) {
        URI s3StoragePath = URI.create(songFile.getStoragePath());
        return s3Client.getObject(GetObjectRequest.builder()
                .key(s3Utilities.parseUri(s3StoragePath).key().orElseThrow())
                .bucket(s3Utilities.parseUri(s3StoragePath).bucket().orElseThrow())
                .build()
        ).response();
    }
}
