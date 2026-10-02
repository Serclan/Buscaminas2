import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

//Hecho por Sergio Garcia Ramiro
public class Buscaminas extends javax.swing.JFrame {
    int filas = 10;
    int columnas = 10;
    JButton[][] botones = new JButton[filas][columnas];
    boolean[][] minas = new boolean[filas][columnas];
    boolean[][] destapadas = new boolean[filas][columnas];
    int totalMinas = 10;
    int casillasSegurasDestapadas = 0;
    boolean juegoTerminado = false;
    JLabel contadorLabel;
    int contador = 0;
    JPanel panelIntermedio;
    JPanel panelTablero;
    javax.swing.JComboBox<String> menuTamaño;
    public Buscaminas() {
        initComponents();
        setTitle("Buscaminas");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new GridBagLayout());
        GridBagConstraints gbcPrincipal = new GridBagConstraints();
        gbcPrincipal.fill = GridBagConstraints.BOTH;

        //Panel superior
        JPanel panelSuperior = crearPanelSuperior(contador);
        gbcPrincipal.gridx = 0;
        gbcPrincipal.gridy = 0;
        gbcPrincipal.weightx = 1.0;
        gbcPrincipal.weighty = 0.15;
        add(panelSuperior, gbcPrincipal);

        //panel intermedio
        panelIntermedio = crearPanelIntermedio();
        gbcPrincipal.gridx = 0;
        gbcPrincipal.gridy = 1;
        gbcPrincipal.weightx = 1.0;
        gbcPrincipal.weighty = 0.70;
        add(panelIntermedio, gbcPrincipal);

        //panel inferior
        JPanel panelInferior = crearPanelInferior();
        gbcPrincipal.gridx = 0;
        gbcPrincipal.gridy = 2;
        gbcPrincipal.weightx = 1.0;
        gbcPrincipal.weighty = 0.15;
        add(panelInferior, gbcPrincipal);

        // Menu de opciones
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // dificultad
        javax.swing.JMenu menuDificultad = new javax.swing.JMenu("Minas");
        menuBar.add(menuDificultad);
        javax.swing.ButtonGroup grupoDificultad = new javax.swing.ButtonGroup();
        javax.swing.JRadioButtonMenuItem dificultad10 = new javax.swing.JRadioButtonMenuItem("10 minas", true);
        javax.swing.JRadioButtonMenuItem dificultad15 = new javax.swing.JRadioButtonMenuItem("15 minas");
        javax.swing.JRadioButtonMenuItem dificultad20 = new javax.swing.JRadioButtonMenuItem("20 minas");

        grupoDificultad.add(dificultad10);
        grupoDificultad.add(dificultad15);
        grupoDificultad.add(dificultad20);

        dificultad10.addActionListener(e -> dificultadSeleccionada(10));
        dificultad15.addActionListener(e -> dificultadSeleccionada(15));
        dificultad20.addActionListener(e -> dificultadSeleccionada(20));

        menuDificultad.add(dificultad10);
        menuDificultad.add(dificultad15);
        menuDificultad.add(dificultad20);
    
        
        // Menu de ayuda
        javax.swing.JMenu menuAyuda = new javax.swing.JMenu("Ayuda");
        menuBar.add(menuAyuda);
        javax.swing.JMenuItem ayudaItem = new javax.swing.JMenuItem("Ayuda");
        ayudaItem.addActionListener(e -> acercades());
        menuAyuda.add(ayudaItem);

        iniciarPartida();
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Buscaminas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() -> new Buscaminas().setVisible(true));
    }

    private void cambiarTamaño(int n) {
        filas = n;
        columnas = n;
        minas = new boolean[filas][columnas];
        destapadas = new boolean[filas][columnas];

        if (n == 10) {
            setSize(600, 600);
        } else if (n == 15) {
            setSize(750, 750);
        } else if (n == 20) {
            setSize(850, 850);
        }
        setLocationRelativeTo(null);

        generarBotonesTablero();
        iniciarPartida();
    }

    private JPanel crearPanelSuperior(int contador) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Informacion de la partida"));
        panel.setBackground(new Color(240, 240, 240));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); 
        gbc.fill = GridBagConstraints.HORIZONTAL;

        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        contadorLabel = new JLabel("Minas pulsadas: " + contador);
        panel.add(contadorLabel, gbc);

        menuTamaño = new javax.swing.JComboBox<>();
        menuTamaño.addItem("10x10");
        menuTamaño.addItem("15x15");
        menuTamaño.addItem("20x20");
        
        gbc.gridx = 1;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(menuTamaño, gbc);

        // Titulo Buscaminas
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.fill = GridBagConstraints.LINE_END;
        panel.add(new JLabel("Buscaminas"), gbc);

        JButton Aplicar = new JButton("Aplicar");
        Aplicar.addActionListener(e -> {
            String seleccion = (String) menuTamaño.getSelectedItem();
            if ("10x10".equals(seleccion)) {
                cambiarTamaño(10);
            } else if ("15x15".equals(seleccion)) {
                cambiarTamaño(15);
            } else if ("20x20".equals(seleccion)) {
                cambiarTamaño(20);
            }
        });
        gbc.gridx = 3;
        gbc.gridy = 0;
        panel.add(Aplicar,gbc);
        
        return panel;
    }

    private JPanel crearPanelIntermedio() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Tablero de Juego"));
        panel.setBackground(new Color(220, 230, 240));

        this.panelIntermedio = panel;
        panelTablero = new JPanel();
        panel.add(panelTablero, BorderLayout.CENTER);
        generarBotonesTablero();

        return panel;
    }

    private void generarBotonesTablero() {
        panelTablero.removeAll();
        panelTablero.setLayout(new GridLayout(filas, columnas, 1, 1));
        botones = new JButton[filas][columnas];

        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) {
                botones[f][c] = new JButton("");
                final int fila = f;
                final int col = c;
                botones[f][c].addActionListener(e -> pulsarCasilla(fila, col));
                panelTablero.add(botones[f][c]);
            }
        }

        panelTablero.revalidate();
        panelTablero.repaint();
        panelIntermedio.revalidate();
        panelIntermedio.repaint();
    }
   
    private JPanel crearPanelInferior() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Menu de opciones"));
        panel.setBackground(new Color(225, 235, 245));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JButton btnNueva = new JButton("Nueva partida");
        btnNueva.addActionListener(e -> iniciarPartida());
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(btnNueva, gbc);

        JButton salir = new JButton("Salir");
        salir.addActionListener(e -> System.exit(0));
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(salir, gbc);

        return panel;
    }

    private void dificultadSeleccionada(int minas) {
        totalMinas = minas;
        iniciarPartida();
    }

    private void iniciarPartida() {
        juegoTerminado = false;
        casillasSegurasDestapadas = 0;
        if (contadorLabel != null) {
            contadorLabel.setText("Partida en curso | " + totalMinas + " minas escondidas");
        }

        
        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) {
                minas[f][c] = false;
                destapadas[f][c] = false;
                if (botones[f][c] != null) {
                    botones[f][c].setText("");
                    botones[f][c].setEnabled(true);
                    botones[f][c].setBackground(null);
                }
            }
        }

        // Colocar minas aleatorias
        int colocadas = 0;
        while (colocadas < totalMinas) {
            int f = (int) (Math.random() * filas);
            int c = (int) (Math.random() * columnas);
            if (!minas[f][c]) {
                minas[f][c] = true;
                colocadas++;
            }
        }
    }

    private void pulsarCasilla(int f, int c) {
        if (juegoTerminado || destapadas[f][c]) {
            return;
        }

        destapadas[f][c] = true;

        // Si es una mina
        if (minas[f][c]) {
            botones[f][c].setText("X");
            botones[f][c].setBackground(Color.RED);
            juegoTerminado = true;
            contadorLabel.setText("¡Has pisado una mina! Has perdido.");
            mostrarTodasLasMinas();
            return;
        }

        
        botones[f][c].setEnabled(false);
        botones[f][c].setBackground(Color.WHITE);
        casillasSegurasDestapadas++;

        
        if (casillasSegurasDestapadas == (filas * columnas - totalMinas)) {
            juegoTerminado = true;
            contadorLabel.setText("¡Enhorabuena! Has esquivado todas las minas.");
            mostrarTodasLasMinas();
        }
    }

    private void acercades(){
        acercade acerca_de = new acercade();
        acerca_de.setVisible(true);
    }

    private void mostrarTodasLasMinas() {
        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) {
                if (minas[f][c]) {
                    botones[f][c].setText("X");
                    if (!destapadas[f][c]) {
                        botones[f][c].setBackground(new Color(255, 180, 180));
                    }
                }
            }
        }
    }

}
