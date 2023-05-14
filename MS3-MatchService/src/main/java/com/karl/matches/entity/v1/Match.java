//package com.karl.matches.entity.v1;
//
//import javax.persistence.*;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Set;
//
//
//@Deprecated
//@Entity
///*MATCH IS A RESERVED WORD IN SQL!!!
//@Table(name = "match")*/
//@Table(name = "mt_matches")
//public class Match {
//
//    @Id
//    @Column(name = "match_id",nullable = false)
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer matchId;
//
//    /*TODO
//    Read this:
//    https://medium.com/@nutanbhogendrasharma/creating-a-restful-web-service-with-spring-boot-part-5-9e502affcd0a*/
//
//    /* Removed because replaced with DTO
//    @JsonBackReference*/
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="field_id", nullable=false)
//    private Field field;
//
//    /* Removed because replaced with DTO
//    @JsonBackReference*/
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="tournament_id", nullable=false)
//    private Tournament tournament;
//
//    @Column(name = "participants_id")
//    private String participantsId;
//
//    @Column(name = "date_time")
//    private LocalDateTime dateTime;
//
//    /* Removed because replaced with DTO
//    @JsonManagedReference*/
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "match")
//    private Set<Ticket> tickets;
//
//    /* Removed because replaced with DTO
//    @JsonManagedReference*/
//    @ManyToMany(mappedBy = "matches",cascade = CascadeType.ALL)
//    private Collection<Team> teams = new ArrayList<>();
//
//    public Match() {}
//    public Match(Integer matchId, Field field, Tournament tournament, String participantsId, LocalDateTime dateTime, Set<Ticket> tickets, Collection<Team> teams) {
//        this.matchId = matchId;
//        this.field = field;
//        this.tournament = tournament;
//        this.participantsId = participantsId;
//        this.dateTime = dateTime;
//        this.tickets = tickets;
//        this.teams = teams;
//    }
//
//    public Integer getMatchId() {
//        return matchId;
//    }
//
//    public void setMatchId(Integer matchId) {
//        this.matchId = matchId;
//    }
//
//    public Field getField() {
//        return field;
//    }
//
//    public void setField(Field field) {
//        this.field = field;
//    }
//
//    public Tournament getTournament() {
//        return tournament;
//    }
//
//    public void setTournament(Tournament tournament) {
//        this.tournament = tournament;
//    }
//
//    public String getParticipantsId() {
//        return participantsId;
//    }
//
//    public void setParticipantsId(String participantsId) {
//        this.participantsId = participantsId;
//    }
//
//    public LocalDateTime getDateTime() {
//        return dateTime;
//    }
//
//    public void setDateTime(LocalDateTime dateTime) {
//        this.dateTime = dateTime;
//    }
//
//    public Set<Ticket> getTickets() {
//        return tickets;
//    }
//
//    public void setTickets(Set<Ticket> tickets) {
//        this.tickets = tickets;
//    }
//
//    public Collection<Team> getTeams() {
//        return teams;
//    }
//
//    public void setTeams(Collection<Team> teams) {
//        this.teams = teams;
//    }
//}
