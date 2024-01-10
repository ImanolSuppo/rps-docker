package ar.edu.utn.frc.tup.lciii.playerapi.entities;

import jakarta.persistence.*;
import jdk.jfr.Label;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "games")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String code;

    @Column
    private String name;

    @Lob //Para agrandar el espacio del varchar en la BD
    @Column
    private String description;

    @Lob //Para agrandar el espacio del varchar en la BD
    @Column
    private String rules;

}
