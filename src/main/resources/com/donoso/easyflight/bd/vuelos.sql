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

CREATE TABLE aeropuerto (
  id int PRIMARY KEY,
  nombre VARCHAR(255)
);

CREATE TABLE vuelo (
  id VARCHAR(50) PRIMARY KEY,
  origen INT,
  destino INT,
  fechaSalida DATE,
  horaSalida TIME,
  horaLlegada TIME,
  avion VARCHAR(50),
  FOREIGN KEY (avion) REFERENCES avion(id),
  FOREIGN KEY (origen) REFERENCES aeropuerto(id),
  FOREIGN KEY (destino) REFERENCES aeropuerto(id)
);

-- Insertar datos en la tabla avion
INSERT INTO avion (id, modelo, pasajeros) VALUES
('A001', 'Boeing 737', 150),
('A002', 'Airbus A320', 180),
('A003', 'Boeing 747', 400),
('A004', 'Embraer E190', 110),
('A005', 'Airbus A330', 300),
('A006', 'Boeing 777', 350),
('A007', 'Bombardier CRJ900', 90),
('A008', 'Airbus A380', 550);

-- Insertar datos en la tabla usuario
INSERT INTO usuario (nombre, apellido, user, password, administrador) VALUES
('Juan', 'Pérez', 'juanperez', '12345', true),
('Ana', 'García', 'anagarcia', '12345', false),
('Carlos', 'Martínez', 'carlosmartinez', '12345', false);

-- Insertar datos en la tabla de aeropuerto
INSERT INTO aeropuerto (id, nombre) VALUES
(1, 'A Coruña (LCG)'),
(2, 'Adolfo Suárez Madrid-Barajas (MAD)'),
(3, 'Albacete (ABC)'),
(4, 'Algeciras (AEI)'),
(5, 'Alicante-Elche Miguel Hernández (ALC)'),
(6, 'Almería (LEI)'),
(7, 'Asturias (OVD)'),
(8, 'Badajoz (BJZ)'),
(9, 'Bilbao (BIO)'),
(10, 'Burgos (RGS)'),
(11, 'Ceuta (JCU)'),
(12, 'César Manrique-Lanzarote (ACE)'),
(13, 'Córdoba (ODB)'),
(14, 'El Hierro (VDE)'),
(15, 'Federico García Lorca Granada-Jaén (GRX)'),
(16, 'Fuerteventura (FUE)'),
(17, 'Girona-Costa Brava (GRO)'),
(18, 'Gran Canaria (LPA)'),
(19, 'Huesca-Pirineos (HSK)'),
(20, 'Ibiza (IBZ)'),
(21, 'Internacional Región de Murcia (RMU)'),
(22, 'Jerez (XRY)'),
(23, 'Josep Tarradellas Barcelona-El Prat (BCN)'),
(24, 'La Gomera (GMZ)'),
(25, 'La Palma (SPC)'),
(26, 'León (LEN)'),
(27, 'Logroño-Agoncillo (RJL)'),
(28, 'Madrid-Cuatro Vientos (LECU)'),
(29, 'Melilla (MLN)'),
(30, 'Menorca (MAH)'),
(31, 'Málaga-Costa del Sol (AGP)'),
(32, 'Palma de Mallorca (PMI)'),
(33, 'Pamplona (PNA)'),
(34, 'Reus (REU)'),
(35, 'Sabadell (QSA)'),
(36, 'Salamanca (SLM)'),
(37, 'San Sebastián (EAS)'),
(38, 'Santiago-Rosalía de Castro (SCQ)'),
(39, 'Seve Ballesteros-Santander (SDR)'),
(40, 'Sevilla (SVQ)'),
(41, 'Son Bonet (LESB)'),
(42, 'Tenerife Norte-Ciudad de La Laguna (TFN)'),
(43, 'Tenerife Sur (TFS)'),
(44, 'Valencia (VLC)'),
(45, 'Valladolid (VLL)'),
(46, 'Vigo (VGO)'),
(47, 'Vitoria (VIT)'),
(48, 'Zaragoza (ZAZ)');

-- Insertar datos en la tabla vuelo
INSERT INTO vuelo (id, origen, destino, fechaSalida, horaSalida, horaLlegada, avion) VALUES
('AC1254', 1, 2, '2024-03-14', '14:00:00', '15:00:00', 'A001'),
('AT7720', 10, 22, '2023-09-23', '10:35:00', '14:11:00', 'A003'),
('BC9874', 40, 15, '2024-03-02', '12:00:00', '13:35:00', 'A002'),
('BS4595', 38, 19, '2023-04-17', '05:42:00', '22:31:00', 'A003'),
('BV9259', 36, 3, '2023-04-24', '21:08:00', '23:32:00', 'A001'),
('EE3887', 4, 15, '2023-07-31', '20:04:00', '18:40:00', 'A001'),
('EO2626', 13, 25, '2023-11-07', '14:58:00', '00:16:00', 'A002'),
('GB6053', 44, 35, '2023-12-29', '06:55:00', '19:16:00', 'A008'),
('GW5349', 35, 1, '2023-04-13', '22:22:00', '01:10:00', 'A008'),
('JP9473', 30, 23, '2024-01-28', '15:34:00', '22:52:00', 'A008'),
('KJ6126', 29, 16, '2024-02-19', '23:56:00', '19:11:00', 'A006'),
('KX3637', 40, 36, '2023-04-03', '07:18:00', '14:07:00', 'A004'),
('MG7572', 39, 7, '2023-04-01', '02:16:00', '02:36:00', 'A003'),
('MH0493', 2, 22, '2023-07-15', '16:15:00', '06:33:00', 'A008'),
('NP3183', 13, 15, '2024-01-09', '07:54:00', '16:39:00', 'A008'),
('OE1389', 16, 9, '2023-05-17', '13:18:00', '19:09:00', 'A005'),
('OT0455', 16, 10, '2023-03-15', '00:58:00', '19:09:00', 'A001'),
('PK1424', 45, 16, '2023-09-11', '04:19:00', '01:25:00', 'A005'),
('QG2188', 26, 1, '2023-07-26', '07:21:00', '20:13:00', 'A007'),
('QU9605', 31, 2, '2023-08-17', '09:31:00', '12:02:00', 'A002'),
('RD6378', 17, 19, '2023-12-14', '23:31:00', '17:06:00', 'A005'),
('RM3236', 4, 19, '2023-05-09', '22:05:00', '15:00:00', 'A002'),
('SV8550', 3, 35, '2024-01-09', '16:48:00', '01:50:00', 'A006'),
('VL2356', 8, 2, '2024-04-12', '10:00:00', '11:10:00', 'A001'),
('VL3344', 2, 3, '2024-03-03', '10:00:00', '11:00:00', 'A001'),
('WH8896', 1, 33, '2023-09-25', '02:56:00', '07:47:00', 'A008'),
('XN9319', 6, 47, '2023-03-27', '12:23:00', '09:52:00', 'A006'),
('ZA2337', 30, 34, '2023-04-05', '17:23:00', '19:48:00', 'A002'),
('ZU9455', 47, 33, '2023-08-05', '06:02:00', '10:03:00', 'A004');