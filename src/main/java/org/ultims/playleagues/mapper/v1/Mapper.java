package org.ultims.playleagues.mapper.v1;


import org.ultims.playleagues.model.MatchCard;
import org.ultims.playleagues.model.MatchCardReport;
import org.ultims.playleagues.model.TeamCardReport;
import org.ultims.playleagues.model.TeamReport;

import java.time.LocalDate;

public class Mapper {

    public static MatchCard matchCard(Object[] record) {
        String id = record[0].toString();
        String firstTeam = record[1].toString();
        int firstTeamScore = Integer.parseInt(record[2].toString());
        String secondTeam = record[3].toString();
        int secondTeamScore = Integer.parseInt(record[4].toString());
        LocalDate date = LocalDate.parse(record[5].toString());

        return new MatchCard(id, firstTeam, firstTeamScore, secondTeam, secondTeamScore, date);
    }

    public static MatchCardReport matchCardReport(Object[] record) {
        String id = record[0].toString();
        String name = record[1].toString();
        int totalMatches = Integer.parseInt(record[2].toString());
        int totalWins = Integer.parseInt(record[3].toString());
        int totalLosses = Integer.parseInt(record[4].toString());
        int totalDraws = Integer.parseInt(record[5].toString());

        return new MatchCardReport(id, name, totalMatches, totalWins, totalLosses, totalDraws);
    }

    public static TeamReport teamReport(Object[] record) {
        String id = record[0].toString();
        String name = record[1].toString();
        int totalTeams = Integer.parseInt(record[2].toString());

        return new TeamReport(id, name, totalTeams);
    }

    public static TeamCardReport teamCardReport(Object[] record) {
        String id = record[0].toString();
        int totalWins = Integer.parseInt(record[1].toString());
        int totalLosses = Integer.parseInt(record[2].toString());
        int totalDraws = Integer.parseInt(record[3].toString());

        return new TeamCardReport(id, totalWins, totalLosses, totalDraws);
    }
}
