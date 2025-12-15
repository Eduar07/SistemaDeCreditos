package com.eduar.dao;


import com.eduar.util.ArchivoUtil;
import com.eduar.modelo.Prestamo;
import com.eduar.modelo.Cliente;
import com.eduar.modelo.Empleado;
import com.eduar.util.ConexionDb;
import java.sql.*;
import java.util.ArrayList;

public class PrestamoDAOImpl implements IDao<Prestamo> {
    
    private ClienteDAOImpl clienteDAO;
    private EmpleadoDAOImpl empleadoDAO;
    
    public PrestamoDAOImpl() {
        this.clienteDAO = new ClienteDAOImpl();
        this.empleadoDAO = new EmpleadoDAOImpl();
    }
    
    @Override
public void guardar(Prestamo prestamo) {
    String sql = "INSERT INTO prestamos (cliente_id, empleado_id, monto, interes, cuotas, " +
                 "fecha_inicio, estado, saldo_pendiente) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    
    try (Connection conn = ConexionDb.getConexion();
         PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        
        pstmt.setInt(1, prestamo.getCliente().getId());
        pstmt.setInt(2, prestamo.getEmpleado().getId());
        pstmt.setDouble(3, prestamo.getMonto());
        pstmt.setDouble(4, prestamo.getInteres());
        pstmt.setInt(5, prestamo.getCuotas());
        pstmt.setDate(6, Date.valueOf(prestamo.getFechaInicio()));
        pstmt.setString(7, prestamo.getEstado());
        pstmt.setDouble(8, prestamo.getSaldoPendiente());
        
        pstmt.executeUpdate();
        
        try (ResultSet rs = pstmt.getGeneratedKeys()) {
            if (rs.next()) {
                prestamo.setId(rs.getInt(1));
                
                // ✅ GUARDAR EN ARCHIVO
                String linea = String.format("%d|%d|%d|%.2f|%.2f|%d|%s|%s|%.2f",
                    prestamo.getId(),
                    prestamo.getCliente().getId(),
                    prestamo.getEmpleado().getId(),
                    prestamo.getMonto(),
                    prestamo.getInteres(),
                    prestamo.getCuotas(),
                    prestamo.getFechaInicio().toString(),
                    prestamo.getEstado(),
                    prestamo.getSaldoPendiente()
                );
                ArchivoUtil.guardarLinea("data/prestamos.txt", linea);
            }
        }
        
    } catch (SQLException e) {
        System.err.println("Error al guardar préstamo: " + e.getMessage());
    }
}
    
    @Override
    public Prestamo buscarPorId(int id) {
        String sql = "SELECT * FROM prestamos WHERE id = ?";
        
        try (Connection conn = ConexionDb.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int clienteId = rs.getInt("cliente_id");
                    Cliente cliente = clienteDAO.buscarPorId(clienteId);
                    
                    if (cliente == null) {
                        System.err.println("Cliente o Empleado no encontrado para préstamo ID: " + id);
                        return null;
                    }
                    
                    int empleadoId = rs.getInt("empleado_id");
                    Empleado empleado = empleadoDAO.buscarPorId(empleadoId);
                    
                    if (empleado == null) {
                        System.err.println("Cliente o Empleado no encontrado para préstamo ID: " + id);
                        return null;
                    }
                    
                    Prestamo prestamo = new Prestamo(
                        cliente,
                        empleado,
                        rs.getDouble("monto"),
                        rs.getDouble("interes"),
                        rs.getInt("cuotas"),
                        rs.getDate("fecha_inicio").toLocalDate()
                    );
                    
                    prestamo.setId(rs.getInt("id"));
                    prestamo.setEstado(rs.getString("estado"));
                    prestamo.setSaldoPendiente(rs.getDouble("saldo_pendiente"));
                    
                    return prestamo;
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error al buscar préstamo: " + e.getMessage());
        }
        
        return null;
    }
    
    
    @Override
    public ArrayList<Prestamo> listar() {
        ArrayList<Prestamo> prestamos = new ArrayList<>();
        String sql = "SELECT * FROM prestamos";
        
        try (Connection conn = ConexionDb.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                int clienteId = rs.getInt("cliente_id");
                Cliente cliente = clienteDAO.buscarPorId(clienteId);
                
                int empleadoId = rs.getInt("empleado_id");
                Empleado empleado = empleadoDAO.buscarPorId(empleadoId);
                
                if (cliente == null || empleado == null) {
                    System.err.println("Cliente o Empleado no encontrado para préstamo ID: " + rs.getInt("id"));
                    continue;
                }
                
                Prestamo prestamo = new Prestamo(
                    cliente,
                    empleado,
                    rs.getDouble("monto"),
                    rs.getDouble("interes"),
                    rs.getInt("cuotas"),
                    rs.getDate("fecha_inicio").toLocalDate()
                );
                
                prestamo.setId(rs.getInt("id"));
                prestamo.setEstado(rs.getString("estado"));
                prestamo.setSaldoPendiente(rs.getDouble("saldo_pendiente"));
                
                prestamos.add(prestamo);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al listar préstamos: " + e.getMessage());
        }
        
        return prestamos;
    }
    
    
    @Override
    public boolean actualizar(Prestamo prestamo) {
        String sql = "UPDATE prestamos SET estado = ?, saldo_pendiente = ? WHERE id = ?";
        
        try (Connection conn = ConexionDb.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, prestamo.getEstado());
            pstmt.setDouble(2, prestamo.getSaldoPendiente());
            pstmt.setInt(3, prestamo.getId());
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar préstamo: " + e.getMessage());
            return false;
        }
    }
    
    
    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM prestamos WHERE id = ?";
        
        try (Connection conn = ConexionDb.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar préstamo: " + e.getMessage());
            return false;
        }
    }
    
    
    @Override
    public int contar() {
        String sql = "SELECT COUNT(*) FROM prestamos";
        
        try (Connection conn = ConexionDb.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al contar préstamos: " + e.getMessage());
        }
        
        return 0;
    }
    
    
    public ArrayList<Prestamo> buscarPorCliente(int clienteId) {
        ArrayList<Prestamo> prestamos = new ArrayList<>();
        String sql = "SELECT * FROM prestamos WHERE cliente_id = ?";
        
        try (Connection conn = ConexionDb.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, clienteId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Cliente cliente = clienteDAO.buscarPorId(clienteId);
                    
                    int empleadoId = rs.getInt("empleado_id");
                    Empleado empleado = empleadoDAO.buscarPorId(empleadoId);
                    
                    if (cliente == null || empleado == null) {
                        System.err.println("Cliente o Empleado no encontrado para préstamo ID: " + rs.getInt("id"));
                        continue;
                    }
                    
                    Prestamo prestamo = new Prestamo(
                        cliente,
                        empleado,
                        rs.getDouble("monto"),
                        rs.getDouble("interes"),
                        rs.getInt("cuotas"),
                        rs.getDate("fecha_inicio").toLocalDate()
                    );
                    
                    prestamo.setId(rs.getInt("id"));
                    prestamo.setEstado(rs.getString("estado"));
                    prestamo.setSaldoPendiente(rs.getDouble("saldo_pendiente"));
                    
                    prestamos.add(prestamo);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error al buscar préstamos por cliente: " + e.getMessage());
        }
        
        return prestamos;
    }
    
    
    public ArrayList<Prestamo> buscarPorEstado(String estado) {
        ArrayList<Prestamo> prestamos = new ArrayList<>();
        String sql = "SELECT * FROM prestamos WHERE estado = ?";
        
        try (Connection conn = ConexionDb.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, estado);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int clienteId = rs.getInt("cliente_id");
                    Cliente cliente = clienteDAO.buscarPorId(clienteId);
                    
                    int empleadoId = rs.getInt("empleado_id");
                    Empleado empleado = empleadoDAO.buscarPorId(empleadoId);
                    
                    if (cliente == null || empleado == null) {
                        System.err.println("Cliente o Empleado no encontrado para préstamo ID: " + rs.getInt("id"));
                        continue;
                    }
                    
                    Prestamo prestamo = new Prestamo(
                        cliente,
                        empleado,
                        rs.getDouble("monto"),
                        rs.getDouble("interes"),
                        rs.getInt("cuotas"),
                        rs.getDate("fecha_inicio").toLocalDate()
                    );
                    
                    prestamo.setId(rs.getInt("id"));
                    prestamo.setEstado(rs.getString("estado"));
                    prestamo.setSaldoPendiente(rs.getDouble("saldo_pendiente"));
                    
                    prestamos.add(prestamo);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error al buscar préstamos por estado: " + e.getMessage());
        }
        
        return prestamos;
    }
}