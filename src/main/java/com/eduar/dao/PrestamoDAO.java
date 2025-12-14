package com.eduar.dao;

import com.eduar.modelo.Prestamo;
import com.eduar.modelo.Cliente;
import com.eduar.modelo.Empleado;
import com.eduar.util.ArchivoUtil;
import java.time.LocalDate;
import java.util.ArrayList;
/**
 * DAO para la clase Prestamo
 * 
 * @author Eduar Humberto Guerrero Vergel
 * @version 1.0
 */
public class PrestamoDAO {
    
    private static final String ARCHIVO = "data/prestamos.txt";
    
    // DAOs relacionados
    private ClienteDAO clienteDAO;
    private EmpleadoDAO empleadoDAO;
    
    public PrestamoDAO() {
        this.clienteDAO = new ClienteDAO();
        this.empleadoDAO = new EmpleadoDAO();
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    CREATE
    // ═══════════════════════════════════════════════════════════
    
    public boolean crear(Prestamo prestamo) {
        int nuevoId = generarNuevoId();
        prestamo.setId(nuevoId);
        
        String linea = prestamoALinea(prestamo);
        return ArchivoUtil.guardarLinea(ARCHIVO, linea);
    }
    
   
    // ═══════════════════════════════════════════════════════════
    //                    READ
    // ═══════════════════════════════════════════════════════════
    
    public ArrayList<Prestamo> obtenerTodos() {
        ArrayList<Prestamo> prestamos = new ArrayList<>();
        ArrayList<String> lineas = ArchivoUtil.leerArchivo(ARCHIVO);
        
        for (String linea : lineas) {
            if (!linea.trim().isEmpty()) {
                Prestamo prestamo = lineaAPrestamo(linea);
                if (prestamo != null) {
                    prestamos.add(prestamo);
                }
            }
        }
        
        return prestamos;
    }
    
    public Prestamo obtenerPorId(int id) {
        ArrayList<Prestamo> prestamos = obtenerTodos();
        
        for (Prestamo prestamo : prestamos) {
            if (prestamo.getId() == id) {
                return prestamo;
            }
        }
        
        return null;
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    UPDATE
    // ═══════════════════════════════════════════════════════════
    
    public boolean actualizar(Prestamo prestamo) {
        ArrayList<String> lineas = ArchivoUtil.leerArchivo(ARCHIVO);
        ArrayList<String> lineasActualizadas = new ArrayList<>();
        
        boolean encontrado = false;
        
        for (String linea : lineas) {
            if (!linea.trim().isEmpty()) {
                String[] datos = linea.split("\\|");
                int idLinea = Integer.parseInt(datos[0]);
                
                if (idLinea == prestamo.getId()) {
                    lineasActualizadas.add(prestamoALinea(prestamo));
                    encontrado = true;
                } else {
                    lineasActualizadas.add(linea);
                }
            }
        }
        
        if (encontrado) {
            return ArchivoUtil.actualizarArchivo(ARCHIVO, lineasActualizadas);
        }
        
        return false;
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    DELETE
    // ═══════════════════════════════════════════════════════════
    
    public boolean eliminar(int id) {
        ArrayList<String> lineas = ArchivoUtil.leerArchivo(ARCHIVO);
        ArrayList<String> lineasFiltradas = new ArrayList<>();
        
        boolean encontrado = false;
        
        for (String linea : lineas) {
            if (!linea.trim().isEmpty()) {
                String[] datos = linea.split("\\|");
                int idLinea = Integer.parseInt(datos[0]);
                
                if (idLinea != id) {
                    lineasFiltradas.add(linea);
                } else {
                    encontrado = true;
                }
            }
        }
        
        if (encontrado) {
            return ArchivoUtil.actualizarArchivo(ARCHIVO, lineasFiltradas);
        }
        
        return false;
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    MÉTODOS AUXILIARES
    // ═══════════════════════════════════════════════════════════
    
    /**
     * Formato: id|clienteId|empleadoId|monto|interes|cuotas|fechaInicio|estado|saldoPendiente
     */
    private String prestamoALinea(Prestamo prestamo) {
        return String.format("%d|%d|%d|%.2f|%.2f|%d|%s|%s|%.2f",
            prestamo.getId(),
            prestamo.getCliente().getId(),
            prestamo.getEmpleado().getId(),
            prestamo.getMonto(),
            prestamo.getInteres(),
            prestamo.getCuotas(),
            prestamo.getFechaInicio().toString(),
            prestamo.getEstado(),
            prestamo.getSaldoPendiente()
        );
    }
    
    private Prestamo lineaAPrestamo(String linea) {
        try {
            String[] datos = linea.split("\\|");
            
            if (datos.length >= 9) {
                // Obtener cliente y empleado con nombres únicos
                int idCliente = Integer.parseInt(datos[1]);
                int idEmpleado = Integer.parseInt(datos[2]);
                
                Cliente clienteObtenido = clienteDAO.obtenerPorId(idCliente);
                Empleado empleadoObtenido = empleadoDAO.obtenerPorId(idEmpleado);
                
                // Verificar que existan
                if (clienteObtenido != null && empleadoObtenido != null) {
                    // Parsear datos
                    int id = Integer.parseInt(datos[0]);
                    double monto = Double.parseDouble(datos[3]);
                    double interes = Double.parseDouble(datos[4]);
                    int cuotas = Integer.parseInt(datos[5]);
                    LocalDate fechaInicio = LocalDate.parse(datos[6]);
                    String estado = datos[7];
                    double saldoPendiente = Double.parseDouble(datos[8]);
                    
                    // Crear préstamo
                    return new Prestamo(
                        id,
                        clienteObtenido,
                        empleadoObtenido,
                        monto,
                        interes,
                        cuotas,
                        fechaInicio,
                        estado,
                        saldoPendiente
                    );
                }
            }
        } catch (Exception e) {
            System.err.println("Error al parsear línea de préstamo: " + linea);
            System.err.println("Detalle: " + e.getMessage());
        }
        
        return null;
    }
    
    private int generarNuevoId() {
        ArrayList<Prestamo> prestamos = obtenerTodos();
        
        if (prestamos.isEmpty()) {
            return 1;
        }
        
        int maxId = 0;
        for (Prestamo prestamo : prestamos) {
            if (prestamo.getId() > maxId) {
                maxId = prestamo.getId();
            }
        }
        
        return maxId + 1;
    }
    
    public int contar() {
        return obtenerTodos().size();
    }
    
    /**
     * Obtiene préstamos por cliente
     */
    public ArrayList<Prestamo> obtenerPorCliente(int clienteId) {
        ArrayList<Prestamo> resultado = new ArrayList<>();
        ArrayList<Prestamo> todos = obtenerTodos();
        
        for (Prestamo prestamo : todos) {
            if (prestamo.getCliente().getId() == clienteId) {
                resultado.add(prestamo);
            }
        }
        
        return resultado;
    }
    
    /**
     * Obtiene préstamos por estado
     */
    public ArrayList<Prestamo> obtenerPorEstado(String estado) {
        ArrayList<Prestamo> resultado = new ArrayList<>();
        ArrayList<Prestamo> todos = obtenerTodos();
        
        for (Prestamo prestamo : todos) {
            if (prestamo.getEstado().equalsIgnoreCase(estado)) {
                resultado.add(prestamo);
            }
        }
        
        return resultado;
    }
}