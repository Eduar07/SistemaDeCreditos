package com.eduar.modelo;

public class Empleado extends Persona {

    private String rol;
    private double salario;

    
    public Empleado() {
        super(0, "", "", "");  
    }
    
  
    public Empleado(String nombre, String documento, String correo, 
                    String rol, double salario) {
        super(0, nombre, documento, correo);
        this.rol = rol;
        this.salario = salario;
    }
    
  
    public Empleado(int id, String nombre, String documento, String correo, 
                    String rol, double salario) {
        super(id, nombre, documento, correo);
        this.rol = rol;
        this.salario = salario;
    }

   
    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        if (rol == null) {
            throw new IllegalArgumentException("el rol no puede ser null");
        }
        if (rol.trim().isEmpty()) {
            throw new IllegalArgumentException("el rol no puede estar vacío");
        }
        this.rol = rol;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        if (salario <= 0) {
            throw new IllegalArgumentException("el salario debe ser mayor a 0");
        }
        this.salario = salario;
    }

  
    @Override
    public String getTipo() {
        return "Empleado";
    }

   
    @Override
    public String toString() {
        return String.format(
            "Empleado [ID: %d | Nombre: %s | Documento: %s | Correo: %s | Rol: %s | Salario: $%.2f]",
            id, nombre, documento, correo, rol, salario
        );
    }

    
    public double calcularSalarioAnual() {
        return salario * 12;
    }

    public boolean esGerente() {
        return rol.equalsIgnoreCase("Gerente");
    }

    public void aumentarSalario(double porcentaje) {
        if (porcentaje <= 0 || porcentaje > 100) {
            throw new IllegalArgumentException(
                "El porcentaje debe estar entre 0 y 100"
            );
        }
        
        double aumento = salario * (porcentaje / 100);
        salario += aumento;
        
        System.out.println("✓ Salario aumentado en " + porcentaje + "%");
        System.out.println("  Nuevo salario: $" + String.format("%.2f", salario));
    }

    public boolean puedeAprobarPrestamos() {
        return esGerente();
    }

    public String getResumen() {
        return nombre + " - " + rol;
    }
}