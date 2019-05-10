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
    volatile boolean goNextGeneration = false;

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

        /*final JButton cleanButton = new JButton("Clean");
        cleanButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetGrid();
                canvasPanel.repaint();
            }
        });*/

        JPanel btnPanel = new JPanel();//панель для кнопок
        btnPanel.add(fillButton);//добавляем кнопку на панель
        btnPanel.add(stepButton);
        btnPanel.add(goButton);
        //btnPanel.add(cleanButton);

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

        // подсчет количества соседей
        int countNeighbors(int x, int y) {
            int count = 0;
            for (int dx = -1; dx < 2; dx++) {
                for (int dy = -1; dy < 2; dy++) {
                    int nX = x + dx;
                    int nY = y + dy;
                    nX = (nX < 0) ? LIFE_SIZE - 1 : nX;
                    nY = (nY < 0) ? LIFE_SIZE - 1 : nY;
                    nX = (nX > LIFE_SIZE - 1) ? 0 : nX;
                    nY = (nY > LIFE_SIZE - 1) ? 0 : nY;
                    count += (lifeGeneration[nX][nY]) ? 1 : 0;
                }
            }
            if (lifeGeneration[x][y]) { count--; }//ели ячейка жива и в ней что то есть
            return count;
        }

        // основной процесс жизни
        void processOfLife() {
            for (int x = 0; x < LIFE_SIZE; x++) {
                for (int y = 0; y < LIFE_SIZE; y++) {
                    int count = countNeighbors(x, y);//считает количество соседей клетки
                    nextGeneration[x][y] = lifeGeneration[x][y];
                    //если количество соседей равно 3, то в ячейке появляется новая клетка иначе ее содержимое остается прежним
                    nextGeneration[x][y] = (count == 3) ? true : nextGeneration[x][y];
                    //если колличесво соседей у клетки меньше 2 или больше 3, то клетка умирает иначе содержимое ячейки остается прежним
                    nextGeneration[x][y] = ((count < 2) || (count > 3)) ? false : nextGeneration[x][y];
                }
            }
            for (int x = 0; x < LIFE_SIZE; x++) {
                System.arraycopy(nextGeneration[x], 0, lifeGeneration[x], 0, LIFE_SIZE);
            }
        }

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
