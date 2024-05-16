-- CUENTAS
CREATE TABLE IF NOT EXISTS cuentas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    numeroCuenta VARCHAR(255) UNIQUE,
    tipoCuenta VARCHAR(255),
    saldoInicial DOUBLE,
    estado BOOLEAN,
    idCliente VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- MOVIMIENTOS
CREATE TABLE IF NOT EXISTS movimientos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE,
    tipoMovimiento VARCHAR(255),
    valor DOUBLE,
    saldo DOUBLE,
    numeroCuenta VARCHAR(255),
    cuenta_id BIGINT,
    FOREIGN KEY (cuenta_id) REFERENCES cuentas(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
