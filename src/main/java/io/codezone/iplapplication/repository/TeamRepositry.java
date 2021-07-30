package io.codezone.iplapplication.repository;

import io.codezone.iplapplication.models.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepositry extends JpaRepository<Team,Long> {

    public Team getByTeamName(String TeamName);
    public List<Team> getByTotalMatchesGreaterThan(Long TotalMatches);

}
