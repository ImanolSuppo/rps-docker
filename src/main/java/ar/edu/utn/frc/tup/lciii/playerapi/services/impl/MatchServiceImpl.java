package ar.edu.utn.frc.tup.lciii.playerapi.services.impl;

import ar.edu.utn.frc.tup.lciii.playerapi.dtos.match.MatchDTO;
import ar.edu.utn.frc.tup.lciii.playerapi.dtos.play.PlayRequest;
import ar.edu.utn.frc.tup.lciii.playerapi.entities.MatchEntity;
import ar.edu.utn.frc.tup.lciii.playerapi.models.*;
import ar.edu.utn.frc.tup.lciii.playerapi.models.rps.MatchRps;
import ar.edu.utn.frc.tup.lciii.playerapi.repositories.jpa.MatchEntityFactory;
import ar.edu.utn.frc.tup.lciii.playerapi.repositories.jpa.MatchJpaRepository;
import ar.edu.utn.frc.tup.lciii.playerapi.services.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MatchServiceImpl implements MatchService {

    @Autowired
    private MatchJpaRepository matchJpaRepository;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private GameServices gameServices;
    @Autowired
    private PlayStrategyFactory playStrategyFactory;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public List<Match> getMatchsByPlayer(Long playerId) {
        List<Match> matches = new ArrayList<>();
        Optional<List<MatchEntity>> optionalMatchEntities = matchJpaRepository.getAllByPlayerId(playerId);
        if(optionalMatchEntities.isPresent()) { //Error: No trae los matchRPS
            //Como es una lista tenemos que mapear con un forEach. En este caso tambien lo modelamos con la Factory
            optionalMatchEntities.get().forEach(me -> {matches.add(modelMapper.map(me, MatchFactory.getTypeOfMatch(me)));});
        }
        return matches;
    }

    @Override
    public Match createMatch(MatchDTO matchDTO) {
        Player player = playerService.getPlayerById(matchDTO.getPlayerId()); //Obtenemos el jugador
        Game game = gameServices.getGameById(matchDTO.getGameId()); //Obtenemos el Juego
        Match match = MatchFactory.createMatch(player, game); //Creamos la partida con el Factory
        MatchEntity matchEntity = matchJpaRepository.save(modelMapper.map(match, MatchEntityFactory.getTypeOfMatch(match))); //Mapeamos primero a ENTITY para ir al JPARepository
        return modelMapper.map(matchEntity, MatchFactory.getTypeOfMatch(matchEntity)); //Volvemos a mapearlo al Model Match para devolverlo al controller
    }

    @Override
    public Match getMatchsById(Long matchRpsId) {
        //otra solucion para obtener el entity
        //MatchEntity matchEntity = (MatchEntity) Hibernate.unproxy(matchJpaRepository.getReferenceById(matchRpsId));
        Optional<MatchEntity> matchEntity = matchJpaRepository.findById(matchRpsId);
        if (matchEntity.isPresent()){
            return modelMapper.map(matchEntity, MatchFactory.getTypeOfMatch(matchEntity.get()));
        }
        return null;
    }

    //TODO ELIMINAR METODO
    @Override
    public Match updateMatch(MatchRps match) {
        Optional<MatchEntity> matchEntityOptional = matchJpaRepository.findById(match.getId());
        if (matchEntityOptional.isPresent()) {
            MatchRps matchUpdate = modelMapper.map(matchEntityOptional.get(), MatchRps.class);
            // Actualizar los datos necesarios en el objeto MatchEntity
            matchUpdate.setStatus(match.getStatus()); // Actualiza el estado del partido
            matchUpdate.setRemainderPlays(match.getRemainderPlays());
            matchUpdate.setStatus(match.getStatus());
            matchUpdate.setPlayer1Score(match.getPlayer1Score());
            matchUpdate.setPlayer2Score(match.getPlayer2Score());
            matchUpdate.setPlays(match.getPlays());
            matchUpdate.setWinnerId(match.getWinnerId());
            // Guardar los cambios en la base de datos
            Match pasarMatch = matchUpdate;
            MatchEntity updatedMatchEntity = matchJpaRepository.save(modelMapper.map(match, MatchEntityFactory.getTypeOfMatch(match)));

            // Devolver el objeto Match actualizado
            return modelMapper.map(updatedMatchEntity, MatchFactory.getTypeOfMatch(updatedMatchEntity));
        }
        return null;
    }

    @Transactional
    @Override
    public Play play(Long id, PlayRequest playRequest) {
        Match match = this.getMatchsById(id);
        if(match == null){
            throw new EntityNotFoundException();
        }
        if(match.getStatus() != MatchStatus.STARTED) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("the match is %s", match.getStatus()));
        }
        Play play = PlayFactory.getPlayInstance(playRequest, match.getGame().getCode());
        PlayMatch playMatch = playStrategyFactory.getPlayStrategy(match.getGame().getCode());
        return playMatch.play(play, match);
    }

}
