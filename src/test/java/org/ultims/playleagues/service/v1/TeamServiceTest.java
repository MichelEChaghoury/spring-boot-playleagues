package org.ultims.playleagues.service.v1;

import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.ultims.playleagues.model.Team;
import org.ultims.playleagues.repository.v1.TeamRepository;
import org.ultims.playleagues.service.league.LeagueService;
import org.ultims.playleagues.service.team.TeamService;
import org.ultims.playleagues.service.team.v1.TeamServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private LeagueService leagueService;

    private TeamService teamService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        teamService = new TeamServiceImpl(teamRepository, leagueService);
    }

    @AfterEach
    void tearDown() {
    }

    @Nested
    @DisplayName("Get all teams")
    class RetrieveAllTeams {

        List<Team> teams = new ArrayList<>();

        @BeforeEach
        void setUp() {
            teams.add(new Team(UUID.randomUUID().toString(), "team1", "league1"));
            teams.add(new Team(UUID.randomUUID().toString(), "team2", "league1"));
            teams.add(new Team(UUID.randomUUID().toString(), "team3", "league2"));
            teams.add(new Team(UUID.randomUUID().toString(), "team4", "league2"));
        }

        @Test
        @DisplayName("Should Retrieve All Teams")
        void shouldReturnAllTeams() {
            // Given
            when(teamRepository.retrieveAllTeams())
                    .thenReturn(teams);

            // When
            List<Team> actual = teamService.retrieveAll();

            // Then
            assertThat(actual.size()).isEqualTo(teams.size());
            verify(teamRepository, times(1)).retrieveAllTeams();
        }
    }

    @Nested
    @DisplayName("Retrieve team by name")
    class RetrieveTeamByName {

        @Test
        @DisplayName("should return team found")
        void shouldReturnTeamFoundWithGivenName() {
            // Given
            String teamName = "team1";
            String teamId = UUID.randomUUID().toString();
            String leagueId = UUID.randomUUID().toString();
            Team team = new Team(teamId, teamName, leagueId);
            when(teamRepository.retrieveTeamByName(teamName))
                    .thenReturn(team);

            // When
            Team actual = teamService.retrieveByName(teamName);

            // Then
            assertThat(actual.getName()).isEqualTo(teamName);
            assertThat(actual.getLeagueId()).isEqualTo(team.getLeagueId());
            verify(teamRepository, times(1)).retrieveTeamByName(teamName);
        }

        @Test
        @DisplayName("should return null when team not found")
        void shouldReturnNullWhenNoTeamWithGivenNameWasFound() {
            // Given
            when(teamRepository.retrieveTeamByName(anyString()))
                    .thenReturn(null);

            // When
            Team actual = teamService.retrieveByName(anyString());

            // Then
            assertThat(actual).isNull();
            verify(teamRepository, times(1)).retrieveTeamByName(anyString());
        }
    }

    @Nested
    @DisplayName("Retrieve Teams by League Id")
    class RetrieveTeamsByLeagueId {

        String leagueId = "league1";
        List<Team> teams = new ArrayList<>();

        @BeforeEach
        void setUp() {
            teams.add(new Team(UUID.randomUUID().toString(), "team1", leagueId));
            teams.add(new Team(UUID.randomUUID().toString(), "team2", leagueId));
            teams.add(new Team(UUID.randomUUID().toString(), "team3", leagueId));
        }

        @Test
        @DisplayName("should return teams found with given league id")
        void shouldReturnTeamsWithGivenLeagueId() {
            // Given
            when(teamRepository.retrieveTeamsByLeagueId(leagueId))
                    .thenReturn(teams);

            // When
            List<Team> actual = teamService.retrieveByLeagueId(leagueId);

            // Then
            assertThat(actual.size()).isEqualTo(teams.size());
            verify(teamRepository, times(1)).retrieveTeamsByLeagueId(leagueId);
        }

        @Test
        @DisplayName("should return empty list when no team found")
        void shouldReturnNullEmptyListNoTeamWithGivenLeagueIdWasFound() {
            // Given
            when(teamRepository.retrieveTeamsByLeagueId(leagueId))
                    .thenReturn(new ArrayList<>());

            // When
            List<Team> actual = teamService.retrieveByLeagueId(leagueId);

            // Then
            assertThat(actual.size()).isEqualTo(0);
            verify(teamRepository, times(1)).retrieveTeamsByLeagueId(leagueId);
        }
    }

    @Nested
    @DisplayName("Get Team by Id")
    class RetrieveTeamById {

        String teamId = UUID.randomUUID().toString();
        Team team = new Team(teamId, "team", "league");

        @Test
        @DisplayName("should return NULL when no team with given id was found")
        void shouldReturnNullWhenNoTeamWithGivenIdWasFound() {
            // Given
            when(teamRepository.retrieveTeamById(teamId))
                    .thenReturn(null);

            // When
            Team actual = teamService.retrieveById("id");

            // Then
            assertThat(actual).isNull();
            verify(teamRepository, times(1)).retrieveTeamById(anyString());
        }

        @Test
        @DisplayName("should return team found with given id")
        void shouldReturnTeamFoundWithGivenId() {
            // Given
            when(teamRepository.retrieveTeamById(teamId))
                    .thenReturn(team);

            // When
            Team actual = teamService.retrieveById(teamId);

            // Then
            assertThat(actual).isEqualTo(team);
            verify(teamRepository, times(1)).retrieveTeamById(teamId);
        }
    }

    @Nested
    @DisplayName("Does Team Exists")
    class DoesTeamExist {

        String teamId = UUID.randomUUID().toString();
        Team team = new Team(teamId, "team", "league");

        @Test
        @DisplayName("should return true when team does exist")
        void ShouldReturnTrueWhenTeamWithGivenIdExist() {
            // Given

            when(teamRepository.retrieveTeamById(teamId))
                    .thenReturn(team);

            // When
            boolean actual = teamService.doesExist(teamId);

            // Then
            assertThat(actual).isTrue();
        }

        @Test
        @DisplayName("should return false when team does not exist")
        void shouldReturnTrueWhenTeamWithGivenIdDoesNotExist() {
            // Given
            when(teamRepository.retrieveTeamById(teamId))
                    .thenReturn(null);

            // When
            boolean actual = teamService.doesExist(teamId);

            // Then
            assertThat(actual).isFalse();
        }

    }

    @Nested
    @DisplayName("delete team by id")
    class DeleteTeamById {

        String teamId = UUID.randomUUID().toString();
        Team team = new Team(teamId, "team", "league");

        @Test
        @DisplayName("should return true when team was deleted")
        void shouldReturnTrueWhenTeamWasFoundAndDeleted() {
            // Given
            when(teamService.retrieveById(teamId))
                    .thenReturn(team);

            // When
            boolean isDeleted = teamService.deleteById(teamId);

            // Then
            assertThat(isDeleted).isTrue();
            verify(teamRepository, times(1)).deleteById(teamId);
        }

        @Test
        @DisplayName("should return false when team was not deleted")
        void shouldReturnFalseWhenNoTeamWasFoundToBeDeleted() {
            // Given
            when(teamService.retrieveById(anyString()))
                    .thenReturn(null);

            // When
            boolean isDeleted = teamService.deleteById(anyString());

            // Then
            assertThat(isDeleted).isFalse();
            verify(teamRepository, never()).deleteById(anyString());
        }
    }

    @Nested
    @DisplayName("Update Team")
    class UpdateTeamById {

        String teamId = UUID.randomUUID().toString();
        String teamName = "team";
        Team team = new Team(teamId, teamName, "league");

        @Test
        @DisplayName("Update team with given id")
        void shouldUpdateTeamFoundWithGivenId() {
            // Given
            when(teamRepository.retrieveTeamById(teamId))
                    .thenReturn(team);

            // When
            boolean isUpdate = teamService.update(team);

            // Then
            assertThat(isUpdate).isTrue();
            verify(teamRepository, times(1)).retrieveTeamById(teamId);
            verify(teamRepository, times(1)).modifyTeamNameById(teamName, teamId);
        }

        @Test
        @DisplayName("should return false when no team was updated")
        void shouldReturnFalseWhenNoTeamWasFoundToBeUpdated() {
            // Given
            when(teamRepository.retrieveTeamById(teamId))
                    .thenReturn(null);

            // When
            boolean isUpdated = teamService.update(team);

            // Then
            assertThat(isUpdated).isFalse();
            verify(teamRepository, times(1)).retrieveTeamById(teamId);
            verify(teamRepository, never()).modifyTeamNameById(teamId, teamName);
        }
    }

    @Nested
    @DisplayName("Check If Team Name already exists")
    class CheckTeamNameAlreadyExists {

        String teamId = UUID.randomUUID().toString();
        String teamName = "team";
        Team team = new Team(teamId, teamName, "league");

        @Test
        @DisplayName("should return true when team name already exists")
        void shouldReturnTeamWhenTeamWithGivenNameWasFound() {
            // Given
            when(teamRepository.retrieveTeamByName(teamName))
                    .thenReturn(team);

            // When
            boolean isTeamNameUnique = teamService.isTeamNameUnique(teamName);

            // Then
            assertThat(isTeamNameUnique).isTrue();
            verify(teamRepository, times(1)).retrieveTeamByName(teamName);
        }

        @Test
        @DisplayName("should return false when team name does not exists")
        void shouldReturnFalseWhenNoTeamWithGivenNameWasFound() {
            // Given
            when(teamRepository.retrieveTeamByName(teamName))
                    .thenReturn(null);

            // When
            boolean isTeamNameUnique = teamService.isTeamNameUnique(teamName);

            // Then
            assertThat(isTeamNameUnique).isFalse();
            verify(teamRepository, times(1)).retrieveTeamByName(teamName);
        }
    }

    @Nested
    @DisplayName("Create New Team")
    class CreateNewTeam {

        String leagueId = UUID.randomUUID().toString();
        String teamName = "team";
        String teamId = UUID.randomUUID().toString();
        Team team = new Team(teamId, teamName, leagueId);

        @Test
        @DisplayName("should return true when team created")
        void shouldReturnNewTeamCreated() {
            // Given
            when(leagueService.doesExist(leagueId))
                    .thenReturn(true);
            when(teamRepository.createNewTeam(teamId, teamName, leagueId))
                    .thenReturn(team);

            // When
            boolean isCreated = teamService.create(team);

            // Then
            assertThat(isCreated).isTrue();
            verify(teamRepository, times(1)).createNewTeam(teamId, teamName, leagueId);
        }

        @Test
        @DisplayName("should return false when league Id does not exist")
        void shouldReturnFalseWhenLeagueIdDoesNotExists() {
            // Given
            when(leagueService.doesExist(leagueId))
                    .thenReturn(false);

            // When
            boolean isCreated = teamService.create(team);

            // Then
            assertThat(isCreated).isFalse();
            verify(teamRepository, never()).createNewTeam(teamId, teamName, leagueId);
        }
    }
}