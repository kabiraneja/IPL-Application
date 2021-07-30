package io.codezone.iplapplication.controller;

import io.codezone.iplapplication.models.Match;
import io.codezone.iplapplication.models.Team;
import io.codezone.iplapplication.repository.MatchRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/matchData")
@CrossOrigin
public class MatchController {
    private MatchRepositry matchRepositry;
    TeamController T;
    @Autowired
    MatchController(MatchRepositry matchRepositry ,TeamController T) {
        this.matchRepositry = matchRepositry;
        this.T = T;
    }

    @GetMapping("/team/{teamName}")
    public Team getTeam(@PathVariable("teamName") String teamName) {

        Team team = T.getByTeam(teamName);
        Pageable pageable = PageRequest.of(0,4);
        List<Match>latestMatches = matchRepositry.getLatestMatchFromTeamName(teamName,pageable);
        team.setLatestMatches(latestMatches);
        return team;
    }

    @GetMapping("/matchwinner/{teamName}")
    public List<Match> getAllMatchesWonBy(@PathVariable("teamName") String teamName) {
        return matchRepositry.findByWinner(teamName);
    }

    @GetMapping("/matchwinner/{winner}/tosswinner/{toss}")
    public List<Match> getAllWinnersAndTossWinner(@PathVariable("winner") String winner , @PathVariable("toss") String toss) {
        return matchRepositry.findByWinnerAndTossWinner(winner,toss);
    }

    @GetMapping("/season/{season}/winner/{winner}")
    public List<Match> seasonAndAllMatchWon(@PathVariable("season") String season , @PathVariable("winner") String winner) {
        List<Match>list = matchRepositry.getSeasonAndAllMatchWonByTeam(season,winner);
        return list;
    }
}
