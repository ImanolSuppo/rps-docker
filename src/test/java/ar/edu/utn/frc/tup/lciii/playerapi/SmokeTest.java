package ar.edu.utn.frc.tup.lciii.playerapi;
import static org.assertj.core.api.Assertions.assertThat;

import ar.edu.utn.frc.tup.lciii.playerapi.controllers.PingController;
import ar.edu.utn.frc.tup.lciii.playerapi.services.PlayerService;
import org.junit.jupiter.api.Test;
import ar.edu.utn.frc.tup.lciii.playerapi.controllers.PlayerController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SmokeTest {
    @Autowired
    private PlayerController playerController;

    @Autowired
    private PingController pingController;

    @Autowired
    private PlayerService playerService;

    @Test
    public void contextLoads() throws Exception {
        assertThat(playerController).isNotNull();
        assertThat(pingController).isNotNull();
        assertThat(playerService).isNotNull();
    }
}
