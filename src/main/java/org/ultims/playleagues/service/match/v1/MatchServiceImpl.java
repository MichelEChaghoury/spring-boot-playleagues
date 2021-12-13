package org.ultims.playleagues.service.match.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ultims.playleagues.model.Match;
import org.ultims.playleagues.model.MatchCard;
import org.ultims.playleagues.model.Team;
import org.ultims.playleagues.repository.v1.MatchRepository;
import org.ultims.playleagues.repository.v1.TeamRepository;
import org.ultims.playleagues.service.match.MatchService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MatchServiceImpl implements MatchService {

    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;

    @Autowired
    public MatchServiceImpl(MatchRepository matchRepository, TeamRepository teamRepository) {
        this.matchRepository = matchRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public List<MatchCard> getMatchCards() {

        List<MatchCard> matchCards = new ArrayList<>();
        matchRepository.findAll().forEach((match) -> {
            Team firstTeam = teamRepository.retrieveTeamById(match.getFirstTeamId());
            Team secondTeam = teamRepository.retrieveTeamById(match.getSecondTeamId());

            MatchCard matchCard = new MatchCard(
                    match.getId(),
                    firstTeam.getName(),
                    match.getFirstTeamScore(),
                    secondTeam.getName(),
                    match.getSecondTeamScore(),
                    match.getDate()
            );

            matchCards.add(matchCard);
        });

        return matchCards;
    }

    @Override
    public List<Match> getAll() {
        return matchRepository.findAll();
    }

    @Override
    public List<MatchCard> getMatchCardsByDate(LocalDate date) {
        List<MatchCard> matchCards = getMatchCards();

        List<MatchCard> filteredCards = matchCards
                .stream()
                .filter(matchCard -> matchCard.getDate().equals(date))
                .toList();

        return filteredCards;
    }

    @Override
    public List<MatchCard> getMatchCardsByTeam(String teamId) {
        List<Match> matches = matchRepository.findByFirstTeamIdOrSecondTeamId(teamId, teamId);
        List<MatchCard> matchCards = new ArrayList<>();

        matches.forEach(match -> {
            Team firstTeam = teamRepository.retrieveTeamById(match.getFirstTeamId());
            Team secondTeam = teamRepository.retrieveTeamById(match.getSecondTeamId());

            MatchCard matchCard = new MatchCard(
                    match.getId(),
                    firstTeam.getName(),
                    match.getFirstTeamScore(),
                    secondTeam.getName(),
                    match.getSecondTeamScore(),
                    match.getDate()
            );

            matchCards.add(matchCard);
        });

        return matchCards;
    }

    @Override
    public List<MatchCard> getMatchCardsByYear(int year) {
        List<MatchCard> matchCards = getMatchCards();

        List<MatchCard> filteredCards = matchCards
                .stream()
                .filter(matchCard -> matchCard.getDate().getYear() == year)
                .toList();

        return filteredCards;
    }

}
