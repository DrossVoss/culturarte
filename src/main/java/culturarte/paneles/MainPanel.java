package culturarte.paneles;
import javax.swing.JOptionPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * MainPanel - a JPanel that provides the desktop and menus.
 */
public class MainPanel extends JPanel {

    private JDesktopPane desktop;

    public MainPanel() {
        init();
    }

    private void init() {
        this.setLayout(new BorderLayout());
        desktop = new JDesktopPane();
        this.add(desktop, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();

        JMenu mUsuarios = new JMenu("Usuarios");
        JMenuItem miAlta = new JMenuItem("Alta de Perfil");
        miAlta.addActionListener((ActionEvent e) -> openInternal("UCAltaPerfilPanel"));
        JMenuItem miConsultaProp = new JMenuItem("Consulta Perfil Proponente");
        miConsultaProp.addActionListener((ActionEvent e) -> openInternal("UCConsultaPerfilProponentePanel"));
        JMenuItem miConsultaCol = new JMenuItem("Consulta Perfil Colaborador");
        miConsultaCol.addActionListener((ActionEvent e) -> openInternal("UCConsultaPerfilColaboradorPanel"));
        mUsuarios.add(miAlta);
        mUsuarios.add(miConsultaProp);
        mUsuarios.add(miConsultaCol);

        JMenu mPropuestas = new JMenu("Propuestas");
        JMenuItem miAltaP = new JMenuItem("Alta de Propuesta");
        miAltaP.addActionListener((ActionEvent e) -> openInternal("UCAltaPropuestaPanel"));
        JMenuItem miModificar = new JMenuItem("Modificar Propuesta");
        miModificar.addActionListener((ActionEvent e) -> openInternal("UCModificarPropuestaPanel"));
        JMenuItem miConsultaP = new JMenuItem("Consulta de Propuesta");
        miConsultaP.addActionListener((ActionEvent e) -> openInternal("UCConsultaPropuestaPanel"));
        JMenuItem miConsultaEstado = new JMenuItem("Consulta por Estado");
        miConsultaEstado.addActionListener((ActionEvent e) -> openInternal("UCConsultaPorEstadoPanel"));
        JMenuItem miAltaCat = new JMenuItem("Alta de Categoria");
        miAltaCat.addActionListener((ActionEvent e) -> openInternal("UCAltaCategoriaPanel"));
        mPropuestas.add(miAltaP);
        mPropuestas.add(miModificar);
        mPropuestas.add(miConsultaP);
        mPropuestas.add(miConsultaEstado);
        mPropuestas.add(miAltaCat);

        JMenu mCol = new JMenu("Colaboraciones");
        JMenuItem miRegistrar = new JMenuItem("Registrar Colaboración");
        miRegistrar.addActionListener((ActionEvent e) -> openInternal("UCRegistrarColaboracionPanel"));
        JMenuItem miConsultaColabs = new JMenuItem("Consulta colaboraciones");
        miConsultaColabs.addActionListener((ActionEvent e) -> openInternal("UCConsultaColaboracionPanel"));
        JMenuItem miCancelar = new JMenuItem("Cancelar colaboración");
        miCancelar.addActionListener((ActionEvent e) -> openInternal("UCCancelarColaboracionPanel"));
        mCol.add(miRegistrar);
        mCol.add(miConsultaColabs);
        mCol.add(miCancelar);

        JMenu mSocial = new JMenu("Social");
        JMenuItem miSeguir = new JMenuItem("Seguir usuario");
        miSeguir.addActionListener((ActionEvent e) -> openInternal("UCSeguirUsuarioPanel"));
        JMenuItem miDejar = new JMenuItem("Dejar de seguir");
        miDejar.addActionListener((ActionEvent e) -> openInternal("UCDejarDeSeguirUsuarioPanel"));
        mSocial.add(miSeguir);
        mSocial.add(miDejar);

        menuBar.add(mUsuarios);
        menuBar.add(mPropuestas);
        menuBar.add(mCol);
        menuBar.add(mSocial);

        // Put the menu bar on top inside this panel
        JPanel north = new JPanel(new BorderLayout());
        north.add(menuBar, BorderLayout.NORTH);
        this.add(north, BorderLayout.NORTH);
    }

    private void openInternal(String className) {
        try {
            Class<?> cls = Class.forName("culturarte.paneles." + className);
            Object obj = cls.getDeclaredConstructor().newInstance();
            if (obj instanceof JPanel) {
                JPanel panel = (JPanel) obj;
                JInternalFrame iframe = new JInternalFrame(className, true, true, true, true);
                iframe.getContentPane().add(panel);

                Dimension pref = panel.getPreferredSize();
                if (pref != null && pref.width > 0 && pref.height > 0) {
                    iframe.pack(); // respeta el tamaño preferido del panel
                } else {
                    iframe.setSize(400, 300); // tamaño por defecto
                }

                iframe.setVisible(true);
                desktop.add(iframe);
                iframe.setSelected(true);
            } else {
                JOptionPane.showMessageDialog(this, "Clase " + className + " no es JPanel.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al abrir panel: " + ex.getMessage());
        }
    }
} 
