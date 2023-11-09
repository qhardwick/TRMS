package com.infy.services;

import com.infy.dtos.FormDto;
import com.infy.repositories.FormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class FormServiceImpl implements FormService {

    private FormRepository formRepository;

    @Autowired
    public FormServiceImpl(FormRepository formRepository) {
        this.formRepository = formRepository;
    }

    // Add new Form:
    @Override
    public Mono<FormDto> addForm(FormDto newFormDto) {
        return formRepository.save(newFormDto.getForm()).map(form -> new FormDto(form));
    }

    // Get Form by ID:
    @Override
    public Mono<FormDto> getFormById(UUID id) {
        return formRepository.findById(id).map(form -> new FormDto(form));
    }

    // Update Form by ID:
    @Override
    public Mono<FormDto> updateFormById(UUID uuid, FormDto updatedFormDto) {
        return formRepository.findById(uuid)
                .flatMap(form -> {
                    if (updatedFormDto.getEventType() != null) {
                        form.setEventType(updatedFormDto.getEventType());
                    }
                    if (updatedFormDto.getGradeFormat() != null) {
                        form.setGradeFormat(updatedFormDto.getGradeFormat());
                    }
                    if (updatedFormDto.getDescription() != null) {
                        form.setDescription(updatedFormDto.getDescription());
                    }
                    if (updatedFormDto.getEventDate() != null) {
                        form.setEventDate(updatedFormDto.getEventDate());
                        form.setUrgent(updatedFormDto.isUrgent());
                    }
                    if (updatedFormDto.getEventTime() != null) {
                        form.setEventTime(updatedFormDto.getEventTime());
                    }
                    if (updatedFormDto.getLocation() != null) {
                        form.setLocation(updatedFormDto.getLocation());
                    }
                    if (updatedFormDto.getJustification() != null) {
                        form.setJustification(updatedFormDto.getJustification());
                    }
                    if (updatedFormDto.getCost() > 0) {
                        form.setCost(updatedFormDto.getCost());
                    }
                    // May need to implement a method specifically for updating days missed later, since 0 is a valid value:
                    if (updatedFormDto.getDaysMissed() > 0) {
                        form.setDaysMissed(updatedFormDto.getDaysMissed());
                    }
                    if(updatedFormDto.getAttachment() != null) {
                        form.setAttachment(updatedFormDto.getAttachment());
                    }
                    if(updatedFormDto.getSupervisorApproval() != null) {
                        form.setSupervisorApproval(updatedFormDto.getSupervisorApproval());
                    }
                    if(updatedFormDto.getDepartmentHeadApproval() != null) {
                        form.setDepartmentHeadApproval(updatedFormDto.getDepartmentHeadApproval());
                    }

                    return formRepository.save(form);
                }).map(form -> new FormDto(form));
    }

    @Override
    public Mono<Void> deleteFormById(UUID uuid) {
        return formRepository.deleteById(uuid);
    }
}
