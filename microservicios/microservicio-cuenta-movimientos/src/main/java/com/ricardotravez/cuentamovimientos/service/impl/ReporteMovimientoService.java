package com.ricardotravez.cuentamovimientos.service.impl;

import com.ricardotravez.cuentamovimientos.dto.MovimientoDTO;
import com.ricardotravez.cuentamovimientos.repository.MovimientoRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReporteMovimientoService {
    private MovimientoRepository movimientoRepository;
    private ModelMapper modelMapper;

    public List<MovimientoDTO> obtenerMovimientosEntreFechasPorCuenta(LocalDate fechaInicio, LocalDate fechaFin, String numeroCuenta) {
        return movimientoRepository.obtenerMovimientosEntreFechasPorCuenta(fechaInicio, fechaFin, numeroCuenta).stream().map(
                (movimiento )-> modelMapper.map(movimiento,MovimientoDTO.class)).collect(Collectors.toList());
    }
}
