package ar.edu.utn.frc.tup.lciii.playerapi.services;

import ar.edu.utn.frc.tup.lciii.playerapi.entities.MatchEntity;
import ar.edu.utn.frc.tup.lciii.playerapi.entities.MatchRpsEntity;
import ar.edu.utn.frc.tup.lciii.playerapi.models.Game;
import ar.edu.utn.frc.tup.lciii.playerapi.models.Match;
import ar.edu.utn.frc.tup.lciii.playerapi.models.MatchStatus;
import ar.edu.utn.frc.tup.lciii.playerapi.models.Player;
import ar.edu.utn.frc.tup.lciii.playerapi.models.rps.MatchRps;
import ar.edu.utn.frc.tup.lciii.playerapi.models.rps.PlayRps;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;

//Esta clase Factory nos sirve para que el modelMapper pueda hacer el modelo del juego que se elija
public class MatchFactory {

    @Autowired
    private static PlayerService playerService;


    //Metodo principal. Donde el Factory se encarga de elegir de que juego hacer la partida
    public static Match createMatch(Player player, Game game){
        switch (game.getCode()) {
            case "RPS":
                return createMatchRps(player, game);
            default:
                return new MatchRps();
        }
    }

    //Este metodo crea una partida de tipo RPS
    private static Match createMatchRps(Player player, Game game) {
        MatchRps matchRps = (MatchRps) getBasicMatch(player, game); //Creamos el Match basico
        matchRps.setNumberOfPlays(10); //Seteamos numero de jugadas
        matchRps.setRemainderPlays(10); //Seteamos jugadas restantes
        matchRps.setPlayer1Score(0); //Seteamos score
        matchRps.setPlayer2Score(0); //Seteamos score
        matchRps.setPlays(new ArrayList<PlayRps>()); //Inicialisamos las jugadas
        return matchRps;
    }

    //Este metodo setea los valores de una partida basica (es decir setea los campos de Match)
    private static Match getBasicMatch(Player player, Game game) {
        Match match = getMatchInstance(game.getCode()); //Creamos la instancia del Match (new)
        match.setPlayer1(player);//Seteamos El Player
        match.setPlayer2(playerService.getPlayerById(1000000L)); //TODO: Pasar por parametro del controller el Player(si es nulo usar la PC)
        match.setGame(game); //Seteamos el Game
        match.setCreatedDate(LocalDateTime.now()); //Seteamos la fecha creada (AHORA)
        match.setStatus(MatchStatus.STARTED); //Seteamos el Status en STARTED!! (como creamos un match empieza en STARTED)
        return match;
    }

    //Este metodo instancia el Match vacio
    public static Match getMatchInstance(String gameCode){
        switch (gameCode){
            case "RPS":
                return new MatchRps();
            default:
                return new MatchRps();
        }
    }

    //Este metodo se utiliza para saber a que Match tenemos que modelar
    public static Class<? extends Match> getTypeOfMatch(MatchEntity me) {
        switch (me.getGame().getCode()) {
            case "RPS":
                return MatchRps.class;
            default:
                return MatchRps.class;
        }
    }
}
