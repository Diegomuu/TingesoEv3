package Diego.KartingBack.controllers;

import Diego.KartingBack.entities.ClienteEntity;
import Diego.KartingBack.repositories.ClienteRepository;
import Diego.KartingBack.services.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<ClienteEntity> registrarCliente(@RequestBody ClienteEntity clienteRequest) {
        ClienteEntity cliente = clienteService.buscarORegistrarCliente(
                clienteRequest.getNombre(), clienteRequest.isCumpleanos()
        );
        return ResponseEntity.ok(cliente);
    }

    @GetMapping("/buscar/{nombre}")
    public ResponseEntity<?> buscarCliente(@PathVariable String nombre) {
        Optional<ClienteEntity> clienteExistente = clienteService.buscarClientePorNombre(nombre);

        if (clienteExistente.isPresent()) {
            return ResponseEntity.ok(clienteExistente.get()); // ✅ Devuelve el cliente si existe
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado"); // ✅ Devuelve 404
        }
    }

}
