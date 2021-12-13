package org.ultims.playleagues.service.match;

import org.ultims.playleagues.model.Match;
import org.ultims.playleagues.model.MatchCard;

import java.time.LocalDate;
import java.util.List;

public interface MatchService {

    List<MatchCard> getMatchCards();

    List<Match> getAll();

    List<MatchCard> getMatchCardsByDate(LocalDate date);

    List<MatchCard> getMatchCardsByYear(int year);

    List<MatchCard> getMatchCardsByTeam(String teamId);
}
