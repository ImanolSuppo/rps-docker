package ar.edu.utn.frc.tup.lciii.playerapi.services;

import ar.edu.utn.frc.tup.lciii.playerapi.dtos.match.MatchDTO;
import ar.edu.utn.frc.tup.lciii.playerapi.dtos.play.PlayRequest;
import ar.edu.utn.frc.tup.lciii.playerapi.models.Match;
import ar.edu.utn.frc.tup.lciii.playerapi.models.Play;
import ar.edu.utn.frc.tup.lciii.playerapi.models.rps.MatchRps;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MatchService {

    List<Match> getMatchsByPlayer(Long playerId);

    Match createMatch(MatchDTO match);

    Match getMatchsById(Long id);

    Match updateMatch(MatchRps match);

    Play play(Long id, PlayRequest playRequest);
}
