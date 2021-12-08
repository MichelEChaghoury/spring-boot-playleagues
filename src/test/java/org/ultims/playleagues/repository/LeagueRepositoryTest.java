package org.ultims.playleagues.repository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.ultims.playleagues.model.League;
import org.ultims.playleagues.repository.v1.LeagueRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class LeagueRepositoryTest {

    private final LeagueRepository leagueRepository;

    @Autowired
    LeagueRepositoryTest(LeagueRepository leagueRepository) {
        this.leagueRepository = leagueRepository;
    }

    @BeforeEach
    void setUp() {
        leagueRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
    }

    @Nested
    @DisplayName("Retrieve League By Id")
    class RetrieveLeagueById {

        String leagueId = UUID.randomUUID().toString();
        String leagueName = "english";

        @Test
        @DisplayName("should return NULL if league with given id does not exist")
        void shouldReturnNullWhenNoLeagueWithGivenIdWasFound() {
            // Given

            // When
            League actual = leagueRepository.retrieveLeagueById(leagueId);

            // Then
            assertThat(actual).isNull();
        }

        @Test
        @DisplayName("should return league with given id")
        void shouldRetrieveLeagueWithGivenId() {
            // Given
            League expected = leagueRepository.save(new League(leagueId, leagueName));

            // When
            League actual = leagueRepository.retrieveLeagueById(leagueId);

            // Then
            assertThat(actual).isEqualTo(expected);
        }

    }

    @Nested
    @DisplayName("Retrieve League By Name")
    class RetrieveLeagueByName {

        String leagueName = "english";

        @Test
        @DisplayName("should return NULL if league with given name does not exist")
        void shouldReturnNullWhenNoLeagueWithGivenNameWasFound() {
            // Given

            // When
            League league = leagueRepository.retrieveLeagueByName(leagueName);

            // Then
            assertNull(league);
        }

        @Test
        @DisplayName("should return league with given name")
        void shouldReturnLeagueWhenLeagueWithGivenNameWasFound() {
            // Given
            League expected = leagueRepository.save(new League(UUID.randomUUID().toString(), leagueName));

            // When
            League actual = leagueRepository.retrieveLeagueByName(leagueName);

            // Then
            assertThat(actual).isEqualTo(expected);
        }

    }

    @Nested
    @DisplayName("Retrieve All Leagues")
    class RetrieveAllLeagues {

        List<League> leagues = new ArrayList<>();

        @BeforeEach
        void setUp() {
            leagues.add(new League(UUID.randomUUID().toString(), "english"));
            leagues.add(new League(UUID.randomUUID().toString(), "spanish"));
            leagues.add(new League(UUID.randomUUID().toString(), "french"));
        }

        @Test
        @DisplayName("should return all leagues")
        void shouldRetrieveAllLeagues() {
            // Given
            leagues.forEach(leagueRepository::save);

            // When
            List<League> actual = leagueRepository.retrieveAllLeagues();

            // Then
            assertThat(actual.size()).isEqualTo(leagues.size());
            actual.forEach(league -> assertThat(league).isIn(leagues));
        }

    }

    @Nested
    @DisplayName("Update League")
    class UpdateLeagueById {

        String leagueId = UUID.randomUUID().toString();
        String leagueName = "english";

        @Test
        @DisplayName("should update league with given id")
        void shouldUpdateLeagueNameWithGivenIdWhenFound() {
            // Given
            leagueRepository.save(new League(leagueId, leagueName));

            // When
            String newLeagueName = "spanish";
            League actual = leagueRepository.changeLeagueNameById(leagueId, newLeagueName);

            // Then
            assertThat(actual.getId()).isEqualTo(leagueId);
            assertThat(actual.getName()).isEqualTo(newLeagueName);
        }

    }

    @Nested
    @DisplayName("Create League")
    class CreateNewLeague {

        String leagueId = UUID.randomUUID().toString();
        String leagueName = "english";
        League league = new League(leagueId, leagueName);

        @Test
        @DisplayName("should create new league and return it")
        void shouldCreateNewLeague() {
            // Given

            // When
            League actual = leagueRepository.createNewLeague(leagueId, leagueName);

            // Then
            assertThat(actual).isEqualTo(league);
        }
    }

    @Nested
    @DisplayName("Delete League")
    class DeleteLeagueById {

        String leagueId = UUID.randomUUID().toString();
        String leagueName = "english";
        League league = new League(leagueId, leagueName);

        @Test
        @DisplayName("should delete league with given id")
        void shouldDeleteLeagueWithGivenId() {
            // Given
            leagueRepository.save(league);

            // When
            leagueRepository.deleteById(leagueId);
            League actual = leagueRepository.retrieveLeagueById(leagueId);

            // Then
            assertThat(actual).isNull();
        }
    }

}