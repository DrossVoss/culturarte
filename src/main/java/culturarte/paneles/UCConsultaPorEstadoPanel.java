
package culturarte.paneles;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class UCConsultaPorEstadoPanel extends JPanel {

    private JComboBox<String> comboEstados;
    private JButton bListar;
    private JButton bVer;
    private JButton bVolver;
    private JButton bFinalizar;
    private JScrollPane jScrollPane1;
    private JTextArea txtPropuestas;

    public UCConsultaPorEstadoPanel() {
        initComponents();
        this.setPreferredSize(new Dimension(600, 360));
        // cargar estados al iniciar (vienen de la tabla estados_propuestas)
        loadEstados();
    }

    private void loadEstados() {
        // Cargamos los estados por defecto. Esto NO consulta la base de datos.
        comboEstados.removeAllItems();
        comboEstados.addItem("Ingresada");
        comboEstados.addItem("Publicada");
        comboEstados.addItem("En Financiación");
        comboEstados.addItem("Financiada");
        comboEstados.addItem("No financiada");
        comboEstados.addItem("Cancelada");
    }

    private void initComponents() {
        comboEstados = new JComboBox<>();
        bListar = new JButton();
        bVer = new JButton();
        bVolver = new JButton();
        bFinalizar = new JButton();
        jScrollPane1 = new JScrollPane();
        txtPropuestas = new JTextArea();

        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        comboEstados.setPreferredSize(new Dimension(300,24));
        add(comboEstados);

        bListar.setText("Listar propuestas");
        bListar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bListarActionPerformed(evt);
            }
        });
        add(bListar);

        bVer.setText("Ver propuesta");
        bVer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bVerActionPerformed(evt);
            }
        });
        add(bVer);

        bVolver.setText("Elegir otro estado");
        bVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bVolverActionPerformed(evt);
            }
        });
        add(bVolver);

        bFinalizar.setText("Finalizar");
        bFinalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bFinalizarActionPerformed(evt);
            }
        });
        add(bFinalizar);

        txtPropuestas.setColumns(50);
        txtPropuestas.setRows(12);
        txtPropuestas.setEditable(false);
        jScrollPane1.setViewportView(txtPropuestas);
        jScrollPane1.setPreferredSize(new Dimension(760,220));
        add(jScrollPane1);
    }
    private void bListarActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            if (comboEstados.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Primero seleccioná un estado.");
                return;
            }
            String estado = (String) comboEstados.getSelectedItem();
            List<culturarte.casosuso.Propuesta> list = new culturarte.casosuso.PropuestaService().findByEstado(estado);
            txtPropuestas.setText("");
            if (list == null || list.isEmpty()) {
                txtPropuestas.setText("(no hay propuestas en el estado seleccionado)");
                return;
            }
            StringBuilder sb = new StringBuilder();
            for (culturarte.casosuso.Propuesta p : list) {
                sb.append(p.getId()).append(": ").append(p.getTitulo() == null ? "" : p.getTitulo()).append("\n");
            }
            txtPropuestas.setText(sb.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al listar propuestas: " + ex.getMessage());
        }
    }

    private void bVerActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            String sel = txtPropuestas.getSelectedText();
            String id = null;
            if (sel == null || sel.trim().isEmpty()) {
                // try to get selected item from textarea by caret line
                int caret = txtPropuestas.getCaretPosition();
                String text = txtPropuestas.getText();
                if (text == null || text.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No hay propuestas listadas. Listá primero.");
                    return;
                }
                // get current line
                int lineStart = text.lastIndexOf('\n', Math.max(0, caret-1));
                int lineEnd = text.indexOf('\n', caret);
                if (lineStart == -1) lineStart = 0; else lineStart = lineStart + 1;
                if (lineEnd == -1) lineEnd = text.length();
                String line = text.substring(lineStart, lineEnd).trim();
                if (line.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Seleccioná la propuesta que querés ver (clic en su línea).");
                    return;
                }
                id = line.split(":")[0].trim();
            } else {
                id = sel.split(":")[0].trim();
            }

            if (id == null || id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No se pudo determinar la propuesta seleccionada.");
                return;
            }

            Optional<culturarte.casosuso.Propuesta> opt = new culturarte.casosuso.PropuestaService().findById(id);
            if (!opt.isPresent()) {
                JOptionPane.showMessageDialog(this, "No se encontró la propuesta con id: " + id);
                return;
            }
            culturarte.casosuso.Propuesta p = opt.get();

            // Mostrar detalles en un diálogo (reutiliza lógica similar a UCConsultaPropuestaPanel)
            StringBuilder detalles = new StringBuilder();
            detalles.append("Ref: ").append(p.getId()).append("\n");
            detalles.append("Título: ").append(p.getTitulo() == null ? "" : p.getTitulo()).append("\n\n");
            detalles.append("Descripción:\n").append(p.getDescripcion() == null ? "(sin descripción)" : p.getDescripcion()).append("\n\n");
            detalles.append("Lugar: ").append(p.getLugar() == null ? "" : p.getLugar()).append("\n");
            detalles.append("Fecha realización: ").append(p.getFechaRealizacion() == null ? "" : p.getFechaRealizacion().toString()).append("\n");
            detalles.append("Precio entrada: ").append(p.getPrecioEntrada() == null ? "" : p.getPrecioEntrada().toString()).append("\n");
            detalles.append("Monto necesario: ").append(p.getMontoNecesario() == null ? "" : p.getMontoNecesario().toString()).append("\n");
            detalles.append("Estado actual: ").append(p.getEstadoActual() == null ? "" : p.getEstadoActual()).append("\n");
            detalles.append("Proponente: ").append(p.getProponente() == null ? "" : p.getProponente().getNickname()).append("\n\n");

            List<culturarte.casosuso.Colaboracion> cols = new ArrayList<>();
            try {
                cols = new culturarte.casosuso.ColaboracionService().findByPropuestaRef(id);
            } catch (Exception ex) {
                try {
                    javax.persistence.EntityManager em = culturarte.casosuso.JPAUtil.getEntityManager();
                    try {
                        javax.persistence.TypedQuery<culturarte.casosuso.Colaboracion> q = em.createQuery(
                            "SELECT c FROM Colaboracion c WHERE c.propuesta.id = :pid OR c.propuesta.id = :pid", culturarte.casosuso.Colaboracion.class);
                        q.setParameter("pid", id);
                        cols = q.getResultList();
                    } finally {
                        if (em != null && em.isOpen()) em.close();
                    }
                } catch (Exception ex2) {
                    ex2.printStackTrace();
                }
            }

            java.math.BigDecimal total = java.math.BigDecimal.ZERO;
            Set<String> nicks = new LinkedHashSet<>();
            for (culturarte.casosuso.Colaboracion c : cols) {
                if (c.getColaborador() != null) nicks.add(c.getColaborador().getNickname());
                if (c.getMonto() != null) total = total.add(c.getMonto());
            }

            detalles.append("Colaboradores (" + nicks.size() + "):\n");
            if (nicks.isEmpty()) detalles.append("(ninguno)\n");
            else for (String nn : nicks) detalles.append("- ").append(nn).append("\n");
            detalles.append("\nTotal recaudado: ").append(total.toString()).append("\n");

            // show dialog with details and image (if exists)
            JTextArea area = new JTextArea(detalles.toString());
            area.setEditable(false);
            JScrollPane sp = new JScrollPane(area);
            sp.setPreferredSize(new Dimension(480, 320));

            // image label
            JLabel imgLabel = new JLabel("(sin imagen)");
            imgLabel.setPreferredSize(new Dimension(220,150));
            imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
            imgLabel.setBorder(BorderFactory.createEtchedBorder());

            String imagenPath = null;
            try { imagenPath = p.getImagenPath(); } catch (Throwable t) { /* ignore */ }

            if (imagenPath != null && !imagenPath.trim().isEmpty()) {
                try {
                    java.net.URL url = new java.net.URL(imagenPath);
                    javax.swing.ImageIcon icon = new javax.swing.ImageIcon(url);
                    java.awt.Dimension pref = imgLabel.getPreferredSize();
                    java.awt.Image img = icon.getImage().getScaledInstance(pref.width, pref.height, java.awt.Image.SCALE_SMOOTH);
                    imgLabel.setIcon(new javax.swing.ImageIcon(img));
                    imgLabel.setText(null);
                } catch (Exception ex) {
                    try {
                        java.io.File f = new java.io.File(imagenPath);
                        if (f.exists()) {
                            javax.swing.ImageIcon icon = new javax.swing.ImageIcon(imagenPath);
                            java.awt.Dimension pref = imgLabel.getPreferredSize();
                    java.awt.Image img = icon.getImage().getScaledInstance(pref.width, pref.height, java.awt.Image.SCALE_SMOOTH);
                            imgLabel.setIcon(new javax.swing.ImageIcon(img));
                            imgLabel.setText(null);
                        } else {
                            imgLabel.setIcon(null);
                            imgLabel.setText("(imagen no disponible)");
                        }
                    } catch (Exception ex2) {
                        imgLabel.setIcon(null);
                        imgLabel.setText("(imagen no disponible)");
                    }
                }
            }

            JPanel content = new JPanel(new BorderLayout(8,8));
            content.add(sp, BorderLayout.CENTER);
            content.add(imgLabel, BorderLayout.EAST);

            JOptionPane.showMessageDialog(this, content, "Detalle Propuesta", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al ver propuesta: " + ex.getMessage());
        }
    }

    private void bVolverActionPerformed(java.awt.event.ActionEvent evt) {
        // simply clear proposals and allow user to choose another state
        txtPropuestas.setText("");
    }

    private void bFinalizarActionPerformed(java.awt.event.ActionEvent evt) {
        // clear UI - in actual app could close dialog; here we clear selections
        comboEstados.removeAllItems();
        txtPropuestas.setText("");
    }
}
