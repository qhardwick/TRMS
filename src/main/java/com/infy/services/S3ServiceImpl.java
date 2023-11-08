package com.infy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.InputStream;

@Service
public class S3ServiceImpl implements S3Service {

    private final S3AsyncClient s3;
    private final String bucketName;

    @Autowired
    public S3ServiceImpl(S3AsyncClient s3, @Value("${BUCKET_NAME}") String bucketName) {
        this.s3 = s3;
        this.bucketName = bucketName;
    }

    // Upload file to S3 bucket:
    @Override
    public Mono<Void> uploadFile(String key, byte[] file) {
        s3.putObject(PutObjectRequest.builder().bucket(bucketName).key(key).build(), AsyncRequestBody.fromBytes(file));
        return Mono.empty();
    }

    // Download file from S3 bucket:
    @Override
    public InputStream downloadFile(String key) {
        //return s3.getObject(GetObjectRequest.builder().bucket(bucketName).key(key).build()).asInputStream();
        return null;
    }
}