package Diego.KartingBack.services;

import Diego.KartingBack.entities.ComprobanteEntity;
import Diego.KartingBack.entities.DetallePagoEntity;
import Diego.KartingBack.repositories.ComprobanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ComprobanteService {

    @Autowired
    private ComprobanteRepository comprobanteRepository;

    @Autowired
    private RackSemanalService rackSemanalService;



}
