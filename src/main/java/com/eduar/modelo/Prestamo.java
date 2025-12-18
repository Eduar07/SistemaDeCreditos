package com.eduar.modelo;

import java.time.LocalDate;
import java.util.ArrayList;

/*=======================================================EXAMEN DE JAVA======================================================== */
/*============================================================================================================================= */
public class Prestamo {
    
    /*Aqui realizamos los atributos de la clase pretamo */
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
    
    
    /*==================================================Constructores=========================================================== */
    
   
    public Prestamo() {
        this.pagos = new ArrayList<>();
        this.estado = "pendiente";
    }
    
    /* Constructor sin ID */
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
    
   /* Constructor con ID */
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
    
    
 /* Aqui se va a realizar los metodos get y getters*/
    
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
            throw new IllegalArgumentException("EL Monto Debe ser Mayor a 0");
        }
    }
    
    public double getInteres() {
        return interes;
    }
    
    public void setInteres(double interes) {
        if (interes >= 0 && interes <= 100) {
            this.interes = interes;
        } else {
            throw new IllegalArgumentException("El Interés debe estar entre 0 y 100");
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
    
    
   /* Aqui realizamos el metodo calculo para calcular el monto total */
    
    /*Método calcularTotalAPagar() que retorne el monto total a pagar (monto + intereses), segun lo Pide Adrian parchao
 */
    public double calcularMontoTotal() {
        double montoInteres = monto * (interes / 100);
        return monto + montoInteres;
    }
    
    
    public double calcularCuotaMensual() {
        return calcularMontoTotal() / cuotas;
    }
    
 
    public int calcularCuotasPagadas() {
        double totalPagado = calcularTotalPagado();
        double cuotaMensual = calcularCuotaMensual();
        
        if (cuotaMensual > 0) {
            return (int) (totalPagado / cuotaMensual);
        }
        return 0;
    }
    
 
    public double calcularTotalPagado() {
        double total = 0;
        for (Pago pago : pagos) {
            total += pago.getMonto();
        }
        return total;
    }
    
    
    public double calcularPorcentajePagado() {
        double montoTotal = calcularMontoTotal();
        double totalPagado = calcularTotalPagado();
        
        if (montoTotal > 0) {
            return (totalPagado / montoTotal) * 100;
        }
        return 0;
    }
    
    
    /* Metodo negocio del prestamo */
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
    
   
    public boolean estaVencido() {

    if (estado.equalsIgnoreCase("vencido")) {
        return true;
    }
    
    if (estado.equalsIgnoreCase("pagado")) {
        return false;
    }
        
        LocalDate fechaVencimiento = fechaInicio.plusMonths(cuotas);
        return LocalDate.now().isAfter(fechaVencimiento);
    }
    
   
    public boolean estaAlDia() {
        int cuotasPagadas = calcularCuotasPagadas();
        
        long mesesTranscurridos = java.time.temporal.ChronoUnit.MONTHS.between(
            fechaInicio, LocalDate.now()
        );
        
        return cuotasPagadas >= mesesTranscurridos;
    }
    
   
    public int calcularCuotasPendientes() {
        return cuotas - calcularCuotasPagadas();
    }
    
/* Sobrescribe toString() para mostrar la información del préstamo. lo que pide Adrian Parchao
 */
    
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