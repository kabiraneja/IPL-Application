package io.codezone.iplapplication.controller;

import io.codezone.iplapplication.models.Team;
import io.codezone.iplapplication.repository.TeamRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teamData")
@CrossOrigin
public class TeamController {
    private TeamRepositry teamRepositry;
    @Autowired
    TeamController(TeamRepositry teamRepositry) {
        this.teamRepositry = teamRepositry;
    }

    @GetMapping("/team/{teamName}")
    public Team getByTeam(@PathVariable("teamName") String teamName) {
        return teamRepositry.getByTeamName(teamName);
    }

    @GetMapping("/team/all-teams")
    public List<Team> getAllTeams() {
        List<Team>list = teamRepositry.findAll();
        return list;
    }

    @GetMapping("/team/total-matches-greater100/{matches}")
    public List<Team> getTotalMatches(@PathVariable("matches") Long matches) {
        List<Team>list = teamRepositry.getByTotalMatchesGreaterThan(matches);
        return list;
    }

}
