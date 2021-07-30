package io.codezone.iplapplication.repository;

import io.codezone.iplapplication.models.Match;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MatchRepositry extends JpaRepository<Match,Long> {

    List<Match> findByWinner(String teamName);
    List<Match> findByWinnerAndTossWinner(String S1,String S2);
    @Query("SELECT m FROM Match m where m.team1 = :teamName OR m.team2 = :teamName ORDER BY m.date DESC")
    List<Match> getLatestMatchFromTeamName(@Param("teamName") String TeamName, Pageable pagable);
    @Query("SELECT m FROM Match m WHERE m.season=:season AND m.winner=:teamName AND (m.team1 =:teamName OR m.team2 =:teamName)")
    List<Match> getSeasonAndAllMatchWonByTeam(@Param("season") String Season ,@Param("teamName") String TeamName);
}
