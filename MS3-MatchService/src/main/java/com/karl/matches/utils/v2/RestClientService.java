package com.karl.matches.utils.v2;

import com.karl.matches.clients.FieldClient;
import com.karl.matches.clients.TeamClient;
import com.karl.matches.clients.TournamentClient;
import com.karl.matches.dto.v2.FieldDTO;
import com.karl.matches.dto.v2.MatchDTO;
import com.karl.matches.dto.v2.TeamDTO;
import com.karl.matches.dto.v2.TournamentDTO;
import com.karl.matches.entity.v2.Match;
import feign.FeignException;
import org.springframework.stereotype.Service;

@Service
public class RestClientService {

    private final TeamClient teamClient;
    private final TournamentClient tournamentClient;
    private final FieldClient fieldClient;

    public RestClientService(TeamClient teamClient, TournamentClient tournamentClient, FieldClient fieldClient) {
        this.teamClient = teamClient;
        this.tournamentClient = tournamentClient;
        this.fieldClient = fieldClient;
    }

    public MatchDTO retrieveAttributeData(Match m) {

        FieldDTO field = fieldClient.getField(m.getFieldId());
        TournamentDTO tournament = tournamentClient.getTournament(m.getTournamentId());

        TeamDTO home = teamClient.getTeam(m.getHomeTeamId());
        TeamDTO away = teamClient.getTeam(m.getAwayTeamId());

        return MatchDtoConverter.toDto(m,tournament,field,home,away);
    }

    public boolean verityFieldExists(int id) {
        boolean b = false;

        try {
            b = fieldClient.getField(id) != null;
        } catch (FeignException.BadRequest e) {
            System.out.println("Bad request exception occurred: " + e.getMessage());
        } catch (FeignException e) {
            System.out.println("Feign exception occurred: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An exception occurred: " + e.getMessage());
        }
        return b;
    }

    public boolean verityTournamentExists(int id) {
        boolean b = false;

        try {
            b = tournamentClient.getTournament(id) != null;
        } catch (FeignException.BadRequest e) {
            System.out.println("Bad request exception occurred: " + e.getMessage());
        } catch (FeignException e) {
            System.out.println("Feign exception occurred: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An exception occurred: " + e.getMessage());
        }
        return b;
    }

    public boolean verityTeamExists(int id) {
        boolean b = false;

        try {
            b = teamClient.getTeam(id) != null;
        } catch (FeignException.BadRequest e) {
            System.out.println("Bad request exception occurred: " + e.getMessage());
        } catch (FeignException e) {
            System.out.println("Feign exception occurred: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An exception occurred: " + e.getMessage());
        }
        return b;
    }
}
