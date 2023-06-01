package com.karl.matches.clients;

import com.karl.matches.dto.TeamDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@FeignClient(value = "TEAMS/api/v1/teams")
public interface TeamClient {
    @GetMapping("/")
    List<TeamDTO> getAllTeams();

    @GetMapping("/{id}")
    TeamDTO getTeam(@PathVariable("id") int id);

}
