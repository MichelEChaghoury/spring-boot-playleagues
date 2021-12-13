package org.ultims.playleagues.controller.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.ultims.playleagues.contract.v1.ApiRoutes;
import org.ultims.playleagues.contract.v1.request.CreateTeamRequest;
import org.ultims.playleagues.contract.v1.request.UpdateTeamRequest;
import org.ultims.playleagues.contract.v1.response.MessageResponse;
import org.ultims.playleagues.model.League;
import org.ultims.playleagues.model.Team;
import org.ultims.playleagues.repository.v1.LeagueRepository;
import org.ultims.playleagues.repository.v1.TeamRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TeamControllerTest {

    private final MockMvc mockMvc;
    private final LeagueRepository leagueRepository;
    private final TeamRepository teamRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    TeamControllerTest(MockMvc mockMvc, LeagueRepository leagueRepository, TeamRepository teamRepository, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.leagueRepository = leagueRepository;
        this.teamRepository = teamRepository;
        this.objectMapper = objectMapper;
    }

    @BeforeEach
    void setUp() {
        teamRepository.deleteAll();
        leagueRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
    }

    @Nested
    @DisplayName("GET /api/v1/teams")
    class GetLeagues {

        String leagueId = UUID.randomUUID().toString();
        String leagueName = "league1";
        List<Team> teams = new ArrayList<>();

        @BeforeEach
        void setUp() {
            teams.add(new Team(UUID.randomUUID().toString(), "team1", leagueId));
            teams.add(new Team(UUID.randomUUID().toString(), "team2", leagueId));
            teams.add(new Team(UUID.randomUUID().toString(), "team3", leagueId));
        }

        @Test
        @DisplayName("should return all teams")
        void shouldReturnAllTeams() throws Exception {
            // Given
            leagueRepository.save(new League(leagueId, leagueName));
            teams.forEach(teamRepository::save);
            // When

            // Then
            mockMvc.perform(get(ApiRoutes.TEAMS.GET_ALL))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$[0].id").isNotEmpty())
                    .andExpect(jsonPath("$[0].name").isNotEmpty())
                    .andExpect(jsonPath("$[0].leagueId").isNotEmpty());
        }
    }

    @Nested
    @DisplayName("GET /api/v1/teams/search?leagueId={leagueId}")
    class RetrieveTeamsByLeagueId {

        String leagueId = UUID.randomUUID().toString();
        String secondLeagueId = UUID.randomUUID().toString();
        String leagueName = "league1";
        String secondLeagueName = "league2";

        String BASE_URL = ApiRoutes.TEAMS.GET_BY_LEAGUE_ID + "?leagueId=" + leagueId;

        @Test
        @DisplayName("should return 200 Ok")
        void shouldReturnTeamsFound() throws Exception {
            // Given
            leagueRepository.save(new League(leagueId, leagueName));
            leagueRepository.save(new League(secondLeagueId, secondLeagueName));

            teamRepository.save(new Team(UUID.randomUUID().toString(), "team1", leagueId));
            teamRepository.save(new Team(UUID.randomUUID().toString(), "team2", leagueId));
            teamRepository.save(new Team(UUID.randomUUID().toString(), "team3", secondLeagueId));
            teamRepository.save(new Team(UUID.randomUUID().toString(), "team4", secondLeagueId));

            // When / Then
            mockMvc.perform(get(BASE_URL))
                    .andDo(print())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].id").isNotEmpty())
                    .andExpect(jsonPath("$[0].name").isNotEmpty())
                    .andExpect(jsonPath("$[0].leagueId").value(leagueId));
        }
    }

    @Nested
    @DisplayName("Get /api/v1/teams/{teamId}")
    class GetTeamById {

        String teamId = UUID.randomUUID().toString();
        String leagueId = UUID.randomUUID().toString();
        String teamName = "team1";
        String message = "No team with id: " + teamId + " was found";
        String BASE_URL = ApiRoutes.TEAMS.GET_BY_ID.replace("{id}", teamId);

        @Test
        @DisplayName("should return team found with 200 OK")
        void shouldReturn200OKWhenTeamWithGivenIdWasFound() throws Exception {
            // Given
            leagueRepository.save(new League(leagueId, "league1"));
            teamRepository.save(new Team(teamId, teamName, leagueId));

            // When / Then
            mockMvc.perform(get(BASE_URL))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.id").value(teamId))
                    .andExpect(jsonPath("$.name").value(teamName))
                    .andExpect(jsonPath("$.leagueId").value(leagueId));

        }

        @Test
        @DisplayName("should return 404 NOT FOUND")
        void shouldReturn404NotFoundWhenNoTeamWithGivenIdWasFound() throws Exception {
            // Given

            // When / Then
            mockMvc.perform(get(BASE_URL))
                    .andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value(message));

        }
    }

    @Nested
    @DisplayName("DELETE /api/v1/teams/{teamId}")
    class DeleteTeamById {

        String teamId = UUID.randomUUID().toString();
        String teamName = "team";
        String leagueId = UUID.randomUUID().toString();
        String leagueName = "league";
        League league = new League(leagueId, leagueName);
        Team team = new Team(teamId, teamName, leagueId);
        String BASE_URL = ApiRoutes.TEAMS.DELETE_BY_ID.replace("{id}", teamId);
        MockHttpServletRequestBuilder mockRequest;

        @BeforeEach
        void setUp() {
            mockRequest = MockMvcRequestBuilders.delete(BASE_URL)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON);
        }

        @Test
        @DisplayName("should return 204 NO CONTENT")
        void shouldDeleteTeamByIdAndReturn204NoContent() throws Exception {
            // Given
            leagueRepository.save(league);
            teamRepository.save(team);

            MessageResponse response = new MessageResponse("Team with id: " + teamId + " was removed successfully");

            // When / Then
            mockMvc.perform(mockRequest)
                    .andDo(print())
                    .andExpect(status().isNoContent())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value(response.message()));
        }

        @Test
        @DisplayName("should return 404 NOT FOUND")
        void shouldReturn404NotFoundWhenTeamWithGivenIdWasNotFound() throws Exception {
            // Given
            MessageResponse response = new MessageResponse("Team with id: " + teamId + " was not found");

            // When / Then
            mockMvc.perform(mockRequest)
                    .andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value(response.message()));
        }
    }

    @Nested
    @DisplayName("POST /api/v1/teams")
    class CreateTeam {

        String leagueId = UUID.randomUUID().toString();
        String leagueName = "league";
        League league = new League(leagueId, leagueName);
        String teamName = "team";
        String teamId = UUID.randomUUID().toString();
        Team team = new Team(teamId, teamName, leagueId);
        CreateTeamRequest request = new CreateTeamRequest(teamName, leagueId);
        String BASE_URL = ApiRoutes.TEAMS.CREATE;
        MockHttpServletRequestBuilder mockRequest;
        MessageResponse response = new MessageResponse("Unable to create team with name: " + teamName + " and league Id: " + leagueId);

        @BeforeEach
        void setUp() throws JsonProcessingException {
            mockRequest = MockMvcRequestBuilders.post(BASE_URL)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request));
        }

        @Test
        @DisplayName("should return 201 CREATED")
        void shouldReturn201Created() throws Exception {
            // Given
            leagueRepository.save(league);

            // When / Then
            mockMvc.perform(mockRequest)
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.id").isNotEmpty())
                    .andExpect(jsonPath("$.name").value(teamName))
                    .andExpect(jsonPath("$.leagueId").value(leagueId));
        }

        @Test
        @DisplayName("should return 400 BAD REQUEST")
        void shouldReturn400BadRequest() throws Exception {
            // Given
            leagueRepository.save(league);
            teamRepository.save(team);

            // When / Then
            mockMvc.perform(mockRequest)
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value(response.message()));
        }
    }

    @Nested
    @DisplayName("PUT /api/v1/teams/{id}")
    class UpdateTeamById {

        String leagueId = UUID.randomUUID().toString();
        String leagueName = "league";
        League league = new League(leagueId, leagueName);
        String teamName = "team";
        String newTeamName = "new_team_name";
        String teamId = UUID.randomUUID().toString();
        Team team = new Team(teamId, teamName, leagueId);
        UpdateTeamRequest request = new UpdateTeamRequest(newTeamName);
        String BASE_URL = ApiRoutes.TEAMS.UPDATE_BY_ID.replace("{id}", teamId);
        MockHttpServletRequestBuilder mockRequest;
        MessageResponse response = new MessageResponse("Unable to update team");

        @BeforeEach
        void setUp() throws JsonProcessingException {
            mockRequest = MockMvcRequestBuilders.put(BASE_URL)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request));
        }

        @Test
        @DisplayName("should update team with given id")
        void shouldReturn200Ok() throws Exception {
            // Given
            leagueRepository.save(league);
            teamRepository.save(team);

            // When / Then
            mockMvc.perform(mockRequest)
                    .andDo(print())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(teamId))
                    .andExpect(jsonPath("$.name").value(newTeamName))
                    .andExpect(jsonPath("$.leagueId").value(leagueId));

        }

        @Test
        @DisplayName("should return 400 BAD REQUEST")
        void shouldReturn400BadRequestWhenTeamWasNotUpdated() throws Exception {
            // Given

            // When / Then
            mockMvc.perform(mockRequest)
                    .andDo(print())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value(response.message()));
        }
    }
}