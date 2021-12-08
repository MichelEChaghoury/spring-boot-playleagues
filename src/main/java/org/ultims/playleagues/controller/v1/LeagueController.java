package org.ultims.playleagues.controller.v1;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ultims.playleagues.contract.v1.ApiRoutes;
import org.ultims.playleagues.contract.v1.request.CreateLeagueRequest;
import org.ultims.playleagues.contract.v1.request.UpdateLeagueRequest;
import org.ultims.playleagues.contract.v1.response.LeagueResponse;
import org.ultims.playleagues.contract.v1.response.MessageResponse;
import org.ultims.playleagues.model.League;
import org.ultims.playleagues.service.league.LeagueService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Tag(name = "League Resource")
@RestController
public class LeagueController {


    private final LeagueService leagueService;

    @Autowired
    public LeagueController(LeagueService leagueService) {
        this.leagueService = leagueService;
    }

    @GetMapping(ApiRoutes.LEAGUES.GET_ALL)
    public ResponseEntity<List<LeagueResponse>> getLeagues() {
        List<LeagueResponse> response = new ArrayList<>();

        leagueService.retrieveAll().forEach(league -> {
            LeagueResponse leagueResponse = new LeagueResponse(league.getId(), league.getName());
            response.add(leagueResponse);
        });

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(ApiRoutes.LEAGUES.GET_BY_ID)
    public ResponseEntity<Object> getLeague(@PathVariable("id") String id) {
        League league = leagueService.retrieveById(id);

        if (league == null) {
            MessageResponse message = new MessageResponse("No League with id " + id + " was found");
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            LeagueResponse response = new LeagueResponse(league.getId(), league.getName());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @GetMapping(ApiRoutes.LEAGUES.GET_BY_NAME)
    public ResponseEntity<Object> getLeagueByName(@RequestParam("name") String name) {
        League league = leagueService.retrieveByName(name);

        if (league == null) {
            MessageResponse message = new MessageResponse("No League with name " + name + " was found");
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            LeagueResponse response = new LeagueResponse(league.getId(), league.getName());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @PostMapping(ApiRoutes.LEAGUES.CREATE)
    public ResponseEntity<Object> createLeague(@Valid @RequestBody CreateLeagueRequest request) {

        String id = UUID.randomUUID().toString();
        League league = new League(id, request.name());
        boolean isCreated = leagueService.create(league);

        if (isCreated) {
            LeagueResponse response = new LeagueResponse(league.getId(), league.getName());
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            MessageResponse message = new MessageResponse(
                    "Unable to create league with name: " + league.getName());
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping(ApiRoutes.LEAGUES.UPDATE_BY_ID)
    public ResponseEntity<Object> updateLeague(@PathVariable("id") String id, @Valid @RequestBody UpdateLeagueRequest request) {
        League league = new League(id, request.name());

        boolean isUpdated = leagueService.update(league);

        if (isUpdated) {
            LeagueResponse response = new LeagueResponse(league.getId(), league.getName());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            MessageResponse message = new MessageResponse("No League with id " + id + " was found");
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(ApiRoutes.LEAGUES.DELETE_BY_ID)
    public ResponseEntity<Object> deleteLeague(@PathVariable("id") String id) {

        boolean isDeleted = leagueService.deleteById(id);

        if (isDeleted) {
            MessageResponse message = new MessageResponse("League with id " + id + " was removed successfully");
            return new ResponseEntity<>(message, HttpStatus.NO_CONTENT);
        } else {
            MessageResponse message = new MessageResponse("No League with id " + id + " was found");
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }
}