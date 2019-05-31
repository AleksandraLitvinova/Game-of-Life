package Game;

public class Process extends GameOfLife{
    // основной процесс жизни
    void processOfLife() {
        for (int x = 0; x < LIFE_SIZE; x++) {
            for (int y = 0; y < LIFE_SIZE; y++) {
                int count = countNeighbors(x, y);//считает количество соседей клетки
                nextGeneration[x][y] = lifeGeneration[x][y];
                //если количество соседей равно 3, то в ячейке появляется новая клетка иначе ее содержимое остается прежним
                nextGeneration[x][y] = (count == 3) || nextGeneration[x][y];
                //если колличесво соседей у клетки меньше 2 или больше 3, то клетка умирает иначе содержимое ячейки остается прежним
                nextGeneration[x][y] = ((count >= 2) && (count <= 3)) && nextGeneration[x][y];
            }
        }
        for (int x = 0; x < LIFE_SIZE; x++){
            System.arraycopy(nextGeneration[x], 0, lifeGeneration[x], 0, LIFE_SIZE);
        }
    }

    //поиск соседей
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
        if (lifeGeneration[x][y]) {
            count--;
        }//ели ячейка жива и в ней что то есть
        return count;
    }
}
