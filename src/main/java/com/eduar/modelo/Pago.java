package com.eduar.modelo;

import java.time.LocalDate;



public class Pago {
    
    private int id;
    
    private Prestamo prestamo;
    
    private LocalDate fechaPago;       
    private double monto;             
    private String observaciones;      
    
    
 
    public Pago() {
    }
    
   
    public Pago(Prestamo prestamo, LocalDate fechaPago, double monto, String observaciones) {
        this.prestamo = prestamo;
        this.fechaPago = fechaPago;
        this.monto = monto;
        this.observaciones = observaciones;
    }
    
   
    public Pago(int id, Prestamo prestamo, LocalDate fechaPago, 
                double monto, String observaciones) {
        this.id = id;
        this.prestamo = prestamo;
        this.fechaPago = fechaPago;
        this.monto = monto;
        this.observaciones = observaciones;
    }
    
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public Prestamo getPrestamo() {
        return prestamo;
    }
    
    public void setPrestamo(Prestamo prestamo) {
        this.prestamo = prestamo;
    }
    
    public LocalDate getFechaPago() {
        return fechaPago;
    }
    
    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }
    
    public double getMonto() {
        return monto;
    }
    
    public void setMonto(double monto) {
        if (monto > 0) {
            this.monto = monto;
        } else {
            throw new IllegalArgumentException("El monto debe ser mayor a 0");
        }
    }
    
    public String getObservaciones() {
        return observaciones;
    }
    
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    

    public boolean cubreCuotaCompleta() {
        if (prestamo != null) {
            double cuotaMensual = prestamo.calcularCuotaMensual();
            return monto >= cuotaMensual;
        }
        return false;
    }
    
    
    public boolean esPagoParcial() {
        return !cubreCuotaCompleta();
    }
    
   
    public String getTipoPago() {
        return cubreCuotaCompleta() ? "Cuota completa" : "Pago parcial";
    }
    
    
   
    @Override
    public String toString() {
        return String.format(
            "Pago #%d | Fecha: %s | Monto: $%,.2f | %s",
            id, fechaPago, monto, getTipoPago()
        );
    }
}