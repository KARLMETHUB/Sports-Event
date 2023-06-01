package com.karl.ms9liveupdatesservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.karl.ms9liveupdatesservice.constants.Events;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BasketballGameEvent implements GameEvent {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Events event = Events.GAME_START;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String
            homeTeamName = "",
            awayTeamName = "";

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int
            homeTeamScore = 0,
            awayTeamScore = 0;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private LocalDateTime timestamp;

    @Override
    public String getEventLog() {

        if (event.name().equals(Events.GAME_START.name()))
            return "Game is starting. Timestamp: " + timestamp;

        if (event.name().equals(Events.GAME_END.name())) {
            return "Game is finished. Final score: Home: "
                    + homeTeamScore + ",Away: "
                    + awayTeamScore + ". "
                    + (homeTeamScore > awayTeamScore ? homeTeamName : awayTeamName)
                    + " wins!. Timestamp: " + timestamp;
        }

        if (event.name().equals(Events.SCORE_UPDATE.name())) {
            return "Score update. Current score: Home: " + homeTeamScore
                    + ",Away: " + awayTeamScore + ". Timestamp: " + timestamp;
        }

        return "Error! Something went wrong.";
    }

}
