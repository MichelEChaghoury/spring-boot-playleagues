package org.ultims.playleagues.contract.v1.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public record UpdateTeamRequest
        (@Size(min = 3, max = 32, message = "Team Name must be between 3 and 32 characters")
         @NotEmpty(message = "Team Name cannot be null or empty")
         String name) {
}
