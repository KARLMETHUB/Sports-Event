package com.karl.matches.clients;

import com.karl.matches.dto.FieldDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "fields/api/v1/fields")
public interface FieldClient {

    @GetMapping("{id}")
    FieldDTO getField(@PathVariable("id") int id);
}
