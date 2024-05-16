package com.ricardotravez.cuentamovimientos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuentaReporteDTO {
    private String nombres;
    private String dni;
    private String tipoCuenta;
    private String numeroCuenta;
    private BigDecimal montoInicial;
    private String fechaInicio;
    private String fechaFinal;
    private String estado;
    private List<CuentaReporteDetalleDTO> cuentaReporteDetalle;
}
