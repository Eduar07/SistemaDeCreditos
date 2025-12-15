package com.eduar.modelo;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Clase Prestamo - Representa un préstamo del sistema
 * 
 * @author Eduar Humberto Guerrero Vergel
 * @version 1.0
 */
public class Prestamo {
    
    // ═══════════════════════════════════════════════════════════
    //                        ATRIBUTOS
    // ═══════════════════════════════════════════════════════════
    
    private int id;
    private Cliente cliente;
    private Empleado empleado;
    private double monto;
    private double interes;
    private int cuotas;
    private LocalDate fechaInicio;
    private String estado;
    private double saldoPendiente;
    private ArrayList<Pago> pagos;
    
    
    // ═══════════════════════════════════════════════════════════
    //                      CONSTRUCTORES
    // ═══════════════════════════════════════════════════════════
    
    /**
     * Constructor vacío
     */
    public Prestamo() {
        this.pagos = new ArrayList<>();
        this.estado = "pendiente";
    }
    
    /**
     * Constructor sin ID (para crear nuevos préstamos)
     * 
     * @param cliente Cliente que solicita
     * @param empleado Empleado que aprueba
     * @param monto Monto del préstamo
     * @param interes Tasa de interés
     * @param cuotas Número de cuotas
     * @param fechaInicio Fecha de inicio
     */
    public Prestamo(Cliente cliente, Empleado empleado, double monto, 
                    double interes, int cuotas, LocalDate fechaInicio) {
        this.cliente = cliente;
        this.empleado = empleado;
        this.monto = monto;
        this.interes = interes;
        this.cuotas = cuotas;
        this.fechaInicio = fechaInicio;
        this.estado = "pendiente";
        this.saldoPendiente = calcularMontoTotal();
        this.pagos = new ArrayList<>();
    }
    
    /**
     * Constructor completo con ID (para cargar desde BD/archivo)
     * 
     * @param id ID del préstamo
     * @param cliente Cliente del préstamo
     * @param empleado Empleado que aprobó
     * @param monto Monto del préstamo
     * @param interes Tasa de interés
     * @param cuotas Número de cuotas
     * @param fechaInicio Fecha de inicio
     * @param estado Estado actual
     * @param saldoPendiente Saldo pendiente
     */
    public Prestamo(int id, Cliente cliente, Empleado empleado, double monto,
                    double interes, int cuotas, LocalDate fechaInicio, 
                    String estado, double saldoPendiente) {
        this.id = id;
        this.cliente = cliente;
        this.empleado = empleado;
        this.monto = monto;
        this.interes = interes;
        this.cuotas = cuotas;
        this.fechaInicio = fechaInicio;
        this.estado = estado;
        this.saldoPendiente = saldoPendiente;
        this.pagos = new ArrayList<>();
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                    GETTERS Y SETTERS
    // ═══════════════════════════════════════════════════════════
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public Cliente getCliente() {
        return cliente;
    }
    
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public Empleado getEmpleado() {
        return empleado;
    }
    
    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }
    
    public double getMonto() {
        return monto;
    }
    
    public void setMonto(double monto) {
        if (monto > 0) {
            this.monto = monto;
            this.saldoPendiente = calcularMontoTotal();
        } else {
            throw new IllegalArgumentException("El monto debe ser mayor a 0");
        }
    }
    
    public double getInteres() {
        return interes;
    }
    
    public void setInteres(double interes) {
        if (interes >= 0 && interes <= 100) {
            this.interes = interes;
        } else {
            throw new IllegalArgumentException("El interés debe estar entre 0 y 100");
        }
    }
    
    public int getCuotas() {
        return cuotas;
    }
    
    public void setCuotas(int cuotas) {
        if (cuotas > 0) {
            this.cuotas = cuotas;
        } else {
            throw new IllegalArgumentException("Las cuotas deben ser mayor a 0");
        }
    }
    
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }
    
    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public double getSaldoPendiente() {
        return saldoPendiente;
    }
    
    public void setSaldoPendiente(double saldoPendiente) {
        this.saldoPendiente = saldoPendiente;
    }
    
    public ArrayList<Pago> getPagos() {
        return pagos;
    }
    
    public void setPagos(ArrayList<Pago> pagos) {
        this.pagos = pagos;
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                   MÉTODOS DE CÁLCULO
    // ═══════════════════════════════════════════════════════════
    
    /**
     * Calcula el monto total del préstamo (capital + intereses)
     */
    public double calcularMontoTotal() {
        double montoInteres = monto * (interes / 100);
        return monto + montoInteres;
    }
    
    /**
     * Calcula el valor de la cuota mensual
     */
    public double calcularCuotaMensual() {
        return calcularMontoTotal() / cuotas;
    }
    
    /**
     * Calcula cuántas cuotas se han pagado
     */
    public int calcularCuotasPagadas() {
        double totalPagado = calcularTotalPagado();
        double cuotaMensual = calcularCuotaMensual();
        
        if (cuotaMensual > 0) {
            return (int) (totalPagado / cuotaMensual);
        }
        return 0;
    }
    
    /**
     * Calcula el total pagado hasta ahora
     */
    public double calcularTotalPagado() {
        double total = 0;
        for (Pago pago : pagos) {
            total += pago.getMonto();
        }
        return total;
    }
    
    /**
     * Calcula el porcentaje pagado del préstamo
     */
    public double calcularPorcentajePagado() {
        double montoTotal = calcularMontoTotal();
        double totalPagado = calcularTotalPagado();
        
        if (montoTotal > 0) {
            return (totalPagado / montoTotal) * 100;
        }
        return 0;
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                   MÉTODOS DE NEGOCIO
    // ═══════════════════════════════════════════════════════════
    
    /**
     * Registra un pago y actualiza el saldo pendiente
     */
    public void registrarPago(Pago pago) {
        if (pago == null) {
            throw new IllegalArgumentException("El pago no puede ser null");
        }
        
        if (pago.getMonto() <= 0) {
            throw new IllegalArgumentException("El monto del pago debe ser mayor a 0");
        }
        
        pagos.add(pago);
        saldoPendiente -= pago.getMonto();
        
        if (saldoPendiente <= 0) {
            estado = "pagado";
            saldoPendiente = 0;
        }
    }
    
    /**
     * Verifica si el préstamo está vencido
     */
    public boolean estaVencido() {
    // Si ya está marcado como vencido en BD
    if (estado.equalsIgnoreCase("vencido")) {
        return true;
    }
    
    // Si ya está pagado, no puede estar vencido
    if (estado.equalsIgnoreCase("pagado")) {
        return false;
    }
        
        LocalDate fechaVencimiento = fechaInicio.plusMonths(cuotas);
        return LocalDate.now().isAfter(fechaVencimiento);
    }
    
    /**
     * Verifica si el préstamo está al día
     */
    public boolean estaAlDia() {
        int cuotasPagadas = calcularCuotasPagadas();
        
        long mesesTranscurridos = java.time.temporal.ChronoUnit.MONTHS.between(
            fechaInicio, LocalDate.now()
        );
        
        return cuotasPagadas >= mesesTranscurridos;
    }
    
    /**
     * Obtiene el número de cuotas pendientes
     */
    public int calcularCuotasPendientes() {
        return cuotas - calcularCuotasPagadas();
    }
    
    
    // ═══════════════════════════════════════════════════════════
    //                      toString()
    // ═══════════════════════════════════════════════════════════
    
    @Override
    public String toString() {
        return String.format(
            "Préstamo #%d\n" +
            "  Cliente: %s\n" +
            "  Empleado: %s\n" +
            "  Monto: $%,.2f | Interés: %.2f%% | Cuotas: %d\n" +
            "  Monto total: $%,.2f | Cuota mensual: $%,.2f\n" +
            "  Estado: %s | Saldo pendiente: $%,.2f\n" +
            "  Fecha inicio: %s | Pagos realizados: %d",
            id, 
            cliente != null ? cliente.getNombre() : "N/A", 
            empleado != null ? empleado.getNombre() : "N/A",
            monto, interes, cuotas,
            calcularMontoTotal(), calcularCuotaMensual(),
            estado, saldoPendiente,
            fechaInicio, pagos.size()
        );
    }
}