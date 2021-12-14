package org.ultims.playleagues.service.match;

import org.ultims.playleagues.model.Match;
import org.ultims.playleagues.model.MatchCard;
import org.ultims.playleagues.model.MatchCardReport;
import org.ultims.playleagues.model.TeamReport;

import java.util.List;

public interface MatchService {

    List<MatchCard> getMatchCards();

    List<MatchCardReport> getMatchCardReports();

    List<Match> getAll();

    List<MatchCard> getMatchCardsByYear(String year);

    List<MatchCard> getMatchCardsByTeam(String teamId);

    List<TeamReport> getTeamReports();

}
