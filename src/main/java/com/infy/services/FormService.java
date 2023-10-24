package com.infy.services;

import com.infy.dto.FormDto;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

public interface FormService {

    // Add new Form:
    Mono<FormDto> addForm(FormDto formDto);

    // Get Form by ID:
    Mono<FormDto> getFormById(UUID id);

    // Update Form by ID:
    Mono<FormDto> updateFormById(UUID uuid, FormDto updatedFormDto);

    // Delete Form by ID:
    Mono<Void> deleteFormById(UUID uuid);
}
