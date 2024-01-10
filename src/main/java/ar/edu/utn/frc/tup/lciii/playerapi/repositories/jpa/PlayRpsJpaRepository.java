package ar.edu.utn.frc.tup.lciii.playerapi.repositories.jpa;

import ar.edu.utn.frc.tup.lciii.playerapi.entities.PlayRpsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayRpsJpaRepository extends JpaRepository<PlayRpsEntity, Long> {
}
