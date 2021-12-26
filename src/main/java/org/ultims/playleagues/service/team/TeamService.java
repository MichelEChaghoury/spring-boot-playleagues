package org.ultims.playleagues.service.team;

import org.ultims.playleagues.model.Team;
import org.ultims.playleagues.model.TeamLeague;
import org.ultims.playleagues.service.EntityService;

import java.util.List;

public interface TeamService extends EntityService<Team, String> {

    Team retrieveByName(String teamName);

    List<Team> retrieveByLeagueId(String leagueId);

    boolean isTeamNameUnique(String teamName);

    List<TeamLeague> getTeamLeagues();

}
