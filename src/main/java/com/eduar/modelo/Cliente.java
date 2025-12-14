package com.eduar.modelo;


public class Cliente extends Persona {
 private String telefono;


 public Cliente(){
    super();
 }

 public Cliente( String nombre, String documento, String correo, String telefono){
    super(0, nombre, documento, correo);
    this.telefono = telefono;
 }

public Cliente(int id, String nombre, String documento, String correo, String telefono){
    super(id, nombre, documento, correo);
    this.telefono = telefono;
}
public String getTelefono(){
    return telefono;

}

public void setTelefono(String telefono){
    if(telefono == null){
        throw new IllegalArgumentException("El teléfono no puede ser null");
    }
    if(telefono.trim().isEmpty()){
        throw new IllegalArgumentException("El teléfono no puede estar vacío");
    }
    String telefonoLimpio = telefono.replaceAll("[^0-9]", "");

        
   if (telefonoLimpio.length() < 7 || telefonoLimpio.length() > 15) {
            throw new IllegalArgumentException(
                "El teléfono debe tener entre 7 y 15 dígitos"
            );
        }     
    this.telefono = telefono.trim();

}
 @Override
    public String getTipo() {
        return "Cliente";
    }

        @Override
    public String toString() {
        return String.format(
            "Cliente [ID: %d | Nombre: %s | Documento: %s | Correo: %s | Teléfono: %s]",
            id,         
            nombre,     
            documento,  
            correo,     
            telefono   
        );
    }

       public boolean esCelular() {
        if (telefono != null && telefono.length() >= 10) {
            String telefonoLimpio = telefono.replaceAll("[^0-9]", "");
            
            return telefonoLimpio.charAt(0) == '3';
        }
        return false;
    }

      public String getTelefonoFormateado() {
        if (telefono == null) {
            return "";
        }

        String telefonoLimpio = telefono.replaceAll("[^0-9]", "");
 if (telefonoLimpio.length() == 10) {
            return telefonoLimpio.substring(0, 3) + "-" + 
                   telefonoLimpio.substring(3, 6) + "-" + 
                   telefonoLimpio.substring(6);
        }
        
        return telefono;
    }

      public boolean tieneTelefonoValido() {
        if (telefono == null || telefono.trim().isEmpty()) {
            return false;
        }
        
        String telefonoLimpio = telefono.replaceAll("[^0-9]", "");
        return telefonoLimpio.length() >= 7;
    }

     public String getResumen() {
        return nombre + " - " + getTelefonoFormateado();
    }
    
   
    public String getTipoTelefono() {
        return esCelular() ? "Celular" : "Fijo";
    }

}






