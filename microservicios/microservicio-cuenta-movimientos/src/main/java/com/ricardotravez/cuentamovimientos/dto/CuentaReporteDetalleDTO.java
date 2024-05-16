package com.ricardotravez.cuentamovimientos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuentaReporteDetalleDTO {
    private String tipoTransaccion;
    private String fechaMovimiento;
    private String tipoMovimiento;
    private BigDecimal montoMovimiento;
    private BigDecimal saldoDisponible;
    private String observacion;
}
