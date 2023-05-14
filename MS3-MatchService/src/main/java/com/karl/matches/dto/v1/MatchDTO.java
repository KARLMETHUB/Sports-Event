//package com.karl.matches.dto.v1;
//
//import com.karl.matches.entity.v1.Field;
//import com.karl.matches.entity.v1.Team;
//import com.karl.matches.entity.v1.Ticket;
//import com.karl.matches.entity.v1.Tournament;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDateTime;
//import java.util.Collection;
//import java.util.List;
//import java.util.Set;
//
//@Deprecated
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class MatchDTO {
//
//    /*TODO: Convert all fields to DTO*/
//    private Integer matchId;
//    private Field field;
//    private Tournament tournament;
//    private String participantsId;
//    private LocalDateTime dateTime;
//    private Set<Ticket> tickets;
//    private Collection<Team> teams;
//
//    /*TODO: Remove this*/
//    private List<TeamDTO> objectList;
//
//}