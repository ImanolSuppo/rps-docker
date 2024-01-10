package ar.edu.utn.frc.tup.lciii.playerapi.models;


import ar.edu.utn.frc.tup.lciii.playerapi.utils.validations.password.ValidPassword;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Player {

    @Schema(title = "Player ID", description = "the player id", example = "1")
    private Long id;

    @Schema(title = "Player user name", description = "the player user name", example = "Imanol", nullable = false)
    @NotNull(message = "UserName can't by null")
    private String userName;

    @Schema(title = "Player password", description = "the player password", example = "Password123", nullable = false)
    @NotNull(message = "password can't by null")
    @ValidPassword
    private String password;

    @Schema(title = "Player email", description = "the player email", example = "email@email.com", nullable = false)
    @NotNull(message = "email can't by null")
    @Email(message = "The email need to be a valid email")
    private String email;

    @Schema(title = "Player avatar url", description = "the player avatar ", example = "https://localhost:8080/avatars/myUserName", nullable = true)
    private String avatar;

    @Schema(title = "Player last login", description = "the player last login", example = "01-01-2020 21:10_50", nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime lastLoginDate;

}
