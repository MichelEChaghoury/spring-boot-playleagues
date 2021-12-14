package org.ultims.playleagues.model;

import java.util.Objects;

public class MatchCardReport {

    private String id;
    private String name;
    private int totalMatches;
    private int totalWins;
    private int totalLosses;
    private int totalDraws;

    public MatchCardReport() {
    }

    public MatchCardReport(String id, String name, int totalMatches, int totalWins, int totalLosses, int totalDraws) {
        this.id = id;
        this.name = name;
        this.totalMatches = totalMatches;
        this.totalWins = totalWins;
        this.totalLosses = totalLosses;
        this.totalDraws = totalDraws;
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

    public int getTotalMatches() {
        return totalMatches;
    }

    public void setTotalMatches(int totalMatches) {
        this.totalMatches = totalMatches;
    }

    public int getTotalWins() {
        return totalWins;
    }

    public void setTotalWins(int totalWins) {
        this.totalWins = totalWins;
    }

    public int getTotalLosses() {
        return totalLosses;
    }

    public void setTotalLosses(int totalLosses) {
        this.totalLosses = totalLosses;
    }

    public int getTotalDraws() {
        return totalDraws;
    }

    public void setTotalDraws(int totalDraws) {
        this.totalDraws = totalDraws;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MatchCardReport)) return false;
        MatchCardReport that = (MatchCardReport) o;
        return getTotalMatches() == that.getTotalMatches() && getTotalWins() == that.getTotalWins() && getTotalLosses() == that.getTotalLosses() && getTotalDraws() == that.getTotalDraws() && Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getTotalMatches(), getTotalWins(), getTotalLosses(), getTotalDraws());
    }

    @Override
    public String toString() {
        return "MatchCardReport{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", totalMatches=" + totalMatches +
                ", totalWins=" + totalWins +
                ", totalLosses=" + totalLosses +
                ", totalDraws=" + totalDraws +
                '}';
    }
}
