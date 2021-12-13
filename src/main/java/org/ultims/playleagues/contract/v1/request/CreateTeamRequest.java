package org.ultims.playleagues.contract.v1.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public record CreateTeamRequest
        (@Size(min = 3, max = 32, message = "Team Name must be between 3 and 32 characters")
         @NotEmpty(message = "Team Name cannot be null or empty")
         String name,

         @Size(min = 36, max = 36, message = "League Id must be between 36 characters")
         @NotEmpty(message = "League Id cannot be null or empty")
         String leagueId
        ) {
}
