package com.karl.matches.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "mt_field")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Field implements Serializable {

    @Id
    @Column(name = "field_id",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fieldId;

    @Column(name = "field_name",nullable = false)
    private String name;
    @Column(name = "field_address",nullable = false)
    private String address;
    @Column(name = "field_capacity",nullable = false)
    private String capacity;

    @JsonIgnore
    /*@JsonManagedReference*/
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "field")
    private Match match;
    public Field() {}

    public Integer getFieldId() {
        return fieldId;
    }

    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }
}
