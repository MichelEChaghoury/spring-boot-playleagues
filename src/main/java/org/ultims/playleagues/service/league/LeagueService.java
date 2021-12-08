package org.ultims.playleagues.service.league;

import org.ultims.playleagues.model.League;
import org.ultims.playleagues.service.EntityService;

public interface LeagueService extends EntityService<League, String> {
    League retrieveByName(String leagueName);
    boolean isLeagueNameUnique(String leagueName);
}
