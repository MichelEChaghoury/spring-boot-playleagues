package org.ultims.playleagues.contract.v1.response;

import java.time.LocalDate;

public record MatchCardResponse(
        String id,
        String firstTeam,
        int firstTeamScore,
        String secondTeam,
        int secondTeamScore,
        LocalDate date
) {
}
