package ar.edu.utn.frc.tup.lciii.playerapi.controllers;

import ar.edu.utn.frc.tup.lciii.playerapi.dtos.match.MatchDTO;
import ar.edu.utn.frc.tup.lciii.playerapi.dtos.play.PlayDTO;
import ar.edu.utn.frc.tup.lciii.playerapi.models.Match;
import ar.edu.utn.frc.tup.lciii.playerapi.models.rps.PlayRps;
import ar.edu.utn.frc.tup.lciii.playerapi.services.PlayRpsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@RestController
@RequestMapping("/plays_rps")
public class PlayRPSController {

    @Autowired
    private PlayRpsService playRPSService;

    @GetMapping("/{id}")
    public ResponseEntity<PlayRps> getById(@PathVariable Long id){
        return ResponseEntity.ok(playRPSService.getPlayById(id));
    }

    //Este controller no se va a usar
    @PostMapping("")
    public ResponseEntity<PlayRps> savePlay(@RequestBody @Valid PlayDTO playDTO){
        PlayRps playSaved = playRPSService.createPlay(playDTO);
        if (Objects.isNull(playSaved)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The request has error");
        }else {
            return ResponseEntity.ok(playSaved);
        }
    }
}
