package io.codezone.iplapplication.processor;

import io.codezone.iplapplication.models.Match;
import io.codezone.iplapplication.models.MatchInput;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDate;

public class MatchProcessor implements ItemProcessor<MatchInput,Match> {

    private static final Logger log = (Logger) LoggerFactory.getLogger(MatchProcessor.class);

    @Override
    public Match process(final MatchInput matchInput) throws Exception {
        Match match = new Match();
        match.setId(Long.parseLong(matchInput.getId()));
        match.setCity(matchInput.getCity());
        match.setDate(LocalDate.parse(matchInput.getDate()));
        match.setSeason(matchInput.getDate().split("-")[0]);
        match.setPlayerOfMatch(matchInput.getPlayerOfMatch());
        match.setVenue(matchInput.getVenue());

        String team1 , team2;

        if("bat".equalsIgnoreCase(matchInput.getTossDecision())) {
            team1 = matchInput.getTossWinner();
            team2 = matchInput.getTossWinner().equals(matchInput.getTeam1()) ?
                    matchInput.getTeam2() : matchInput.getTeam1();
        }
        else {
            team1 = matchInput.getTossWinner().equals(matchInput.getTeam1()) ?
                    matchInput.getTeam2() : matchInput.getTeam1();
            team2 = matchInput.getTossWinner();
        }

        match.setTeam1(team1);
        match.setTeam2(team2);
        match.setTossDecision(matchInput.getTossDecision());
        match.setTossWinner(matchInput.getTossWinner());
        match.setWinner(matchInput.getWinner());
        match.setResult(matchInput.getResult());
        match.setResultMargin(matchInput.getResultMargin());
        match.setUmpire1(matchInput.getUmpire1());
        match.setUmpire2(matchInput.getUmpire2());

        log.info("Converting (" + matchInput.toString() + ") into (" + match.toString() + ")");
        return match;
    }


}
