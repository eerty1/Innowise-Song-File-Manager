package com.innowise.s3_storage;

import com.innowise.model.SongFile;
import com.innowise.repository.SongFileRepositoryService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.innowise.model.StorageType.LOCAL;
import static com.innowise.model.StorageType.S3;

@Service
public class S3StorageService {
    private final S3Client s3Client;
    private final String bucketName;
    private final SongFileRepositoryService songFileRepositoryService;
    private final String pathToLocalStorage;

    @Autowired
    public S3StorageService(S3Client s3Client,
                            SongFileRepositoryService songFileRepositoryService,
                            @Value("${aws.bucket-name}") String bucketName,
                            @Value("${aws.local-storage-path}") String pathToLocalStorage)
    {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
        this.songFileRepositoryService = songFileRepositoryService;
        this.pathToLocalStorage = pathToLocalStorage;
        createBucket();
    }

    public SongFile saveFileS3(MultipartFile songFile) throws IOException {
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
            return songFileRepositoryService.addSongToDatabase(songFile, getObjectURL(objectKey), S3);
        } catch (SdkClientException sdkClientException) {
            return saveFileLocally(songFile);
        }
    }

    private SongFile saveFileLocally(MultipartFile songFile) throws IOException {
        File songFilePath = new File(pathToLocalStorage + songFile.getOriginalFilename());
        try (FileOutputStream fileOutputStream = new FileOutputStream(songFilePath)) {
            fileOutputStream.write(songFile.getBytes());
            return songFileRepositoryService.addSongToDatabase(songFile, songFilePath.getAbsolutePath(), LOCAL);
        }
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
}
