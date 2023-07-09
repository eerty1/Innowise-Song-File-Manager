package com.innowise.web;

import com.innowise.camel_producer.SongIdProducer;
import com.innowise.s3_storage.S3StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class SongFileUploadController {
    private final S3StorageService s3StorageService;
    private final SongIdProducer songIdProducer;
    private final String songUploadMessage;

    @Autowired
    public SongFileUploadController(S3StorageService s3StorageService,
                                    SongIdProducer songIdProducer,
                                    @Value("${song-upload-message}") String songUploadMessage)
    {
        this.s3StorageService = s3StorageService;
        this.songIdProducer = songIdProducer;
        this.songUploadMessage = songUploadMessage;
    }

    @PostMapping(path = "/upload-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> handleFileUpload(@RequestParam("song-file") MultipartFile songFile) throws IOException {
        songIdProducer.createMessageBody(s3StorageService.saveFileS3(songFile));
        return new ResponseEntity<>(songUploadMessage, HttpStatus.CREATED);
    }
}
