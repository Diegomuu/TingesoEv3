package Diego.KartingBack.repositories;

import Diego.KartingBack.entities.RackSemanalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface RackSemanalRepository extends JpaRepository<RackSemanalEntity, Long> {

    Optional<RackSemanalEntity> findBySemanaInicio(LocalDate semanaInicio);

}
