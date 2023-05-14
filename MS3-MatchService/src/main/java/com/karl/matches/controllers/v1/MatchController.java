//package com.karl.matches.controllers.v1;
//
//import com.karl.matches.exception.custom.DtoToEntityMappingException;
//import com.karl.matches.exception.custom.ResourceExistsException;
//import com.karl.matches.exception.custom.ResourceNotFoundException;
//import com.karl.matches.dto.v1.MatchDTO;
//import com.karl.matches.service.v1.MatchService;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Deprecated
//@RestController
//@ConditionalOnExpression("${legacy.match.controller.enabled:true}")
//@RequestMapping("/api/v1/matches")
//public class MatchController {
//
//    private final MatchService matchService;
//
//    public MatchController(MatchService matchService) {
//        this.matchService = matchService;
//    }
//
//    @GetMapping("/")
//    public List<MatchDTO> getAll() {
//        return matchService.getAllMatches();
//    }
//
//    @GetMapping("/{matchId}")
//    public MatchDTO getTeam(@PathVariable("matchId") int matchId) throws ResourceNotFoundException {
//        return matchService.getMatch(matchId);
//    }
//
//    @PostMapping("/create")
//    public ResponseEntity<MatchDTO> createTeam(@RequestBody MatchDTO matchDTO) throws ResourceExistsException, DtoToEntityMappingException {
//        return new ResponseEntity<>(matchService.createMatch(matchDTO), HttpStatus.OK);
//    }
//
//}
