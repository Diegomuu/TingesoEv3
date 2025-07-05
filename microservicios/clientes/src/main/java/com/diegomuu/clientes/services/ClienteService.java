package com.diegomuu.clientes.services;

import com.diegomuu.clientes.entity.ClienteEntity;
import com.diegomuu.clientes.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    public ClienteEntity obtenerCliente(Long id) {
        return clienteRepository.findById(id).orElse(null);
    }

    public void registrarCliente(ClienteEntity cliente) {
        clienteRepository.save(cliente);
    }

    public Optional<ClienteEntity> buscarClientePorNombre(String nombre) {
        return clienteRepository.findByNombreIgnoreCase(nombre);
    }

    @Transactional
    public void incrementarVisitasMensuales(List<Long> clienteIds) {
        for (Long id : clienteIds) {
            ClienteEntity cliente = clienteRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado: " + id));
            cliente.setVisitasMensuales(cliente.getVisitasMensuales() + 1);
            clienteRepository.save(cliente);
        }
    }

}

