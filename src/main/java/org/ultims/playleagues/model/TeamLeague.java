package org.ultims.playleagues.model;

import java.util.Objects;

public class TeamLeague {
    private String id;
    private String team;
    private String leagueName;

    public TeamLeague() {
    }

    public TeamLeague(String id, String team, String leagueName) {
        this.id = id;
        this.team = team;
        this.leagueName = leagueName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TeamLeague that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getTeam(), that.getTeam()) && Objects.equals(getLeagueName(), that.getLeagueName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTeam(), getLeagueName());
    }

    @Override
    public String toString() {
        return "TeamLeague{" +
                "id='" + id + '\'' +
                ", team='" + team + '\'' +
                ", leagueName='" + leagueName + '\'' +
                '}';
    }
}
