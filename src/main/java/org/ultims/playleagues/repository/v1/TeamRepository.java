package org.ultims.playleagues.repository.v1;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.ultims.playleagues.model.Team;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, String> {

    @Query(value = "CALL `get_all_teams`();", nativeQuery = true)
    List<Team> retrieveAllTeams();

    @Query(value = "CALL `get_team_by_id`(:team_id);", nativeQuery = true)
    Team retrieveTeamById(@Param("team_id") String teamId);

    @Query(value = "CALL `get_team_by_name`(:team_name);", nativeQuery = true)
    Team retrieveTeamByName(@Param("team_name") String teamName);

    @Query(value = "CALL `get_all_teams_by_league_id`(:league_id);", nativeQuery = true)
    List<Team> retrieveTeamsByLeagueId(@Param("league_id") String leagueId);

    @Query(value = "CALL `update_team_name_by_id`(:team_name, :team_id);", nativeQuery = true)
    Team modifyTeamNameById(@Param("team_name") String teamName, @Param("team_id") String teamId);

    @Query(value = "CALL `create_new_team`(:team_id, :team_name, :league_id);", nativeQuery = true)
    Team createNewTeam(@Param("team_id") String teamId, @Param("team_name") String teamName, @Param("league_id") String leagueId);
}
