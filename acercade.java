import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class acercade extends javax.swing.JFrame {

    public void mensaje(){
        setLayout(new GridBagLayout());
        JLabel mensajes = new JLabel("Esto es un juego de buscaminas hecho por Sergio Garcia Ramiro");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        add(mensajes, gbc);
    }

    public acercade(){
        setSize(500, 500);
        setTitle("Acerca de");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        mensaje();
    }


    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new acercade().setVisible(true));
    }
}
