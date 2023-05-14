package com.karl.ms6fieldservice.service;

import com.karl.ms6fieldservice.dto.FieldDTO;
import com.karl.ms6fieldservice.entity.Field;
import com.karl.ms6fieldservice.exception.FieldCreateException;
import com.karl.ms6fieldservice.exception.FieldUpdateException;
import com.karl.ms6fieldservice.exception.ResourceNotFoundException;
import com.karl.ms6fieldservice.repository.FieldRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.karl.ms6fieldservice.constants.ExceptionMessage.*;
import static com.karl.ms6fieldservice.constants.ResponseMessage.*;
import static com.karl.ms6fieldservice.utils.FieldDtoConverter.toEntity;

@Service
public class FieldService {

    private final FieldRepository fieldRepository;

    public FieldService(FieldRepository fieldRepository) {
        this.fieldRepository = fieldRepository;
    }

    public List<Field> getAllFields() {
        return fieldRepository.findAll();
    }

    public Field getField(int fieldId) throws ResourceNotFoundException {
        Optional<Field> field = fieldRepository.findById(fieldId);

        return field.orElseThrow(() -> new ResourceNotFoundException(String.format(RESOURCE_NOT_FOUND,fieldId)));
    }

    public Field createField(FieldDTO fieldDTO) throws FieldCreateException {

        if (fieldDTO.getFieldId() != null)
            throw new FieldCreateException(ID_SHOULD_BE_NULL_ON_CREATE);

        return fieldRepository.save(toEntity(fieldDTO));
    }

    public Field updateField(FieldDTO fieldDTO) throws FieldUpdateException, ResourceNotFoundException {

        if (fieldDTO.getFieldId() == null)
            throw new FieldUpdateException(ID_REQUIRED);

        if (!fieldRepository.existsById(fieldDTO.getFieldId()))
            throw new ResourceNotFoundException(String.format(RESOURCE_NOT_FOUND,fieldDTO.getFieldId()));

        return fieldRepository.save(toEntity(fieldDTO));
    }

    public String deleteTeam(int fieldId) throws ResourceNotFoundException {
        if (fieldId < 1)
            throw new ResourceNotFoundException(ID_PARAM_LESS_THAN_ZERO);

        if(!fieldRepository.existsById(fieldId))
            throw new ResourceNotFoundException(
                    String.format(RESOURCE_NOT_FOUND,fieldId));

        fieldRepository.deleteById(fieldId);

        return String.format(fieldRepository.existsById(fieldId) ?
                DELETE_MESSAGE_FAILED : DELETE_MESSAGE_SUCCESS, fieldId);
    }
}
