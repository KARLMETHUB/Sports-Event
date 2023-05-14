//package com.karl.matches.entity.v1;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//
//import javax.persistence.*;
//
//@Deprecated
//@Entity
//@Table(name = "mt_tournament")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//public class Tournament {
//
//    @Id
//    @Column(name = "tournament_id",nullable = false)
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer tournamentId;
//    @Column(name = "tournament_name",nullable = false)
//    private String tournamentName;
//    @Column(name = "sports_category",nullable = false)
//    private String sportsCategory;
//    @Column(name = "tournament_style",nullable = false)
//    private String tournamentStyle;
//
//    @JsonIgnore
//    /*@JsonManagedReference*/
//    @OneToOne(fetch = FetchType.LAZY, mappedBy = "field")
//    private Match match;
//
//    public Tournament() {
//    }
//
//    public Integer getTournamentId() {
//        return tournamentId;
//    }
//
//    public void setTournamentId(Integer tournamentId) {
//        this.tournamentId = tournamentId;
//    }
//
//    public String getTournamentName() {
//        return tournamentName;
//    }
//
//    public void setTournamentName(String tournamentName) {
//        this.tournamentName = tournamentName;
//    }
//
//    public String getSportsCategory() {
//        return sportsCategory;
//    }
//
//    public void setSportsCategory(String sportsCategory) {
//        this.sportsCategory = sportsCategory;
//    }
//
//    public String getTournamentStyle() {
//        return tournamentStyle;
//    }
//
//    public void setTournamentStyle(String tournamentStyle) {
//        this.tournamentStyle = tournamentStyle;
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
