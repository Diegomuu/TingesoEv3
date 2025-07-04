package Diego.KartingBack.controllers;

import Diego.KartingBack.entities.KartEntity;
import Diego.KartingBack.services.KartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/karts")
@CrossOrigin
public class KartController {

    @Autowired
    private KartService kartService;

    @PostMapping("/agregar")
    public KartEntity crearKart(@RequestBody KartEntity nuevoKart) {
        System.out.println("Recibido JSON -> estado: " + nuevoKart.getEstado() + ", modelo: " + nuevoKart.getModelo());
        return kartService.agregarKart(nuevoKart);
    }

    @GetMapping("/ver")
    public List<KartEntity> obtenerTodos() {
        return kartService.obtenerTodos();
    }

}
