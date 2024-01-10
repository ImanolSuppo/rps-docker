package ar.edu.utn.frc.tup.lciii.playerapi.services;

import ar.edu.utn.frc.tup.lciii.playerapi.models.Game;
import org.springframework.stereotype.Service;

@Service
public interface GameServices {

    Game getGameById(long id);
}
