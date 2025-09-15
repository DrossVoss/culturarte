
package culturarte.paneles;

import culturarte.casosuso.DatabaseConnection;
import culturarte.casosuso.ImageService;
import culturarte.casosuso.Propuesta;
import culturarte.casosuso.PropuestaService;
import culturarte.casosuso.Usuario;
import culturarte.casosuso.UsuarioService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import java.util.List;

/**
 * Panel para consultar el perfil de un proponente (administrador).
 *
 * Funcionalidad requerida:
 * - Mostrar lista de proponentes (apodo/id).
 * - Al seleccionar uno, mostrar datos básicos: nombre, apellido, apodo, email,
 *   imagen asociada (si existe), biografía y dirección de su página web.
 * - Mostrar todas las propuestas del usuario, diferenciadas por estado:
 *   Cancelada, Publicada, En Financiación, Financiada, No Financiada.
 *   Para cada propuesta mostrar: ref, título, lista de usuarios que colaboraron
 *   y el monto total recaudado (sum de colaboraciones).
 *
 * Implementación:
 * - Usa UsuarioService (JPA) para obtener lista/usuario (biografía cargada desde
 *   la tabla biografias por el servicio).
 * - Usa JDBC (DatabaseConnection) para listar propuestas y calcular estado / recaudado
 *   sin forzar operaciones de escritura con JPA.
 */
public class UCConsultaPerfilProponentePanel extends JPanel {

    private JList<String> listProponentes;
    private DefaultListModel<String> listModel;
    private Map<String, String> idByListEntry = new HashMap<>(); // map display -> id

    private JLabel lblNombre;
    private JLabel lblApodo;
    private JLabel lblEmail;
    private JLabel lblWeb;
    private JLabel lblFoto;
    private JTextArea txtBiografia;
    private JTextArea txtPropuestas; // muestra agrupadas por estado

    public UCConsultaPerfilProponentePanel() {
        initComponents();
        loadProponentes();
    }

    private void initComponents() {
        setLayout(new BorderLayout(8,8));

        // Left: lista de proponentes
        listModel = new DefaultListModel<>();
        listProponentes = new JList<>(listModel);
        listProponentes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane spLeft = new JScrollPane(listProponentes);
        spLeft.setPreferredSize(new Dimension(260, 600));
        add(spLeft, BorderLayout.WEST);

        // Right: datos y propuestas
        JPanel right = new JPanel(new BorderLayout(6,6));
        add(right, BorderLayout.CENTER);

        // Top panel: foto + basic info
        JPanel top = new JPanel(new BorderLayout(6,6));
        right.add(top, BorderLayout.NORTH);

        lblFoto = new JLabel();
        lblFoto.setPreferredSize(new Dimension(180,120));
        lblFoto.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        top.add(lblFoto, BorderLayout.WEST);

        JPanel info = new JPanel(new GridLayout(0,1));
        lblNombre = new JLabel();
        lblApodo = new JLabel();
        lblEmail = new JLabel();
        lblWeb = new JLabel();
        lblWeb.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        info.add(lblNombre);
        info.add(lblApodo);
        info.add(lblEmail);
        info.add(lblWeb);
        top.add(info, BorderLayout.CENTER);

        // Middle: biografia
        JPanel mid = new JPanel(new BorderLayout(4,4));
        mid.setBorder(BorderFactory.createTitledBorder("Biografía"));
        txtBiografia = new JTextArea();
        txtBiografia.setLineWrap(true);
        txtBiografia.setWrapStyleWord(true);
        txtBiografia.setEditable(false);
        JScrollPane spBio = new JScrollPane(txtBiografia);
        spBio.setPreferredSize(new Dimension(400,160));
        mid.add(spBio, BorderLayout.CENTER);
        right.add(mid, BorderLayout.CENTER);

        // Bottom: propuestas agrupadas
        JPanel bot = new JPanel(new BorderLayout(4,4));
        bot.setBorder(BorderFactory.createTitledBorder("Propuestas (por estado)"));
        txtPropuestas = new JTextArea();
        txtPropuestas.setEditable(false);
        txtPropuestas.setFont(txtPropuestas.getFont().deriveFont(12f));
        txtPropuestas.setLineWrap(true);
        txtPropuestas.setWrapStyleWord(true);
        JScrollPane spProp = new JScrollPane(txtPropuestas);
        spProp.setPreferredSize(new Dimension(400,260));
        bot.add(spProp, BorderLayout.CENTER);
        right.add(bot, BorderLayout.SOUTH);

        // listeners
        listProponentes.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selected = listProponentes.getSelectedValue();
                if (selected != null) {
                    String id = idByListEntry.get(selected);
                    if (id != null) loadPerfilProponente(id);
                }
            }
        });

        // click web label to open in system browser (best-effort)
        lblWeb.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String url = lblWeb.getText();
                if (url != null && !url.trim().isEmpty()) {
                    try {
                        if (!url.startsWith("http")) url = "http://" + url;
                        java.awt.Desktop.getDesktop().browse(new java.net.URI(url));
                    } catch (Exception ex) {
                        // ignore
                    }
                }
            }
        });
    }

    private void loadProponentes() {
        try {
            UsuarioService us = new UsuarioService();
            List<Usuario> props = us.findByTipo("P");
            listModel.clear();
            idByListEntry.clear();
            for (Usuario u : props) {
                String display = String.format("%s — %s %s", u.getId(), safe(u.getNickname()), safe(u.getNombre()));
                listModel.addElement(display);
                idByListEntry.put(display, u.getId());
            }
            if (!listModel.isEmpty()) listProponentes.setSelectedIndex(0);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error cargando proponentes: " + ex.getMessage());
        }
    }

    private String safe(String s) { return s==null?"":s; }

    private void loadPerfilProponente(String id) {
        try {
            UsuarioService us = new UsuarioService();
            Optional<Usuario> opt = us.findById(id);
            if (!opt.isPresent()) {
                JOptionPane.showMessageDialog(this, "Proponente no encontrado: " + id);
                return;
            }
            Usuario u = opt.get();
            lblNombre.setText("Nombre: " + safe(u.getNombre()) + " " + safe(u.getApellido()));
            lblApodo.setText("Apodo: " + safe(u.getNickname()));
            lblEmail.setText("Email: " + safe(u.getEmail()));
            // Obtener sitio_web desde detalles_proponentes (si existe)
            String sitio = null;
            try {
                javax.persistence.EntityManager em = culturarte.casosuso.JPAUtil.getEntityManagerFactory().createEntityManager();
                try {
                    Object r = em.createNativeQuery("SELECT sitio_web FROM detalles_proponentes WHERE usuario_id = ?")
                                 .setParameter(1, id)
                                 .getResultStream().findFirst().orElse(null);
                    if (r != null) sitio = r.toString();
                } catch (Exception exq) {
                    sitio = safe(u.getWeb());
                } finally {
                    if (em != null && em.isOpen()) em.close();
                }
            } catch (Throwable exOuter) {
                sitio = safe(u.getWeb());
            }
            lblWeb.setText(safe(sitio));

            txtBiografia.setText(safe(u.getBiografia()));

            // imagen
            lblFoto.setIcon(null);
            try {
                String img = u.getImgPath();
                if (img != null && !img.trim().isEmpty()) {
                    File f = ImageService.resolve(img);
                    if (f != null && f.exists()) {
                        ImageIcon ic = new ImageIcon(new ImageIcon(f.getAbsolutePath()).getImage().getScaledInstance(
                                lblFoto.getWidth(), lblFoto.getHeight(), Image.SCALE_SMOOTH));
                        lblFoto.setIcon(ic);
                    }
                }
            } catch (Throwable t) {
                lblFoto.setIcon(null);
            }

            loadPropuestasAgrupadas(id);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar perfil: " + ex.getMessage());
        }
    }

    private void loadPropuestasAgrupadas(String proponenteId) {
        // agrupamos por estado final (última fila de estados_propuestas por fecha/hora)
        Map<String, List<Map<String,Object>>> byEstado = new LinkedHashMap<>();
        // initialize desired states in preferred order
        String[] estadosOrder = new String[] { "Cancelada", "Publicada", "En Financiación", "Financiada", "No Financiada" };
        for (String e : estadosOrder) byEstado.put(e, new ArrayList<>());

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DatabaseConnection.getConnection();
            // get proposals for this proponente
            ps = conn.prepareStatement("SELECT ref, titulo, descripcion, lugar, fecha, precio_entrada, monto FROM propuestas WHERE proponente = ?");
            ps.setString(1, proponenteId);
            rs = ps.executeQuery();
            while (rs.next()) {
                String ref = rs.getString("ref");
                String titulo = rs.getString("titulo");
                // determine latest estado
                String estado = null;
                try (PreparedStatement ps2 = conn.prepareStatement("SELECT estado FROM estados_propuestas WHERE propuesta_ref = ? ORDER BY fecha DESC, hora DESC LIMIT 1")) {
                    ps2.setString(1, ref);
                    try (ResultSet rs2 = ps2.executeQuery()) {
                        if (rs2.next()) estado = rs2.getString("estado");
                    }
                } catch (Exception ex2) { estado = null; }

                if (estado == null) estado = "No Financiada";

                // compute total recaudado and collaborators
                BigDecimal total = BigDecimal.ZERO;
                List<String> colaboradores = new ArrayList<>();
                try (PreparedStatement ps3 = conn.prepareStatement("SELECT colaborador, SUM(monto) as s FROM colaboraciones WHERE propuesta_ref = ? GROUP BY colaborador")) {
                    ps3.setString(1, ref);
                    try (ResultSet rs3 = ps3.executeQuery()) {
                        while (rs3.next()) {
                            String colId = rs3.getString("colaborador");
                            colaboradores.add(colId);
                            BigDecimal s = rs3.getBigDecimal("s");
                            if (s != null) total = total.add(s);
                        }
                    }
                } catch (Exception ex3) { /* ignore */ }

                // transform estado to one of our buckets
                String bucket = mapEstadoToBucket(estado);

                Map<String,Object> info = new HashMap<>();
                info.put("ref", ref);
                info.put("titulo", titulo);
                info.put("total", total);
                info.put("colaboradores", colaboradores);
                byEstado.get(bucket).add(info);
            }

            // build display text
            StringBuilder out = new StringBuilder();
            for (String state : estadosOrder) {
                List<Map<String,Object>> list = byEstado.get(state);
                out.append("=== ").append(state).append(" (").append(list.size()).append(") ===\n");
                for (Map<String,Object> pinfo : list) {
                    String ref = (String)pinfo.get("ref");
                    String titulo = (String)pinfo.get("titulo");
                    BigDecimal total = (BigDecimal)pinfo.get("total");
                    @SuppressWarnings("unchecked")
                    List<String> cols = (List<String>)pinfo.get("colaboradores");
                    // resolve collaborator nicknames
                    List<String> names = new ArrayList<>();
                    if (!cols.isEmpty()) {
                        try (PreparedStatement ps4 = conn.prepareStatement("SELECT apodo FROM usuarios WHERE id = ?")) {
                            for (String cid : cols) {
                                ps4.setString(1, cid);
                                try (ResultSet rs4 = ps4.executeQuery()) {
                                    if (rs4.next()) names.add(rs4.getString("apodo") + " (" + cid + ")");
                                    else names.add(cid);
                                }
                            }
                        } catch (Exception ex4) { names = new ArrayList<>(cols); }
                    }
                    out.append(String.format("- %s : %s\n   Recaudado: %s  Colaboradores: %s\n", ref, titulo, (total==null?"0":total.toPlainString()), String.join(", ", names)));
                }
                out.append("\n");
            }
            txtPropuestas.setText(out.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
            txtPropuestas.setText("Error cargando propuestas: " + ex.getMessage());
        } finally {
            try { if (rs!=null) rs.close(); } catch (Exception e) {}
            try { if (ps!=null) ps.close(); } catch (Exception e) {}
            try { if (conn!=null) conn.close(); } catch (Exception e) {}
        }
    }

    private String mapEstadoToBucket(String estado) {
        if (estado == null) return "No Financiada";
        String e = estado.trim().toLowerCase();
        switch (e) {
            case "cancelada": return "Cancelada";
            case "publicada": return "Publicada";
            case "en financiación":
            case "en financiacion": return "En Financiación";
            case "financiada": return "Financiada";
            default: return "No Financiada";
        }
    }
}
