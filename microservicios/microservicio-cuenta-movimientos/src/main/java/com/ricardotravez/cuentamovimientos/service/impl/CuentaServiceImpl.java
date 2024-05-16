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
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
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

    @Override
    public List<CuentaDTO> findByIdCliente(String idCliente) {
        return cuentaRepository.findByIdCliente(idCliente).stream().map(
                (cuenta) -> modelMapper.map(cuenta, CuentaDTO.class)).collect(Collectors.toList());
    }

    @Override
    public CuentaReporteDTO obtenerReporteCuentaCliente(String numeroCuenta, LocalDate fechaInicio, LocalDate fechaFin) {
        try{
            Cuenta cuenta = cuentaRepository.findByNumeroCuentaAndMovimientoFecha(numeroCuenta, fechaInicio, fechaFin)
                    .orElseThrow(() -> new RecursoNoEncontradoException("Error datos incorrectos"));

            List<CuentaReporteDetalleDTO> cuentaReporteDetalleDTOS = cuenta.getMovimientos().stream().map(movimiento -> modelMapper.map(movimiento, CuentaReporteDetalleDTO.class))
                    .collect(Collectors.toList());

            CuentaReporteDTO cuentaReporteDTO = modelMapper.map(cuenta, CuentaReporteDTO.class);

            cuentaReporteDTO.setCuentaReporteDetalle(cuentaReporteDetalleDTOS);

            return cuentaReporteDTO;
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }


    }
}
