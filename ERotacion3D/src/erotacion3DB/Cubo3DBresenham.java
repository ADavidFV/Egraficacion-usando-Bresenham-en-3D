package erotacion3DB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
// Clase que representa el panel del cubo en 3D

public class Cubo3DBresenham extends JPanel {

    private Algoritmos3D algoritmos;// Instancia de la clase que realiza los cálculos 3D

    // Constructor que recibe el tamaño del cubo
    public Cubo3DBresenham(int tamañoCubo) {
        this.algoritmos = new Algoritmos3D(tamañoCubo);// Inicializa la instancia de Algoritmos3D
        algoritmos.dibujarCubo();// Dibuja el cubo en su posición inicial
    }
// Método para proyectar un punto 3D al plano 2D (proyección en perspectiva)

    private double[] proyectarPunto(double[] punto3D) {
        double distanciaObservadorAlPlano = 1000; // Distancia del observador al plano de la pantalla

        // Coordenadas del punto 3D
        double x = punto3D[0];
        double y = punto3D[1];
        double z = punto3D[2];

        // Fórmula de proyección en perspectiva
        double xP = (distanciaObservadorAlPlano * x) / (distanciaObservadorAlPlano - z);
        double yP = (distanciaObservadorAlPlano * y) / (distanciaObservadorAlPlano - z);

        // Devolver las coordenadas proyectadas como un array
        return new double[]{xP, yP};
    }


    // Método para iniciar la interfaz gráfica
    public void iniciar() {
        JFrame frame = new JFrame("Cubo 3D");// Crea una ventana
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// Define la acción al cerrar la ventana
        frame.setSize(600, 600);// Establece el tamaño de la ventana

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1)); // Panel para botones de rotación
        JButton rotarXButton = new JButton("Rotar en X");// Botón para rotar en el eje X
        JButton rotarYButton = new JButton("Rotar en Y");// Botón para rotar en el eje Y

        rotarXButton.setPreferredSize(new Dimension(150, 30));// Establece el tamaño del botón rotarXButton
        rotarYButton.setPreferredSize(new Dimension(150, 30)); // Establece el tamaño del botón rotarYButton
        // Acción al presionar el botón de rotación en X
        rotarXButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                double anguloX = Double.parseDouble(JOptionPane.showInputDialog("Ingrese ángulo de rotación en X:"));
                algoritmos.aplicarRotacionX(anguloX);// Aplica rotación en el eje X
                repaint();// Vuelve a pintar el cubo con la rotación aplicada
            }
        });

        // Acción al presionar el botón de rotación en Y
        rotarYButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                double anguloY = Double.parseDouble(JOptionPane.showInputDialog("Ingrese ángulo de rotación en Y:"));
                algoritmos.aplicarRotacionY(anguloY); // Aplica rotación en el eje Y
                repaint(); // Vuelve a pintar el cubo con la rotación aplicada
            }
        });

        buttonPanel.add(rotarXButton);// Agrega el botón rotarXButton al panel
        buttonPanel.add(rotarYButton);// Agrega el botón rotarYButton al panel

        frame.add(buttonPanel, BorderLayout.PAGE_END); // Agrega el panel de botones en la parte inferior de la ventana
        frame.add(this);// Agrega este panel (Cubo3DBresenham) a la ventana
        frame.setVisible(true);// Hace visible la ventana
    }

    // Método para pintar el cubo en el panel
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);

        double[][] puntos3D = algoritmos.obtenerPuntos3D();// Obtiene los puntos del cubo
/*
        // Proyecta cada punto 3D al plano 2D
        double[][] puntos2D = new double[puntos3D.length][2];
        for (int i = 0; i < puntos3D.length; i++) {
            puntos2D[i] = proyectarPunto(puntos3D[i]);
        }

        // Dibuja las líneas que representan el cubo usando los puntos 2D proyectados
        for (int i = 0; i < 4; i++) {
            dibujarLineaBresenham(g, puntos2D[i], puntos2D[(i + 1) % 4]);
            dibujarLineaBresenham(g, puntos2D[i + 4], puntos2D[((i + 1) % 4) + 4]);
            dibujarLineaBresenham(g, puntos2D[i], puntos2D[i + 4]);
        }*/

        // Dibuja las líneas que representan el cubo usando el algoritmo de Bresenham
        dibujarLineaBresenham(g, puntos3D[0], puntos3D[1]);
        dibujarLineaBresenham(g, puntos3D[1], puntos3D[2]);
        dibujarLineaBresenham(g, puntos3D[2], puntos3D[3]);
        dibujarLineaBresenham(g, puntos3D[3], puntos3D[0]);

        dibujarLineaBresenham(g, puntos3D[4], puntos3D[5]);
        dibujarLineaBresenham(g, puntos3D[5], puntos3D[6]);
        dibujarLineaBresenham(g, puntos3D[6], puntos3D[7]);
        dibujarLineaBresenham(g, puntos3D[7], puntos3D[4]);

        dibujarLineaBresenham(g, puntos3D[0], puntos3D[4]);
        dibujarLineaBresenham(g, puntos3D[1], puntos3D[5]);
        dibujarLineaBresenham(g, puntos3D[2], puntos3D[6]);
        dibujarLineaBresenham(g, puntos3D[3], puntos3D[7]);

    }

    // Método para dibujar una línea usando el algoritmo de Bresenham
    private void dibujarLineaBresenham(Graphics g, double[] punto1, double[] punto2) {
        // Implementación del algoritmo de Bresenham para dibujar una línea
        // ...

        int x1 = (int) punto1[0];
        int y1 = (int) punto1[1];
        int x2 = (int) punto2[0];
        int y2 = (int) punto2[1];

        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;
        int err = dx - dy;

        while (x1 != x2 || y1 != y2) {
            g.fillRect(x1, y1, 1, 1);
            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x1 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y1 += sy;
            }
        }
    }

    // Método principal para iniciar la aplicación
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            int tamañoCubo = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el tamaño del cubo (0-100):"));
            if (tamañoCubo < 0 || tamañoCubo > 100) {
                System.out.println("El tamaño del cubo debe estar en el rango de 0 a 100.");
                return;
            }
            Cubo3DBresenham panel = new Cubo3DBresenham(tamañoCubo);
            panel.iniciar();// Inicia la interfaz gráfica
        });
    }
}

// Clase que realiza los cálculos y transformaciones 3D
class Algoritmos3D {

    private double[][] puntos3D = new double[8][4];// Matriz para los puntos 3D del cubo
    private int tamañoCubo;// Tamaño del cubo

    // Constructor que recibe el tamaño del cubo
    public Algoritmos3D(int tamañoCubo) {
        this.tamañoCubo = tamañoCubo;
    }

    // Método para dibujar el cubo en su posición inicial
    public void dibujarCubo() {
        // Definir los vértices del cubo
        puntos3D[0] = new double[]{100, 100, 100, 1};
        puntos3D[1] = new double[]{100 + tamañoCubo, 100, 100, 1};
        puntos3D[2] = new double[]{100 + tamañoCubo, 100 + tamañoCubo, 100, 1};
        puntos3D[3] = new double[]{100, 100 + tamañoCubo, 100, 1};
        puntos3D[4] = new double[]{100, 100, 100 + tamañoCubo, 1};
        puntos3D[5] = new double[]{100 + tamañoCubo, 100, 100 + tamañoCubo, 1};
        puntos3D[6] = new double[]{100 + tamañoCubo, 100 + tamañoCubo, 100 + tamañoCubo, 1};
        puntos3D[7] = new double[]{100, 100 + tamañoCubo, 100 + tamañoCubo, 1};
    }

    // Métodos privados para cálculos de matrices y transformaciones
    private void aplicarTransformacion(double[][] matrizTransformacion) {
        for (int i = 0; i < puntos3D.length; i++) {
            double[] punto = puntos3D[i];
            double[] resultado = new double[4];
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    resultado[j] += punto[k] * matrizTransformacion[k][j];
                }
            }
            puntos3D[i] = resultado;
        }
    }

// Método para aplicar rotación en el eje X (en grados)
    public void aplicarRotacionX(double thetaX) {
        double[][] matrizRotacionX = obtenerMatrizRotacionX(thetaX);
        aplicarTransformacion(matrizRotacionX);
    }

// Método para aplicar rotación en el eje Y (en grados)
    public void aplicarRotacionY(double thetaY) {
        double[][] matrizRotacionY = obtenerMatrizRotacionY(thetaY);
        aplicarTransformacion(matrizRotacionY);
    }

// Métodos privados para cálculos de matrices y transformaciones en grados
    private double[][] obtenerMatrizRotacionX(double thetaX) {
        double cosX = Math.cos(Math.toRadians(thetaX));
        double sinX = Math.sin(Math.toRadians(thetaX));
        return new double[][]{
            {1, 0, 0, 0},
            {0, cosX, -sinX, 0},
            {0, sinX, cosX, 0},
            {0, 0, 0, 1}
        };
    }

    private double[][] obtenerMatrizRotacionY(double thetaY) {
        double cosY = Math.cos(Math.toRadians(thetaY));
        double sinY = Math.sin(Math.toRadians(thetaY));
        return new double[][]{
            {cosY, 0, sinY, 0},
            {0, 1, 0, 0},
            {-sinY, 0, cosY, 0},
            {0, 0, 0, 1}
        };
    }
    // Método para obtener los puntos 3D del cubo

    public double[][] obtenerPuntos3D() {
        return puntos3D;
    }
}
