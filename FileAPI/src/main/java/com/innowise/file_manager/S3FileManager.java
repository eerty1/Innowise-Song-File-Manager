package com.innowise.file_manager;

import com.innowise.model.song.SongFile;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.io.InputStream;

import static com.innowise.model.song.StorageType.S3;

@Service("s3FileManager")
public class S3FileManager implements FileManager {

    private final S3Client s3Client;
    private final String bucketName;
    @Autowired
    public S3FileManager(S3Client s3Client,
                         @Value("${aws.bucket-name}") String bucketName)
    {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
        createBucket();
    }
    @Override
    public SongFile saveFile(MultipartFile songFile, SongFile song) throws IOException {
        String objectKey = RandomStringUtils.randomAlphanumeric(50);
        try (InputStream inputStream = songFile.getInputStream()) {
            s3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(objectKey)
                            .contentLength(songFile.getSize())
                            .contentType(songFile.getContentType())
                            .build(),
                    RequestBody.fromInputStream(inputStream, songFile.getSize())

            );
            song.setStoragePath(getObjectURL(objectKey));
            song.setStorageType(S3);
            return song;
        }
    }

    @Override
    public void deleteFile(SongFile songFile) {
        s3Client.deleteObject(
                DeleteObjectRequest.builder()
                        .bucket(bucketName)
                        .key(getObjectKey(songFile.getStoragePath()))
                        .build()
        );
    }

    private void createBucket() {
        try {
            s3Client.headBucket(HeadBucketRequest.builder()
                    .bucket(bucketName)
                    .build()
            );

        } catch (NoSuchBucketException noSuchBucketException) {
            s3Client.createBucket(CreateBucketRequest.builder()
                    .bucket(bucketName)
                    .build()
            );
        }
    }

    private String getObjectURL(String objectKey) {
        return s3Client.utilities()
                .getUrl(GetUrlRequest.builder()
                        .bucket(bucketName)
                        .key(objectKey)
                        .build())
                .toExternalForm();
    }

    public String getObjectKey(String storagePath) {
        String[] splitStoragePath = storagePath.split("/");
        return splitStoragePath[splitStoragePath.length - 1];
    }
}
