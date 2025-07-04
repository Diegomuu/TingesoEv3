package Diego.KartingBack.repositories;

import Diego.KartingBack.entities.RackSemanalEntity;
import Diego.KartingBack.entities.SlotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.Optional;

@Repository
public interface SlotRepository extends JpaRepository<SlotEntity, Long> {
    Optional<SlotEntity>
    findFirstByRackSemanalAndDiaSemanaAndOcupadoFalse(
            RackSemanalEntity rack, DayOfWeek diaSemana);
}
