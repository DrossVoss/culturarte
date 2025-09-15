package culturarte.paneles;

import culturarte.casosuso.Propuesta;
import javax.swing.*;
import java.awt.*;

public class PropuestaDetalleDialog extends JDialog {
    public PropuestaDetalleDialog(Window parent, Propuesta p) {
        super(parent, "Detalle de propuesta", ModalityType.APPLICATION_MODAL);
        init(p);
    }

    private void init(Propuesta p) {

        // Enriquecer datos desde la BD si faltan en el objeto Propuesta
        try {
            java.sql.Connection conn = null;
            java.sql.PreparedStatement ps = null;
            java.sql.ResultSet rs = null;
            try {
                conn = culturarte.casosuso.DatabaseConnection.getConnection();
                // Descripción desde descripciones_propuestas si es null
                if (p.getDescripcion() == null) {
                    ps = conn.prepareStatement("SELECT descripcion FROM descripciones_propuestas WHERE ref = ?");
                    ps.setString(1, p.getId());
                    rs = ps.executeQuery();
                    if (rs.next()) {
                        try { p.setDescripcion(rs.getString("descripcion")); } catch (Exception ignore) {}
                    }
                    try { rs.close(); } catch (Exception ignore) {}
                    try { ps.close(); } catch (Exception ignore) {}
                }
                // Tipo de retorno por defecto desde propuestas si es null
                if (p.getTipoRetorno() == null) {
                    ps = conn.prepareStatement("SELECT tipo_retorno FROM propuestas WHERE ref = ?");
                    ps.setString(1, p.getId());
                    rs = ps.executeQuery();
                    if (rs.next()) {
                        try { p.setTipoRetorno(rs.getString("tipo_retorno")); } catch (Exception ignore) {}
                    }
                    try { rs.close(); } catch (Exception ignore) {}
                    try { ps.close(); } catch (Exception ignore) {}
                }
                // Intentar recuperar fecha de realización desde la tabla propuestas si falta
                if (p.getFechaRealizacion() == null) {
                    try {
                        ps = conn.prepareStatement("SELECT fecha FROM propuestas WHERE ref = ?");
                        ps.setString(1, p.getId());
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            java.sql.Date fd2 = rs.getDate("fecha");
                            if (fd2 != null) {
                                try { p.setFechaRealizacion(fd2.toLocalDate()); } catch (Exception ignore) {}
                            }
                        }
                    } catch (Exception ignore) {}
                    try { if (rs != null) { rs.close(); rs = null; } } catch (Exception ignore) {}
                    try { if (ps != null) { ps.close(); ps = null; } } catch (Exception ignore) {}
                }

                // Fecha de publicación: último estado 'Publicada' en estados_propuestas
                ps = conn.prepareStatement("SELECT fecha, hora FROM estados_propuestas WHERE propuesta_ref = ? AND estado = 'Publicada' ORDER BY fecha DESC, hora DESC LIMIT 1");
                ps.setString(1, p.getId());
                rs = ps.executeQuery();
                if (rs.next()) {
                    java.sql.Date fd = rs.getDate("fecha");
                    java.sql.Time ht = rs.getTime("hora");
                    if (fd != null) {
                        java.time.LocalDate dpub = fd.toLocalDate();
                        java.time.LocalTime tpub = (ht != null) ? ht.toLocalTime() : java.time.LocalTime.MIDNIGHT;
                        try { p.setFechaPublicacion(java.time.LocalDateTime.of(dpub, tpub)); } catch (Exception ignore) {}
                    }
                }
            } catch (Exception ex) {
                // ignore DB enrichment errors but print stacktrace for debug
                ex.printStackTrace();
            } finally {
                try { if (rs != null) rs.close(); } catch (Exception e) {}
                try { if (ps != null) ps.close(); } catch (Exception e) {}
                try { if (conn != null) conn.close(); } catch (Exception e) {}
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        JTextArea ta = new JTextArea();
        ta.setEditable(false);
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(p.getId() == null ? "(sin id)" : p.getId()).append("\n\n");
        sb.append("Título: ").append(p.getTitulo() == null ? "(sin título)" : p.getTitulo()).append("\n\n");
        sb.append("Descripción:\n").append(p.getDescripcion() == null ? "(no disponible)" : p.getDescripcion()).append("\n\n");
        try {
            if (p.getCategoria() != null) sb.append("Categoría: ").append(p.getCategoria().getNombre()).append("\n");
        } catch (Exception ex) {}
        sb.append("Lugar: ").append(p.getLugar() == null ? "(no disponible)" : p.getLugar()).append("\n");
        sb.append("Fecha realización: ").append(p.getFechaRealizacion() == null ? "(no disponible)" : p.getFechaRealizacion().toString()).append("\n");
        sb.append("Precio entrada: ").append(p.getPrecioEntrada() == null ? "(no disponible)" : p.getPrecioEntrada().toString()).append("\n");
        sb.append("Monto necesario: ").append(p.getMontoNecesario() == null ? "(no disponible)" : p.getMontoNecesario().toString()).append("\n");
        sb.append("Fecha publicación: ").append(p.getFechaPublicacion() == null ? "(no disponible)" : p.getFechaPublicacion().toString()).append("\n");
        sb.append("Estado actual: ").append(p.getEstadoActual() == null ? "(no disponible)" : p.getEstadoActual()).append("\n");
        sb.append("Tipo retorno por defecto: ").append(p.getTipoRetorno() == null ? "(no disponible)" : p.getTipoRetorno()).append("\n");
        try {
            if (p.getProponente() != null) {
                sb.append("\nProponente (nickname): ").append(p.getProponente().getNickname()).append(" (id=").append(p.getProponente().getId()).append(")\n");
            }
        } catch (Exception ex) {}
ta.setText(sb.toString());
        ta.setCaretPosition(0);
        JScrollPane sp = new JScrollPane(ta);
        sp.setPreferredSize(new Dimension(700,420));
        JPanel buttons = new JPanel();
        JButton close = new JButton("Cerrar");
        close.addActionListener(e -> dispose());
        buttons.add(close);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(sp, BorderLayout.CENTER);
        getContentPane().add(buttons, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(getOwner());
    }
}
