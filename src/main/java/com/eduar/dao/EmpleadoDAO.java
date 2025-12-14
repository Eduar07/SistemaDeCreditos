package com.eduar.dao;

import com.eduar.modelo.Empleado;
import com.eduar.util.ArchivoUtil;
import java.util.ArrayList;



public class EmpleadoDAO {
    
    private static final String ARCHIVO = "data/empleados.txt";
    
    
    //                    CREATE
    
    public boolean crear(Empleado empleado) {
        int nuevoId = generarNuevoId();
        empleado.setId(nuevoId);
        
        String linea = empleadoALinea(empleado);
        return ArchivoUtil.guardarLinea(ARCHIVO, linea);
    }
    
    
    //                    READ
    
    public ArrayList<Empleado> obtenerTodos() {
        ArrayList<Empleado> empleados = new ArrayList<>();
        ArrayList<String> lineas = ArchivoUtil.leerArchivo(ARCHIVO);
        
        for (String linea : lineas) {
            if (!linea.trim().isEmpty()) {
                Empleado empleado = lineaAEmpleado(linea);
                if (empleado != null) {
                    empleados.add(empleado);
                }
            }
        }
        
        return empleados;
    }
    
    public Empleado obtenerPorId(int id) {
        ArrayList<Empleado> empleados = obtenerTodos();
        
        for (Empleado empleado : empleados) {
            if (empleado.getId() == id) {
                return empleado;
            }
        }
        
        return null;
    }
    
    public Empleado obtenerPorDocumento(String documento) {
        ArrayList<Empleado> empleados = obtenerTodos();
        
        for (Empleado empleado : empleados) {
            if (empleado.getDocumento().equals(documento)) {
                return empleado;
            }
        }
        
        return null;
    }
    
    
    //                    UPDATE
    
    public boolean actualizar(Empleado empleado) {
        ArrayList<String> lineas = ArchivoUtil.leerArchivo(ARCHIVO);
        ArrayList<String> lineasActualizadas = new ArrayList<>();
        
        boolean encontrado = false;
        
        for (String linea : lineas) {
            if (!linea.trim().isEmpty()) {
                String[] datos = linea.split("\\|");
                int idLinea = Integer.parseInt(datos[0]);
                
                if (idLinea == empleado.getId()) {
                    lineasActualizadas.add(empleadoALinea(empleado));
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
    
    
    //                    DELETE
    
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
    
    
    //                    MÉTODOS AUXILIARES
    
  
    private String empleadoALinea(Empleado empleado) {
        return String.format("%d|%s|%s|%s|%s|%.2f",
            empleado.getId(),
            empleado.getNombre(),
            empleado.getDocumento(),
            empleado.getCorreo(),
            empleado.getRol(),
            empleado.getSalario()
        );
    }
    
    private Empleado lineaAEmpleado(String linea) {
        try {
            String[] datos = linea.split("\\|");
            
            if (datos.length >= 6) {
                return new Empleado(
                    Integer.parseInt(datos[0]),     
                    datos[1],                      
                    datos[2],                       
                    datos[3],                       
                    datos[4],                      
                    Double.parseDouble(datos[5])    
                );
            }
        } catch (Exception e) {
            System.err.println("Error al parsear línea: " + linea);
        }
        
        return null;
    }
    
    private int generarNuevoId() {
        ArrayList<Empleado> empleados = obtenerTodos();
        
        if (empleados.isEmpty()) {
            return 1;
        }
        
        int maxId = 0;
        for (Empleado empleado : empleados) {
            if (empleado.getId() > maxId) {
                maxId = empleado.getId();
            }
        }
        
        return maxId + 1;
    }
    
    public int contar() {
        return obtenerTodos().size();
    }
    
    public boolean existeDocumento(String documento) {
        return obtenerPorDocumento(documento) != null;
    }
    
  
    public ArrayList<Empleado> obtenerPorRol(String rol) {
        ArrayList<Empleado> resultado = new ArrayList<>();
        ArrayList<Empleado> todos = obtenerTodos();
        
        for (Empleado empleado : todos) {
            if (empleado.getRol().equalsIgnoreCase(rol)) {
                resultado.add(empleado);
            }
        }
        
        return resultado;
    }
}