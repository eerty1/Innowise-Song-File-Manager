package com.innowise.aws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.sqs.SqsClient;

import java.net.URI;

@Configuration
public class AwsConfiguration {

    @Value("${aws.endpoint-url}")
    private String awsEndpointURL;

    @Value("${aws.accessKeyId}")
    private String accessKey;

    @Value("${aws.secretAccessKey}")
    private String secretKey;

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.EU_NORTH_1)
                .credentialsProvider(provideAwsCredentials())
                .endpointOverride(URI.create(awsEndpointURL))
                .build();
    }

    @Bean("sqsClient")
    public SqsClient sqsClient() {
        return SqsClient.builder()
                .region(Region.EU_NORTH_1)
                .credentialsProvider(provideAwsCredentials())
                .endpointOverride(URI.create(awsEndpointURL))
                .build();
    }

    private StaticCredentialsProvider provideAwsCredentials() {
        return StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey));
    }
}
