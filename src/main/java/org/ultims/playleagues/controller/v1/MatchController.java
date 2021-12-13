package org.ultims.playleagues.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ultims.playleagues.contract.v1.ApiRoutes;
import org.ultims.playleagues.contract.v1.response.MatchResponse;
import org.ultims.playleagues.model.MatchCard;
import org.ultims.playleagues.repository.v1.MatchRepository;
import org.ultims.playleagues.service.match.MatchService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class MatchController {

    private final MatchService matchService;
    private final MatchRepository matchRepository;

    @Autowired
    public MatchController(MatchService matchService, MatchRepository matchRepository) {
        this.matchService = matchService;
        this.matchRepository = matchRepository;
    }

    @GetMapping(ApiRoutes.MATCHES.GET_REPORTS)
    public ResponseEntity getMatchesCards() {
        List<MatchCard> matchCards = matchService.getMatchCards();
        return ok(matchCards);

    }

    @GetMapping(ApiRoutes.MATCHES.GET_BY_DATE)
    public ResponseEntity<List<MatchCard>> getMatchedByDate(@RequestParam("date") String date) {

        // cast String to LocalDate
        LocalDate localDate = LocalDate.parse(date);

        List<MatchCard> matchCards = matchService.getMatchCardsByDate(localDate);
        return ok(matchCards);
    }

    @GetMapping(ApiRoutes.MATCHES.GET_BY_YEAR)
    public ResponseEntity<List<MatchCard>> getMatchedByDate(@PathVariable("year") int year) {

        List<MatchCard> matchCards = matchService.getMatchCardsByYear(year);
        return ok(matchCards);
    }

    @GetMapping(ApiRoutes.MATCHES.GET_BY_TEAM_ID)
    public ResponseEntity<List<MatchCard>> getMatchedByTeam(@PathVariable("teamId") String teamId) {

        List<MatchCard> matchCards = matchService.getMatchCardsByTeam(teamId);
        return ok(matchCards);
    }

    @GetMapping(ApiRoutes.MATCHES.GET_ALL)
    public ResponseEntity<List<MatchResponse>> getMatches() {
        List<MatchResponse> responses = new ArrayList<>();

        matchService.getAll().forEach((match) -> {
            responses.add(new MatchResponse(
                    match.getId(),
                    match.getFirstTeamId(),
                    match.getFirstTeamScore(),
                    match.getSecondTeamId(),
                    match.getSecondTeamScore(),
                    match.getDate()
            ));
        });

        return ok(responses);
    }
}
