package org.ultims.playleagues.service.team.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ultims.playleagues.model.Team;
import org.ultims.playleagues.model.TeamLeague;
import org.ultims.playleagues.repository.v1.TeamRepository;
import org.ultims.playleagues.service.league.LeagueService;
import org.ultims.playleagues.service.team.TeamService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final LeagueService leagueService;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository, LeagueService leagueService) {
        this.teamRepository = teamRepository;
        this.leagueService = leagueService;
    }

    @Override
    public List<Team> retrieveAll() {
        return teamRepository.retrieveAllTeams();
    }

    @Override
    public Team retrieveById(String id) {
        return teamRepository.retrieveTeamById(id);
    }

    @Override
    public boolean create(Team team) {
        boolean doesTeamExist = doesExist(team.getId());
        boolean isUnique = isTeamNameUnique(team.getName());
        boolean doesLeagueExist = leagueService.doesExist(team.getLeagueId());

        if (doesTeamExist || isUnique || !doesLeagueExist) {
            return false;
        } else {
            teamRepository.createNewTeam(team.getId(), team.getName(), team.getLeagueId());
            return true;
        }
    }

    @Override
    public boolean update(Team team) {
        boolean doesTeamExist = doesExist(team.getId());

        if (doesTeamExist) {
            teamRepository.modifyTeamNameById(team.getName(), team.getId());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteById(String id) {
        boolean doesTeamExist = doesExist(id);

        if (doesTeamExist) {
            teamRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Team retrieveByName(String teamName) {
        return teamRepository.retrieveTeamByName(teamName);
    }

    @Override
    public List<Team> retrieveByLeagueId(String leagueId) {
        return teamRepository.retrieveTeamsByLeagueId(leagueId);
    }

    @Override
    public boolean isTeamNameUnique(String teamName) {
        Team result = retrieveByName(teamName);

        return result != null;
    }

    @Override
    public List<TeamLeague> getTeamLeagues() {
        List<TeamLeague> teamLeagues = new ArrayList<>();
        Query query = entityManager.createStoredProcedureQuery("get_teams_with_league_name");
        List<Object[]> resultList = query.getResultList();

        resultList.forEach((record) -> {
            String id = record[0].toString();
            String name = record[1].toString();
            String leagueName = record[2].toString();

            TeamLeague teamLeague = new TeamLeague(id, name, leagueName);
            teamLeagues.add(teamLeague);
        });

        return teamLeagues;
    }

    @Override
    public boolean doesExist(String id) {
        Team team = retrieveById(id);

        return team != null;
    }
}
