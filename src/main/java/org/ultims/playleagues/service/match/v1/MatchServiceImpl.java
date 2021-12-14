package org.ultims.playleagues.service.match.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ultims.playleagues.mapper.v1.Mapper;
import org.ultims.playleagues.model.Match;
import org.ultims.playleagues.model.MatchCard;
import org.ultims.playleagues.model.MatchCardReport;
import org.ultims.playleagues.model.TeamReport;
import org.ultims.playleagues.repository.v1.MatchRepository;
import org.ultims.playleagues.service.match.MatchService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Service
public class MatchServiceImpl implements MatchService {

    @PersistenceContext
    private EntityManager entityManager;
    private final MatchRepository matchRepository;

    @Autowired
    public MatchServiceImpl(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    @Override
    public List<MatchCard> getMatchCards() {
        List<MatchCard> matchCards = new ArrayList<>();

        Query query = entityManager.createStoredProcedureQuery("get_all_matches_cards");
        List<Object[]> resultList = query.getResultList();

        resultList.forEach((record) -> {
            MatchCard matchCard = Mapper.matchCard(record);
            matchCards.add(matchCard);
        });

        return matchCards;
    }

    @Override
    public List<MatchCardReport> getMatchCardReports() {
        List<MatchCardReport> matchCardReports = new ArrayList<>();

        Query query = entityManager.createStoredProcedureQuery("get_matches_cards_reports");
        List<Object[]> resultList = query.getResultList();

        resultList.forEach((record) -> {
            MatchCardReport report = Mapper.matchCardReport(record);
            matchCardReports.add(report);
        });

        return matchCardReports;
    }

    @Override
    public List<Match> getAll() {
        return matchRepository.findAll();
    }

    @Override
    public List<MatchCard> getMatchCardsByYear(String year) {

        List<MatchCard> matchCards = new ArrayList<>();

        Query query = entityManager.createNativeQuery("CALL `get_matches_cards_by_year`(?);");
        query.setParameter(1, year);
        List<Object[]> resultList = query.getResultList();

        resultList.forEach((record) -> {
            MatchCard card = Mapper.matchCard(record);
            matchCards.add(card);
        });

        return matchCards;
    }

    @Override
    public List<MatchCard> getMatchCardsByTeam(String teamId) {


        List<MatchCard> matchCards = new ArrayList<>();

        Query query = entityManager.createNativeQuery("CALL `get_match_card_by_team_id`(?);");
        query.setParameter(1, teamId);
        List<Object[]> resultList = query.getResultList();

        resultList.forEach((record) -> {
            MatchCard card = Mapper.matchCard(record);
            matchCards.add(card);
        });

        return matchCards;

    }

    @Override
    public List<TeamReport> getTeamReports() {
        List<TeamReport> teamReports = new ArrayList<>();

        Query query = entityManager.createStoredProcedureQuery("get_total_teams_by_leagues");
        List<Object[]> resultList = query.getResultList();

        resultList.forEach((record) -> {
            TeamReport teamReport = Mapper.teamReport(record);
            teamReports.add(teamReport);
        });

        return teamReports;
    }
}
