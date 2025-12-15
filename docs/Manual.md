# 📖 Manual de Usuario - CrediYa S.A.S.

**Sistema de Gestión de Préstamos**  
Versión 1.0 | Diciembre 2025

---

## 📑 Índice

1. [Introducción](#introducción)
2. [Inicio Rápido](#inicio-rápido)
3. [Gestión de Empleados](#gestión-de-empleados)
4. [Gestión de Clientes](#gestión-de-clientes)
5. [Gestión de Préstamos](#gestión-de-préstamos)
6. [Gestión de Pagos](#gestión-de-pagos)
7. [Reportes](#reportes)
8. [Solución de Problemas](#solución-de-problemas)
9. [Preguntas Frecuentes](#preguntas-frecuentes)

---

## 🎯 Introducción

### ¿Qué es CrediYa S.A.S.?

CrediYa es un sistema de gestión de préstamos diseñado para empresas de microcrédito. Permite administrar empleados, clientes, préstamos y pagos de manera eficiente y segura.

### ¿Qué puedo hacer con el sistema?

- ✅ Registrar y gestionar empleados
- ✅ Administrar base de datos de clientes
- ✅ Crear préstamos con cálculo automático de intereses
- ✅ Registrar pagos y actualizar saldos
- ✅ Generar reportes estadísticos
- ✅ Detectar préstamos vencidos
- ✅ Identificar clientes morosos

### Requisitos para usar el sistema

- Computador con Java 17 instalado
- Docker corriendo (para la base de datos)
- Conocimientos básicos de navegación por menús de consola

---

## 🚀 Inicio Rápido

### Paso 1: Iniciar el Sistema
```bash
cd ~/Documentos/Proyecto/SistemaDeCreditos/proyecto
mvn exec:java -Dexec.mainClass="com.eduar.MenuPrincipal"
```

### Paso 2: Verificar Conexión

Al iniciar, verás este mensaje si todo está correcto:
```
╔═══════════════════════════════════════╗
║  ✓ CONEXIÓN A MYSQL (DOCKER) EXITOSA ║
╚═══════════════════════════════════════╝
```

### Paso 3: Navegar por el Menú

- Escribe el **número** de la opción deseada
- Presiona **Enter** para confirmar
- Sigue las instrucciones en pantalla

---

## 👥 Gestión de Empleados

### 1.1 Registrar un Nuevo Empleado

**Ruta:** Menú Principal → 1. Gestión de Empleados → 1. Registrar nuevo empleado

**Datos requeridos:**

| Campo | Descripción | Ejemplo |
|-------|-------------|---------|
| Nombre completo | Nombre y apellidos | Ana García López |
| Documento | Cédula sin puntos | 1234567890 |
| Correo | Email corporativo | ana.garcia@crediya.com |
| Rol | Gerente/Asesor/Contador/Cajero/Auxiliar | Asesor |
| Salario | Salario mensual en pesos | 2800000 |

**Ejemplo paso a paso:**
```
--- Registrar Nuevo Empleado ---
Nombre completo: Ana García López
Documento: 1234567890
Correo electrónico: ana.garcia@crediya.com
Rol (Gerente/Asesor/Contador/Cajero/Auxiliar): Asesor
Salario: 2800000

✓ Empleado registrado exitosamente
```

**✅ Validaciones automáticas:**
- Correo debe contener `@` y `.`
- Documento debe ser único
- Salario debe ser mayor a 0

---

### 1.2 Listar Empleados

**Ruta:** Menú Principal → 1 → 2

Muestra todos los empleados activos en formato tabla:
```
--- Lista de Empleados ---

ID    Nombre                    Rol             Documento       Salario     
--------------------------------------------------------------------------------
1     Ana García López          Asesor          1234567890      $2,800,000
2     Carlos Pérez              Contador        9876543210      $3,200,000

Total: 2 empleados
```

---

### 1.3 Buscar Empleado por ID

**Ruta:** Menú Principal → 1 → 3
```
Ingrese ID del empleado: 1

Empleado #1
  Nombre: Ana García López
  Documento: 1234567890
  Correo: ana.garcia@crediya.com
  Rol: Asesor
  Salario: $2,800,000.00
```

---

### 1.4 Buscar por Documento

**Ruta:** Menú Principal → 1 → 4

Útil cuando no recuerdas el ID pero sí el documento del empleado.

---

### 1.5 Buscar por Rol

**Ruta:** Menú Principal → 1 → 5

Filtra empleados por cargo (Gerente, Asesor, Contador, etc.)

---

### 1.6 Actualizar Empleado

**Ruta:** Menú Principal → 1 → 6

Permite modificar:
- ✅ Correo electrónico
- ✅ Rol
- ✅ Salario

**No se puede modificar:**
- ❌ Nombre
- ❌ Documento (dato único)

**Procedimiento:**
1. Ingresa el ID del empleado
2. El sistema muestra los datos actuales
3. Escribe el nuevo valor o presiona Enter para mantener
4. Confirma los cambios

---

### 1.7 Eliminar Empleado

**Ruta:** Menú Principal → 1 → 7

⚠️ **IMPORTANTE:** No puedes eliminar un empleado que tenga préstamos asociados.

**El sistema verifica automáticamente:**
- Si tiene préstamos → ❌ No permite eliminar
- Si no tiene préstamos → ✅ Solicita confirmación

**Proceso:**
```
Ingrese ID del empleado a eliminar: 1

Empleado #1
  Nombre: Ana García López
  ...

¿Está seguro de eliminar este empleado? (S/N): S

✓ Empleado eliminado exitosamente.
```

---

### 1.8 Ver Nómina Total

**Ruta:** Menú Principal → 1 → 8

Calcula y muestra:
- Total de empleados activos
- Nómina mensual total
- Nómina anual proyectada
```
--- Nómina Total ---
Total empleados: 5
Nómina mensual: $15,000,000.00
Nómina anual: $180,000,000.00
```

---

## 👤 Gestión de Clientes

### 2.1 Registrar un Nuevo Cliente

**Ruta:** Menú Principal → 2 → 1

**Datos requeridos:**

| Campo | Descripción | Ejemplo |
|-------|-------------|---------|
| Nombre completo | Nombre y apellidos | Juan Pérez Gómez |
| Documento | Cédula sin puntos | 1098765432 |
| Correo | Email personal | juan.perez@gmail.com |
| Teléfono | 10 dígitos | 3227886539 |

**Ejemplo:**
```
--- Registrar Nuevo Cliente ---
Nombre completo: Juan Pérez Gómez
Documento: 1098765432
Correo electrónico: juan.perez@gmail.com
Teléfono (10 dígitos): 3227886539

✓ Cliente registrado exitosamente
```

---

### 2.2 Listar Clientes

**Ruta:** Menú Principal → 2 → 2
```
--- Lista de Clientes ---

ID    Nombre                    Documento       Correo                          Teléfono    
-----------------------------------------------------------------------------------------------
1     Juan Pérez Gómez          1098765432      juan.perez@gmail.com            3227886539
2     María Rodríguez           2222222222      maria.rodriguez@email.com       3007654321

Total: 2 clientes
```

---

### 2.3 Buscar Cliente

**Opciones:**
- **Por ID** (Menú 2 → 3)
- **Por Documento** (Menú 2 → 4)

---

### 2.4 Actualizar Cliente

**Ruta:** Menú Principal → 2 → 5

Permite modificar:
- ✅ Correo electrónico
- ✅ Teléfono

**No se puede modificar:**
- ❌ Nombre
- ❌ Documento

---

### 2.5 Eliminar Cliente

**Ruta:** Menú Principal → 2 → 6

⚠️ **RESTRICCIÓN:** No puedes eliminar un cliente con préstamos activos.

---

## 💰 Gestión de Préstamos

### 3.1 Crear un Nuevo Préstamo

**Ruta:** Menú Principal → 3 → 1

**Requisitos previos:**
- ✅ El cliente debe estar registrado
- ✅ El empleado debe estar registrado
- ✅ El cliente NO debe tener un préstamo pendiente

**Datos requeridos:**

| Campo | Rango/Validación | Ejemplo |
|-------|------------------|---------|
| ID del cliente | Cliente existente | 1 |
| ID del empleado | Empleado existente | 2 |
| Monto | $500,000 - $50,000,000 | 5000000 |
| Tasa de interés | 1% - 15% | 5.5 |
| Número de cuotas | 3 - 60 meses | 12 |

**Ejemplo completo:**
```
--- Crear Nuevo Préstamo ---
ID del cliente: 1
Cliente: Juan Pérez Gómez

ID del empleado que aprueba: 2
Empleado: Ana García López

Monto del préstamo ($500,000 - $50,000,000): 5000000
Tasa de interés (1% - 15%): 5.5
Número de cuotas (3 - 60 meses): 12

╔═══════════════════════════════════════╗
║  ✓ PRÉSTAMO APROBADO Y CREADO        ║
╚═══════════════════════════════════════╝
Cliente: Juan Pérez Gómez
Monto: $5,000,000.00
Interés: 5.5%
Cuotas: 12 meses
Monto total: $5,275,000.00
Cuota mensual: $439,583.33
```

**📊 Cálculos automáticos:**
- **Monto Total** = Monto + (Monto × Interés%)
- **Cuota Mensual** = Monto Total / Cuotas
- **Saldo Pendiente** = Monto Total (al inicio)

---

### 3.2 Listar Préstamos

**Ruta:** Menú Principal → 3 → 2
```
--- Lista de Préstamos ---

ID    Cliente              Monto           Cuotas   Estado     Saldo          
---------------------------------------------------------------------------------
1     Juan Pérez Gómez     $5,000,000      12       pendiente  $5,275,000
2     María Rodríguez      $3,000,000      6        vencido    $3,135,000

Total: 2 préstamos
```

---

### 3.3 Buscar Préstamo

**Opciones:**
- **Por ID** (Menú 3 → 3)
- **Por Cliente** (Menú 3 → 4): Muestra todos los préstamos de un cliente
- **Por Estado** (Menú 3 → 5): Filtra por pendiente/pagado/vencido

---

### 3.4 Cambiar Estado de Préstamo

**Ruta:** Menú Principal → 3 → 6

**Estados válidos:**
- `pendiente` - Préstamo activo
- `pagado` - Préstamo completamente pagado
- `vencido` - Préstamo con cuotas atrasadas

**Uso:**
```
Ingrese ID del préstamo: 1

Estado actual: pendiente
Nuevo estado (pendiente/pagado/vencido): vencido

✓ Estado del préstamo actualizado a: vencido
```

---

### 3.7 Ver Préstamos Vencidos

**Ruta:** Menú Principal → 3 → 7

Muestra todos los préstamos que:
- Tienen estado "vencido", O
- La fecha de vencimiento ya pasó
```
--- Préstamos Vencidos ---
⚠️  Total préstamos vencidos: 1

Préstamo #2
  Cliente: María Rodríguez
  Monto: $3,000,000.00
  Saldo pendiente: $3,135,000.00
  Fecha inicio: 2025-11-15
```

---

## 💳 Gestión de Pagos

### 4.1 Registrar un Pago

**Ruta:** Menú Principal → 4 → 1

**Procedimiento:**

1. Ingresa el ID del préstamo
2. El sistema muestra:
   - Cliente
   - Saldo pendiente
   - Cuota mensual sugerida
3. Ingresa el monto del pago
4. Opcionalmente, agrega observaciones

**Ejemplo:**
```
--- Registrar Pago ---
ID del préstamo: 1

Préstamo #1
Cliente: Juan Pérez Gómez
Saldo pendiente: $5,275,000.00
Cuota mensual: $439,583.33

Monto del pago: 500000
Observaciones (opcional): Pago inicial

╔═══════════════════════════════════════╗
║  ✓ PAGO REGISTRADO EXITOSAMENTE      ║
╚═══════════════════════════════════════╝
Monto: $500,000.00
Nuevo saldo: $4,775,000.00
```

**🔄 Actualizaciones automáticas:**
- ✅ Reduce el saldo pendiente
- ✅ Guarda en MySQL y archivo
- ✅ Cambia estado a "pagado" si saldo = 0

---

### 4.2 Listar Pagos

**Ruta:** Menú Principal → 4 → 2
```
--- Lista de Pagos ---

ID    Préstamo     Monto           Observaciones                 
----------------------------------------------------------------------
1     1            $500,000        Pago inicial
2     1            $439,583        Cuota mes 2

Total: 2 pagos
```

---

### 4.3 Buscar Pago

**Opciones:**
- **Por ID** (Menú 4 → 3)
- **Por Préstamo** (Menú 4 → 4): Muestra histórico completo

**Ejemplo de histórico:**
```
Pagos del préstamo #1:
  • Pago #1 - $500,000.00 - 2025-12-01
  • Pago #2 - $439,583.33 - 2025-12-15

Total pagado: $939,583.33
```

---

### 4.5 Ver Total Recaudado

**Ruta:** Menú Principal → 4 → 5
```
--- Total Recaudado ---
Total de pagos: 15
Total recaudado: $12,345,678.90
```

---

## 📊 Reportes

### 5.1 Reporte General del Sistema

**Ruta:** Menú Principal → 5 → 1

Muestra un resumen completo:
- Total de empleados y nómina
- Total de clientes
- Total de préstamos y cartera
- Total recaudado

---

### 5.2 Reporte de Clientes

**Ruta:** Menú Principal → 5 → 2

Lista detallada de todos los clientes activos.

---

### 5.3 Reporte de Préstamos

**Ruta:** Menú Principal → 5 → 3

Lista de todos los préstamos con:
- Cliente
- Monto
- Cuotas
- Estado
- Saldo
- Fecha de inicio

---

### 5.4 Reporte por Cliente

**Ruta:** Menú Principal → 5 → 4

Muestra historial completo de préstamos de un cliente específico.

---

### 5.5 Reporte de Préstamos Vencidos

**Ruta:** Menú Principal → 5 → 5

Lista préstamos vencidos con:
- Total de préstamos vencidos
- Cartera vencida total

---

### 5.6 Préstamos Activos (Streams)

**Ruta:** Menú Principal → 5 → 6

**Tecnología:** Usa `Stream.filter()` para filtrar préstamos con estado "pendiente"
```
╔═══════════════════════════════════════╗
║    REPORTE DE PRÉSTAMOS ACTIVOS      ║
╚═══════════════════════════════════════╝

ID    Cliente              Monto           Saldo          
------------------------------------------------------------
1     Juan Pérez           $5,000,000      $4,775,000

✓ Total préstamos activos: 1
✓ Cartera activa: $4,775,000.00
```

---

### 5.7 Clientes Morosos (Streams)

**Ruta:** Menú Principal → 5 → 7

**Tecnología:** Usa `filter()` + `map()` + `distinct()` + `collect()`

Identifica clientes con préstamos vencidos.

---

### 5.8 Total por Empleado (groupBy)

**Ruta:** Menú Principal → 5 → 8

**Tecnología:** Usa `Collectors.groupingBy()` + `Collectors.summingDouble()`
```
╔═══════════════════════════════════════╗
║   TOTAL PRESTADO POR EMPLEADO        ║
╚═══════════════════════════════════════╝

Empleado                       Total Prestado        
-------------------------------------------------------
Ana García López               $12,000,000
Carlos Pérez Ruiz              $8,500,000
```

---

### 5.9 Préstamos Ordenados (sorted)

**Ruta:** Menú Principal → 5 → 9

**Tecnología:** Usa `sorted()` con lambda

Ordena préstamos de mayor a menor monto.

---

### 5.10 Estadísticas (Collectors)

**Ruta:** Menú Principal → 5 → 10

**Tecnología:** Usa `Collectors` múltiples
```
╔═══════════════════════════════════════╗
║     ESTADÍSTICAS DE PRÉSTAMOS        ║
╚═══════════════════════════════════════╝

📊 Total de préstamos: 10
💰 Monto total prestado: $45,000,000.00
💵 Cartera total: $32,500,000.00
📈 Promedio por préstamo: $4,500,000.00

📋 Préstamos por estado:
   • pendiente: 7
   • pagado: 2
   • vencido: 1
```

---

## 🔧 Solución de Problemas

### Problema 1: "No se pudo conectar a la base de datos"

**Causa:** Docker no está corriendo

**Solución:**
```bash
# Verificar estado de Docker
docker ps

# Si no hay contenedores, iniciar Docker
docker-compose up -d

# Verificar que MySQL esté corriendo
docker ps | grep crediya_mysql
```

---

### Problema 2: "Error al guardar en archivo"

**Causa:** No existe la carpeta `data/`

**Solución:**
```bash
cd proyecto
mkdir -p data
touch data/empleados.txt
touch data/clientes.txt
touch data/prestamos.txt
touch data/pagos.txt
```

---

### Problema 3: "Entrada inválida. Ingrese un número"

**Causa:** Escribiste texto en lugar de un número

**Solución:**
- Asegúrate de escribir **solo números**
- No uses puntos ni comas en números enteros
- Para decimales usa `.` (punto), no `,` (coma)

---

### Problema 4: No puedo eliminar un empleado/cliente

**Causa:** Tiene registros asociados (préstamos)

**Solución:**
1. Primero elimina o completa los préstamos asociados
2. Luego intenta eliminar el empleado/cliente
3. O mantén el registro (el sistema usa eliminación lógica)

---

## ❓ Preguntas Frecuentes

### ¿Puedo tener múltiples préstamos al mismo tiempo?

❌ No. Un cliente solo puede tener **un préstamo activo** a la vez. Debe completar el préstamo actual antes de solicitar uno nuevo.

---

### ¿Qué pasa si borro un empleado que creó préstamos?

⚠️ **No puedes eliminarlo** si tiene préstamos asociados. El sistema te lo impedirá automáticamente.

---

### ¿Los datos se guardan automáticamente?

✅ Sí. Cada operación se guarda **inmediatamente** en:
1. Base de datos MySQL
2. Archivos de texto en `data/`

---

### ¿Puedo acceder a la base de datos directamente?

✅ Sí. Puedes usar **phpMyAdmin**:

1. Abre tu navegador
2. Ve a `http://localhost:8080`
3. Usuario: `root`
4. Contraseña: `crediya123`

---

### ¿Cómo hago un backup de los datos?

**Opción 1: Copiar archivos de texto**
```bash
cp -r data/ backup_$(date +%Y%m%d)/
```

**Opción 2: Exportar desde MySQL**
```bash
docker exec crediya_mysql mysqldump -u root -pcrediya123 crediya_db > backup.sql
```

---

### ¿Qué significan los colores en el menú?

- 🔵 **Azul**: Títulos de menú
- 🟢 **Verde**: Operaciones exitosas
- 🔴 **Rojo**: Errores
- 🟡 **Amarillo**: Advertencias
- 🔷 **Cyan**: Información

---

## 📞 Contacto y Soporte

**¿Necesitas ayuda adicional?**

📧 Email: [tu-email@ejemplo.com](mailto:tu-email@ejemplo.com)  
🐙 GitHub Issues: [Reportar un problema](https://github.com/TU_USUARIO/SistemaDeCreditos/issues)  
📚 Documentación: [README.md](../README.md)

---

**Versión del Manual:** 1.0  
**Última actualización:** Diciembre 2025  
**Autor:** Eduar Humberto Guerrero Vergel