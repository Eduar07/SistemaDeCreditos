package com.eduar.modelo;

public abstract class Persona {

    protected int id;
    protected String nombre;
    protected String documento;
    protected String correo;

    
    public Persona() {
    }
    
 
    public Persona(int id, String nombre, String documento, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.documento = documento;
        this.correo = correo;
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null) {
            throw new IllegalArgumentException("El nombre no puede ser null");
        }
        if (nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        this.nombre = nombre.trim();
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        if (documento == null) {
            throw new IllegalArgumentException("El documento no puede ser null");
        }
        if (documento.trim().isEmpty()) {
            throw new IllegalArgumentException("El documento no puede estar vacío");
        }
        this.documento = documento.trim();
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        if (correo == null) {
            throw new IllegalArgumentException("El correo no puede ser null");
        }
        if (!correo.contains("@") || !correo.contains(".")) {
            throw new IllegalArgumentException("El correo no es válido");
        }
        this.correo = correo.trim();
    }

    public abstract String getTipo();

    @Override
    public String toString() {
        return String.format("ID: %d | Nombre: %s | Documento: %s | Correo: %s", 
                             id, nombre, documento, correo);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Persona)) {
            return false;
        }
        Persona otra = (Persona) obj;
        return this.documento.equals(otra.documento);
    }

    @Override
    public int hashCode() {
        return documento.hashCode();
    }
}