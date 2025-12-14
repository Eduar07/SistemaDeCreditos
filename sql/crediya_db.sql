-- ═══════════════════════════════════════════════════════════
--           CREDIYA S.A.S. - BASE DE DATOS (UTF-8)
-- ═══════════════════════════════════════════════════════════

DROP DATABASE IF EXISTS crediya_db;

CREATE DATABASE crediya_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE crediya_db;

-- Configurar encoding para esta sesión
SET NAMES utf8mb4;
SET CHARACTER_SET_CLIENT = utf8mb4;
SET CHARACTER_SET_RESULTS = utf8mb4;
SET CHARACTER_SET_CONNECTION = utf8mb4;

-- ═══════════════════════════════════════════════════════════
--                    TABLA: clientes
-- ═══════════════════════════════════════════════════════════

CREATE TABLE clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    documento VARCHAR(20) NOT NULL UNIQUE,
    correo VARCHAR(100) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE,
    
    INDEX idx_documento (documento),
    INDEX idx_nombre (nombre)
) ENGINE=InnoDB 
  DEFAULT CHARSET=utf8mb4 
  COLLATE=utf8mb4_unicode_ci;

-- ═══════════════════════════════════════════════════════════
--                    TABLA: empleados
-- ═══════════════════════════════════════════════════════════

CREATE TABLE empleados (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    documento VARCHAR(20) NOT NULL UNIQUE,
    correo VARCHAR(100) NOT NULL,
    rol VARCHAR(50) NOT NULL,
    salario DECIMAL(12, 2) NOT NULL,
    fecha_ingreso TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE,
    
    INDEX idx_documento (documento),
    INDEX idx_rol (rol)
) ENGINE=InnoDB 
  DEFAULT CHARSET=utf8mb4 
  COLLATE=utf8mb4_unicode_ci;

-- ═══════════════════════════════════════════════════════════
--                    TABLA: prestamos
-- ═══════════════════════════════════════════════════════════

CREATE TABLE prestamos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT NOT NULL,
    empleado_id INT NOT NULL,
    monto DECIMAL(12, 2) NOT NULL,
    interes DECIMAL(5, 2) NOT NULL,
    cuotas INT NOT NULL,
    fecha_inicio DATE NOT NULL,
    estado VARCHAR(20) DEFAULT 'pendiente',
    saldo_pendiente DECIMAL(12, 2) NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE RESTRICT,
    FOREIGN KEY (empleado_id) REFERENCES empleados(id) ON DELETE RESTRICT,
    
    INDEX idx_cliente (cliente_id),
    INDEX idx_empleado (empleado_id),
    INDEX idx_estado (estado),
    INDEX idx_fecha (fecha_inicio)
) ENGINE=InnoDB 
  DEFAULT CHARSET=utf8mb4 
  COLLATE=utf8mb4_unicode_ci;

-- ═══════════════════════════════════════════════════════════
--                    TABLA: pagos
-- ═══════════════════════════════════════════════════════════

CREATE TABLE pagos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    prestamo_id INT NOT NULL,
    fecha_pago DATE NOT NULL,
    monto DECIMAL(12, 2) NOT NULL,
    observaciones TEXT,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (prestamo_id) REFERENCES prestamos(id) ON DELETE CASCADE,
    
    INDEX idx_prestamo (prestamo_id),
    INDEX idx_fecha (fecha_pago)
) ENGINE=InnoDB 
  DEFAULT CHARSET=utf8mb4 
  COLLATE=utf8mb4_unicode_ci;

-- ═══════════════════════════════════════════════════════════
--                    DATOS DE PRUEBA
-- ═══════════════════════════════════════════════════════════

-- Insertar clientes de prueba
INSERT INTO clientes (nombre, documento, correo, telefono) VALUES
('Juan Pérez', '1111111111', 'juan.perez@email.com', '3001234567'),
('María Rodríguez', '2222222222', 'maria.rodriguez@email.com', '3007654321'),
('Pedro García', '3333333333', 'pedro.garcia@email.com', '3009876543');

-- Insertar empleados de prueba
INSERT INTO empleados (nombre, documento, correo, rol, salario) VALUES
('Ana García', '1234567890', 'ana.garcia@crediya.com', 'Gerente', 4500000.00),
('Carlos López', '9876543210', 'carlos.lopez@crediya.com', 'Asesor', 2800000.00);

-- Insertar préstamos de prueba
INSERT INTO prestamos (cliente_id, empleado_id, monto, interes, cuotas, fecha_inicio, saldo_pendiente) VALUES
(1, 1, 5000000.00, 5.50, 12, '2025-12-13', 5275000.00),
(2, 2, 3000000.00, 4.50, 6, '2025-12-13', 3135000.00);

-- Insertar pagos de prueba
INSERT INTO pagos (prestamo_id, fecha_pago, monto, observaciones) VALUES
(1, '2025-12-13', 439583.33, 'Primera cuota'),
(1, '2025-12-13', 439583.33, 'Segunda cuota');

-- ═══════════════════════════════════════════════════════════
--                    VERIFICACIÓN
-- ═══════════════════════════════════════════════════════════

SELECT 'Clientes' AS Tabla, COUNT(*) AS Total FROM clientes
UNION ALL
SELECT 'Empleados', COUNT(*) FROM empleados
UNION ALL
SELECT 'Préstamos', COUNT(*) FROM prestamos
UNION ALL
SELECT 'Pagos', COUNT(*) FROM pagos;

SELECT '✓ Base de datos creada correctamente con UTF-8' AS Mensaje;