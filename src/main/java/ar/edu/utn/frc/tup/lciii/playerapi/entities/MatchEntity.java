package ar.edu.utn.frc.tup.lciii.playerapi.entities;

import ar.edu.utn.frc.tup.lciii.playerapi.models.MatchStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "matches")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "game_id") //Relacion a la tabla Game
    @ManyToOne //Un game tiene muchas partidas
    private GameEntity game;

    @JoinColumn(name = "player1_id") //Relacion a la tabla Player
    @ManyToOne //Un player tiene muchas partidas
    private PlayerEntity player1;
    @JoinColumn(name = "player2_id") //Relacion a la tabla Player
    @ManyToOne //Un player tiene muchas partidas
    private PlayerEntity player2;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @Column
    @Enumerated(EnumType.STRING)
    private MatchStatus status;
}
