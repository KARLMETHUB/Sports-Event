package com.karl.matches.model.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "mt_teams")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Team {

    @Id
    @Column(name = "team_id",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer teamId;

    @Column(name = "team_name",nullable = false)
    private String teamName;

    @OneToMany(mappedBy="team")
    @JsonManagedReference
    /* Avoids circular reference Infinite recursion */
    private Set<Player> playerList;

    /*@JsonBackReference*/
    @JsonIgnore
    /*Created mapping Table for teams and matches*/
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "mt_team_matches",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "match_id"))
    private Collection<Match> matches = new ArrayList<>();

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Set<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(Set<Player> playerList) {
        this.playerList = playerList;
    }

    @Override
    public String toString() {
        return "Team{" +
                "teamId=" + teamId +
                ", teamName='" + teamName + '\'' +
                ", playerList=" + playerList +
                '}';
    }

    public Collection<Match> getMatches() {
        return matches;
    }

    public void setMatches(Collection<Match> matches) {
        this.matches = matches;
    }
}
