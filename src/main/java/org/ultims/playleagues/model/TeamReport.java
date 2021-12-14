package org.ultims.playleagues.model;

import java.util.Objects;

public class TeamReport {
    private String id;
    private String name;
    private int totalTeams;

    public TeamReport() {
    }

    public TeamReport(String id, String name, int totalTeams) {
        this.id = id;
        this.name = name;
        this.totalTeams = totalTeams;
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

    public int getTotalTeams() {
        return totalTeams;
    }

    public void setTotalTeams(int totalTeams) {
        this.totalTeams = totalTeams;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TeamReport)) return false;
        TeamReport that = (TeamReport) o;
        return getTotalTeams() == that.getTotalTeams() && Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getTotalTeams());
    }

    @Override
    public String toString() {
        return "TeamReport{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", totalTeams=" + totalTeams +
                '}';
    }
}