package com.karl.ms6fieldservice.controller;

import com.karl.ms6fieldservice.dto.FieldDTO;
import com.karl.ms6fieldservice.exception.FieldCreateException;
import com.karl.ms6fieldservice.exception.FieldUpdateException;
import com.karl.ms6fieldservice.exception.ResourceNotFoundException;
import com.karl.ms6fieldservice.service.FieldService;
import com.karl.ms6fieldservice.utils.FieldDtoConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.karl.ms6fieldservice.utils.FieldDtoConverter.toDto;

@RestController
@RequestMapping("/api/v1/fields")
public class FieldController {

    private final FieldService fieldService;

    public FieldController(FieldService fieldService) {
        this.fieldService = fieldService;
    }

    @GetMapping("/")
    public ResponseEntity<List<FieldDTO>> getAllFields() {

        return new ResponseEntity<>(
                fieldService
                        .getAllFields()
                        .stream()
                        .map(FieldDtoConverter::toDto)
                        .collect(Collectors.toList())
                , HttpStatus.OK);
    }

    @GetMapping("/{fieldId}")
    public ResponseEntity<FieldDTO> getField(@PathVariable("fieldId") int fieldId)
            throws ResourceNotFoundException {

        return new ResponseEntity<>(
                toDto(fieldService.getField(fieldId))
                , HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<FieldDTO> createField(@RequestBody FieldDTO fieldDTO)
            throws FieldCreateException {

        return new ResponseEntity<>(
                toDto(fieldService.createField(fieldDTO)),
                HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<FieldDTO> updateField(@RequestBody FieldDTO fieldDTO)
            throws FieldUpdateException, ResourceNotFoundException {

        return new ResponseEntity<>(
                toDto(fieldService.updateField(fieldDTO)),
                HttpStatus.OK);
    }

    @DeleteMapping("/delete/{fieldId}")
    public ResponseEntity<String> deleteFieldById(@PathVariable("fieldId") int fieldId)
            throws ResourceNotFoundException {

        return new ResponseEntity<>(
                fieldService.deleteTeam(fieldId),
                HttpStatus.OK);
    }

}
