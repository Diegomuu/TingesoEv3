package Diego.KartingBack.repositories;

import Diego.KartingBack.entities.DetallePagoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetallePagoRepository extends JpaRepository<DetallePagoEntity,Long> {
}
