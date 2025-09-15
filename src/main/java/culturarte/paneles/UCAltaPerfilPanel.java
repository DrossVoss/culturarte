/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package culturarte.paneles;

import javax.swing.JOptionPane;
import java.awt.Image;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.ImageIcon;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import culturarte.casosuso.UsuarioService;
import culturarte.casosuso.Usuario;
import java.time.LocalDate;


/**
 *
 * @author tecnologo
 */
public class UCAltaPerfilPanel extends javax.swing.JPanel {

    /**
     * Creates new form UCAltaPerfilPanel
     */
    public UCAltaPerfilPanel() {
        initComponents();
        this.setPreferredSize(new java.awt.Dimension(380, 550));
        // <assistant-fixed-sizes> Ajustes de tamaño para evitar componentes diminutos
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 10));
        if (txtApellido!=null) try { txtApellido.setColumns(20); txtApellido.setPreferredSize(new java.awt.Dimension(200,24)); } catch (Exception ex) {}
        if (txtBiografia!=null) try { txtBiografia.setColumns(20); txtBiografia.setPreferredSize(new java.awt.Dimension(200,24)); } catch (Exception ex) {}
        if (txtDireccion!=null) try { txtDireccion.setColumns(20); txtDireccion.setPreferredSize(new java.awt.Dimension(200,24)); } catch (Exception ex) {}
        if (txtEmail!=null) try { txtEmail.setColumns(20); txtEmail.setPreferredSize(new java.awt.Dimension(200,24)); } catch (Exception ex) {}
        if (txtFecha!=null) try { txtFecha.setColumns(20); txtFecha.setPreferredSize(new java.awt.Dimension(200,24)); } catch (Exception ex) {}
        if (txtNickname!=null) try { txtNickname.setColumns(20); txtNickname.setPreferredSize(new java.awt.Dimension(200,24)); } catch (Exception ex) {}
        if (txtNombre!=null) try { txtNombre.setColumns(20); txtNombre.setPreferredSize(new java.awt.Dimension(200,24)); } catch (Exception ex) {}
        if (txtWeb!=null) try { txtWeb.setColumns(20); txtWeb.setPreferredSize(new java.awt.Dimension(200,24)); } catch (Exception ex) {}
        if (txtBiografia!=null) try { txtBiografia.setRows(4); txtBiografia.setColumns(20); txtBiografia.setPreferredSize(new java.awt.Dimension(400,80)); } catch (Exception ex) {}
        if (comboTipo!=null) try { comboTipo.setPrototypeDisplayValue("XXXXXXXXXXXXXXXX"); comboTipo.setPreferredSize(new java.awt.Dimension(200,24)); } catch (Exception ex) {}
        if (jLabel1!=null) try { jLabel1.setPreferredSize(new java.awt.Dimension(120,20)); } catch (Exception ex) {}
        if (jLabel2!=null) try { jLabel2.setPreferredSize(new java.awt.Dimension(120,20)); } catch (Exception ex) {}
        if (jLabel3!=null) try { jLabel3.setPreferredSize(new java.awt.Dimension(120,20)); } catch (Exception ex) {}
        if (jLabel4!=null) try { jLabel4.setPreferredSize(new java.awt.Dimension(120,20)); } catch (Exception ex) {}
        if (jLabel5!=null) try { jLabel5.setPreferredSize(new java.awt.Dimension(120,20)); } catch (Exception ex) {}
        if (jLabel6!=null) try { jLabel6.setPreferredSize(new java.awt.Dimension(120,20)); } catch (Exception ex) {}
        if (jLabel7!=null) try { jLabel7.setPreferredSize(new java.awt.Dimension(120,20)); } catch (Exception ex) {}
        if (jLabel8!=null) try { jLabel8.setPreferredSize(new java.awt.Dimension(120,20)); } catch (Exception ex) {}
        if (jLabel9!=null) try { jLabel9.setPreferredSize(new java.awt.Dimension(120,20)); } catch (Exception ex) {}
        if (lblImagenMini!=null) try { lblImagenMini.setPreferredSize(new java.awt.Dimension(120,20)); } catch (Exception ex) {}
        // </assistant-fixed-sizes>

        // inicializaciones adicionales: botón para seleccionar imagen y vista previa
        btnSeleccionarImagen = new JButton("Seleccionar Imagen (JPG/PNG)");
        lblImagenMini = new javax.swing.JLabel();
        lblImagenMini.setPreferredSize(new java.awt.Dimension(100,100));
        btnSeleccionarImagen.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            fc.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Imagenes JPG/PNG", "jpg","jpeg","png"));
            int r = fc.showOpenDialog(this);
            if (r == JFileChooser.APPROVE_OPTION) {
                File sel = fc.getSelectedFile();
                try {
                    // copiar a carpeta server_images usando ImageService (devuelve ruta relativa)
                    String rel = culturarte.casosuso.ImageService.saveImage(sel, txtNickname.getText().trim().replaceAll("\\s+","_"));
                    imagenSeleccionadaPath = rel;
                    ImageIcon ic = new ImageIcon(new ImageIcon(culturarte.casosuso.ImageService.resolve(rel).getAbsolutePath()).getImage().getScaledInstance(100,100,Image.SCALE_SMOOTH));
                    lblImagenMini.setIcon(ic);
                    // add components to UI
                    jPanel1.add(btnSeleccionarImagen);
                    jPanel1.add(lblImagenMini);
                    jPanel1.revalidate();
                    jPanel1.repaint();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error al copiar la imagen: " + ex.getMessage());
                }
            }
        });
        // add button initially
        jPanel1.add(btnSeleccionarImagen);
        jPanel1.add(lblImagenMini);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtNickname = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtApellido = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtFecha = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        comboTipo = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtBiografia = new javax.swing.JTextArea();
        jLabel9 = new javax.swing.JLabel();
        txtWeb = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        jLabel2.setText("Nickname:");
        jPanel1.add(jLabel2);

        txtNickname.setColumns(20);
        txtNickname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNicknameActionPerformed(evt);
            }
        });
        jPanel1.add(txtNickname);

        jLabel1.setText("Nombre:");
        jPanel1.add(jLabel1);

        txtNombre.setColumns(22);
        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });
        jPanel1.add(txtNombre);

        jLabel3.setText("Apellido:");
        jPanel1.add(jLabel3);

        txtApellido.setColumns(22);
        txtApellido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtApellidoActionPerformed(evt);
            }
        });
        jPanel1.add(txtApellido);

        jLabel4.setText("Email:");
        jPanel1.add(jLabel4);

        txtEmail.setColumns(22);
        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });
        jPanel1.add(txtEmail);

        jLabel5.setText("Fecha Nac (YYYY-MM-DD):");
        jPanel1.add(jLabel5);

        txtFecha.setColumns(10);
        txtFecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFechaActionPerformed(evt);
            }
        });
        jPanel1.add(txtFecha);

        jLabel6.setText("Tipo:          ");
        jPanel1.add(jLabel6);

        comboTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PROPONENTE", "COLABORADOR" }));
        comboTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboTipoActionPerformed(evt);
            }
        });
        jPanel1.add(comboTipo);

        jLabel7.setText("Dirección (opcional):           ");
        jPanel1.add(jLabel7);

        txtDireccion.setColumns(15);
        txtDireccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDireccionActionPerformed(evt);
            }
        });
        jPanel1.add(txtDireccion);

        jLabel8.setText("Biografía (opcional):");
        jPanel1.add(jLabel8);

        txtBiografia.setColumns(20);
        txtBiografia.setRows(4);
        jScrollPane1.setViewportView(txtBiografia);

        jPanel1.add(jScrollPane1);

        jLabel9.setText("Página web (opcional):");
        jPanel1.add(jLabel9);

        txtWeb.setColumns(15);
        txtWeb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtWebActionPerformed(evt);
            }
        });
        jPanel1.add(txtWeb);

        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        btnAceptar.setText("Aceptar");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });
        jPanel2.add(btnAceptar);

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel2.add(btnCancelar);

        jPanel1.add(jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtNicknameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNicknameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNicknameActionPerformed

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void txtApellidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtApellidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtApellidoActionPerformed

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void txtFechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaActionPerformed

    private void comboTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboTipoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboTipoActionPerformed

    private void txtDireccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDireccionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDireccionActionPerformed

    private void txtWebActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtWebActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtWebActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
limpiar();    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        String nick = txtNickname.getText().trim();
        String email = txtEmail.getText().trim();
        if (nick.isEmpty() || email.isEmpty()) { JOptionPane.showMessageDialog(this, "Nickname y email requeridos"); return; }
try {
    culturarte.casosuso.Usuario u = new culturarte.casosuso.Usuario();
    u.setNickname(txtNickname.getText().trim());
    u.setNombre(txtNombre.getText().trim());
    u.setApellido(txtApellido.getText().trim());
    u.setEmail(txtEmail.getText().trim());
    String fecha = txtFecha.getText().trim();
    if (!fecha.isEmpty()) {
        try {
            u.setFechaNacimiento(java.time.LocalDate.parse(fecha));
        } catch (Exception exDate) {
            // ignored - invalid date format
        }
    }
    Object sel = comboTipo.getSelectedItem();
    if (sel != null) u.setTipo(sel.toString());
    u.setDireccion(txtDireccion.getText().trim());
    u.setBiografia(txtBiografia.getText().trim());
    u.setWeb(txtWeb.getText().trim());
    // store image path selected via file chooser (relative path into server_images)
    if (imagenSeleccionadaPath != null && !imagenSeleccionadaPath.trim().isEmpty()) {
        u.setImgPath(imagenSeleccionadaPath);
    }
    // store direccion from form
    u.setDireccion(txtDireccion.getText().trim());

    culturarte.casosuso.UsuarioService svc = new culturarte.casosuso.UsuarioService();
    svc.save(u);

    JOptionPane.showMessageDialog(this, "Usuario creado correctamente");
    limpiar();
} catch (Exception ex) {
    ex.printStackTrace();
    javax.swing.JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
}
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void limpiar() {
    txtNickname.setText("");
    txtNombre.setText("");
    txtApellido.setText("");
    txtEmail.setText("");
    txtFecha.setText("");
    comboTipo.setSelectedIndex(0);
    txtDireccion.setText("");
    txtBiografia.setText("");
    txtWeb.setText("");
}


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JComboBox<String> comboTipo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextArea txtBiografia;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtNickname;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtWeb;
    private javax.swing.JLabel lblImagenMini;
    private javax.swing.JButton btnSeleccionarImagen;
    private String imagenSeleccionadaPath = null;
    // End of variables declaration//GEN-END:variables
}