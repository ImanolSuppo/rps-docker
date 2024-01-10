package ar.edu.utn.frc.tup.lciii.playerapi.dtos.play;

import ar.edu.utn.frc.tup.lciii.playerapi.models.rps.ShapeHand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayDTO {

    private Long matchRpsId;
    private ShapeHand shapeHandPlayer1;
}
