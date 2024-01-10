package ar.edu.utn.frc.tup.lciii.playerapi.services;

import ar.edu.utn.frc.tup.lciii.playerapi.dtos.login.Credential;
import ar.edu.utn.frc.tup.lciii.playerapi.dtos.login.CredentialV2;
import ar.edu.utn.frc.tup.lciii.playerapi.models.Player;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {
    Player login(Credential credential);
    Player login(CredentialV2 credential);
}
