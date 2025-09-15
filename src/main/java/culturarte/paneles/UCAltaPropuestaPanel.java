/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package culturarte.paneles;

import javax.swing.JFileChooser;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


/*
 Pasos para cambiar el tamaño de este panel:
 1) Abrir este archivo: UCAltaPropuestaPanel.java
2) En el constructor (UCAltaPropuestaPanel), modificar la dimensión en setPreferredSize:
   this.setPreferredSize(new java.awt.Dimension(W, H));
   donde W es el ancho y H la altura en píxeles.
3) Recompilar el proyecto (mvn/gradle/NetBeans) y ejecutar para probar.
*/

import javax.swing.JOptionPane;
import culturarte.casosuso.CategoriaService;
import culturarte.casosuso.Categoria;
import culturarte.util.CategoriaTreeSelector;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JDialog;
import java.util.List;
import java.util.function.Consumer;
import culturarte.casosuso.PropuestaService;
import culturarte.casosuso.UsuarioService;
import culturarte.casosuso.Propuesta;
import culturarte.casosuso.Usuario;
import culturarte.casosuso.DatabaseConnection;


/**
 *
 * @author tecnologo
 */
public class UCAltaPropuestaPanel extends javax.swing.JPanel {

    
    private static javax.swing.tree.DefaultMutableTreeNode buildCategoriasRoot() {
        javax.swing.tree.DefaultMutableTreeNode root = new javax.swing.tree.DefaultMutableTreeNode("Categorías");

        // Teatro (carpeta)
        javax.swing.tree.DefaultMutableTreeNode teatro = new javax.swing.tree.DefaultMutableTreeNode("Teatro");
        javax.swing.tree.DefaultMutableTreeNode teatroD = new javax.swing.tree.DefaultMutableTreeNode("Teatro Dramático"); teatroD.setAllowsChildren(false);
        javax.swing.tree.DefaultMutableTreeNode teatroM = new javax.swing.tree.DefaultMutableTreeNode("Teatro Musical"); teatroM.setAllowsChildren(false);
        javax.swing.tree.DefaultMutableTreeNode comedia = new javax.swing.tree.DefaultMutableTreeNode("Comedia");
        javax.swing.tree.DefaultMutableTreeNode standup = new javax.swing.tree.DefaultMutableTreeNode("Stand-up"); standup.setAllowsChildren(false);
        comedia.add(standup);
        teatro.add(teatroD);
        teatro.add(teatroM);
        teatro.add(comedia);

        // Literatura (carpeta vacía)
        javax.swing.tree.DefaultMutableTreeNode literatura = new javax.swing.tree.DefaultMutableTreeNode("Literatura");

        // Música
        javax.swing.tree.DefaultMutableTreeNode musica = new javax.swing.tree.DefaultMutableTreeNode("Música");
        javax.swing.tree.DefaultMutableTreeNode festival = new javax.swing.tree.DefaultMutableTreeNode("Festival"); festival.setAllowsChildren(false);
        javax.swing.tree.DefaultMutableTreeNode concierto = new javax.swing.tree.DefaultMutableTreeNode("Concierto"); concierto.setAllowsChildren(false);
        musica.add(festival);
        musica.add(concierto);

        // Cine
        javax.swing.tree.DefaultMutableTreeNode cine = new javax.swing.tree.DefaultMutableTreeNode("Cine");
        javax.swing.tree.DefaultMutableTreeNode cineAl = new javax.swing.tree.DefaultMutableTreeNode("Cine al Aire Libre"); cineAl.setAllowsChildren(false);
        javax.swing.tree.DefaultMutableTreeNode cinePedal = new javax.swing.tree.DefaultMutableTreeNode("Cine a Pedal"); cinePedal.setAllowsChildren(false);
        cine.add(cineAl);
        cine.add(cinePedal);

        // Danza
        javax.swing.tree.DefaultMutableTreeNode danza = new javax.swing.tree.DefaultMutableTreeNode("Danza");
        javax.swing.tree.DefaultMutableTreeNode ballet = new javax.swing.tree.DefaultMutableTreeNode("Ballet"); ballet.setAllowsChildren(false);
        javax.swing.tree.DefaultMutableTreeNode flamenco = new javax.swing.tree.DefaultMutableTreeNode("Flamenco"); flamenco.setAllowsChildren(false);
        danza.add(ballet);
        danza.add(flamenco);

        // Carnaval
        javax.swing.tree.DefaultMutableTreeNode carnaval = new javax.swing.tree.DefaultMutableTreeNode("Carnaval");
        javax.swing.tree.DefaultMutableTreeNode murga = new javax.swing.tree.DefaultMutableTreeNode("Murga"); murga.setAllowsChildren(false);
        javax.swing.tree.DefaultMutableTreeNode humoristas = new javax.swing.tree.DefaultMutableTreeNode("Humoristas"); humoristas.setAllowsChildren(false);
        javax.swing.tree.DefaultMutableTreeNode parodistas = new javax.swing.tree.DefaultMutableTreeNode("Parodistas"); parodistas.setAllowsChildren(false);
        javax.swing.tree.DefaultMutableTreeNode lubolos = new javax.swing.tree.DefaultMutableTreeNode("Lubolos"); lubolos.setAllowsChildren(false);
        javax.swing.tree.DefaultMutableTreeNode revista = new javax.swing.tree.DefaultMutableTreeNode("Revista"); revista.setAllowsChildren(false);
        carnaval.add(murga);
        carnaval.add(humoristas);
        carnaval.add(parodistas);
        carnaval.add(lubolos);
        carnaval.add(revista);

        // add top-level folders to root
        root.add(teatro);
        root.add(literatura);
        root.add(musica);
        root.add(cine);
        root.add(danza);
        root.add(carnaval);

        return root;
    }




    /**
     * Creates new form UCAltaPropuestaPanel
     */
public UCAltaPropuestaPanel() {
    initComponents();
        // --- Tamaño predeterminado: 1000 x 1000 ---
        // Para cambiarlo: editar esta línea o seguir los pasos en el README dentro del proyecto.
        this.setPreferredSize(new java.awt.Dimension(380, 400));

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

    loadCategorias();
    loadProponentes();
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
        jLabel11 = new javax.swing.JLabel();
        txtTitulo = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        comboCategoria = new culturarte.util.CategoriaTreeSelector();
        jLabel4 = new javax.swing.JLabel();
        comboProponente = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        txtLugar = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        spinnerFecha = new javax.swing.JSpinner();
        jLabel7 = new javax.swing.JLabel();
        txtPrecioEntrada = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtMontoNecesario = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        comboEstado = new javax.swing.JComboBox<>();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel11.setText("Titulo:  ");
        jPanel1.add(jLabel11);

        txtTitulo.setColumns(20);
        txtTitulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTituloActionPerformed(evt);
            }
        });
        jPanel1.add(txtTitulo);

        jLabel1.setText("Descripción:");
        jPanel1.add(jLabel1);

        txtDescripcion.setColumns(20);
        txtDescripcion.setRows(4);
        jScrollPane1.setViewportView(txtDescripcion);

        jPanel1.add(jScrollPane1);

        jLabel3.setText("Categoría:");
        jPanel1.add(jLabel3);

        comboCategoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboCategoriaActionPerformed(evt);
            }
        });
        jPanel1.add(comboCategoria);

        jLabel4.setText("Proponente:                                     ");
        jPanel1.add(jLabel4);

        comboProponente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboProponente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboProponenteActionPerformed(evt);
            }
        });
        jPanel1.add(comboProponente);

        jLabel5.setText("Lugar:       ");
        jPanel1.add(jLabel5);

        txtLugar.setColumns(20);
        txtLugar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLugarActionPerformed(evt);
            }
        });
        jPanel1.add(txtLugar);

        jLabel6.setText("Fecha (yyyy-mm-dd):");
        jPanel1.add(jLabel6);

        spinnerFecha.setModel(new javax.swing.SpinnerDateModel());
        jPanel1.add(spinnerFecha);

        jLabel7.setText("Precio entrada:                ");
        jPanel1.add(jLabel7);

        txtPrecioEntrada.setColumns(13);
        txtPrecioEntrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecioEntradaActionPerformed(evt);
            }
        });
        jPanel1.add(txtPrecioEntrada);

        jLabel8.setText("Monto necesario:");
        jPanel1.add(jLabel8);

        txtMontoNecesario.setColumns(15);
        txtMontoNecesario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMontoNecesarioActionPerformed(evt);
            }
        });
        jPanel1.add(txtMontoNecesario);

        
        jLabelTipoRetorno = new javax.swing.JLabel();
        comboTipoRetorno = new javax.swing.JComboBox<>();
        jLabelTipoRetorno.setText("Tipo retorno:");
        jPanel1.add(jLabelTipoRetorno);
        comboTipoRetorno.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "entrada", "porcentaje" }));
        comboTipoRetorno.setPreferredSize(new java.awt.Dimension(200, 24));
        jPanel1.add(comboTipoRetorno);

        jLabelImagen = new javax.swing.JLabel();
        txtImagen = new javax.swing.JTextField();
        btnSeleccionarImagen = new javax.swing.JButton();

        jLabelImagen.setText("Imagen (opcional):");
        jPanel1.add(jLabelImagen);

        txtImagen.setColumns(20);
        txtImagen.setEditable(false);
        txtImagen.setPreferredSize(new java.awt.Dimension(300,24));
        jPanel1.add(txtImagen);

        btnSeleccionarImagen.setText("Seleccionar...");
        btnSeleccionarImagen.setPreferredSize(new java.awt.Dimension(120,24));
        btnSeleccionarImagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionarImagenActionPerformed(evt);
            }
        });
        jPanel1.add(btnSeleccionarImagen);
jLabel9.setText("Estado inicial:");
        jPanel1.add(jLabel9);

        comboEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ingresada", "Publicada", "En Financiación", "Financiada", "No financiada", "Cancelada" }));
        
        comboEstado.setSelectedItem("Ingresada");
        comboEstado.setEnabled(false);
comboEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboEstadoActionPerformed(evt);
            }
        });
        jPanel1.add(comboEstado);

        btnAceptar.setText("Aceptar");
        btnAceptar.setPreferredSize(new java.awt.Dimension(120, 24));
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });
        jPanel1.add(btnAceptar);

        btnCancelar.setText("Cancelar");
        btnCancelar.setPreferredSize(new java.awt.Dimension(120, 24));
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCancelar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 380, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void comboCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboCategoriaActionPerformed

    }//GEN-LAST:event_comboCategoriaActionPerformed

    private void comboProponenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboProponenteActionPerformed

    }//GEN-LAST:event_comboProponenteActionPerformed

    private void txtLugarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLugarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLugarActionPerformed

    private void txtPrecioEntradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioEntradaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioEntradaActionPerformed

    private void txtMontoNecesarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMontoNecesarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMontoNecesarioActionPerformed

    private void txtTituloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTituloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTituloActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        // Crear y persistir Propuesta usando JPA
        javax.persistence.EntityManager em = null;
        try {
            em = culturarte.casosuso.JPAUtil.getEntityManager();
            // NO iniciar transacción aquí: usaremos PropuestaService.save(p) para persistir y manejar transacciones.

            culturarte.casosuso.Propuesta p = new culturarte.casosuso.Propuesta();
            p.setTitulo(txtTitulo.getText().trim());
            p.setDescripcion(txtDescripcion.getText().trim());
            if (comboCategoria.getSelectedItem() != null) {
                String categoriaId = ((String)comboCategoria.getSelectedItem()).split(":")[0].trim();
                p.setCategoria(em.find(culturarte.casosuso.Categoria.class, categoriaId));
            }
            if (comboProponente.getSelectedItem() != null) {
                String proponenteId = ((String)comboProponente.getSelectedItem()).split(":")[0].trim();
                p.setProponente(em.find(culturarte.casosuso.Usuario.class, proponenteId));
            }
            p.setLugar(txtLugar.getText().trim());

            // Fecha desde spinnerFecha (assume Date)
            try {
                java.util.Date d = (java.util.Date) spinnerFecha.getValue();
                p.setFechaRealizacion(d.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
            } catch (Exception ex) {
                // dejar nulo si no se puede parsear
            }

            // Precio entrada
            try {
                String s = txtPrecioEntrada.getText().trim();
                if (!s.isEmpty()) p.setPrecioEntrada(new java.math.BigDecimal(s));
            } catch (Exception ex) {
                p.setPrecioEntrada(null);
            }

            // Monto necesario
            try {
                String s = txtMontoNecesario.getText().trim();
                if (!s.isEmpty()) p.setMontoNecesario(new java.math.BigDecimal(s));
            } catch (Exception ex) {
                p.setMontoNecesario(null);
            }

            // Estado
            try {
                p.setEstadoActual((String) comboEstado.getSelectedItem());
            } catch (Exception ex) {
                p.setEstadoActual(null);
            }

            p.setFechaPublicacion(java.time.LocalDateTime.now());


            
            // --- Asignar 'ref' único si la clave primaria es String; manejo robusto por reflection ---
            try {
                // buscar campo con @Id o un campo llamado 'ref'/'id'
                java.lang.reflect.Field idField = null;
                for (java.lang.reflect.Field f : p.getClass().getDeclaredFields()) {
                    if (f.isAnnotationPresent(javax.persistence.Id.class)) {
                        idField = f;
                        break;
                    }
                }
                if (idField == null) {
                    try {
                        idField = p.getClass().getDeclaredField("ref");
                    } catch (NoSuchFieldException nsf) {
                        try { idField = p.getClass().getDeclaredField("id"); } catch (NoSuchFieldException nsf2) { /* leave null */ }
                    }
                }
                if (idField == null) {
                    throw new RuntimeException("No se pudo localizar el campo @Id en Propuesta; ajustá la entidad o asigná el id antes de persistir.");
                }
                idField.setAccessible(true);
                Object currentId = idField.get(p);

                // solo intentamos generar cuando la PK es String y está vacía/nula
                if ((currentId == null) || (currentId instanceof String && ((String)currentId).trim().isEmpty())) {
                    if (idField.getType() != String.class) {
                        throw new RuntimeException("La clave primaria de Propuesta no es String. Para este proyecto la generación automática de 'ref' espera un String. Considerá cambiar la entidad o asignar manualmente el id."); 
                    }
                    java.util.Random rnd = new java.util.Random();
                    String refCandidate = null;
                    int attempts = 0;
                    do {
                        StringBuilder sb = new StringBuilder(3);
                        for (int i = 0; i < 3; i++) {
                            char c = (char)('A' + rnd.nextInt(26));
                            sb.append(c);
                        }
                        refCandidate = sb.toString();
                        attempts++;
                    } while (em.find(culturarte.casosuso.Propuesta.class, refCandidate) != null && attempts < 1000);

                    if (em.find(culturarte.casosuso.Propuesta.class, refCandidate) != null) {
                        throw new RuntimeException("No se pudo generar un ref único para la Propuesta después de " + attempts + " intentos.");
                    }

                    idField.set(p, refCandidate);
                }
            } catch (RuntimeException rre) {
                // rethrow para que el rollback y manejo superior ocurra
                throw rre;
            } catch (Exception genEx) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                throw new RuntimeException(genEx);
            }
// Persistir usando PropuestaService para asegurar inserción de estado inicial y manejo consistente
            try {
                // Ensure tipo retorno is set (platform supports 'entrada' or 'porcentaje')
                try {
                    if (comboTipoRetorno != null && comboTipoRetorno.getSelectedItem() != null) {
                        p.setTipoRetorno((String) comboTipoRetorno.getSelectedItem());
                    } else if (p.getTipoRetorno() == null) {
                        p.setTipoRetorno("entrada");
                    }
                } catch (Exception ignore) {}

                if (txtImagen != null && txtImagen.getText() != null && !txtImagen.getText().trim().isEmpty()) {
                    p.setImagenPath(txtImagen.getText().trim());
                }
                new culturarte.casosuso.PropuestaService().save(p);
            // Asegurar explícitamente que exista un registro inicial en estados_propuestas (JDBC),
            // por si PropuestaService.save no pudo insertarlo por algún motivo.
            try {
                culturarte.casosuso.Propuesta mergedProp = p;
                // If PropuestaService.save returned merged entity, try to use it; but here save may have mutated p.
                // Attempt to read current id
                String mergedId = null;
                try { mergedId = (mergedProp.getId() == null ? p.getId() : mergedProp.getId()); } catch (Throwable t) { mergedId = p.getId(); }
                if (mergedId == null) mergedId = p.getId();
                if (mergedId != null) {
                    try (java.sql.Connection conn2 = DatabaseConnection.getConnection()) {
                        try (java.sql.PreparedStatement psChk = conn2.prepareStatement("SELECT COUNT(*) FROM estados_propuestas WHERE propuesta_ref = ?")) {
                            psChk.setString(1, mergedId);
                            try (java.sql.ResultSet rsChk = psChk.executeQuery()) {
                                int cnt = 0;
                                if (rsChk.next()) cnt = rsChk.getInt(1);
                                if (cnt == 0) {
                                    try (java.sql.PreparedStatement psIns = conn2.prepareStatement("INSERT INTO estados_propuestas (propuesta_ref, estado, fecha, hora) VALUES (?, ?, CURDATE(), CURTIME())")) {
                                        String estadoInicial = null;
                                        try { estadoInicial = p.getEstadoActual(); } catch (Throwable t) {}
                                        if (estadoInicial == null || estadoInicial.trim().isEmpty()) estadoInicial = "Ingresada";
                                        psIns.setString(1, mergedId);
                                        psIns.setString(2, estadoInicial);
                                        psIns.executeUpdate();
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (Exception insEx) {
                System.err.println("UCAltaPropuestaPanel: no se pudo asegurar estado inicial JDBC: " + insEx.getMessage());
                insEx.printStackTrace(System.err);
            }

            } catch (Exception svcEx) {
                // fallback: intentar persistir directamente con la EntityManager actual (si existe)
                try {
                    if (em == null || !em.isOpen()) em = culturarte.casosuso.JPAUtil.getEntityManager();
                    if (!em.getTransaction().isActive()) em.getTransaction().begin();
                    em.persist(p);
                    em.getTransaction().commit();
                } catch (Exception ex2) {
                    if (em != null && em.getTransaction().isActive()) em.getTransaction().rollback();
                    throw ex2;
                }
            }


            javax.swing.JOptionPane.showMessageDialog(this, "Propuesta creada correctamente");
            clearForm();
        } catch (Exception ex) {
            if (em != null && em.getTransaction().isActive()) em.getTransaction().rollback();
            ex.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void comboEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboEstadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboEstadoActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        txtTitulo.setText("");
        txtDescripcion.setText("");
        txtLugar.setText("");
        txtPrecioEntrada.setText("");
        txtMontoNecesario.setText("");

        if (comboCategoria.getItemCount() > 0) comboCategoria.setSelectedIndex(0);
        if (comboProponente.getItemCount() > 0) comboProponente.setSelectedIndex(0);
        if (comboEstado.getItemCount() > 0) comboEstado.setSelectedIndex(0);

        spinnerFecha.setValue(new java.util.Date());
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void clearForm() {
    txtTitulo.setText("");
    txtDescripcion.setText("");
    txtLugar.setText("");
    txtPrecioEntrada.setText("");
    txtMontoNecesario.setText("");
    if (comboCategoria.getItemCount() > 0) comboCategoria.setSelectedIndex(0);
    if (comboProponente.getItemCount() > 0) comboProponente.setSelectedIndex(0);
    if (comboEstado.getItemCount() > 0) comboEstado.setSelectedIndex(0);
}
    
    private void loadCategorias() {
        try {
            java.util.List<culturarte.casosuso.Categoria> cats = new culturarte.casosuso.CategoriaService().findAll();
            comboCategoria.setCategories(cats);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }

    }

    private void loadProponentes() {
        comboProponente.removeAllItems();
        try {
            java.util.List<culturarte.casosuso.Usuario> list = new culturarte.casosuso.UsuarioService().findByTipo("PROPONENTE");
            for (culturarte.casosuso.Usuario u : list) {
                comboProponente.addItem(u.getId() + ":" + u.getNickname());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }







    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnSeleccionarCategoria;
    private culturarte.util.CategoriaTreeSelector comboCategoria;
    private javax.swing.JComboBox<String> comboEstado;
    private javax.swing.JComboBox<String> comboProponente;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner spinnerFecha;
    private javax.swing.JTextArea txtDescripcion;
    private javax.swing.JTextField txtLugar;
    private javax.swing.JTextField txtMontoNecesario;
    private javax.swing.JTextField txtPrecioEntrada;
    private javax.swing.JTextField txtTitulo;
    private javax.swing.JComboBox<String> comboTipoRetorno;
    private javax.swing.JLabel jLabelTipoRetorno;
        private javax.swing.JLabel jLabelImagen;
    private javax.swing.JTextField txtImagen;
    private javax.swing.JButton btnSeleccionarImagen;
// End of variables declaration//GEN-END:variables


    private void btnSeleccionarImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionarImagenActionPerformed
        try {
            JFileChooser chooser = new JFileChooser();
            int ret = chooser.showOpenDialog(this);
            if (ret == JFileChooser.APPROVE_OPTION) {
                File src = chooser.getSelectedFile();
                // create uploads dir relative to working dir
                File uploadsDir = new File("uploads");
                if (!uploadsDir.exists()) uploadsDir.mkdirs();
                // Copy file to uploads with same name (overwrites if exists)
                Path dest = Paths.get(uploadsDir.getAbsolutePath(), src.getName());
                Files.copy(src.toPath(), dest, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                // store absolute URI so other panels can load it
                txtImagen.setText(dest.toUri().toString());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al copiar la imagen: " + ex.getMessage());
        }
    }//GEN-LAST:event_btnSeleccionarImagenActionPerformed

}
