package io.codezone.iplapplication.config;

import io.codezone.iplapplication.models.Team;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    private final EntityManager entityManager;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JobCompletionNotificationListener(EntityManager entityManager, JdbcTemplate jdbcTemplate) {
        this.entityManager = entityManager;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");

//            jdbcTemplate.query("SELECT team1, team2, date, winner FROM match",
//                    (rs, row) -> "Team1: " + rs.getString(1) +",Team2 :" +
//                            rs.getString(2) + ",Date:" + rs.getString(3));
//            ).forEach(match-> System.out.println(match));


//            List<Object[]> list =  entityManager.createQuery("SELECT team1, team2, date, winner FROM Match", Object[].class).getResultList();
//            for(Object[] obj:list) {
//                System.out.println("Team1:" + obj[0] + "Team2:" + obj[1] + "Date" + obj[2] + "Winner:" + obj[3]);
//            }

            Map<String, Team> map = new HashMap<>();
            List<Object[]> lTeam1 = entityManager.createQuery("SELECT team1, count(*) FROM Match group by team1",Object[].class).getResultList();
            for(Object[] obj:lTeam1) {
                String teamName = (String)obj[0];
                long matchCount = (long)obj[1];

                Team team = new Team();
                team.setTeamName(teamName);
                team.setTotalMatches(matchCount);

                map.put(teamName,team);
            }

            List<Object[]> lTeam2 = entityManager.createQuery("SELECT team2, count(*) FROM Match group by team2",Object[].class).getResultList();
            for(Object[] obj:lTeam2) {
                String teamName = (String)obj[0];
                long matchCount = (long)obj[1];

                Team team = map.get(teamName);
                if(team!=null)
                team.setTotalMatches(matchCount+team.getTotalMatches());

                map.put(teamName,team);
            }

            List<Object[]> lWinner = entityManager.createQuery("SELECT winner, count(*) FROM Match group by winner",Object[].class).getResultList();
            for(Object[] obj:lWinner) {
                String teamName = (String)obj[0];
                long winnerCount = (long)obj[1];

                Team team = map.get(teamName);
                if(team!=null)
                    team.setTotalWins(winnerCount);
                else System.out.println("Testing null");
            }

            for(Team team:map.values()) {
                System.out.println(team.toString());
                entityManager.persist(team);
            }
        }
    }

}
