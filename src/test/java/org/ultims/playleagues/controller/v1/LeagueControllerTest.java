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
import org.ultims.playleagues.contract.v1.request.CreateLeagueRequest;
import org.ultims.playleagues.contract.v1.request.UpdateLeagueRequest;
import org.ultims.playleagues.contract.v1.response.MessageResponse;
import org.ultims.playleagues.model.League;
import org.ultims.playleagues.repository.v1.LeagueRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class LeagueControllerTest {

    private final MockMvc mockMvc;
    private final LeagueRepository leagueRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    LeagueControllerTest(MockMvc mockMvc, LeagueRepository leagueRepository, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.leagueRepository = leagueRepository;
        this.objectMapper = objectMapper;
    }

    @BeforeEach
    void setUp() {
        leagueRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
    }

    @Nested
    @DisplayName("GET /api/v1/leagues")
    class GetLeagues {

        List<League> leagues = new ArrayList<>();
        String BASE_URL = ApiRoutes.LEAGUES.GET_ALL;

        @BeforeEach
        void setUp() {
            leagues.add(new League(UUID.randomUUID().toString(), "english"));
            leagues.add(new League(UUID.randomUUID().toString(), "spanish"));
            leagues.add(new League(UUID.randomUUID().toString(), "french"));
        }

        @Test
        @DisplayName("should return all leagues")
        void shouldReturnAllLeagues() throws Exception {
            // Given
            leagues.forEach(leagueRepository::save);

            // When / Then
            mockMvc.perform(get(BASE_URL))
                    .andDo(print()).andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$[0].name").isNotEmpty())
                    .andExpect(jsonPath("$[0].id").isString());
        }

    }

    @Nested
    @DisplayName("GET /api/v1/leagues/{id}")
    class GetLeagueById {

        String leagueId = UUID.randomUUID().toString();
        String responseMessage = "No League with id " + leagueId + " was found";
        String BASE_URL = ApiRoutes.LEAGUES.GET_BY_ID.replace("{id}", leagueId);

        @Test
        @DisplayName("should return league with given id")
        void shouldReturnLeagueFoundWithGivenId() throws Exception {
            // Given
            String leagueName = "english";

            leagueRepository.save(new League(leagueId, leagueName));

            // When / Then
            mockMvc.perform(get(BASE_URL))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.name").value("english"))
                    .andExpect(jsonPath("$.id").value(leagueId));
        }

        @Test
        @DisplayName("should return 404 NOT FOUND")
        void shouldReturn404NotFoundWhenNoLeagueWithGivenId() throws Exception {
            // Given

            // When / Then
            mockMvc.perform(get(BASE_URL))
                    .andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value(responseMessage));

        }
    }

    @Nested
    @DisplayName("GET /api/v1/leagues/search?name={name}")
    class GetLeagueByName {

        String leagueId = UUID.randomUUID().toString();
        String leagueName = "english";
        String responseMessage = "No League with name " + leagueName + " was found";
        String BASE_URL = ApiRoutes.LEAGUES.GET_BY_NAME + "?name=" + leagueName;

        @Test
        @DisplayName("should return league found with given name")
        void shouldReturnLeagueFoundWithGivenName() throws Exception {
            // Given
            leagueRepository.save(new League(leagueId, leagueName));

            // When / Then
            mockMvc.perform(get(BASE_URL))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.id").isNotEmpty())
                    .andExpect(jsonPath("$.name").value(leagueName));
        }

        @Test
        @DisplayName("should return 404 NOT FOUND")
        void shouldReturn404NotFoundWhenNoLeagueFoundWithGivenName() throws Exception {
            // Given

            // When / Then
            mockMvc.perform(get(BASE_URL))
                    .andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value(responseMessage));
        }

    }

    @Nested
    @DisplayName("POST /api/v1/leagues")
    class PostLeague {

        String leagueName = "english";
        String leagueId = UUID.randomUUID().toString();
        CreateLeagueRequest request = new CreateLeagueRequest(leagueName);

        String BASE_URL = ApiRoutes.LEAGUES.CREATE;
        MessageResponse response = new MessageResponse(
                "Unable to create league with name: " + leagueName);
        MockHttpServletRequestBuilder mockRequest;

        @BeforeEach
        void setUp() throws JsonProcessingException {
            mockRequest = MockMvcRequestBuilders.post(BASE_URL)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request));
        }

        @Test
        @DisplayName("should return 201 CREATED")
        void shouldAddNewLeague() throws Exception {
            // Given

            // When / Then
            mockMvc.perform(mockRequest)
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").isNotEmpty())
                    .andExpect(jsonPath("$.name").value(leagueName));
        }

        @Test
        @DisplayName("should return 400 BAD REQUEST")
        void shouldReturnBadRequestWhenLeagueIsNotCreated() throws Exception {
            // Given
            leagueRepository.save(new League(leagueId, leagueName));

            // When / Then
            mockMvc.perform(mockRequest)
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value(response.message()));
        }

    }

    @Nested
    @DisplayName("PUT /api/v1/leagues/{id}")
    class UpdateLeagueById {

        String leagueId = UUID.randomUUID().toString();
        String leagueName = "english";
        String newLeagueName = "spanish";
        League league = new League(leagueId, leagueName);

        String BASE_URL = ApiRoutes.LEAGUES.UPDATE_BY_ID.replace("{id}", leagueId);

        UpdateLeagueRequest requestBody = new UpdateLeagueRequest(newLeagueName);
        MessageResponse message = new MessageResponse("No League with id " + leagueId + " was found");

        MockHttpServletRequestBuilder mockRequest;

        @BeforeEach
        void setUp() throws JsonProcessingException {
            mockRequest = MockMvcRequestBuilders.put(BASE_URL)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(requestBody));
        }

        @Test
        @DisplayName("should update league with given id")
        void shouldUpdateLeagueNameById() throws Exception {
            // Given
            leagueRepository.save(league);

            // When / Then
            mockMvc.perform(mockRequest)
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.id").value(leagueId))
                    .andExpect(jsonPath("$.name").value(newLeagueName));
        }

        @Test
        @DisplayName("should return 400 BAD REQUEST")
        void shouldReturn400BadRequestWhenUpdatingLeagueNameFailed() throws Exception {
            // Given

            // When / Then
            mockMvc.perform(mockRequest)
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value(message.message()));
        }
    }

    @Nested
    @DisplayName("DELETE /api/v1/leagues/{id}")
    class DeleteLeagueById {

        String leagueId = UUID.randomUUID().toString();
        String leagueName = "english";
        League league = new League(leagueId, leagueName);
        String BASE_URL = ApiRoutes.LEAGUES.DELETE_BY_ID.replace("{id}", leagueId);

        MockHttpServletRequestBuilder mockRequest;

        @BeforeEach
        void setUp() {
            mockRequest = MockMvcRequestBuilders.delete(BASE_URL)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON);
        }

        @Test
        @DisplayName("should delete league and return 204 NO CONTENT")
        void shouldDeleteLeagueWithGivenId() throws Exception {
            // Given
            leagueRepository.save(league);
            MessageResponse response = new MessageResponse("League with id " + leagueId + " was removed successfully");

            // When / Then
            mockMvc.perform(mockRequest)
                    .andDo(print())
                    .andExpect(status().isNoContent())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value(response.message()));
        }

        @Test
        @DisplayName("should return 400 BAD REQUEST")
        void shouldReturn404NotFoundWhenLeagueDoesNotExists() throws Exception {
            // Given
            MessageResponse response = new MessageResponse("No League with id " + leagueId + " was found");

            // When / Then
            mockMvc.perform(mockRequest)
                    .andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value(response.message()));
        }
    }
}