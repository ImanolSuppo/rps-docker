package ar.edu.utn.frc.tup.lciii.playerapi.services.impl;

import ar.edu.utn.frc.tup.lciii.playerapi.dtos.play.PlayDTO;
import ar.edu.utn.frc.tup.lciii.playerapi.entities.MatchEntity;
import ar.edu.utn.frc.tup.lciii.playerapi.entities.PlayRpsEntity;
import ar.edu.utn.frc.tup.lciii.playerapi.models.Match;
import ar.edu.utn.frc.tup.lciii.playerapi.models.MatchStatus;
import ar.edu.utn.frc.tup.lciii.playerapi.models.rps.MatchRps;
import ar.edu.utn.frc.tup.lciii.playerapi.models.rps.PlayRps;
import ar.edu.utn.frc.tup.lciii.playerapi.models.rps.ShapeHand;
import ar.edu.utn.frc.tup.lciii.playerapi.repositories.jpa.PlayRpsJpaRepository;
import ar.edu.utn.frc.tup.lciii.playerapi.services.MatchFactory;
import ar.edu.utn.frc.tup.lciii.playerapi.services.MatchService;
import ar.edu.utn.frc.tup.lciii.playerapi.services.PlayRpsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class PlayRpsServiceImpl implements PlayRpsService {

    @Autowired
    MatchService matchService;
    @Autowired
    PlayRpsJpaRepository playRpsJpaRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PlayRps createPlay(PlayDTO playDTO) {
        PlayRps playRps = new PlayRps();
        MatchRps matchRps = (MatchRps) matchService.getMatchsById(playDTO.getMatchRpsId());
        if(matchRps == null){
            return null;
        }
        if(matchRps.getStatus() == MatchStatus.STARTED){
            jugarRps(playRps, matchRps, playDTO);
            matchService.updateMatch(matchRps);
            PlayRpsEntity playRpsEntity = playRpsJpaRepository.save(modelMapper.map(playRps, PlayRpsEntity.class));
            return modelMapper.map(playRpsEntity, PlayRps.class);
        }
        else{
            return null;
        }

    }

    @Override
    public PlayRps getPlayById(Long id) {
        Optional<PlayRpsEntity> playRpsEntity = playRpsJpaRepository.findById(id);
        if (playRpsEntity.isPresent()){
            return modelMapper.map(playRpsEntity, PlayRps.class);
        }
        return null;
    }

    private void jugarRps(PlayRps playRps, MatchRps matchRps, PlayDTO playDTO) {
        Random random = new Random();
        ShapeHand jugadaPC = ShapeHand.values()[random.nextInt(3)];
        ShapeHand jugadaPlayer = playDTO.getShapeHandPlayer1();
        matchRps.setRemainderPlays(matchRps.getRemainderPlays() + 1);
        if (jugadaPlayer == jugadaPC) {
            matchRps.setPlayer1Score(matchRps.getPlayer1Score() + 1);
            matchRps.setPlayer2Score(matchRps.getPlayer2Score() + 1);

        } else if ((jugadaPlayer == ShapeHand.ROCK && jugadaPC == ShapeHand.SCISSORS)
                || (jugadaPlayer == ShapeHand.PAPER && jugadaPC == ShapeHand.ROCK)
                || (jugadaPlayer == ShapeHand.SCISSORS && jugadaPC == ShapeHand.PAPER)) {

            matchRps.setPlayer1Score(matchRps.getPlayer1Score() + 2);
            playRps.setWinnerId(matchRps.getPlayer1().getId());
        } else {
            matchRps.setPlayer2Score(matchRps.getPlayer2Score() + 2);
            playRps.setWinnerId(1000000L);
        }
        playRps.setMatchRpsId(matchRps.getId());
        playRps.setShapeHandPlayer1(jugadaPlayer);
        playRps.setShapeHandPlayer2(jugadaPC);
        matchRps.getPlays().add(playRps);
        if(matchRps.getRemainderPlays() == matchRps.getNumberOfPlays()){
            matchRps.setStatus(MatchStatus.FINISHED);
            if(matchRps.getPlayer1Score() > matchRps.getPlayer2Score())
                matchRps.setWinnerId(matchRps.getPlayer2().getId());
            if(matchRps.getPlayer1Score() < matchRps.getPlayer2Score())
                matchRps.setWinnerId(1000000L);
        }
    }
}
