package com.eduar.dao;

import com.eduar.modelo.Cliente;
import com.eduar.util.ArchivoUtil;
import java.util.ArrayList;


public class ClienteDAO {
    
    //                    CONSTANTE: RUTA DEL ARCHIVO
    
    private static final String ARCHIVO = "data/clientes.txt";
    
    
                //    MÉTODO CREATE (CREAR)
  
    public boolean crear(Cliente cliente) {
        int nuevoId = generarNuevoId();
        cliente.setId(nuevoId);
        
        String linea = clienteALinea(cliente);
        
        return ArchivoUtil.guardarLinea(ARCHIVO, linea);
    }
    
    
    //                    MÉTODO READ 
    
    
    public ArrayList<Cliente> obtenerTodos() {
        ArrayList<Cliente> clientes = new ArrayList<>();
        
        ArrayList<String> lineas = ArchivoUtil.leerArchivo(ARCHIVO);
        
        for (String linea : lineas) {
            if (!linea.trim().isEmpty()) {
                Cliente cliente = lineaACliente(linea);
                if (cliente != null) {
                    clientes.add(cliente);
                }
            }
        }
        
        return clientes;
    }
    
    
    //                    MÉTODO READ (LEER POR ID)
   
    public Cliente obtenerPorId(int id) {
        ArrayList<Cliente> clientes = obtenerTodos();
        
        for (Cliente cliente : clientes) {
            if (cliente.getId() == id) {
                return cliente;
            }
        }
        
        return null; 
    }
    
 
    public Cliente obtenerPorDocumento(String documento) {
        ArrayList<Cliente> clientes = obtenerTodos();
        
        for (Cliente cliente : clientes) {
            if (cliente.getDocumento().equals(documento)) {
                return cliente;
            }
        }
        
        return null;
    }
    
    
    //                    MÉTODO UPDATE (ACTUALIZAR)
    
    
    public boolean actualizar(Cliente cliente) {
        ArrayList<String> lineas = ArchivoUtil.leerArchivo(ARCHIVO);
        ArrayList<String> lineasActualizadas = new ArrayList<>();
        
        boolean encontrado = false;
        
        for (String linea : lineas) {
            if (!linea.trim().isEmpty()) {
                String[] datos = linea.split("\\|");
                int idLinea = Integer.parseInt(datos[0]);
                
                if (idLinea == cliente.getId()) {
                    lineasActualizadas.add(clienteALinea(cliente));
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
    
    
    //                    MÉTODO DELETE (ELIMINAR)
    
    
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
    
   
    private String clienteALinea(Cliente cliente) {
        return String.format("%d|%s|%s|%s|%s",
            cliente.getId(),
            cliente.getNombre(),
            cliente.getDocumento(),
            cliente.getCorreo(),
            cliente.getTelefono()
        );
    }
    
  
    private Cliente lineaACliente(String linea) {
        try {
            String[] datos = linea.split("\\|");
            
            if (datos.length >= 5) {
                return new Cliente(
                    Integer.parseInt(datos[0]),  
                    datos[1],                  
                    datos[2],                   
                    datos[3],                   
                    datos[4]                     
                );
            }
        } catch (Exception e) {
            System.err.println("Error al parsear línea: " + linea);
            System.err.println("Error: " + e.getMessage());
        }
        
        return null;
    }
    
   
    private int generarNuevoId() {
        ArrayList<Cliente> clientes = obtenerTodos();
        
        if (clientes.isEmpty()) {
            return 1;  
        }
        
        int maxId = 0;
        for (Cliente cliente : clientes) {
            if (cliente.getId() > maxId) {
                maxId = cliente.getId();
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
}