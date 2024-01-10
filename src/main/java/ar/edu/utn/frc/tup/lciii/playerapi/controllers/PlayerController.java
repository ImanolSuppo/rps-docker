package ar.edu.utn.frc.tup.lciii.playerapi.controllers;

import ar.edu.utn.frc.tup.lciii.playerapi.dtos.common.ErrorApi;
import ar.edu.utn.frc.tup.lciii.playerapi.entities.PlayerEntity;
import ar.edu.utn.frc.tup.lciii.playerapi.models.Match;
import ar.edu.utn.frc.tup.lciii.playerapi.models.Player;
import ar.edu.utn.frc.tup.lciii.playerapi.repositories.jpa.PlayerJpaRepository;
import ar.edu.utn.frc.tup.lciii.playerapi.services.MatchService;
import ar.edu.utn.frc.tup.lciii.playerapi.services.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/players")
public class PlayerController {

    @Autowired
    private PlayerService playerService;
    @Autowired
    private MatchService matchService;

    @GetMapping("/{id}")
    public ResponseEntity<Player> getById(@PathVariable Long id){
        return ResponseEntity.ok(playerService.getPlayerById(id));
    }

    @Operation(
            summary = "Create a new Player",
            description = "Return the player created with your id"
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content =
                @Content(schema = @Schema(implementation = Player.class))),
            @ApiResponse(responseCode = "400", description = "Username or email already exists", content =
                @Content(schema = @Schema(implementation = ErrorApi.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content =
                @Content(schema = @Schema(implementation = ErrorApi.class)))
    })

    @PostMapping("")
    public ResponseEntity<Player> savePlayer(@RequestBody @Valid Player player){
        Player playerSaved = playerService.savePlayer(player);
        if (Objects.isNull(playerSaved)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username or email already exists");
        }else {
            return ResponseEntity.ok(playerSaved);
        }
    }

    @GetMapping("/{id}/matches")
    public ResponseEntity<List<Match>> getMatchesOfPlayer(@PathVariable Long id) {
        return ResponseEntity.ok(matchService.getMatchsByPlayer(id));
    }
}
