package com.karl.ms6fieldservice.utils;

import com.karl.ms6fieldservice.dto.FieldDTO;
import com.karl.ms6fieldservice.entity.Field;

public class FieldDtoConverter {

    private FieldDtoConverter() {}

    public static Field toEntity(FieldDTO f) {
        return Field.builder()
                .fieldId(f.getFieldId())
                .address(f.getAddress())
                .name(f.getName())
                .capacity(f.getCapacity())
                .build();
    }

    public static FieldDTO toDto(Field f) {
        return FieldDTO.builder()
                .fieldId(f.getFieldId())
                .address(f.getAddress())
                .name(f.getName())
                .capacity(f.getCapacity())
                .build();
    }
}
