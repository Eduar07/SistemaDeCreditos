package com.eduar.dao;

import com.eduar.modelo.Pago;
import com.eduar.modelo.Prestamo;
import com.eduar.util.ArchivoUtil;
import java.time.LocalDate;
import java.util.ArrayList;


public class PagoDAO {
    
    private static final String ARCHIVO = "data/pagos.txt";
    
    private PrestamoDAO prestamoDAO;
    
    public PagoDAO() {
        this.prestamoDAO = new PrestamoDAO();
    }
    
    
    //                    CREATE
    
    public boolean crear(Pago pago) {
        int nuevoId = generarNuevoId();
        pago.setId(nuevoId);
        
        String linea = pagoALinea(pago);
        return ArchivoUtil.guardarLinea(ARCHIVO, linea);
    }
    
    
    //                    READ
    
    public ArrayList<Pago> obtenerTodos() {
        ArrayList<Pago> pagos = new ArrayList<>();
        ArrayList<String> lineas = ArchivoUtil.leerArchivo(ARCHIVO);
        
        for (String linea : lineas) {
            if (!linea.trim().isEmpty()) {
                Pago pago = lineaAPago(linea);
                if (pago != null) {
                    pagos.add(pago);
                }
            }
        }
        
        return pagos;
    }
    
    public Pago obtenerPorId(int id) {
        ArrayList<Pago> pagos = obtenerTodos();
        
        for (Pago pago : pagos) {
            if (pago.getId() == id) {
                return pago;
            }
        }
        
        return null;
    }
    
    
    //                    UPDATE
    
    public boolean actualizar(Pago pago) {
        ArrayList<String> lineas = ArchivoUtil.leerArchivo(ARCHIVO);
        ArrayList<String> lineasActualizadas = new ArrayList<>();
        
        boolean encontrado = false;
        
        for (String linea : lineas) {
            if (!linea.trim().isEmpty()) {
                String[] datos = linea.split("\\|");
                int idLinea = Integer.parseInt(datos[0]);
                
                if (idLinea == pago.getId()) {
                    lineasActualizadas.add(pagoALinea(pago));
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
            return
            ArchivoUtil.actualizarArchivo(ARCHIVO, lineasFiltradas);
}
                return false;
}
private String pagoALinea(Pago pago) {
    return String.format("%d|%d|%s|%.2f|%s",
        pago.getId(),
        pago.getPrestamo().getId(),
        pago.getFechaPago().toString(),
        pago.getMonto(),
        pago.getObservaciones() != null ? pago.getObservaciones() : ""
    );
}

private Pago lineaAPago(String linea) {
    try {
        String[] datos = linea.split("\\|");
        
        if (datos.length >= 4) {
            int prestamoId = Integer.parseInt(datos[1]);
            Prestamo prestamo = prestamoDAO.obtenerPorId(prestamoId);
            
            if (prestamo != null) {
                String observaciones = datos.length >= 5 ? datos[4] : "";
                
                return new Pago(
                    Integer.parseInt(datos[0]),     
                    prestamo,                       
                    LocalDate.parse(datos[2]),      
                    Double.parseDouble(datos[3]),   
                    observaciones                  
                );
            }
        }
    } catch (Exception e) {
        System.err.println("Error al parsear línea: " + linea);
    }
    
    return null;
}

private int generarNuevoId() {
    ArrayList<Pago> pagos = obtenerTodos();
    
    if (pagos.isEmpty()) {
        return 1;
    }
    
    int maxId = 0;
    for (Pago pago : pagos) {
        if (pago.getId() > maxId) {
            maxId = pago.getId();
        }
    }
    
    return maxId + 1;
}

public int contar() {
    return obtenerTodos().size();
}


public ArrayList<Pago> obtenerPorPrestamo(int prestamoId) {
    ArrayList<Pago> resultado = new ArrayList<>();
    ArrayList<Pago> todos = obtenerTodos();
    
    for (Pago pago : todos) {
        if (pago.getPrestamo().getId() == prestamoId) {
            resultado.add(pago);
        }
    }
    
    return resultado;
}
}