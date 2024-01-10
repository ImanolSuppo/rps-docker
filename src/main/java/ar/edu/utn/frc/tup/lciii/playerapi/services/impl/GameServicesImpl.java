package ar.edu.utn.frc.tup.lciii.playerapi.services.impl;

import ar.edu.utn.frc.tup.lciii.playerapi.entities.GameEntity;
import ar.edu.utn.frc.tup.lciii.playerapi.models.Game;
import ar.edu.utn.frc.tup.lciii.playerapi.repositories.jpa.GameJpaRepository;
import ar.edu.utn.frc.tup.lciii.playerapi.repositories.jpa.MatchJpaRepository;
import ar.edu.utn.frc.tup.lciii.playerapi.services.GameServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameServicesImpl implements GameServices {

    @Autowired
    private GameJpaRepository gameJpaRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public Game getGameById(long id) {
        GameEntity gameEntity = gameJpaRepository.getReferenceById(id);
        return modelMapper.map(gameEntity, Game.class);
    }
}
