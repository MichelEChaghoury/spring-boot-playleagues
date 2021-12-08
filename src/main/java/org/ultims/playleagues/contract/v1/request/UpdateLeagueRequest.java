package org.ultims.playleagues.contract.v1.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public record UpdateLeagueRequest
        (@Size(min = 3, max = 32, message = "League Name must be between 3 and 32 characters")
         @NotEmpty(message = "League Name cannot be null or empty")
         String name) {
}
