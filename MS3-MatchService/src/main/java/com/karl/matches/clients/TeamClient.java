package com.karl.matches.clients;

import com.karl.matches.dto.v2.TeamDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


/*TODO: Add configuration*/
@FeignClient(value = "TEAMS/api/v1/teams")
//@FeignClient(value = "teams",url = "localhost:9091/api/v1/teams/")
public interface TeamClient {

    //@RequestMapping(method = RequestMethod.GET, value = "/")
    @GetMapping("/")
    List<TeamDTO> getAllTeams();

    @GetMapping("/{id}")
    TeamDTO getTeam(@PathVariable("id") int id);


}
