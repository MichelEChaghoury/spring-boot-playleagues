package org.ultims.playleagues.service.league.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ultims.playleagues.model.League;
import org.ultims.playleagues.repository.v1.LeagueRepository;
import org.ultims.playleagues.service.league.LeagueService;

import java.util.List;

@Service
public class LeagueServiceImpl implements LeagueService {

    private final LeagueRepository leagueRepository;

    @Autowired
    public LeagueServiceImpl(LeagueRepository leagueRepository) {
        this.leagueRepository = leagueRepository;
    }


    @Override
    public List<League> retrieveAll() {
        return leagueRepository.retrieveAllLeagues();
    }

    @Override
    public League retrieveById(String leagueId) {
        return leagueRepository.retrieveLeagueById(leagueId);
    }

    @Override
    public League retrieveByName(String leagueName) {
        return leagueRepository.retrieveLeagueByName(leagueName);
    }

    @Override
    public boolean isLeagueNameUnique(String leagueName) {
        League result = retrieveByName(leagueName);

        return result != null;
    }

    @Override
    public boolean create(League league) {
        boolean doesExists = doesExist(league.getId());
        boolean nameAlreadyExist = isLeagueNameUnique(league.getName());

        if (doesExists || nameAlreadyExist) {
            return false;
        } else {
            leagueRepository.createNewLeague(league.getId(), league.getName());
            return true;
        }
    }

    @Override
    public boolean update(League league) {
        boolean doesExists = doesExist(league.getId());
        if (doesExists) {
            leagueRepository.changeLeagueNameById(league.getId(), league.getName());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteById(String leagueId) {
        boolean doesLeagueExists = doesExist(leagueId);

        if (doesLeagueExists) {
            leagueRepository.deleteById(leagueId);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean doesExist(String id) {
        League league = leagueRepository.retrieveLeagueById(id);

        return league != null;
    }
}
