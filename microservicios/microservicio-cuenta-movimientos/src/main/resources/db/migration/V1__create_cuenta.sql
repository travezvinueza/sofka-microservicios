-- CUENTAS
CREATE TABLE IF NOT EXISTS cuentas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero_cuenta VARCHAR(255) UNIQUE,
    tipo_cuenta VARCHAR(255),
    fecha DATE,
    saldo_inicial DOUBLE,
    estado BOOLEAN,
    id_cliente VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- MOVIMIENTOS
CREATE TABLE IF NOT EXISTS movimientos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE,
    tipo_movimiento VARCHAR(255),
    valor DOUBLE,
    saldo DOUBLE,
    cuenta_id BIGINT,
    FOREIGN KEY (cuenta_id) REFERENCES cuentas(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insertar datos en la tabla cuentas
INSERT INTO cuentas (numero_cuenta, tipo_cuenta, fecha, saldo_inicial, estado, id_cliente) VALUES
('123456', 'Corriente', '2024-05-16', 1000.00, true, 1),
('234567', 'Ahorros', '2024-05-15', 2000.00, true, 2),
('345678', 'Corriente', '2024-05-16', 3000.00, true, 3),
('456789', 'Ahorros', '2024-05-16', 4000.00, true, 4);


-- Insertar datos en la tabla movimientos
INSERT INTO movimientos (fecha, tipo_movimiento, valor, saldo, cuenta_id) VALUES
('2024-05-16', 'DEPOSITO', 500.00, 1500.00, 1),
('2024-05-16', 'RETIRO', 200.00, 1800.00, 2),
('2024-05-16', 'DEPOSITO', 1000.00, 4000.00, 3),
('2024-05-16', 'DEPOSITO', 500.00, 4500.00, 4);
