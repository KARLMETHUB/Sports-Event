//package com.karl.matches.entity.v1;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//
//import javax.persistence.*;
//
//@Deprecated
//@Entity
//@Table(name = "mt_field")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//public class Field {
//
//    @Id
//    @Column(name = "field_id",nullable = false)
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer fieldId;
//
//    @Column(name = "field_name",nullable = false)
//    private String name;
//    @Column(name = "field_address",nullable = false)
//    private String address;
//    @Column(name = "field_capacity",nullable = false)
//    private String capacity;
//
//    @JsonIgnore
//    /*@JsonManagedReference*/
//    @OneToOne(fetch = FetchType.LAZY, mappedBy = "field")
//    private Match match;
//
//    public Integer getFieldId() {
//        return fieldId;
//    }
//
//    public void setFieldId(Integer fieldId) {
//        this.fieldId = fieldId;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public String getCapacity() {
//        return capacity;
//    }
//
//    public void setCapacity(String capacity) {
//        this.capacity = capacity;
//    }
//
//    public Match getMatch() {
//        return match;
//    }
//
//    public void setMatch(Match match) {
//        this.match = match;
//    }
//}
