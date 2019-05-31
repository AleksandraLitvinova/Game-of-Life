package Game;

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
    Random random = new Random();
    volatile boolean goNextGeneration = false;

    public static void main(String[] args) {
        new Ju().go();//запускаем класс на исполнение
        new Process().processOfLife();
    }
}



