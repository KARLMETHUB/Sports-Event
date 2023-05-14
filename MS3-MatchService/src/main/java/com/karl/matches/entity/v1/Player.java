//package com.karl.matches.entity.v1;
//
//import com.fasterxml.jackson.annotation.JsonBackReference;
//
//import javax.persistence.*;
//
//@Deprecated
//@Entity
//@Table(name = "mt_players")
//public class Player {
//
//    @Id
//    @Column(name = "player_id",nullable = false)
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer playerId;
//
//    @Column(name = "first_name",nullable = false)
//    private String firstName;
//
//    @Column(name = "last_name",nullable = false)
//    private String lastName;
//
//    @Column(name = "country",nullable = false)
//    private String country;
//
//    @ManyToOne
//    @JoinColumn(name="team_id", nullable=false)
//    /* Avoids circular reference Infinite recursion */
//    @JsonBackReference
//    private Team team;
//
//    public Integer getPlayerId() {
//        return playerId;
//    }
//
//    public void setPlayerId(Integer playerId) {
//        this.playerId = playerId;
//    }
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public String getCountry() {
//        return country;
//    }
//
//    public void setCountry(String country) {
//        this.country = country;
//    }
//
//    public Team getTeam() {
//        return team;
//    }
//
//    public void setTeam(Team team) {
//        this.team = team;
//    }
//
//    @Override
//    public String toString() {
//        return "Player{" +
//                "playerId=" + playerId +
//                ", firstName='" + firstName + '\'' +
//                ", lastName='" + lastName + '\'' +
//                ", country='" + country + '\'' +
//                ", team=" + team +
//                '}';
//    }
//
//
//}
