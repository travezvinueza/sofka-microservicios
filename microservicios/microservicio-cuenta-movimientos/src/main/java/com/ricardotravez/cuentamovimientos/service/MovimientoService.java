package com.ricardotravez.cuentamovimientos.service;

import com.ricardotravez.cuentamovimientos.dto.MovimientoDTO;

import java.util.List;

public interface MovimientoService {
    MovimientoDTO crear(MovimientoDTO movimientoDTO);
    List<MovimientoDTO> listar();
    MovimientoDTO obtenerPorId(Long id);
    MovimientoDTO actualizar(MovimientoDTO movimientoDTO);
    void eliminarPorId(Long id);
}
