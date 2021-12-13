package org.ultims.playleagues.model;

import java.time.LocalDate;
import java.util.Objects;

public class MatchCard {
    private String id;
    private String firstTeam;
    private int firstTeamScore;
    private String secondTeam;
    private int secondTeamScore;
    private LocalDate date;

    public MatchCard(String id, String first_team, int first_team_score, String second_team, int second_team_score, LocalDate date) {
        this.id = id;
        this.firstTeam = first_team;
        this.firstTeamScore = first_team_score;
        this.secondTeam = second_team;
        this.secondTeamScore = second_team_score;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstTeam() {
        return firstTeam;
    }

    public void setFirstTeam(String firstTeam) {
        this.firstTeam = firstTeam;
    }

    public int getFirstTeamScore() {
        return firstTeamScore;
    }

    public void setFirstTeamScore(int firstTeamScore) {
        this.firstTeamScore = firstTeamScore;
    }

    public String getSecondTeam() {
        return secondTeam;
    }

    public void setSecondTeam(String secondTeam) {
        this.secondTeam = secondTeam;
    }

    public int getSecondTeamScore() {
        return secondTeamScore;
    }

    public void setSecondTeamScore(int secondTeamScore) {
        this.secondTeamScore = secondTeamScore;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MatchCard)) return false;
        MatchCard matchCard = (MatchCard) o;
        return getFirstTeamScore() == matchCard.getFirstTeamScore() && getSecondTeamScore() == matchCard.getSecondTeamScore() && Objects.equals(getId(), matchCard.getId()) && Objects.equals(getFirstTeam(), matchCard.getFirstTeam()) && Objects.equals(getSecondTeam(), matchCard.getSecondTeam()) && Objects.equals(getDate(), matchCard.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstTeam(), getFirstTeamScore(), getSecondTeam(), getSecondTeamScore(), getDate());
    }


    @Override
    public String toString() {
        return "MatchCard{" +
                "id='" + id + '\'' +
                ", first_team='" + firstTeam + '\'' +
                ", first_team_score=" + firstTeamScore +
                ", second_team='" + secondTeam + '\'' +
                ", second_team_score=" + secondTeamScore +
                ", date=" + date +
                '}';
    }
}
