package com.ricardotravez.cuentamovimientos.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class MovimientoDTO {
    private Long id;
    private LocalDate fecha;
    private String tipoMovimiento;
    private double valor;
    private double saldo;
    private String numeroCuenta;
}
