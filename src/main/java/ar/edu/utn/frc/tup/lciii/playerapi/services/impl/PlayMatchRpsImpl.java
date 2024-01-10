package ar.edu.utn.frc.tup.lciii.playerapi.services.impl;

import ar.edu.utn.frc.tup.lciii.playerapi.entities.MatchRpsEntity;
import ar.edu.utn.frc.tup.lciii.playerapi.entities.PlayRpsEntity;
import ar.edu.utn.frc.tup.lciii.playerapi.models.MatchStatus;
import ar.edu.utn.frc.tup.lciii.playerapi.models.rps.MatchRps;
import ar.edu.utn.frc.tup.lciii.playerapi.models.rps.PlayRps;
import ar.edu.utn.frc.tup.lciii.playerapi.models.rps.ShapeHand;
import ar.edu.utn.frc.tup.lciii.playerapi.repositories.jpa.MatchJpaRepository;
import ar.edu.utn.frc.tup.lciii.playerapi.repositories.jpa.PlayRpsJpaRepository;
import ar.edu.utn.frc.tup.lciii.playerapi.services.MatchService;
import ar.edu.utn.frc.tup.lciii.playerapi.services.PlayMatch;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;

@Service
public class PlayMatchRpsImpl implements PlayMatch<PlayRps, MatchRps> {

    @Autowired
    private MatchJpaRepository matchJpaRepository;

    @Autowired
    private PlayRpsJpaRepository playRpsJpaRepository;
    @Autowired
    private ModelMapper modelMapper;

    private Random random = new Random();

    @Override
    public PlayRps play(PlayRps play, MatchRps match) {
        play.setMatchRpsId(match.getId());
        if (Objects.isNull(play.getShapeHandPlayer2())) {
            play.setShapeHandPlayer2(getRamdomShapeHand());
        }
        evaluatePlay(play, match);
        calculateMatchScore(play, match);
        calculateMatchStatus(play, match);
        match.setUpdatedAt(LocalDateTime.now());
        playRpsJpaRepository.save(modelMapper.map(play, PlayRpsEntity.class));
        matchJpaRepository.save(modelMapper.map(match, MatchRpsEntity.class));
        return play;
    }

    private void calculateMatchStatus(PlayRps play, MatchRps match) {
        match.setRemainderPlays(match.getRemainderPlays() - 1);
        if (match.getRemainderPlays() == 0) {
            match.setStatus(MatchStatus.FINISHED);
        }
        if (!isMatchTie(match)) {
            if (match.getPlayer1Score() > match.getPlayer2Score()) {
                match.setWinnerId(match.getPlayer1().getId());
            } else {
                match.setWinnerId(match.getPlayer2().getId());
            }
        }
    }

    private boolean isMatchTie(MatchRps match) {
        return match.getPlayer1Score().equals(match.getPlayer2Score());
    }

    private void calculateMatchScore(PlayRps play, MatchRps match) {
        if (Objects.nonNull(play.getWinnerId())) {
            if (play.getWinnerId().equals(match.getPlayer1().getId())) {
                match.setPlayer1Score(match.getPlayer1Score() + 1);
            } else {
                match.setPlayer2Score(match.getPlayer2Score() + 1);
            }
        }
    }

    private void evaluatePlay(PlayRps play, MatchRps match) {
        if (!isPlayTie(play)) {
            setWinner(play, match);
        }
    }

    private void setWinner(PlayRps play, MatchRps match) {
        if ((play.getShapeHandPlayer1() == ShapeHand.ROCK && play.getShapeHandPlayer2() == ShapeHand.SCISSORS)
                || (play.getShapeHandPlayer1() == ShapeHand.PAPER && play.getShapeHandPlayer2() == ShapeHand.ROCK)
                || (play.getShapeHandPlayer1() == ShapeHand.SCISSORS && play.getShapeHandPlayer2() == ShapeHand.PAPER)) {
            play.setWinnerId(match.getPlayer1().getId());
        } else {
            play.setWinnerId(match.getPlayer2().getId());
        }
    }

    private boolean isPlayTie(PlayRps play) {
        return play.getShapeHandPlayer1().equals(play.getShapeHandPlayer2());
    }

    private ShapeHand getRamdomShapeHand() {
        Integer randomNumber = random.nextInt(3);
        return ShapeHand.values()[randomNumber];
    }
}
