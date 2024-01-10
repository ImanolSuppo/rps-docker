package ar.edu.utn.frc.tup.lciii.playerapi.repositories.jpa;

import ar.edu.utn.frc.tup.lciii.playerapi.entities.MatchEntity;
import ar.edu.utn.frc.tup.lciii.playerapi.entities.MatchRpsEntity;
import ar.edu.utn.frc.tup.lciii.playerapi.models.Match;


//Esta clase se utiliza para saber a que Entity tenemos que modelar para el JpaRepository
public class MatchEntityFactory {

    public static Class<? extends MatchEntity> getTypeOfMatch(Match match){
        switch (match.getGame().getCode()) {
            case "RPS":
                return MatchRpsEntity.class;
            default:
                return MatchRpsEntity.class;
        }
    }
}
