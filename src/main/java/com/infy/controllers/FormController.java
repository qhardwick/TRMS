package com.infy.controllers;

import com.infy.dto.FormDto;
import com.infy.services.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("trms/forms")
public class FormController {

    private FormService formService;

    @Autowired
    public FormController(FormService formService) {
        this.formService = formService;
    }

    // Add new Form:
    @PostMapping
    public Mono<ResponseEntity<FormDto>> addForm(@Valid @RequestBody FormDto newFormDto) {
        return formService.addForm(newFormDto)
                .map(form -> ResponseEntity.ok(form))
                .switchIfEmpty(Mono.just(ResponseEntity.badRequest().build()));
    }

    // Get Form by ID:
    @GetMapping("/{id}")
    public Mono<ResponseEntity<FormDto>> getFormById(@PathVariable("id") String id) {
        return formService.getFormById(UUID.fromString(id))
                .map(form -> ResponseEntity.ok(form))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    // Update Form by ID:
    @PutMapping("/{id}")
    public Mono<ResponseEntity<FormDto>> updateFormById(@PathVariable("id") String id, @Valid @RequestBody FormDto updatedFormDto) {
        return formService.updateFormById(UUID.fromString(id), updatedFormDto)
                .map(form -> ResponseEntity.ok(form))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    // Delete Form by ID:
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteFormById(@PathVariable("id") String id) {
        return formService.deleteFormById(UUID.fromString(id))
                .map(form -> ResponseEntity.ok(form))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }
}
