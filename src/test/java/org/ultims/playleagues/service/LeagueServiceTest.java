package org.ultims.playleagues.service;

import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.ultims.playleagues.model.League;
import org.ultims.playleagues.repository.v1.LeagueRepository;
import org.ultims.playleagues.service.league.LeagueService;
import org.ultims.playleagues.service.league.v1.LeagueServiceImpl;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class LeagueServiceTest {

    @Mock
    private LeagueRepository leagueRepository;

    private LeagueService leagueService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        leagueService = new LeagueServiceImpl(leagueRepository);
    }

    @AfterEach
    void tearDown() {
    }

    @Nested
    @DisplayName("Retrieve all leagues")
    class RetrieveLeagues {

        @Test
        @DisplayName("should return all leagues")
        void shouldReturnListOfLeagues() {
            // Given
            List<League> leagues = List.of(
                    new League(UUID.randomUUID().toString(), "spanish"),
                    new League(UUID.randomUUID().toString(), "english"));
            when(leagueRepository.retrieveAllLeagues()).thenReturn(leagues);

            // When
            List<League> actual = leagueService.retrieveAll();

            // Then
            verify(leagueRepository, times(1)).retrieveAllLeagues();
            assertThat(actual).isEqualTo(leagues);
        }

    }

    @Nested
    @DisplayName("Retrieve League By Id")
    class RetrieveLeagueById {

        String id = UUID.randomUUID().toString();

        @Test
        @DisplayName("should return league found with given id")
        void shouldReturnLeagueFoundWithGivenId() {
            // Given
            League league = new League(id, "english");

            when(leagueRepository.retrieveLeagueById(id)).thenReturn(league);

            // When
            League actual = leagueService.retrieveById(id);

            // Then
            assertThat(actual).isEqualTo(league);
            verify(leagueRepository, times(1)).retrieveLeagueById(id);
        }

        @Test
        @DisplayName("should return null when no league found with given id")
        void shouldReturnNullWhenNoLeagueFoundWithGivenId() {
            // Given
            when(leagueRepository.retrieveLeagueById(id)).thenReturn(null);

            // When
            League actual = leagueService.retrieveById(id);

            // Then
            assertThat(actual).isNull();
            verify(leagueRepository, times(1)).retrieveLeagueById(id);
        }
    }

    @Nested
    @DisplayName("Retrieve League By Name")
    class RetrieveLeague {

        String leagueName = "english";

        @Test
        @DisplayName("should return league found with given name")
        void shouldReturnLeagueFoundWithGivenName() {
            // Given
            League league = new League(UUID.randomUUID().toString(), leagueName);
            when(leagueRepository.retrieveLeagueByName(leagueName)).thenReturn(league);

            // When
            League actual = leagueService.retrieveByName(leagueName);

            // Then
            verify(leagueRepository, times(1)).retrieveLeagueByName(leagueName);
            assertThat(actual).isEqualTo(league);
        }

        @Test
        @DisplayName("should return NULL when no league found with given name")
        void shouldReturnNullWhenNoLeagueFoundWithGivenName() {
            // Given
            when(leagueRepository.retrieveLeagueByName(leagueName)).thenReturn(null);

            // When
            League actual = leagueService.retrieveByName(leagueName);

            // Then
            verify(leagueRepository, times(1)).retrieveLeagueByName(leagueName);
            assertThat(actual).isNull();
        }
    }

    @Nested
    @DisplayName("League Existence")
    class DoesLeagueExist {

        String leagueId = UUID.randomUUID().toString();
        String leagueName = "english";
        League league = new League(leagueId, leagueName);

        @Test
        @DisplayName("should return true when league does exist")
        void shouldReturnTrueWhenLeagueDoesExist() {
            // Given
            when(leagueRepository.retrieveLeagueById(leagueId)).thenReturn(league);

            // When
            boolean actual = leagueService.doesExist(leagueId);

            // Then
            verify(leagueRepository, times(1)).retrieveLeagueById(leagueId);
            assertThat(actual).isTrue();
        }

        @Test
        @DisplayName("should return false when league does not exist")
        void shouldReturnFalseWhenLeagueDoesNotExist() {
            // Given
            when(leagueRepository.retrieveLeagueById(leagueId)).thenReturn(null);

            // When
            boolean actual = leagueService.doesExist(leagueId);

            // Then
            verify(leagueRepository, times(1)).retrieveLeagueById(leagueId);
            assertThat(actual).isFalse();
        }

    }

    @Nested
    @DisplayName("Is League Name Unique")
    class DoesLeagueNameExist {

        String leagueName = "english";
        String leagueId = UUID.randomUUID().toString();
        League league = new League(leagueId, leagueName);

        @Test
        void shouldReturnFalseWhenNoLeagueWithGiveNameExist() {
            // Given

            when(leagueRepository.retrieveLeagueByName(leagueName)).thenReturn(null);

            // When
            boolean actual = leagueService.isLeagueNameUnique(leagueName);

            // Then
            verify(leagueRepository, times(1)).retrieveLeagueByName(leagueName);
            assertThat(actual).isFalse();
        }

        @Test
        void shouldReturnTrueWhenLeagueWithGivenNameWasFound() {
            // Given
            when(leagueRepository.retrieveLeagueByName(leagueName)).thenReturn(league);

            // When
            boolean actual = leagueService.isLeagueNameUnique(leagueName);

            // Then
            verify(leagueRepository, times(1)).retrieveLeagueByName(leagueName);
            assertThat(actual).isTrue();
        }
    }

    @Nested
    @DisplayName("Create League")
    class CreateLeague {

        String leagueId = UUID.randomUUID().toString();
        String leagueName = "english";
        League league = new League(leagueId, leagueName);

        @Test
        @DisplayName("should return true when league was created")
        void shouldReturnTrueWhenLeagueWasCreated() {
            // Given
            when(leagueRepository.retrieveLeagueById(leagueId)).thenReturn(league);

            // When
            boolean actual = leagueService.create(league);

            // Then
            verify(leagueRepository, times(1)).retrieveLeagueById(leagueId);
            verify(leagueRepository, never()).createNewLeague(leagueId, leagueName);
            assertThat(actual).isFalse();
        }

        @Test
        @DisplayName("should return false when league with given id already exists")
        void shouldReturnFalseWhenLeagueWithGivenNameAlreadyExists() {
            // Given
            when(leagueRepository.retrieveLeagueById(leagueId)).thenReturn(null);

            // When
            boolean actual = leagueService.create(league);

            // Then
            verify(leagueRepository, times(1)).retrieveLeagueById(leagueId);
            verify(leagueRepository, times(1)).createNewLeague(leagueId, leagueName);
            assertThat(actual).isTrue();
        }
    }

    @Nested
    @DisplayName("Update League")
    class UpdateLeague {

        String leagueId = UUID.randomUUID().toString();
        String leagueName = "english";
        League league = new League(leagueId, leagueName);

        @Test
        @DisplayName("should return true when league name was updated")
        void shouldReturnTrueWhenLeagueNameWasUpdated() {
            // Given
            when(leagueRepository.retrieveLeagueById(leagueId)).thenReturn(league);

            // When
            boolean isUpdated = leagueService.update(league);

            // Then
            verify(leagueRepository, times(1)).changeLeagueNameById(leagueId, leagueName);
            assertThat(isUpdated).isTrue();
        }

        @Test
        @DisplayName("should return false when league name was not updated")
        void shouldReturnFalseWhenLeagueNameWasNotUpdated() {
            // Given
            when(leagueRepository.retrieveLeagueById(leagueId)).thenReturn(null);

            // When
            boolean isUpdated = leagueService.update(league);

            // Then
            verify(leagueRepository, never()).changeLeagueNameById(leagueId, leagueName);
            assertThat(isUpdated).isFalse();
        }
    }

    @Nested
    @DisplayName("should remove league when league exists")
    class DeleteLeague {

        String leagueId = UUID.randomUUID().toString();
        String leagueName = "english";
        League league = new League(leagueId, leagueName);

        @Test
        @DisplayName("should delete league with given id")
        void shouldReturnTrueWhenLeagueWasDeleted() {
            // Given

            when(leagueRepository.retrieveLeagueById(leagueId)).thenReturn(league);

            // When
            boolean isRemoved = leagueService.deleteById(leagueId);

            // Then
            assertThat(isRemoved).isTrue();
            verify(leagueRepository, times(1)).deleteById(leagueId);
        }

        @Test
        @DisplayName("should return false when league does not exist")
        void shouldReturnFalseWhenNoLeagueWithGivenIdWasFound() {
            // Given
            when(leagueRepository.retrieveLeagueById(leagueId)).thenReturn(null);

            // When
            boolean isRemoved = leagueService.deleteById(leagueId);

            // Then
            assertThat(isRemoved).isFalse();
            verify(leagueRepository, never()).deleteById(leagueId);
        }
    }

}