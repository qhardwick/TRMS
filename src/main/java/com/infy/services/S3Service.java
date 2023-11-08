package com.infy.services;

import reactor.core.publisher.Mono;

import java.io.InputStream;

public interface S3Service {

    // Upload file to S3 bucket:
    Mono<Void> uploadFile(String key, byte[] file);

    // Download file from S3 bucket:
    InputStream downloadFile(String key);
}
