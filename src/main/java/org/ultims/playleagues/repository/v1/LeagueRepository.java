package org.ultims.playleagues.repository.v1;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.ultims.playleagues.model.League;

import java.util.List;

@Repository
public interface LeagueRepository extends JpaRepository<League, String> {

    @Query(value = "CALL `get_all_leagues`();", nativeQuery = true)
    List<League> retrieveAllLeagues();

    @Query(value = "CALL `get_league_by_id`(:league_id);", nativeQuery = true)
    League retrieveLeagueById(@Param("league_id") String leagueId);

    @Query(value = "CALL `get_league_by_name`(:league_name);", nativeQuery = true)
    League retrieveLeagueByName(@Param("league_name") String leagueName);

    @Query(value = "CALL `create_new_league`(:league_id, :league_name);", nativeQuery = true)
    League createNewLeague(@Param("league_id") String leagueId, @Param("league_name") String leagueName);

    @Query(value = "CALL `update_league_name_byId`(:league_id, :league_name);", nativeQuery = true)
    League changeLeagueNameById(@Param("league_id") String leagueId, @Param("league_name") String leagueName);
    
}
