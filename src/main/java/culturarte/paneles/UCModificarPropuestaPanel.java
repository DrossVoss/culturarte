/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package culturarte.paneles;


/*
 Pasos para cambiar el tamaño de este panel:
 1) Abrir este archivo: UCModificarPropuestaPanel.java
2) En el constructor (UCModificarPropuestaPanel), modificar la dimensión en setPreferredSize:
   this.setPreferredSize(new java.awt.Dimension(W, H));
   donde W es el ancho y H la altura en píxeles.
3) Recompilar el proyecto (mvn/gradle/NetBeans) y ejecutar para probar.
*/

import culturarte.casosuso.PropuestaService;
import culturarte.casosuso.UsuarioService;
import culturarte.casosuso.CategoriaService;
import culturarte.casosuso.ImageService;
import culturarte.casosuso.Propuesta;
import culturarte.casosuso.Usuario;
import culturarte.casosuso.Categoria;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;


/**
 *
 * @author tecnologo
 */
public class UCModificarPropuestaPanel extends javax.swing.JPanel {

    /**
     * Creates new form UCModificarPropuestaPanel
     */
    public UCModificarPropuestaPanel() {
        initComponents();
        // --- Tamaño predeterminado: 1000 x 1000 ---
        // Para cambiarlo: editar esta línea o seguir los pasos en el README dentro del proyecto.
        this.setPreferredSize(new java.awt.Dimension(450, 250));

                // <assistant-fixed-sizes> Ajustes de tamaño para mantener la misma lógica visual (labels a la izquierda, botones abajo, tamaños fijos)
        try {
            // intentar ajustar jPanel1 mediante reflection (si existe)
            try {
                java.lang.reflect.Field fPanel = this.getClass().getDeclaredField("jPanel1");
                fPanel.setAccessible(true);
                Object p = fPanel.get(this);
                if (p instanceof javax.swing.JPanel) {
                    javax.swing.JPanel panel = (javax.swing.JPanel)p;
                    try { panel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 10)); } catch (Exception ex) {}
                    try { panel.setPreferredSize(new java.awt.Dimension(760,480)); } catch (Exception ex) {}
                }
            } catch (Exception ex) { /* no jPanel1 en este panel */ }

            // etiquetas: tamaño consistente vía reflection
            for (int i=1;i<=20;i++) {
                try {
                    java.lang.reflect.Field f = this.getClass().getDeclaredField("jLabel"+i);
                    f.setAccessible(true);
                    Object obj = f.get(this);
                    if (obj instanceof javax.swing.JLabel) {
                        try { ((javax.swing.JLabel)obj).setPreferredSize(new java.awt.Dimension(120,20)); } catch (Exception e) {}
                    }
                } catch (Exception e) {}
            }

            // campos de texto / areas comunes: aumentar columnas/preferred size si existen
            String[] textFields = new String[] { "txtNombre","txtApellido","txtEmail","txtFecha","txtNickname","txtDireccion","txtWeb","txtLugar","txtCosto","txtDuracion","txtGenero","txtTelefono","txtBiografia" };
            for (String nm: textFields) {
                try {
                    java.lang.reflect.Field f = this.getClass().getDeclaredField(nm);
                    f.setAccessible(true);
                    Object obj = f.get(this);
                    if (obj instanceof javax.swing.JTextField) {
                        try { ((javax.swing.JTextField)obj).setColumns(20); ((javax.swing.JTextField)obj).setPreferredSize(new java.awt.Dimension(200,24)); } catch (Exception ex) {}
                    } else if (obj instanceof javax.swing.JTextArea) {
                        try { ((javax.swing.JTextArea)obj).setColumns(20); ((javax.swing.JTextArea)obj).setPreferredSize(new java.awt.Dimension(400,100)); } catch (Exception ex) {}
                    } else if (obj instanceof javax.swing.JComboBox) {
                        try { ((javax.swing.JComboBox)obj).setPreferredSize(new java.awt.Dimension(200,24)); } catch (Exception ex) {}
                    }
                } catch (Exception e) {}
            }

            // botones: forzar tamaño y ubicacion en jPanel2 si existe
            try {
                java.lang.reflect.Field f2 = this.getClass().getDeclaredField("jPanel2");
                f2.setAccessible(true);
                Object p2 = f2.get(this);
                if (p2 instanceof javax.swing.JPanel) {
                    try { ((javax.swing.JPanel)p2).setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 20, 10)); } catch (Exception ex) {}
                }
            } catch (Exception e) {}
        } catch (Exception e) {
            // no hacer nada si falla
        }
        // </assistant-fixed-sizes>

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        comboPropuestas = new javax.swing.JComboBox<>();
        btnLoad = new javax.swing.JButton();
        btnCargar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        txtLugar = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtMonto = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();

        setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        comboPropuestas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboPropuestas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboPropuestasActionPerformed(evt);
            }
        });
        add(comboPropuestas);

        btnLoad.setText("Cargar propuestas");
        btnLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoadActionPerformed(evt);
            }
        });
        add(btnLoad);

        btnCargar.setText("Cargar imagen");
        btnCargar.setPreferredSize(new java.awt.Dimension(130, 24));
        btnCargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCargarActionPerformed(evt);
            }
        });
        add(btnCargar);
jLabel2.setText("Descripción:");
        add(jLabel2);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(4);
        jScrollPane1.setViewportView(jTextArea1);

        add(jScrollPane1);

        jLabel3.setText("Lugar:");
        add(jLabel3);

        txtLugar.setColumns(25);
        txtLugar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLugarActionPerformed(evt);
            }
        });
        add(txtLugar);

        jLabel4.setText("Precio entrada:");
        add(jLabel4);

        txtPrecio.setColumns(21);
        txtPrecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecioActionPerformed(evt);
            }
        });
        add(txtPrecio);

        jLabel5.setText("Monto necesario:");
        add(jLabel5);

        txtMonto.setColumns(20);
        txtMonto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMontoActionPerformed(evt);
            }
        });
        add(txtMonto);

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        add(btnGuardar);
    }// </editor-fold>//GEN-END:initComponents

    private void txtPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {
//GEN-FIRST:event_btnGuardarActionPerformed
        try {
            if (comboPropuestas.getSelectedItem() == null) { JOptionPane.showMessageDialog(this, "Seleccione una propuesta"); return; }
            String id = ((String)comboPropuestas.getSelectedItem()).split(":")[0].trim();
            culturarte.casosuso.PropuestaService ps = new culturarte.casosuso.PropuestaService();
            java.util.Optional<culturarte.casosuso.Propuesta> op = ps.findById(id);
            if (!op.isPresent()) { JOptionPane.showMessageDialog(this, "Propuesta no encontrada"); return; }
            culturarte.casosuso.Propuesta p = op.get();
            // titulo no se modifica aquí (mantener original)
            p.setDescripcion(jTextArea1.getText().trim());
            p.setLugar(txtLugar.getText().trim());
            if (!txtPrecio.getText().trim().isEmpty()) p.setPrecioEntrada(new java.math.BigDecimal(txtPrecio.getText().trim()));
            if (!txtMonto.getText().trim().isEmpty()) p.setMontoNecesario(new java.math.BigDecimal(txtMonto.getText().trim()));
            if (imagenSeleccionada != null && !imagenSeleccionada.isEmpty()) p.setImagenPath(imagenSeleccionada);
            ps.save(p);
            JOptionPane.showMessageDialog(this, "Propuesta actualizada correctamente (JPA)");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage());
        }

}//GEN-LAST:event_btnGuardarActionPerformed

    private void txtMontoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMontoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMontoActionPerformed

    private void txtLugarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLugarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLugarActionPerformed
    private void btnCargarActionPerformed(java.awt.event.ActionEvent evt) {
//GEN-FIRST:event_btnCargarActionPerformed
        javax.swing.JFileChooser fc = new javax.swing.JFileChooser();
        fc.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Imagenes JPG/PNG", "jpg","jpeg","png"));
        int r = fc.showOpenDialog(this);
        if (r == javax.swing.JFileChooser.APPROVE_OPTION) {
            java.io.File sel = fc.getSelectedFile();
            try {
                // store in a hidden field imagenSeleccionada
                imagenSeleccionada = sel.getAbsolutePath();
                javax.swing.ImageIcon ic = new javax.swing.ImageIcon(new javax.swing.ImageIcon(sel.getAbsolutePath()).getImage().getScaledInstance(150,150,java.awt.Image.SCALE_SMOOTH));
                if (lblPreview == null) { lblPreview = new javax.swing.JLabel(); this.add(lblPreview); }
                lblPreview.setIcon(ic);
                this.revalidate(); this.repaint();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al copiar imagen: " + ex.getMessage());
            }
        }

}//GEN-LAST:event_btnCargarActionPerformed

    private void btnLoadActionPerformed(java.awt.event.ActionEvent evt) {
//GEN-FIRST:event_btnLoadActionPerformed
        try {
            culturarte.casosuso.PropuestaService ps = new culturarte.casosuso.PropuestaService();
            java.util.List<culturarte.casosuso.Propuesta> list = ps.findAll();
            comboPropuestas.removeAllItems();
            for (culturarte.casosuso.Propuesta pitem : list) comboPropuestas.addItem(pitem.getId() + ":" + pitem.getTitulo());
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
}//GEN-LAST:event_btnLoadActionPerformed

    private void comboPropuestasActionPerformed(java.awt.event.ActionEvent evt) {
//GEN-FIRST:event_comboPropuestasActionPerformed
        if (comboPropuestas.getSelectedItem() == null) return;
        String sel = (String) comboPropuestas.getSelectedItem();
        String id = sel.split(":")[0].trim();
        try {
            culturarte.casosuso.PropuestaService ps = new culturarte.casosuso.PropuestaService();
            java.util.Optional<culturarte.casosuso.Propuesta> op = ps.findById(id);
            if (!op.isPresent()) { JOptionPane.showMessageDialog(this, "Propuesta no encontrada"); return; }
            culturarte.casosuso.Propuesta p = op.get();
            jTextArea1.setText(p.getDescripcion());
            txtLugar.setText(p.getLugar());
            txtPrecio.setText(p.getPrecioEntrada() == null ? "" : p.getPrecioEntrada().toString());
            txtMonto.setText(p.getMontoNecesario() == null ? "" : p.getMontoNecesario().toString());
            // set category and proponente combos if needed (combo controls not present here in design)
            if (p.getImagenPath() != null) {
                java.io.File f = culturarte.casosuso.ImageService.resolve(p.getImagenPath());
                if (f != null && f.exists()) {
                    try {
                        javax.swing.ImageIcon ic = new javax.swing.ImageIcon(new javax.swing.ImageIcon(f.getAbsolutePath()).getImage().getScaledInstance(150,150,java.awt.Image.SCALE_SMOOTH));
                        if (lblPreview == null) { lblPreview = new javax.swing.JLabel(); this.add(lblPreview); }
                        lblPreview.setIcon(ic);
                        this.revalidate(); this.repaint();
                    } catch (Exception e) {}
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
}//GEN-LAST:event_comboPropuestasActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton btnCargar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLoad;
    private javax.swing.JComboBox<String> comboPropuestas;
private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField txtLugar;
    private javax.swing.JTextField txtMonto;
    private javax.swing.JTextField txtPrecio;
private String imagenSeleccionada = null;
    private javax.swing.JLabel lblPreview;
    // End of variables declaration//GEN-END:variables
}