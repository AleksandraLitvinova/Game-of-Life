import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class GameOfLife {
    final String NAME_OF_GAME = "Game of Life";//
    final int START_LOCATION = 200;
    final int LIFE_SIZE = 50;
    final int POINT_RADIUS = 10;//радиус точки
    final int FIELD_SIZE = LIFE_SIZE * POINT_RADIUS + 7;//размер поля
    final int BTN_PANEL_HEIGHT = 58;
    boolean[][] lifeGeneration = new boolean[LIFE_SIZE][LIFE_SIZE];//массив текущего поколения
    boolean[][] nextGeneration = new boolean[LIFE_SIZE][LIFE_SIZE];//массив слудующего поколения
    Canvas canvasPanel;
    Random random = new Random();

    public static void main(String[] args) {
        new GameOfLife().go();//запускаем класс на исполнение
    }

    void go() {
        JFrame frame = new JFrame(NAME_OF_GAME);//создаем окно
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//кнопка для того чтобы закрыть окно
        frame.setSize(FIELD_SIZE, FIELD_SIZE + BTN_PANEL_HEIGHT);//размер окна
        frame.setLocation(START_LOCATION, START_LOCATION);//координаты левого верхнего угла окна
        frame.setResizable(false);//нельзя менять размер окна

        canvasPanel = new Canvas();//конва (область для рисования клеток)
        canvasPanel.setBackground(Color.white);//фон конвы

        JButton fillButton = new JButton("Fill");//кнопка заполнения
        fillButton.addActionListener(new FillButtonListener());

        JButton stepButton = new JButton("Step");//кнопка для шага
        stepButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //processOfLife();
                canvasPanel.repaint();
            }
        });

        final JButton goButton = new JButton("Play");

        JPanel btnPanel = new JPanel();//панель для кнопок
        btnPanel.add(fillButton);//добавляем кнопку на панель
        btnPanel.add(stepButton);
        btnPanel.add(goButton);

        frame.getContentPane().add(BorderLayout.CENTER, canvasPanel);
        frame.getContentPane().add(BorderLayout.SOUTH, btnPanel);
        frame.setVisible(true);//видимость окна
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

        // основной процесс жизни
        /*void processOfLife() {
            for (int x = 0; x < LIFE_SIZE; x++) {
                for (int y = 0; y < LIFE_SIZE; y++) {
                    int count = countNeighbors(x, y);//считает количество соседей клетки
                    nextGeneration[x][y] = lifeGeneration[x][y];
                    //если количество соседей равно 3, то в ячейке появляется новая клетка иначе ее содержимое остается прежним
                    nextGeneration[x][y] = (count == 3) ? true : nextGeneration[x][y];
                    //если колличесво соседей у клетки меньше 2 или больше 3, то клетка умирает иначе содержимое ячейки остается прежним
                    // if cell has less than 2 or greater than 3 neighbors - it will be die
                    nextGeneration[x][y] = ((count < 2) || (count > 3)) ? false : nextGeneration[x][y];
                }
            }
            for (int x = 0; x < LIFE_SIZE; x++) {
                System.arraycopy(nextGeneration[x], 0, lifeGeneration[x], 0, LIFE_SIZE);
            }
        }*/

    // отрисовка
    public class Canvas extends JPanel{
        @Override
        public void paint(Graphics g) {
            super.paint(g);//вызываем метод
            for (int x = 0; x < LIFE_SIZE; x++) {
                for (int y = 0; y < LIFE_SIZE; y++) {
                    if (lifeGeneration[x][y]) {//если элимент истина, то рисуется точка
                        g.fillOval(x*POINT_RADIUS, y*POINT_RADIUS, POINT_RADIUS, POINT_RADIUS);
                    }
                }
            }
        }
    }
}
