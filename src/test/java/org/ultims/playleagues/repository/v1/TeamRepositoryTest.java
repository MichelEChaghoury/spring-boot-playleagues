package org.ultims.playleagues.repository.v1;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.ultims.playleagues.model.League;
import org.ultims.playleagues.model.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
class TeamRepositoryTest {

    private final TeamRepository teamRepository;
    private final LeagueRepository leagueRepository;

    @Autowired
    TeamRepositoryTest(TeamRepository teamRepository, LeagueRepository leagueRepository) {
        this.teamRepository = teamRepository;
        this.leagueRepository = leagueRepository;
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
    @DisplayName("Retrieve Teams")
    class RetrieveAllTeams {

        String leagueId = UUID.randomUUID().toString();
        League league = new League(leagueId, "sample");
        List<Team> teams = new ArrayList<>();

        @BeforeEach
        void setUp() {
            teams.add(new Team(UUID.randomUUID().toString(), "team1", leagueId));
            teams.add(new Team(UUID.randomUUID().toString(), "team2", leagueId));
            teams.add(new Team(UUID.randomUUID().toString(), "team3", leagueId));
        }

        @Test
        @DisplayName("should retrieve all teams found")
        void shouldRetrieveAllTeams() {
            // Given
            leagueRepository.save(league);
            teams.forEach(teamRepository::save);

            // When
            List<Team> actual = teamRepository.retrieveAllTeams();

            // Then
            assertThat(actual.size()).isEqualTo(teams.size());
        }
    }

    @Nested
    @DisplayName("Retrieve Teams with same league id")
    class RetrieveTeamsByLeagueId {

        String leagueId = UUID.randomUUID().toString();
        String secondLeagueId = UUID.randomUUID().toString();

        @Test
        @DisplayName("should return all teams with same league id as given")
        void shouldReturnAllTeamsWithSameLeagueId() {
            // Given
            leagueRepository.save(new League(leagueId, "first"));
            leagueRepository.save(new League(secondLeagueId, "second"));

            for (int i = 0; i < 2; i++) {
                teamRepository.save(new Team(UUID.randomUUID().toString(), "team" + (i + 1), leagueId));
            }
            for (int i = 0; i < 4; i++) {
                teamRepository.save(new Team(UUID.randomUUID().toString(), "team" + (i + 3), secondLeagueId));
            }

            // When
            List<Team> teams = teamRepository.retrieveTeamsByLeagueId(leagueId);

            // Then
            assertThat(teams.size()).isEqualTo(2);
            assertThat(teams).allMatch(team -> team.getLeagueId().equals(leagueId));
        }

        @Test
        @DisplayName("should return null when no team was found")
        void shouldReturnNullWhenNoTeamWithGivenLeagueIdWasFound() {
            // Given

            // When
            List<Team> actual = teamRepository.retrieveTeamsByLeagueId(leagueId);

            // Then
            assertThat(actual.size()).isEqualTo(0);
        }
    }

    @Nested
    @DisplayName("Retrieve Team")
    class RetrieveTeamById {

        String leagueId = UUID.randomUUID().toString();
        String teamId = UUID.randomUUID().toString();
        League league = new League(leagueId, "sample");
        Team team = new Team(UUID.randomUUID().toString(), "team1", leagueId);

        @Test
        @DisplayName("should return team found with given id")
        void shouldReturnTeamFoundWithGivenId() {
            // Given
            leagueRepository.save(league);
            teamRepository.save(team);

            // When
            Team actual = teamRepository.retrieveTeamById(team.getId());

            // Then
            assertThat(actual).isEqualTo(team);
        }

        @Test
        @DisplayName("should return NULL when no team found")
        void shouldReturnNullWhenNoTeamWithGivenIdWasFound() {
            // Given

            // When
            Team actual = teamRepository.retrieveTeamById(teamId);

            // Then
            assertThat(actual).isNull();
        }
    }

    @Nested
    @DisplayName("Retrieve Team By Name")
    class RetrieveTeamByName {

        String leagueId = UUID.randomUUID().toString();
        League league = new League(leagueId, "sample");
        String teamId = UUID.randomUUID().toString();
        String teamName = "team1";
        Team team = new Team(teamId, teamName, leagueId);

        @Test
        @DisplayName("should return team found with given name")
        void shouldReturnTeamFoundWithGivenName() {
            // Given
            leagueRepository.save(league);
            teamRepository.save(team);

            // When
            Team actual = teamRepository.retrieveTeamByName(teamName);

            // Then
            assertThat(actual.getId()).isEqualTo(teamId);
            assertThat(actual.getName()).isEqualTo(teamName);
            assertThat(actual.getLeagueId()).isEqualTo(leagueId);
        }

        @Test
        @DisplayName("should return NULL when no team found")
        void shouldReturnNullWhenNoTeamWithGivenNameWasFound() {
            // Given

            // When
            Team actual = teamRepository.retrieveTeamByName(teamName);

            // Then
            assertThat(actual).isNull();
        }
    }

    @Nested
    @DisplayName("Update Team")
    class UpdateTeamNameById {

        String leagueId = UUID.randomUUID().toString();
        String teamId = UUID.randomUUID().toString();
        League league = new League(leagueId, "sample");
        String newTeamName = "new_team_name";
        Team team = new Team(teamId, "team1", leagueId);

        @Test
        @DisplayName("should update name of team found with given id")
        void shouldUpdateNameOfTeamFoundWithGivenId() {
            // Given

            leagueRepository.save(league);
            teamRepository.save(team);

            // When
            Team actual = teamRepository.modifyTeamNameById(newTeamName, teamId);

            // Then
            assertThat(actual.getId()).isEqualTo(teamId);
            assertThat(actual.getName()).isEqualTo(newTeamName);
            assertThat(actual.getLeagueId()).isEqualTo(leagueId);
        }
    }

    @Nested
    @DisplayName("Create Team")
    class CreateTeam {

        String leagueId = UUID.randomUUID().toString();
        League league = new League(leagueId, "league");
        String teamName = "new_team";
        String teamId = UUID.randomUUID().toString();

        @Test
        @DisplayName("should return team created")
        void shouldReturnNewTeamCreated() {
            // Given
            leagueRepository.save(league);

            // When
            Team actual = teamRepository.createNewTeam(teamId, teamName, leagueId);

            // Then
            assertThat(actual.getId()).isEqualTo(teamId);
            assertThat(actual.getName()).isEqualTo(teamName);
            assertThat(actual.getLeagueId()).isEqualTo(leagueId);
        }
    }
}