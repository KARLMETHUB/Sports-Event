package com.karl.matches.dto.v2;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FieldDTO {

    private Integer fieldId;
    private String name;
    private String address;
    private Integer capacity;
}

