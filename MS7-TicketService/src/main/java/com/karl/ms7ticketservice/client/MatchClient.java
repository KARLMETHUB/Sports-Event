package com.karl.ms7ticketservice.client;

import com.karl.ms7ticketservice.dto.match.MatchDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "MATCHES/api/v1/matches")
public interface MatchClient {

    @GetMapping("/")
    List<MatchDTO> getMatches();
    @GetMapping("/{id}")
    MatchDTO getMatch(@PathVariable("id") int id);

}
