package ar.edu.utn.frc.tup.lciii.playerapi.services;

import ar.edu.utn.frc.tup.lciii.playerapi.models.Match;
import ar.edu.utn.frc.tup.lciii.playerapi.models.Play;
import org.springframework.stereotype.Service;

@Service
public interface PlayMatch <P extends Play, M extends Match> {
    P play(P play, M match);
}
