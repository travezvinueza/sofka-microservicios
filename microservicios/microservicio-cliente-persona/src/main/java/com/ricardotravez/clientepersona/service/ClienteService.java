package com.ricardotravez.clientepersona.service;

import com.ricardotravez.clientepersona.dto.ClienteDTO;

import java.util.List;

public interface ClienteService {
    ClienteDTO crear(ClienteDTO clienteDTO);
    List<ClienteDTO> listar();
    ClienteDTO obtenerPorId(Long id);
    ClienteDTO actualizar(ClienteDTO clienteDTO);
    void eliminarPorId(Long id);
}
