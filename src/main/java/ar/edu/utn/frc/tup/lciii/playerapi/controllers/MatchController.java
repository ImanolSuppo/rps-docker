package ar.edu.utn.frc.tup.lciii.playerapi.controllers;

import ar.edu.utn.frc.tup.lciii.playerapi.dtos.match.MatchDTO;
import ar.edu.utn.frc.tup.lciii.playerapi.dtos.play.PlayRequest;
import ar.edu.utn.frc.tup.lciii.playerapi.models.Match;
import ar.edu.utn.frc.tup.lciii.playerapi.models.Play;
import ar.edu.utn.frc.tup.lciii.playerapi.models.Player;
import ar.edu.utn.frc.tup.lciii.playerapi.services.MatchService;
import ar.edu.utn.frc.tup.lciii.playerapi.services.PlayerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@RestController
@RequestMapping("/matches")
public class MatchController {

    @Autowired
    private MatchService matchService;


    @GetMapping("/{id}")
    public ResponseEntity<Match> getById(@PathVariable Long id){
        return ResponseEntity.ok(matchService.getMatchsById(id));
    }
    @PostMapping("")
    public ResponseEntity<Match> saveMatch(@RequestBody @Valid MatchDTO matchDTO){
        Match matchSaved = matchService.createMatch(matchDTO);
        if (Objects.isNull(matchSaved)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The request has error");
        }else {
            return ResponseEntity.ok(matchSaved);
        }
    }
    @PostMapping("/{id}/plays")
    public ResponseEntity<Play> saveMatch(@PathVariable Long id, @RequestBody @Valid PlayRequest playRequest){
        Play playresult = matchService.play(id, playRequest);
        if (Objects.isNull(playresult)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The request has error");
        }else {
            return ResponseEntity.ok(playresult);
        }
    }


}
