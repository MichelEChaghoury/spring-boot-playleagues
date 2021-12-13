package org.ultims.playleagues.contract.v1.response;

import java.time.LocalDate;

public record MatchResponse(
        String id,
        String firstTeamId,
        int firstTeamScore,
        String secondTeamId,
        int secondTeamScore,
        LocalDate date
) {
}
