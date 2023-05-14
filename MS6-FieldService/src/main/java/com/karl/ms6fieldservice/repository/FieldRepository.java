package com.karl.ms6fieldservice.repository;

import com.karl.ms6fieldservice.entity.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FieldRepository extends JpaRepository<Field,Integer> {

    Optional<Field> findById(Integer fieldId);
}
