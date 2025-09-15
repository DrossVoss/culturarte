
package culturarte.paneles;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Optional;

public class UCConsultaPropuestaPanel extends JPanel {

    private JComboBox<String> comboPropuestas;
    private JButton bLoad;
    private JButton bVer;
    private JScrollPane jScrollPane1;
    private JTextArea txtDetallePropuesta;
    private JLabel lblImagen;

    public UCConsultaPropuestaPanel() {
        initComponents();
        this.setPreferredSize(new Dimension(420, 300));
    }

    private void initComponents() {
        comboPropuestas = new JComboBox<>();
        bLoad = new JButton();
        bVer = new JButton();
        jScrollPane1 = new JScrollPane();
        txtDetallePropuesta = new JTextArea();
        lblImagen = new JLabel();

        setLayout(new FlowLayout(FlowLayout.LEFT));

        comboPropuestas.setModel(new DefaultComboBoxModel<>(new String[] { "Item 1" }));
        add(comboPropuestas);

        bLoad.setText("Cargar");
        bLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bLoadActionPerformed(evt);
            }
        });
        add(bLoad);

        bVer.setText("Ver");
        bVer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bVerActionPerformed(evt);
            }
        });
        add(bVer);

        txtDetallePropuesta.setColumns(30);
        txtDetallePropuesta.setRows(10);
        txtDetallePropuesta.setEditable(false);
        jScrollPane1.setViewportView(txtDetallePropuesta);
        add(jScrollPane1);

        lblImagen.setPreferredSize(new Dimension(220,150));
        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
        lblImagen.setVerticalAlignment(SwingConstants.CENTER);
        lblImagen.setBorder(BorderFactory.createEtchedBorder());
        lblImagen.setText("(sin imagen)");
        add(lblImagen);
    }

    private void bLoadActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            comboPropuestas.removeAllItems();
            List<culturarte.casosuso.Propuesta> list = new culturarte.casosuso.PropuestaService().findAll();
            for (culturarte.casosuso.Propuesta p : list) {
                comboPropuestas.addItem(p.getId() + ": " + (p.getTitulo() == null ? "" : p.getTitulo()));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar propuestas: " + ex.getMessage());
        }
    }

    private void bVerActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            if (comboPropuestas.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "No hay propuestas cargadas. Cargá la lista primero.");
                return;
            }
            String sel = (String) comboPropuestas.getSelectedItem();
            String id = sel.split(":")[0].trim();

            Optional<culturarte.casosuso.Propuesta> opt = new culturarte.casosuso.PropuestaService().findById(id);
            if (!opt.isPresent()) {
                JOptionPane.showMessageDialog(this, "No se encontró la propuesta con id: " + id);
                return;
            }
            culturarte.casosuso.Propuesta p = opt.get();

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

            java.util.List<culturarte.casosuso.Colaboracion> cols = new java.util.ArrayList<>();
            try {
                cols = new culturarte.casosuso.ColaboracionService().findByPropuestaRef(id);
            } catch (Exception ex) {
                // fallback JPQL
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
            java.util.Set<String> nicknames = new java.util.LinkedHashSet<>();
            for (culturarte.casosuso.Colaboracion c : cols) {
                if (c.getColaborador() != null) nicknames.add(c.getColaborador().getNickname());
                if (c.getMonto() != null) total = total.add(c.getMonto());
            }

            detalles.append("Colaboradores (" + nicknames.size() + "):\n");
            if (nicknames.isEmpty()) {
                detalles.append("(ninguno)\n");
            } else {
                for (String n : nicknames) detalles.append("- ").append(n).append("\n");
            }
            detalles.append("\nTotal recaudado: ").append(total.toString()).append("\n");

            txtDetallePropuesta.setText(detalles.toString());

            // imagen
            String imagenPath = null;
            try {
                imagenPath = p.getImagenPath();
            } catch (Throwable t) { /* ignore */ }

            if (imagenPath != null && !imagenPath.trim().isEmpty()) {
                try {
                    java.net.URL url = new java.net.URL(imagenPath);
                    javax.swing.ImageIcon icon = new javax.swing.ImageIcon(url);
                    java.awt.Image img = icon.getImage().getScaledInstance(lblImagen.getWidth(), lblImagen.getHeight(), java.awt.Image.SCALE_SMOOTH);
                    lblImagen.setIcon(new javax.swing.ImageIcon(img));
                    lblImagen.setText(null);
                } catch (Exception ex) {
                    try {
                        java.io.File f = new java.io.File(imagenPath);
                        if (f.exists()) {
                            javax.swing.ImageIcon icon = new javax.swing.ImageIcon(imagenPath);
                            java.awt.Image img = icon.getImage().getScaledInstance(lblImagen.getWidth(), lblImagen.getHeight(), java.awt.Image.SCALE_SMOOTH);
                            lblImagen.setIcon(new javax.swing.ImageIcon(img));
                            lblImagen.setText(null);
                        } else {
                            lblImagen.setIcon(null);
                            lblImagen.setText("(imagen no disponible)");
                        }
                    } catch (Exception ex2) {
                        lblImagen.setIcon(null);
                        lblImagen.setText("(imagen no disponible)");
                    }
                }
            } else {
                lblImagen.setIcon(null);
                lblImagen.setText("(sin imagen)");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

}
