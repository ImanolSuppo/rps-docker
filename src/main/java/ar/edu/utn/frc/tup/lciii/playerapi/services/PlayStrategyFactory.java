package ar.edu.utn.frc.tup.lciii.playerapi.services;

import ar.edu.utn.frc.tup.lciii.playerapi.models.rps.MatchRps;
import ar.edu.utn.frc.tup.lciii.playerapi.models.rps.PlayRps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlayStrategyFactory {
    @Autowired
    private PlayMatch<PlayRps, MatchRps> playMatchRps;

    public PlayMatch getPlayStrategy(String gameCode){
        switch (gameCode){
            case "RPS":
                return playMatchRps;
            default:
                return playMatchRps;
        }
    }
}
