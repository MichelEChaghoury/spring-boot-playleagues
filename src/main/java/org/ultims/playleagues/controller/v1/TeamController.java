package org.ultims.playleagues.controller.v1;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ultims.playleagues.contract.v1.ApiRoutes;
import org.ultims.playleagues.contract.v1.request.CreateTeamRequest;
import org.ultims.playleagues.contract.v1.request.UpdateTeamRequest;
import org.ultims.playleagues.contract.v1.response.MessageResponse;
import org.ultims.playleagues.contract.v1.response.TeamResponse;
import org.ultims.playleagues.model.Team;
import org.ultims.playleagues.service.team.TeamService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Tag(name = "Team Resource")
@RestController
public class TeamController {

    private final TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping(ApiRoutes.TEAMS.GET_ALL)
    public ResponseEntity<List<TeamResponse>> getAllTeams() {
        List<TeamResponse> response = new ArrayList<>();

        teamService.retrieveAll().forEach(team -> response.add(new TeamResponse(team.getId(), team.getName(), team.getLeagueId())));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping(ApiRoutes.TEAMS.GET_BY_ID)
    public ResponseEntity<Object> getTeam(@PathVariable("id") String id) {
        Team team = teamService.retrieveById(id);

        if (team == null) {
            var response = new MessageResponse("No team with id: " + id + " was found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(team, HttpStatus.OK);
        }
    }

    @GetMapping(ApiRoutes.TEAMS.GET_BY_LEAGUE_ID)
    public ResponseEntity<List<TeamResponse>> getTeamsByLeagueId(@RequestParam("leagueId") String leagueId) {
        List<TeamResponse> response = new ArrayList<>();

        teamService.retrieveByLeagueId(leagueId).forEach((team) -> response.add(new TeamResponse(team.getId(), team.getName(), team.getLeagueId())));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(ApiRoutes.TEAMS.CREATE)
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
            MessageResponse messageResponse = new MessageResponse("Unable to create team with name: " + name + " and league Id: " + leagueId);
            return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping(ApiRoutes.TEAMS.UPDATE_BY_ID)
    public ResponseEntity<Object> updateTeam(@PathVariable("id") String id, @Valid @RequestBody UpdateTeamRequest request) {
        String teamName = request.name();
        Team team = new Team(id, teamName, null);
        boolean isUpdated = teamService.update(team);

        if (isUpdated) {
            Team updatedTeam = teamService.retrieveById(id);
            TeamResponse response = new TeamResponse(id, teamName, updatedTeam.getLeagueId());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            MessageResponse messageResponse = new MessageResponse("Unable to update team");
            return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(ApiRoutes.TEAMS.DELETE_BY_ID)
    public ResponseEntity<Object> deleteTeam(@PathVariable("id") String id) {
        boolean doesExist = teamService.doesExist(id);

        if (doesExist) {
            teamService.deleteById(id);
            MessageResponse response = new MessageResponse("Team with id: " + id + " was removed successfully");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } else {
            MessageResponse response = new MessageResponse("Team with id: " + id + " was not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
