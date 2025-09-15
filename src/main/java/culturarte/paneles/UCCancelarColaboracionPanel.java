/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package culturarte.paneles;



/*
 Pasos para cambiar el tamaño de este panel:
 1) Abrir este archivo: UCCancelarColaboracionPanel.java
2) En el constructor (UCCancelarColaboracionPanel), modificar la dimensión en setPreferredSize:
   this.setPreferredSize(new java.awt.Dimension(W, H));
   donde W es el ancho y H la altura en píxeles.
3) Recompilar el proyecto (mvn/gradle/NetBeans) y ejecutar para probar.
*/

import culturarte.casosuso.ColaboracionService;
import javax.swing.JOptionPane;

/**
 *
 * @author tecnologo
 */
public class UCCancelarColaboracionPanel extends javax.swing.JPanel {

    /**
     * Creates new form UCCancelarColaboracionPanel
     */
    public UCCancelarColaboracionPanel() {
        initComponents();
        // --- Tamaño predeterminado: 1000 x 1000 ---
        // Para cambiarlo: editar esta línea o seguir los pasos en el README dentro del proyecto.
        this.setPreferredSize(new java.awt.Dimension(430, 100));

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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnCargar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        comboAll = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();

        btnCargar.setText("Cargar");
        btnCargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCargarActionPerformed(evt);
            }
        });

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        comboAll.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { }));
        comboAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboAllActionPerformed(evt);
            }
        });

        jLabel1.setText("Colaboraciones:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(comboAll, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCargar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminar)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboAll, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCargar)
                    .addComponent(btnEliminar))
                .addContainerGap(18, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnCargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargarActionPerformed
        try {
            comboAll.removeAllItems();
            java.util.List<culturarte.casosuso.Colaboracion> list = new ColaboracionService().findAll();
            for (culturarte.casosuso.Colaboracion c : list) {
                comboAll.addItem(c.getId() + ": " + (c.getColaborador()!=null?c.getColaborador().getNickname():"") );
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }//GEN-LAST:event_btnCargarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        try {
            if (comboAll.getSelectedItem() == null) return;
            String s = comboAll.getSelectedItem().toString();
            String id = s.split(":")[0].trim();
            // obtener detalles para mostrar
            culturarte.casosuso.ColaboracionService cs = new culturarte.casosuso.ColaboracionService();
            culturarte.casosuso.Colaboracion c = cs.findById(id);
            if (c == null) {
                JOptionPane.showMessageDialog(this, "No se encontro la colaboracion seleccionada.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            StringBuilder info = new StringBuilder();
            info.append("ID: ").append(c.getId()).append("\n");
            if (c.getColaborador()!=null) info.append("Colaborador: ").append(c.getColaborador().getNickname()).append(" (").append(c.getColaborador().getId()).append(")\n");
            if (c.getPropuesta()!=null) info.append("Propuesta: ").append(c.getPropuesta().getTitulo()).append(" (").append(c.getPropuesta().getId()).append(")\n");
            info.append("Fecha: ").append(c.getFecha()==null? "(no disponible)": c.getFecha().toString()).append("\n");
            info.append("Hora: ").append(c.getHora()==null? "(no disponible)": c.getHora().toString()).append("\n");
            info.append("Monto: ").append(c.getMonto()==null? "(no disponible)": c.getMonto().toString()).append("\n");
            info.append("Tipo retorno: ").append(c.getTipoRetorno()==null? "(no disponible)": c.getTipoRetorno()).append("\n\n");
            int opt = JOptionPane.showConfirmDialog(this, info.toString() + "\n¿Confirmar eliminacion?", "Confirmar eliminacion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (opt == JOptionPane.YES_OPTION) {
                cs.deleteById(id);
                JOptionPane.showMessageDialog(this, "Colaboracion eliminada");
                btnCargarActionPerformed(null);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void comboAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboAllActionPerformed
        // no-op for now
    }//GEN-LAST:event_comboAllActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCargar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JComboBox<String> comboAll;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
