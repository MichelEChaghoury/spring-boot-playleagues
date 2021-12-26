package org.ultims.playleagues.controller.v1;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.ultims.playleagues.contract.v1.ApiRoutes;
import org.ultims.playleagues.contract.v1.request.CreateTeamRequest;
import org.ultims.playleagues.contract.v1.request.UpdateTeamRequest;
import org.ultims.playleagues.contract.v1.response.MessageResponse;
import org.ultims.playleagues.contract.v1.response.TeamResponse;
import org.ultims.playleagues.exception.BadRequestException;
import org.ultims.playleagues.exception.NoFoundResponseException;
import org.ultims.playleagues.model.Team;
import org.ultims.playleagues.model.TeamLeague;
import org.ultims.playleagues.service.team.TeamService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@Tag(name = "Team Controller")
public class TeamController {

    private final TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping(ApiRoutes.TEAMS.GET_ALL)
    public ResponseEntity<List<TeamResponse>> getAllTeams() {
        List<TeamResponse> response = new ArrayList<>();

        teamService.retrieveAll()
                .forEach(team -> response.add(new TeamResponse(team.getId(), team.getName(), team.getLeagueId())));

        return ok(response);
    }

    @GetMapping(ApiRoutes.TEAMS.GET_BY_NAME)
    public ResponseEntity<TeamResponse> getTeamByName(@RequestParam("name") String name) {
        Team team = teamService.retrieveByName(name);

        if (team != null) {
            TeamResponse response = new TeamResponse(team.getId(), team.getName(), team.getLeagueId());
            return ok(response);
        } else {
            throw new NoFoundResponseException("No team with name: " + name + " was found");
        }
    }

    @GetMapping(ApiRoutes.TEAMS.GET_WITH_LEAGUE)
    public ResponseEntity<List<TeamLeague>> getTeamLeagues() {
        List<TeamLeague> teamLeagues = teamService.getTeamLeagues();

        return ok(teamLeagues);
    }

    @GetMapping(ApiRoutes.TEAMS.GET_BY_ID)
    public ResponseEntity<TeamResponse> getTeam(@PathVariable("id") String id) {
        Team team = teamService.retrieveById(id);

        if (team != null) {
            TeamResponse response = new TeamResponse(team.getId(), team.getName(), team.getLeagueId());
            return ok(response);
        } else {
            throw new NoFoundResponseException("No team with id: " + id + " was found");
        }
    }

    @GetMapping(ApiRoutes.TEAMS.GET_BY_LEAGUE_ID)
    public ResponseEntity<List<TeamResponse>> getTeamsByLeagueId(@RequestParam("leagueId") String leagueId) {
        List<TeamResponse> response = new ArrayList<>();

        teamService.retrieveByLeagueId(leagueId).forEach((team) -> {
            response.add(new TeamResponse(team.getId(), team.getName(), team.getLeagueId()));
        });

        return ok(response);
    }

    @PostMapping(ApiRoutes.TEAMS.CREATE)
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Object> createTeam(@Valid @RequestBody CreateTeamRequest request) {
        String id = UUID.randomUUID().toString();
        String name = request.name();
        String leagueId = request.leagueId();

        Team team = new Team(id, name, leagueId);

        boolean isCreated = teamService.create(team);

        if (isCreated) {
            TeamResponse response = new TeamResponse(id, name, leagueId);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            throw new BadRequestException("Unable to create team with name: " + name + " and league Id: " + leagueId);
        }

    }

    @PutMapping(ApiRoutes.TEAMS.UPDATE_BY_ID)
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Object> updateTeam(@PathVariable("id") String id, @Valid @RequestBody UpdateTeamRequest request) {
        String teamName = request.name();
        Team team = new Team(id, teamName, null);
        boolean isUpdated = teamService.update(team);

        if (isUpdated) {
            Team updatedTeam = teamService.retrieveById(id);
            TeamResponse response = new TeamResponse(id, teamName, updatedTeam.getLeagueId());
            return ok(response);
        } else {
            throw new BadRequestException("Unable to update team");
        }
    }

    @DeleteMapping(ApiRoutes.TEAMS.DELETE_BY_ID)
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Object> deleteTeam(@PathVariable("id") String id) {
        boolean doesExist = teamService.doesExist(id);

        if (doesExist) {
            teamService.deleteById(id);
            MessageResponse response = new MessageResponse("Team with id: " + id + " was removed successfully");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } else {
            throw new NoFoundResponseException("Team with id: " + id + " was not found");
        }
    }
}
