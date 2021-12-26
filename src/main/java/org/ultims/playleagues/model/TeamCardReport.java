package org.ultims.playleagues.model;

import java.util.Objects;

public final class TeamCardReport {
    private String id;
    private int totalWins;
    private int totalLosses;
    private int totalDraws;

    public TeamCardReport() {
    }

    public TeamCardReport(String id, int totalWins, int totalLosses, int totalDraws) {
        this.id = id;
        this.totalWins = totalWins;
        this.totalLosses = totalLosses;
        this.totalDraws = totalDraws;
    }

    public String id() {
        return id;
    }

    public int totalWins() {
        return totalWins;
    }

    public int totalLosses() {
        return totalLosses;
    }

    public int totalDraws() {
        return totalDraws;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (TeamCardReport) obj;
        return Objects.equals(this.id, that.id) &&
                this.totalWins == that.totalWins &&
                this.totalLosses == that.totalLosses &&
                this.totalDraws == that.totalDraws;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, totalWins, totalLosses, totalDraws);
    }

    @Override
    public String toString() {
        return "TeamCardReport[" +
                "id=" + id + ", " +
                "totalWins=" + totalWins + ", " +
                "totalLosses=" + totalLosses + ", " +
                "totalDraws=" + totalDraws + ']';
    }

}
