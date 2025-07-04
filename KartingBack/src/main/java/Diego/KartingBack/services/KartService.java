package Diego.KartingBack.services;

import Diego.KartingBack.entities.KartEntity;
import Diego.KartingBack.repositories.KartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KartService {

    @Autowired
    private KartRepository kartRepository;

    public KartEntity agregarKart(KartEntity kart){
        return kartRepository.save(kart);
    }

    public List<KartEntity> obtenerTodos() {
        return kartRepository.findAll();
    }
}
