package org.ultims.playleagues.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.ultims.playleagues.contract.v1.ApiRoutes;
import org.ultims.playleagues.exception.NoFoundResponseException;
import org.ultims.playleagues.model.*;
import org.ultims.playleagues.service.match.MatchService;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class MatchController {

    private final MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping(ApiRoutes.MATCHES.GET_REPORTS)
    public ResponseEntity<List<MatchCard>> getMatchesCards() {
        List<MatchCard> matchCards = matchService.getMatchCards();

        return ok(matchCards);
    }


    @GetMapping(ApiRoutes.MATCHES.GET_TEAM_CARD_REPORT_BY_ID)
    public ResponseEntity<TeamCardReport> getTeamCardReport(@PathVariable String id) {
        TeamCardReport teamCardReport = matchService.getTeamCardReportById(id);

        if (teamCardReport != null) {
            return ok(teamCardReport);
        } else {
            throw new NoFoundResponseException("Team With Id " + id + " Not Found");
        }
    }

    @GetMapping(ApiRoutes.MATCHES.GET_CARD_REPORTS)
    public ResponseEntity<List<MatchCardReport>> getMatchesCardReports() {
        List<MatchCardReport> matchCardReports = matchService.getMatchCardReports();

        return ok(matchCardReports);
    }

    @GetMapping(ApiRoutes.MATCHES.GET_TOTAL_TEAMS_PER_LEAGUE)
    public ResponseEntity<List<TeamReport>> getTeamReports() {
        List<TeamReport> teamReports = matchService.getTeamReports();

        return ok(teamReports);

    }

    @GetMapping(ApiRoutes.MATCHES.GET_BY_YEAR)
    public ResponseEntity<List<MatchCard>> getMatchedByDateYear(@PathVariable("year") String year) {
        List<MatchCard> matchCards = matchService.getMatchCardsByYear(year);

        return ok(matchCards);
    }

    @GetMapping(ApiRoutes.MATCHES.GET_BY_TEAM_ID)
    public ResponseEntity<List<MatchCard>> getMatchedByTeam(@PathVariable("teamId") String teamId) {
        List<MatchCard> matchCards = matchService.getMatchCardsByTeam(teamId);

        return ok(matchCards);
    }

    @GetMapping(ApiRoutes.MATCHES.GET_ALL)
    public ResponseEntity<List<Match>> getMatches() {
        List<Match> matches = matchService.getAll();

        return ok(matches);
    }

}
