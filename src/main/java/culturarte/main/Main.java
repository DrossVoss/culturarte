package culturarte.main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Culturarte - Estación de Trabajo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 700);
            frame.setLocationRelativeTo(null);
            // add MainPanel
            frame.getContentPane().add(new culturarte.paneles.MainPanel());
            frame.setVisible(true);
        });
    }
}
