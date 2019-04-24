import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class GameOfLife {
    final String NAME_OF_GAME = "Conway's Game of Life";
    final int START_LOCATION = 200;
    final int LIFE_SIZE = 50;
    final int POINT_RADIUS = 10;
    final int FIELD_SIZE = LIFE_SIZE * POINT_RADIUS + 7;
    final int BTN_PANEL_HEIGHT = 58;
    boolean[][] lifeGeneration = new boolean[LIFE_SIZE][LIFE_SIZE];
    boolean[][] nextGeneration = new boolean[LIFE_SIZE][LIFE_SIZE];
    volatile boolean goNextGeneration = false; // fixed the problem in 64-bit JVM
    int showDelay = 200;
    Canvas canvasPanel;
    Random random = new Random();

    public static void main(String[] args) {
        new GameOfLife().go();
    }

    void go() {
        JFrame frame = new JFrame(NAME_OF_GAME);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(FIELD_SIZE, FIELD_SIZE + BTN_PANEL_HEIGHT);
        frame.setLocation(START_LOCATION, START_LOCATION);
        frame.setResizable(false);

        canvasPanel = new Canvas();
        canvasPanel.setBackground(Color.white);

        JButton fillButton = new JButton("Fill");
        //fillButton.addActionListener();

        JButton stepButton = new JButton("Step");
        stepButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                processOfLife();
                canvasPanel.repaint();
            }
        });

        final JButton goButton = new JButton("Play");
        goButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                goNextGeneration = !goNextGeneration;
                goButton.setText(goNextGeneration ? "Stop" : "Play");
            }
        });

        JPanel btnPanel = new JPanel();
        btnPanel.add(fillButton);
        btnPanel.add(stepButton);
        btnPanel.add(goButton);

        frame.getContentPane().add(BorderLayout.CENTER, canvasPanel);
        frame.getContentPane().add(BorderLayout.SOUTH, btnPanel);
        frame.setVisible(true);

        // бесконечная жизнь

        // случайное заполнение


        // определить количество соседей
        // основной процесс жизни
        //void processOfLife ()
        // отрисовка


    }

    private void processOfLife() {
    }
}