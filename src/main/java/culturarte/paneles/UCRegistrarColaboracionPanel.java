/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package culturarte.paneles;


/*
 Pasos para cambiar el tamaño de este panel:
 1) Abrir este archivo: UCRegistrarColaboracionPanel.java
2) En el constructor (UCRegistrarColaboracionPanel), modificar la dimensión en setPreferredSize:
   this.setPreferredSize(new java.awt.Dimension(W, H));
   donde W es el ancho y H la altura en píxeles.
3) Recompilar el proyecto (mvn/gradle/NetBeans) y ejecutar para probar.
*/

import javax.swing.JOptionPane;

import javax.swing.*;                 // Para JComboBox, JTextField, JTextArea, JButton, JOptionPane
import java.awt.*;                     // Para Layouts
import java.sql.Connection;            // JDBC Connection
import java.sql.PreparedStatement;     // JDBC PreparedStatement
import java.sql.ResultSet;             // JDBC ResultSet
import culturarte.casosuso.PropuestaService;
import culturarte.casosuso.UsuarioService;
import culturarte.casosuso.ColaboracionService;
import culturarte.casosuso.Colaboracion;
import culturarte.casosuso.Propuesta;
import culturarte.casosuso.Usuario;

/**
 *
 * @author tecnologo
 */
public class UCRegistrarColaboracionPanel extends javax.swing.JPanel {

    /**
     * Creates new form UCRegistrarColaboracionPanel
     */
public UCRegistrarColaboracionPanel() {
    initComponents();
        // --- Tamaño predeterminado: 1000 x 1000 ---
        // Para cambiarlo: editar esta línea o seguir los pasos en el README dentro del proyecto.
        this.setPreferredSize(new java.awt.Dimension(470, 150));

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

    cargarPropuestas();      // llena comboPropuestas
    cargarColaboradores();   // llena comboColaboradores
}


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        comboPropuestas = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        comboColaboradores = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        txtMonto = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        comboRetorno = new javax.swing.JComboBox<>();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setText("Propuesta:");
        add(jLabel1);

        comboPropuestas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboPropuestas.setPreferredSize(new java.awt.Dimension(250, 24));
        comboPropuestas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboPropuestasActionPerformed(evt);
            }
        });
        add(comboPropuestas);

        jLabel2.setText("Colaborador:");
        add(jLabel2);

        comboColaboradores.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboColaboradores.setPreferredSize(new java.awt.Dimension(250, 24));
        comboColaboradores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboColaboradoresActionPerformed(evt);
            }
        });
        add(comboColaboradores);

        jLabel3.setText("Monto:");
        add(jLabel3);

        txtMonto.setColumns(20);
        txtMonto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMontoActionPerformed(evt);
            }
        });
        add(txtMonto);

        jLabel4.setText("Tipo retorno:");
        add(jLabel4);

        comboRetorno.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "entrada", "porcentaje" }));
        comboRetorno.setPreferredSize(new java.awt.Dimension(200, 24));
        comboRetorno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboRetornoActionPerformed(evt);
            }
        });
        add(comboRetorno);btnGuardar.setText("Guardar");
        btnGuardar.setPreferredSize(new java.awt.Dimension(100, 24));
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        add(btnGuardar);

        btnCancelar.setText("Cancelar");
        btnCancelar.setPreferredSize(new java.awt.Dimension(100, 24));
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        add(btnCancelar);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
cancelar();    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
registrar();    }//GEN-LAST:event_btnGuardarActionPerformed

    private void comboRetornoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboRetornoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboRetornoActionPerformed

    private void txtMontoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMontoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMontoActionPerformed

    private void comboColaboradoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboColaboradoresActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboColaboradoresActionPerformed

    private void comboPropuestasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboPropuestasActionPerformed
        try {
            String sel = (String) comboPropuestas.getSelectedItem();
            if (sel == null || sel.trim().isEmpty()) return;
            String id = sel.split(":")[0].trim();
            PropuestaService ps = new PropuestaService();
            java.util.Optional<Propuesta> opt = ps.findById(id);
            if (!opt.isPresent()) {
                JOptionPane.showMessageDialog(this, "No se encontró la propuesta seleccionada.", "Información de propuesta", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Propuesta p = opt.get();
            // abrir dialogo emergente con detalles (PropuestaDetalleDialog)
            PropuestaDetalleDialog dlg = new PropuestaDetalleDialog(javax.swing.SwingUtilities.getWindowAncestor(this), p);
            dlg.setVisible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error mostrando información de la propuesta: " + ex.getMessage());
        }
    }//GEN-LAST:event_comboPropuestasActionPerformed

    private void cargarPropuestas() {
    try {
        PropuestaService ps = new PropuestaService();
        java.util.List<Propuesta> list = ps.findAll();
        comboPropuestas.removeAllItems();
        for (Propuesta p : list) comboPropuestas.addItem(p.getId() + ":" + p.getTitulo());
    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al cargar propuestas: " + ex.getMessage());
    }
}

private void cargarColaboradores() {
    try {
        try {
            java.util.List<String> list = culturarte.casosuso.LegacyDB.listUsuariosByTipo("COLABORADOR");
            comboColaboradores.removeAllItems();
            for (String item : list) comboColaboradores.addItem(item);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar colaboradores: " + ex.getMessage());
        }
} catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al cargar colaboradores: " + ex.getMessage());
    }
}

private void registrar() {
    try {
            javax.persistence.EntityManager em = culturarte.casosuso.JPAUtil.getEntityManager();
            try {
                em.getTransaction().begin();
                String selP = (String) comboPropuestas.getSelectedItem();
                String idP = selP.split(":")[0].trim();
                String selC = (String) comboColaboradores.getSelectedItem();
                String idC = selC.split(":")[0].trim();
                java.math.BigDecimal monto = new java.math.BigDecimal(txtMonto.getText().trim());
                String tipo = (String) comboRetorno.getSelectedItem();

                culturarte.casosuso.Propuesta prop = em.find(culturarte.casosuso.Propuesta.class, idP);
                culturarte.casosuso.Usuario col = em.find(culturarte.casosuso.Usuario.class, idC);
                if (prop != null && col != null) {
                    culturarte.casosuso.Colaboracion c = new culturarte.casosuso.Colaboracion();
                    c.setPropuesta(prop);
                    c.setColaborador(col);
                    c.setMonto(monto);
                    c.setTipoRetorno(tipo);
                        try {
                        // generate a short id like Col01, Col02 ... to fit DB column
                        Object maxObj = em.createNativeQuery("SELECT MAX(id) FROM colaboraciones").getSingleResult();
                        String maxId = (maxObj == null) ? null : String.valueOf(maxObj);
                        String newId;
                        if (maxId != null && maxId.startsWith("Col")) {
                            try {
                                int n = Integer.parseInt(maxId.substring(3));
                                newId = String.format("Col%02d", n + 1);
                            } catch (Exception exn) {
                                newId = "Col01";
                            }
                        } else {
                            newId = "Col01";
                        }
                        c.setId(newId);
                    } catch (Exception qex) {
                        // fallback short id
                        c.setId("Col" + System.currentTimeMillis()%1000);
                    }
                    em.persist(c);
                    em.getTransaction().commit();
                    JOptionPane.showMessageDialog(this, "Colaboración registrada");
                } else {
                    em.getTransaction().rollback();
                    JOptionPane.showMessageDialog(this, "Propuesta o colaborador no encontrados");
                }
            } catch (Exception ex) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                throw ex;
            } finally {
                if (em != null && em.isOpen()) em.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
}

private void cancelar() {
    txtMonto.setText("");
    comboRetorno.setSelectedIndex(0);
    comboPropuestas.setSelectedIndex(0);
    comboColaboradores.setSelectedIndex(0);
}



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<String> comboColaboradores;
    private javax.swing.JComboBox<String> comboPropuestas;
    private javax.swing.JComboBox<String> comboRetorno;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField txtMonto;
private javax.swing.JLabel jLabel5;
// End of variables declaration//GEN-END:variables
}