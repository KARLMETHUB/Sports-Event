package com.karl.ms6fieldservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "mt_field")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Field {

    @Id
    @Column(name = "field_id",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fieldId;

    @Column(name = "field_name",nullable = false)
    private String name;
    @Column(name = "field_address",nullable = false)
    private String address;
    @Column(name = "field_capacity",nullable = false)
    private Integer capacity;

}
