-- PERSONAS
CREATE TABLE personas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255),
    genero VARCHAR(255),
    fecha DATE,
    edad INT,
    identificacion VARCHAR(255),
    direccion VARCHAR(255),
    telefono VARCHAR(255)
);

-- CLIENTS
CREATE TABLE clientes (
	id BIGINT,
	contrasena varchar(150),
	estado BOOLEAN,
	CONSTRAINT client_pkey PRIMARY KEY (id),
	CONSTRAINT fk_client_person FOREIGN KEY (id) REFERENCES personas(id)
);