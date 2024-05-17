package com.ricardotravez.cuentamovimientos.repository;

import com.ricardotravez.cuentamovimientos.entity.Cuenta;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
    Optional<Cuenta> findByNumeroCuenta(String numeroCuenta);
    List<Cuenta> findByIdCliente(String idCliente);

    @Query("SELECT c FROM Cuenta c JOIN c.movimientos m WHERE c.numeroCuenta = :numeroCuenta AND m.fecha BETWEEN :fechaInicio AND :fechaFin")
    Optional<Cuenta> findCuentaReportePorNumeroCuenta(@Param("numeroCuenta") String numeroCuenta, LocalDate fechaInicio, LocalDate fechaFin);

}
