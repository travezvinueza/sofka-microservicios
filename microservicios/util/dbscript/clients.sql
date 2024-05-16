-- PERSONAS
CREATE TABLE personas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255),
    genero VARCHAR(255),
    edad INT,
    identificacion VARCHAR(255),
    direccion VARCHAR(255),
    telefono VARCHAR(255)
);

-- CLIENTES
CREATE TABLE clientes (
    clienteId VARCHAR(255) PRIMARY KEY,
    id BIGINT,
    contrasena VARCHAR(255) NOT NULL,
    estado BOOLEAN,
    FOREIGN KEY (id) REFERENCES personas(id)
);