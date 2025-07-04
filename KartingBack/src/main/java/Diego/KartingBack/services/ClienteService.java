package Diego.KartingBack.services;

import Diego.KartingBack.entities.ClienteEntity;
import Diego.KartingBack.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository; // ✅ Instancia inyectada

    public ClienteEntity buscarORegistrarCliente(String nombre, boolean cumpleanos) {
        Optional<ClienteEntity> clienteExistente = clienteRepository.findByNombre(nombre); //

        if (clienteExistente.isPresent()) {
            ClienteEntity cliente = clienteExistente.get();
            cliente.setVisitasMensuales(cliente.getVisitasMensuales() + 1);
            cliente.setCumpleanos(cumpleanos);
            return clienteRepository.save(cliente);
        } else {
            ClienteEntity nuevoCliente = new ClienteEntity(nombre, 1, cumpleanos);
            return clienteRepository.save(nuevoCliente);
        }
    }

    public Optional<ClienteEntity> buscarClientePorNombre(String nombre) {
        return clienteRepository.findByNombre(nombre); // ✅ Usa la instancia correctamente
    }

}
