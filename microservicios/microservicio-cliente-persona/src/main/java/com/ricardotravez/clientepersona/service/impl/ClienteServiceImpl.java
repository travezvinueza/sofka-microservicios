package com.ricardotravez.clientepersona.service.impl;

import com.ricardotravez.clientepersona.clientApi.ClienteApi;
import com.ricardotravez.clientepersona.dto.ClienteDTO;
import com.ricardotravez.clientepersona.dto.CuentaDTO;
import com.ricardotravez.clientepersona.entity.Cliente;
import com.ricardotravez.clientepersona.entity.MensajeError;
import com.ricardotravez.clientepersona.exception.RecursoNoEncontradoException;
import com.ricardotravez.clientepersona.repository.ClienteRepository;
import com.ricardotravez.clientepersona.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final ModelMapper modelMapper;
    private final ClienteApi clienteApi;


    @Override
    public ClienteDTO crear(ClienteDTO clienteDTO) {
        Optional<Cliente> clienteExistenteOptional = clienteRepository.findByIdentificacion(clienteDTO.getIdentificacion());
        if (clienteExistenteOptional.isPresent()) {
            throw new RecursoNoEncontradoException("Ya existe un cliente con la misma identificación");
        }
        clienteDTO.setFecha(LocalDate.now());
        Cliente cliente = modelMapper.map(clienteDTO, Cliente.class);
        return modelMapper.map(clienteRepository.save(cliente), ClienteDTO.class);
    }

    @Override
    public List<ClienteDTO> listar() {
        try {
            return clienteRepository.findAll().stream().map(
                    (cliente) -> {
                        ClienteDTO clienteDTO = modelMapper.map(cliente, ClienteDTO.class);
                        List<CuentaDTO> cuentaDTO = clienteApi.getCuentaPorClienteId(clienteDTO.getId().toString());
                        clienteDTO.setCuentas(cuentaDTO);
                        return clienteDTO;
                    }
            ).collect(Collectors.toList());
        }catch (Exception e){
            throw new RecursoNoEncontradoException("Error obteniendo datos de cliente "+ e.getMessage());
        }

    }

    @Override
    public ClienteDTO obtenerPorId(Long id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(
                () -> new RecursoNoEncontradoException(MensajeError.RECURSO_NO_ENCONTRADO.toString())
        );
        return modelMapper.map(cliente, ClienteDTO.class);
    }

    @Override
    public ClienteDTO actualizar(ClienteDTO clienteDTO) {

        Cliente clienteDB = modelMapper.map(obtenerPorId(clienteDTO.getId()), Cliente.class);

        clienteDB.setNombre(clienteDTO.getNombre());
        clienteDB.setFecha(clienteDB.getFecha());
        clienteDB.setGenero(clienteDTO.getGenero());
        clienteDB.setEdad(clienteDTO.getEdad());
        clienteDB.setDireccion(clienteDTO.getDireccion());
        clienteDB.setTelefono(clienteDTO.getTelefono());
        clienteDB.setEstado(clienteDTO.isEstado());
        return modelMapper.map(clienteRepository.save(clienteDB), ClienteDTO.class);
    }

    @Override
    public void eliminarPorId(Long id) {
        clienteRepository.findById(id).orElseThrow(
                () -> new RecursoNoEncontradoException(MensajeError.RECURSO_NO_ENCONTRADO.toString())
        );
        clienteRepository.deleteById(id);
    }
}
