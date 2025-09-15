package culturarte.paneles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.time.format.DateTimeFormatter;
import culturarte.casosuso.Usuario;
import culturarte.casosuso.UsuarioService;
import culturarte.casosuso.ColaboracionService;

/**
 * Panel para consultar perfil de colaborador con foto a la izquierda y datos a la derecha.
 */
public class UCConsultaPerfilColaboradorPanel extends JPanel {

    private JComboBox<String> comboColaboradores;
    private JButton bLoad;
    private JLabel lblFoto;
    private JLabel lblNickname;
    private JLabel lblNombre;
    private JLabel lblFecha;
    private JLabel lblEmail;
    private JTextArea txtColabs;

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yy");

    public UCConsultaPerfilColaboradorPanel() {
        initComponents();
        this.setPreferredSize(new Dimension(760, 300));
    }

    private void initComponents() {
        setLayout(new BorderLayout(10,10));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        comboColaboradores = new JComboBox<>();
        comboColaboradores.setPrototypeDisplayValue("XXXXXXXX: verylongnickname");
        bLoad = new JButton("Cargar colaboradores");
        topPanel.add(comboColaboradores);
        topPanel.add(bLoad);

        add(topPanel, BorderLayout.NORTH);

        // Center - content with image on left and info on right
        JPanel center = new JPanel(new BorderLayout(10,10));
        lblFoto = new JLabel();
        lblFoto.setPreferredSize(new Dimension(140,140));
        lblFoto.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        center.add(lblFoto, BorderLayout.WEST);

        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        lblNickname = new JLabel("Nickname: ");
        lblNickname.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblNombre = new JLabel("Nombre: ");
        lblNombre.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblFecha = new JLabel("Fecha nacimiento: ");
        lblFecha.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblEmail = new JLabel("Correo electrónico: ");
        lblEmail.setAlignmentX(Component.LEFT_ALIGNMENT);

        info.add(lblNickname);
        info.add(Box.createVerticalStrut(6));
        info.add(lblNombre);
        info.add(Box.createVerticalStrut(6));
        info.add(lblFecha);
        info.add(Box.createVerticalStrut(6));
        info.add(lblEmail);

        center.add(info, BorderLayout.CENTER);

        // Right side: collaborations list
        txtColabs = new JTextArea();
        txtColabs.setEditable(false);
        txtColabs.setLineWrap(true);
        txtColabs.setWrapStyleWord(true);
        txtColabs.setPreferredSize(new Dimension(260,140));
        JScrollPane sp = new JScrollPane(txtColabs);
        sp.setPreferredSize(new Dimension(260,140));
        center.add(sp, BorderLayout.EAST);

        add(center, BorderLayout.CENTER);

        // Actions
        bLoad.addActionListener(e -> onLoad());
        comboColaboradores.addActionListener(e -> onSelect());
    }

    private void onLoad() {
        try {
            comboColaboradores.removeAllItems();
            java.util.List<String> list = culturarte.casosuso.LegacyDB.listUsuariosByTipo("COLABORADOR");
            for (String item : list) comboColaboradores.addItem(item);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar colaboradores: " + ex.getMessage());
        }
    }

    private void onSelect() {
        if (comboColaboradores.getSelectedItem() == null) return;
        String id = ((String)comboColaboradores.getSelectedItem()).split(":")[0].trim();
        try {
            UsuarioService us = new UsuarioService();
            java.util.Optional<Usuario> ou = us.findById(id);
            if (!ou.isPresent()) {
                JOptionPane.showMessageDialog(this, "Usuario no encontrado");
                return;
            }
            Usuario u = ou.get();

            lblNickname.setText("Nickname: " + safe(u.getNickname()));
            lblNombre.setText("Nombre: " + safe(u.getNombre()) + " " + safe(u.getApellido()));
            if (u.getFechaNacimiento() != null) {
                lblFecha.setText("Fecha nacimiento: " + u.getFechaNacimiento().format(DATE_FORMAT));
            } else {
                lblFecha.setText("Fecha nacimiento: N/D");
            }
            lblEmail.setText("Correo electrónico: " + safe(u.getEmail()));

            // cargar imagen si existe
            String img = u.getImgPath();
            if (img != null && !img.trim().isEmpty()) {
                try {
                    File f = culturarte.casosuso.ImageService.resolve(img);
                    if (f != null && f.exists()) {
                        ImageIcon ic = new ImageIcon(new ImageIcon(f.getAbsolutePath()).getImage().getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(), Image.SCALE_SMOOTH));
                        lblFoto.setIcon(ic);
                    } else {
                        lblFoto.setIcon(null);
                    }
                } catch (Exception ex) {
                    lblFoto.setIcon(null);
                }
            } else {
                lblFoto.setIcon(null);
            }

            // colaboraciones: agrupadas por propuesta mostrando proponente, recaudado y estado
            javax.persistence.EntityManager em = null;
            try {
                em = culturarte.casosuso.JPAUtil.getEntityManagerFactory().createEntityManager();
                java.util.List<Object[]> rows = em.createNativeQuery(
                    "SELECT p.ref, p.titulo, p.proponente, u.apodo as proponente_apodo, SUM(c.monto) as recaudado, " +
                    "(SELECT e.estado FROM estados_propuestas e WHERE e.propuesta_ref = p.ref ORDER BY e.id DESC LIMIT 1) as estado " +
                    "FROM propuestas p JOIN colaboraciones c ON c.propuesta_ref = p.ref JOIN usuarios u ON p.proponente = u.id " +
                    "WHERE c.colaborador = ? GROUP BY p.ref, p.titulo, p.proponente, u.apodo")
                    .setParameter(1, id)
                    .getResultList();
                StringBuilder sb = new StringBuilder();
                for (Object obj : rows) {
                    Object[] r = (Object[]) obj;
                    String ref = r[0] == null ? "" : r[0].toString();
                    String titulo = r[1] == null ? "" : r[1].toString();
                    String proponenteNick = r[3] == null ? "" : r[3].toString();
                    java.math.BigDecimal recaudado = java.math.BigDecimal.ZERO;
                    if (r[4] != null) {
                        try { recaudado = new java.math.BigDecimal(r[4].toString()); } catch (Exception exx) {}
                    }
                    String estado = r[5] == null ? "N/D" : r[5].toString();
                    sb.append(ref).append(" - ").append(titulo).append("\n");
                    sb.append("   Proponente: ").append(proponenteNick).append("\n");
                    sb.append("   Recaudado: $").append(recaudado.toPlainString()).append("   Estado: ").append(estado).append("\n\n");
                }
                txtColabs.setText(sb.toString());
            } catch (Exception exq) {
                exq.printStackTrace();
                // fallback: mostrar colaboraciones individuales
                try {
                    ColaboracionService cs = new ColaboracionService();
                    java.util.List<culturarte.casosuso.Colaboracion> list = cs.findByColaboradorId(id);
                    StringBuilder sb2 = new StringBuilder();
                    for (culturarte.casosuso.Colaboracion c : list) {
                        if (c.getPropuesta() != null) {
                            sb2.append(c.getPropuesta().getId()).append(" - ").append(c.getPropuesta().getTitulo()).append(" - $").append(c.getMonto()).append("\n");
                        }
                    }
                    txtColabs.setText(sb2.toString());
                } catch (Exception ex2) {
                    txtColabs.setText("No se pudo cargar la información de colaboraciones.");
                }
            } finally {
                if (em != null && em.isOpen()) em.close();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al mostrar perfil: " + ex.getMessage());
        }
    }

    private String safe(String s) {
        return s == null ? "" : s;
    }
}
