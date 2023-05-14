package com.karl.matches.clients;

import com.karl.matches.dto.v2.TeamDTO;
import com.karl.matches.dto.v2.TournamentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "tournaments/api/v1/tournaments")
public interface TournamentClient {

    @GetMapping("{id}")
    TournamentDTO getTournament(@PathVariable("id") int id);

}
