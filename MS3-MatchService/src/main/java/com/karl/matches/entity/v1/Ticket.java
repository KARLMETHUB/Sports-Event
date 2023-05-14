//package com.karl.matches.entity.v1;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//
//import javax.persistence.*;
//
//@Deprecated
//@Entity
//@Table(name = "mt_tickets")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//public class Ticket {
//
//    @Id
//    @Column(name = "ticket_id",nullable = false)
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer ticketId;
//
//    @Column(name = "customer_nane",nullable = false)
//    private String customerNane;
//
//    @Column(name = "ticket_price",nullable = false)
//    private Float price;
//
//    @JsonIgnore
//    /*@JsonBackReference*/
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="match_id", nullable=false)
//    private Match match;
//
//    public Ticket() {}
//
//    public Integer getTicketId() {
//        return ticketId;
//    }
//
//    public void setTicketId(Integer ticketId) {
//        this.ticketId = ticketId;
//    }
//
//    public String getCustomerNane() {
//        return customerNane;
//    }
//
//    public void setCustomerNane(String customerNane) {
//        this.customerNane = customerNane;
//    }
//
//    public Float getPrice() {
//        return price;
//    }
//
//    public void setPrice(Float price) {
//        this.price = price;
//    }
//
//    public Match getMatch() {
//        return match;
//    }
//
//    public void setMatch(Match match) {
//        this.match = match;
//    }
//
//
//}
