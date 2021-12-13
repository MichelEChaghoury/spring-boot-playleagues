package org.ultims.playleagues.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Table(name = "teams")
@Entity
public class Team {

    @Id
    private String id;

    private String name;

    @Column(name = "league_id")
    private String leagueId;

    public Team() {
    }

    public Team(String id, String name, String leagueId) {
        this.id = id;
        this.name = name;
        this.leagueId = leagueId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(String leagueId) {
        this.leagueId = leagueId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Team team)) return false;
        return Objects.equals(getId(), team.getId()) && Objects.equals(getName(), team.getName()) && Objects.equals(getLeagueId(), team.getLeagueId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getLeagueId());
    }

    @Override
    public String toString() {
        return "Team{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", leagueId='" + leagueId + '\'' +
                '}';
    }
}
