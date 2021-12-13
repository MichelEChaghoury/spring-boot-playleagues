package org.ultims.playleagues.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "matches")
public class Match {
    @Id
    private String id;
    private String firstTeamId;
    private int firstTeamScore;
    private String secondTeamId;
    private int secondTeamScore;
    private LocalDate date;

    public Match() {
    }

    public Match(String id, String firstTeamId, int firstTeamScore, String secondTeamId, int secondTeamScore, LocalDate date) {
        this.id = id;
        this.firstTeamId = firstTeamId;
        this.firstTeamScore = firstTeamScore;
        this.secondTeamId = secondTeamId;
        this.secondTeamScore = secondTeamScore;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstTeamId() {
        return firstTeamId;
    }

    public void setFirstTeamId(String firstTeamId) {
        this.firstTeamId = firstTeamId;
    }

    public int getFirstTeamScore() {
        return firstTeamScore;
    }

    public void setFirstTeamScore(int firstTeamScore) {
        this.firstTeamScore = firstTeamScore;
    }

    public String getSecondTeamId() {
        return secondTeamId;
    }

    public void setSecondTeamId(String secondTeamId) {
        this.secondTeamId = secondTeamId;
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
        if (!(o instanceof Match match)) return false;
        return firstTeamScore == match.firstTeamScore && secondTeamScore == match.secondTeamScore && Objects.equals(getId(), match.getId()) && Objects.equals(firstTeamId, match.firstTeamId) && Objects.equals(secondTeamId, match.secondTeamId) && Objects.equals(date, match.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), firstTeamId, firstTeamScore, secondTeamId, secondTeamScore, date);
    }

    @Override
    public String toString() {
        return "Match{" +
                "id='" + id + '\'' +
                ", firstTeamId='" + firstTeamId + '\'' +
                ", firstTeamScore=" + firstTeamScore +
                ", secondTeamId='" + secondTeamId + '\'' +
                ", secondTeamScore=" + secondTeamScore +
                ", date='" + date + '\'' +
                '}';
    }
}
