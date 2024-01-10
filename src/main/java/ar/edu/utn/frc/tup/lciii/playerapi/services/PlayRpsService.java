package ar.edu.utn.frc.tup.lciii.playerapi.services;

import ar.edu.utn.frc.tup.lciii.playerapi.dtos.play.PlayDTO;
import ar.edu.utn.frc.tup.lciii.playerapi.models.rps.PlayRps;
import org.springframework.stereotype.Service;

@Service
public interface PlayRpsService {
    PlayRps createPlay(PlayDTO playDTO);
    PlayRps getPlayById(Long id);
}
