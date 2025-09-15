package culturarte.casosuso;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;

/**
 * Servicio de propuestas.
 * Implementación híbrida: usa JDBC (DatabaseConnection) para consultas de lectura
 * que no deberían forzar la inicialización de JPA/Hibernate en entornos donde falla,
 * y usa JPA para operaciones de escritura (save).
 */
public class PropuestaService {

    /**
     * Guarda o actualiza una propuesta usando JPA.
     */
    public Propuesta save(Propuesta p) {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();
            Propuesta merged = em.merge(p);
            em.getTransaction().commit();
            // Después de persistir via JPA, aseguramos que exista un registro inicial en estados_propuestas
            try (java.sql.Connection conn = DatabaseConnection.getConnection()) {
                // verificar si ya existe algún estado para esta propuesta
                try (java.sql.PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM estados_propuestas WHERE propuesta_ref = ?")) {
                    ps.setString(1, merged.getId());
                    try (java.sql.ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            int cnt = rs.getInt(1);
                            if (cnt == 0) {
                                try (java.sql.PreparedStatement ins = conn.prepareStatement("INSERT INTO estados_propuestas (propuesta_ref, estado, fecha, hora) VALUES (?, ?, CURDATE(), CURTIME())")) {
                                    String estadoInicial = merged.getEstadoActual() == null ? "Ingresada" : merged.getEstadoActual();
                                    ins.setString(1, merged.getId());
                                    ins.setString(2, estadoInicial);
                                    ins.executeUpdate();
                                }
                            }
                        }
                    }
                }
            } catch (Exception jdbcEx) {
                // no bloqueamos la operación si falla el registro de estado inicial; solo logueamos
                System.err.println("PropuestaService.save: no se pudo insertar registro inicial en estados_propuestas: " + jdbcEx.getMessage());
                jdbcEx.printStackTrace(System.err);
            }
            return merged;
        } catch (Throwable ex) {
            if (em != null && em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new RuntimeException("Error al guardar propuesta via JPA: " + ex.getMessage(), ex);
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }

    /**
     * Busca propuestas por estado usando JDBC (fallback seguro).
     */
    public List<Propuesta> findByEstado(String estado) {
        List<Propuesta> out = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DatabaseConnection.getConnection();
            String sql = "SELECT p.ref, p.titulo, p.descripcion, p.imagen_url, p.lugar, p.fecha, p.precio_entrada, p.monto, p.proponente FROM propuestas p WHERE p.ref IN (SELECT propuesta_ref FROM estados_propuestas WHERE estado = ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, estado);
            rs = ps.executeQuery();
            while (rs.next()) {
                Propuesta ptemp = new Propuesta();
                ptemp.setId(rs.getString("ref"));
                ptemp.setTitulo(rs.getString("titulo"));
                ptemp.setDescripcion(rs.getString("descripcion"));
                try { ptemp.setImagenPath(rs.getString("imagen_url")); } catch (Throwable t) {}
                ptemp.setLugar(rs.getString("lugar"));
                Date d = rs.getDate("fecha");
                if (d != null) ptemp.setFechaRealizacion(d.toLocalDate());
                java.math.BigDecimal pe = rs.getBigDecimal("precio_entrada");
                if (pe != null) ptemp.setPrecioEntrada(pe);
                java.math.BigDecimal mn = rs.getBigDecimal("monto");
                if (mn != null) ptemp.setMontoNecesario(mn);
                String pid = rs.getString("proponente");
                if (pid != null) {
                    Usuario u = new Usuario();
                    u.setId(pid);
                    // fetch nickname if present
                    try (PreparedStatement ps2 = conn.prepareStatement("SELECT apodo FROM usuarios WHERE id = ?")) {
                        ps2.setString(1, pid);
                        try (ResultSet rs2 = ps2.executeQuery()) {
                            if (rs2.next()) u.setNickname(rs2.getString("apodo"));
                        }
                    } catch (Exception ex2) { /* ignore */ }
                    ptemp.setProponente(u);
                }
                // obtener estado actual (último)
                try (PreparedStatement ps3 = conn.prepareStatement("SELECT estado FROM estados_propuestas WHERE propuesta_ref = ? ORDER BY fecha DESC, hora DESC LIMIT 1")) {
                    ps3.setString(1, ptemp.getId());
                    try (ResultSet rs3 = ps3.executeQuery()) {
                        if (rs3.next()) ptemp.setEstadoActual(rs3.getString("estado"));
                    }
                } catch (Exception ex3) { /* ignore */ }
                out.add(ptemp);
            }
        } catch (Exception ex) {
            throw new RuntimeException("Error JDBC en findByEstado: " + ex.getMessage(), ex);
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
        return out;
    }

    /**
     * Busca una propuesta por id usando JDBC.
     */
    public Optional<Propuesta> findById(String id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DatabaseConnection.getConnection();
            String sql = "SELECT p.ref, p.titulo, p.descripcion, p.imagen_url, p.lugar, p.fecha, p.precio_entrada, p.monto, p.proponente FROM propuestas p WHERE p.ref = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                Propuesta ptemp = new Propuesta();
                ptemp.setId(rs.getString("ref"));
                ptemp.setTitulo(rs.getString("titulo"));
                ptemp.setDescripcion(rs.getString("descripcion"));
                try { ptemp.setImagenPath(rs.getString("imagen_url")); } catch (Throwable t) {}
                ptemp.setLugar(rs.getString("lugar"));
                Date d = rs.getDate("fecha");
                if (d != null) ptemp.setFechaRealizacion(d.toLocalDate());
                java.math.BigDecimal pe = rs.getBigDecimal("precio_entrada");
                if (pe != null) ptemp.setPrecioEntrada(pe);
                java.math.BigDecimal mn = rs.getBigDecimal("monto");
                if (mn != null) ptemp.setMontoNecesario(mn);
                String pid = rs.getString("proponente");
                if (pid != null) {
                    Usuario u = new Usuario();
                    u.setId(pid);
                    try (PreparedStatement ps2 = conn.prepareStatement("SELECT apodo FROM usuarios WHERE id = ?")) {
                        ps2.setString(1, pid);
                        try (ResultSet rs2 = ps2.executeQuery()) {
                            if (rs2.next()) u.setNickname(rs2.getString("apodo"));
                        }
                    } catch (Exception ex2) {}
                    ptemp.setProponente(u);
                }
                // estado actual
                try (PreparedStatement ps3 = conn.prepareStatement("SELECT estado FROM estados_propuestas WHERE propuesta_ref = ? ORDER BY fecha DESC, hora DESC LIMIT 1")) {
                    ps3.setString(1, ptemp.getId());
                    try (ResultSet rs3 = ps3.executeQuery()) {
                        if (rs3.next()) ptemp.setEstadoActual(rs3.getString("estado"));
                    }
                } catch (Exception ex3) {}
                return Optional.of(ptemp);
            } else {
                return Optional.empty();
            }
        } catch (Exception ex) {
            throw new RuntimeException("Error JDBC en findById: " + ex.getMessage(), ex);
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }

    /**
     * Devuelve todas las propuestas (lectura via JDBC)
     */
    public List<Propuesta> findAll() {
        List<Propuesta> out = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DatabaseConnection.getConnection();
            String sql = "SELECT p.ref, p.titulo FROM propuestas p";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Propuesta ptemp = new Propuesta();
                ptemp.setId(rs.getString("ref"));
                ptemp.setTitulo(rs.getString("titulo"));
                out.add(ptemp);
            }
        } catch (Exception ex) {
            throw new RuntimeException("Error JDBC en findAll: " + ex.getMessage(), ex);
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
        return out;
    }
}
