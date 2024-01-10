package ar.edu.utn.frc.tup.lciii.playerapi.repositories.jpa;

import ar.edu.utn.frc.tup.lciii.playerapi.entities.MatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchJpaRepository extends JpaRepository<MatchEntity, Long> {

    @Query("SELECT m FROM MatchEntity m WHERE m.player1.id = :player1Id")
    Optional<List<MatchEntity>> getAllByPlayerId(Long player1Id);
}
