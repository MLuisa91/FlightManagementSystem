CREATE DATABASE vuelosDB;

USE vuelosDB;

CREATE TABLE usuario (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(255),
  apellido VARCHAR(255),
  user VARCHAR(50),
  password VARCHAR(50),
  administrador BOOLEAN
);

CREATE TABLE avion (
  id VARCHAR(50) PRIMARY KEY,
  modelo VARCHAR(255),
  pasajeros INT
);

CREATE TABLE vuelo (
  id VARCHAR(50) PRIMARY KEY,
  origen VARCHAR(255),
  destino VARCHAR(255),
  fechaSalida DATE,
  horaSalida TIME,
  horaLlegada TIME,
  avion VARCHAR(50),
  FOREIGN KEY (avion) REFERENCES avion(id)
);


-- Insertar datos en la tabla avion
INSERT INTO avion (id, modelo, pasajeros) VALUES
('A001', 'Boeing 737', 150),
('A002', 'Airbus A320', 180),
('A003', 'Boeing 747', 400);

-- Insertar datos en la tabla usuario
INSERT INTO usuario (nombre, apellido, user, password, administrador) VALUES
('Juan', 'Pérez', 'juanperez', '12345', true),
('Ana', 'García', 'anagarcia', '12345', false),
('Carlos', 'Martínez', 'carlosmartinez', '12345', false);

-- Insertar datos en la tabla vuelo
INSERT INTO vuelo (id, origen, destino, fechaSalida, horaSalida, horaLlegada, avion) VALUES
('VL2356', 'VALENCIA', 'SEVILLA', '2024-04-12', '10:00:00', '11:10:00', 'A001'),
('BC9874', 'MÁLAGA', 'BARCELONA', '2024-03-02', '12:00:00', '13:35:00', 'A002'),
('SC3000', 'MÁLAGA', 'SANTIAGO DE COMPOSTELA', '2024-05-03', '16:15:00', '17:50:00', 'A003');
