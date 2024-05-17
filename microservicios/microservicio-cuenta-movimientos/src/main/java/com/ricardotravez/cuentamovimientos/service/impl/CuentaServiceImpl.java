package com.ricardotravez.cuentamovimientos.service.impl;

import com.ricardotravez.cuentamovimientos.cuentaapi.CuentaApi;
import com.ricardotravez.cuentamovimientos.dto.ClienteDTO;
import com.ricardotravez.cuentamovimientos.dto.CuentaDTO;
import com.ricardotravez.cuentamovimientos.dto.CuentaReporteDTO;
import com.ricardotravez.cuentamovimientos.dto.CuentaReporteDetalleDTO;
import com.ricardotravez.cuentamovimientos.entity.Cuenta;
import com.ricardotravez.cuentamovimientos.entity.MensajeError;
import com.ricardotravez.cuentamovimientos.exception.ClienteNoEncontradoException;
import com.ricardotravez.cuentamovimientos.exception.RecursoNoEncontradoException;
import com.ricardotravez.cuentamovimientos.repository.CuentaRepository;
import com.ricardotravez.cuentamovimientos.service.CuentaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CuentaServiceImpl implements CuentaService {
    private final CuentaRepository cuentaRepository;
    private final ModelMapper modelMapper;
    private final CuentaApi cuentaApi;

    @Override
    public CuentaDTO crear(CuentaDTO cuentaDTO) {
        try {
            ClienteDTO clienteDTO = cuentaApi.getCientePorId(cuentaDTO.getIdCliente());
            if (clienteDTO == null) {
                throw new ClienteNoEncontradoException("");
            }
            cuentaDTO.setFecha(LocalDate.now());
            Cuenta cuenta = modelMapper.map(cuentaDTO, Cuenta.class);
            return modelMapper.map(cuentaRepository.save(cuenta), CuentaDTO.class);
        } catch (Exception e) {
            throw new ClienteNoEncontradoException("Error: " + e.getMessage());
        }
    }

    @Override
    public List<CuentaDTO> listar() {
        return cuentaRepository.findAll().stream().map(
                (cuenta) -> modelMapper.map(cuenta, CuentaDTO.class)).collect(Collectors.toList());
    }

    @Override
    public CuentaDTO obtenerPorId(Long id) {
        Cuenta cuenta = cuentaRepository.findById(id).orElseThrow(() -> new RecursoNoEncontradoException(MensajeError.RECURSO_NO_ENCONTRADO.toString()));
        return modelMapper.map(cuenta, CuentaDTO.class);
    }

    @Override
    public CuentaDTO actualizar(CuentaDTO cuentaDTO) {
        CuentaDTO cuentaDTODB = obtenerPorId(cuentaDTO.getId());

        cuentaDTODB.setNumeroCuenta(cuentaDTO.getNumeroCuenta());
        cuentaDTODB.setFecha(cuentaDTO.getFecha());
        cuentaDTODB.setTipoCuenta(cuentaDTO.getTipoCuenta());
        cuentaDTODB.setSaldoInicial(cuentaDTO.getSaldoInicial());
        cuentaDTODB.setEstado(cuentaDTO.isEstado());

        Cuenta cuenta = modelMapper.map(cuentaDTODB, Cuenta.class);

        return modelMapper.map(cuentaRepository.save(cuenta), CuentaDTO.class);
    }

    @Override
    public void eliminarPorId(Long id) {
        cuentaRepository.findById(id).orElseThrow(() -> new RecursoNoEncontradoException(MensajeError.RECURSO_NO_ENCONTRADO.toString()));
        cuentaRepository.deleteById(id);
    }

    public List<CuentaDTO> findByIdCliente(String idCliente) {
        List<Cuenta> cuentas = cuentaRepository.findByIdCliente(idCliente);
        return cuentas.stream().map(cuenta -> modelMapper.map(cuenta, CuentaDTO.class)).collect(Collectors.toList());
    }


    @Override
    public CuentaReporteDTO obtenerReporteCuentaCliente(String numeroCuenta, LocalDate fechaInicio, LocalDate fechaFin) {
        try {
            // Buscar la cuenta y sus movimientos utilizando el repositorio
            Optional<Cuenta> cuenta = cuentaRepository.findCuentaReportePorNumeroCuenta(numeroCuenta, fechaInicio, fechaFin);

            // Verificar si la cuenta es nula
            if (cuenta.isEmpty()) {
                throw new RecursoNoEncontradoException("No se encontró la cuenta con el número especificado o no hay movimientos en el rango de fechas proporcionado");
            }

            // Mapear los movimientos de la cuenta a DTOs utilizando ModelMapper
            List<CuentaReporteDetalleDTO> cuentaReporteDetalleDTOS = cuenta.get().getMovimientos().stream()
                    .map(movimiento -> modelMapper.map(movimiento, CuentaReporteDetalleDTO.class))
                    .collect(Collectors.toList());

            // Mapear la cuenta a un DTO
            CuentaReporteDTO cuentaReporteDTO = modelMapper.map(cuenta, CuentaReporteDTO.class);

            // Establecer conexion con servicio cliente para obtener datos de cliente
            ClienteDTO clienteDTO = cuentaApi.getCientePorId(cuenta.get().getIdCliente());
            cuentaReporteDTO.setCuentaReporteDetalle(cuentaReporteDetalleDTOS);
            cuentaReporteDTO.setCliente(clienteDTO);
            return cuentaReporteDTO;
        } catch (RecursoNoEncontradoException ex) {
            // Capturar la excepción específica de RecursoNoEncontradoException y relanzarla
            throw ex;
        } catch (Exception e) {
            // Capturar cualquier otra excepción y registrarla antes de relanzarla
            log.error("Error al obtener el reporte de la cuenta: ", e);
            throw new RuntimeException("Error al obtener el reporte de la cuenta: " + e.getMessage(), e);
        }
    }

}
