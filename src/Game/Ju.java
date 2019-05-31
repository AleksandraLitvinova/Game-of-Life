package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ju extends Process{
    Canvas canvasPanel = new Canvas();//конва (область для рисования клеток)
    void go() {
        JFrame frame = new JFrame(NAME_OF_GAME);//создаем окно
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//кнопка для того чтобы закрыть окно
        frame.setSize(FIELD_SIZE, FIELD_SIZE + BTN_PANEL_HEIGHT);//размер окна
        frame.setLocation(START_LOCATION, START_LOCATION);//координаты левого верхнего угла окна
        frame.setResizable(false);//нельзя менять размер окна

        canvasPanel.setBackground(Color.white);//фон конвы

        JButton stepButton = new JButton("Шаг");//кнопка для шага
        stepButton.setEnabled(false);
        stepButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                processOfLife();
                canvasPanel.repaint();
            }
        });

        final JButton goButton = new JButton("Жить");
        goButton.setEnabled(false);
        goButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                goNextGeneration = !goNextGeneration;
                goButton.setText(goNextGeneration ? "Стоп" : "Жить");
            }
        });

        JButton fillButton = new JButton("Заполнить");//кнопка заполнения
        fillButton.addActionListener(new FillButtonListener());
        fillButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stepButton.setEnabled(true);
                goButton.setEnabled(true);
            }
        });

        JPanel btnPanel = new JPanel();//панель для кнопок
        btnPanel.add(fillButton);//добавляем кнопку на панель
        btnPanel.add(stepButton);
        btnPanel.add(goButton);

        frame.getContentPane().add(BorderLayout.CENTER, canvasPanel);
        frame.getContentPane().add(BorderLayout.SOUTH, btnPanel);
        frame.setVisible(true);//видимость окна

        //бесконечный цикл жизни
        while (true) {
            if (goNextGeneration) {//если значение переменной истина
                processOfLife();//делается шаг
                canvasPanel.repaint();//перерисовывается панель
                try {//делается задержка
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // случайное заполнение
    public class FillButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            for (int x = 0; x < LIFE_SIZE; x++) {
                for (int y = 0; y < LIFE_SIZE; y++) {
                    lifeGeneration[x][y] = random.nextBoolean();
                }
            }
            canvasPanel.repaint();
        }
    }

    // отрисовка
    public class Canvas extends JPanel {
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            for (int x = 0; x < LIFE_SIZE; x++) {
                for (int y = 0; y < LIFE_SIZE; y++) {
                    if (lifeGeneration[x][y]) {
                        g.fillOval(x*POINT_RADIUS, y*POINT_RADIUS, POINT_RADIUS, POINT_RADIUS);
                    }
                }
            }
        }
    }
}


