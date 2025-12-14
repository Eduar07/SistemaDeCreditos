package com.eduar.util;

import java.io.*;
import java.util.ArrayList;


public class ArchivoUtil {
    
    
    public static boolean guardarLinea(String rutaArchivo, String linea) {
        try {
            
            BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo, true));
            
            writer.write(linea);
            
            writer.newLine();
            
            writer.close();
            
            return true;
            
        } catch (IOException e) {
            System.err.println("Error al guardar línea: " + e.getMessage());
            return false;
        }
    }
    
    
   
    public static ArrayList<String> leerArchivo(String rutaArchivo) {
        ArrayList<String> lineas = new ArrayList<>();
        
        try {
         
            BufferedReader reader = new BufferedReader(new FileReader(rutaArchivo));
            
            String linea;
            
            while ((linea = reader.readLine()) != null) {
                lineas.add(linea);
            }
            
            reader.close();
            
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado: " + rutaArchivo);
            System.out.println("Se creará al guardar el primer registro.");
            
        } catch (IOException e) {
            System.err.println("Error al leer archivo: " + e.getMessage());
        }
        
        return lineas;
    }
    
    
    
    public static boolean actualizarArchivo(String rutaArchivo, ArrayList<String> lineas) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo, false));
            
            for (String linea : lineas) {
                writer.write(linea);
                writer.newLine();
            }
            
            writer.close();
            
            return true;
            
        } catch (IOException e) {
            System.err.println("Error al actualizar archivo: " + e.getMessage());
            return false;
        }
    }
    
 
    public static boolean existeArchivo(String rutaArchivo) {
        File archivo = new File(rutaArchivo);
        return archivo.exists();
    }
    
    
    public static int contarLineas(String rutaArchivo) {
        return leerArchivo(rutaArchivo).size();
    }
    
   
    public static boolean eliminarArchivo(String rutaArchivo) {
        File archivo = new File(rutaArchivo);
        return archivo.delete();
    }
    
   
    public static boolean estaVacio(String rutaArchivo) {
        return contarLineas(rutaArchivo) == 0;
    }
    
   
    public static boolean crearArchivoSiNoExiste(String rutaArchivo) {
        File archivo = new File(rutaArchivo);
        
        if (archivo.exists()) {
            return false; 
        }
        
        try {
            return archivo.createNewFile();
        } catch (IOException e) {
            System.err.println("Error al crear archivo: " + e.getMessage());
            return false;
        }
    }
}