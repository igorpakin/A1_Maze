package edu.school.maze.caves.operations;

import edu.school.maze.caves.entities.Cave;
import edu.school.maze.caves.entities.CellStatus;
import edu.school.maze.caves.exceptions.CaveWrongSizeException;

public class CaveGeneration {
    private Cave previousCave = null;
    private Cave nextCave = null;

    public Cave getPreviousCave() {
        return previousCave;
    }

    private int fieldWidth = 0;

    private int fieldHeight = 0;
    private int birthLimit = 0;
    private int deathLimit = 0;
    private long milSecPause = 0L;

    public CaveGeneration(Cave initCave, int birthLimit, int deathLimit,
            long milSecPause) {
        if ((birthLimit >= 0 && birthLimit <= 7) && (deathLimit >= 0 && deathLimit <= 7)) {
            this.birthLimit = birthLimit;
            this.deathLimit = deathLimit;
        } else {
            throw new CaveWrongSizeException("Wrong limits values!");
        }
        this.milSecPause = milSecPause;
        this.fieldWidth = initCave.getCaveWidth();
        this.fieldHeight = initCave.getCaveHeight();
        this.previousCave = new Cave(fieldWidth, fieldHeight, initCave.getCaveField());
        this.nextCave = new Cave(fieldWidth, fieldHeight, initCave.getCaveField());
    }

    public void autoGeneration() {
        while (true) {
            generateStep();
            sleepingMilSecs();
        }
    }

    private void sleepingMilSecs() {
        try {
            Thread.sleep(milSecPause);
        } catch (InterruptedException e) {
            System.out.println("Smth wrong with timer");
        }
    }

    public void generateStep() {
        for (int i = 0; i < fieldHeight; i++) {
            for (int j = 0; j < fieldWidth; j++) {
                int aliveNeighboursCount = aliveNeighboursCount(i, j);
                if (previousCave.getCaveField()[i][j] == CellStatus.ALIVE && aliveNeighboursCount < deathLimit) {
                    nextCave.setCaveCell(i, j, CellStatus.DEAD);
                } else if (previousCave.getCaveField()[i][j] == CellStatus.DEAD && aliveNeighboursCount > birthLimit) {
                    nextCave.setCaveCell(i, j, CellStatus.ALIVE);
                } else {
                    nextCave.setCaveCell(i, j, previousCave.getCaveField()[i][j]);
                }
            }
        }
        rewriteField();
    }

    private void rewriteField() {
        for (int i = 0; i < fieldHeight; i++) {
            for (int j = 0; j < fieldWidth; j++) {
                previousCave.setCaveCell(i, j, nextCave.getCaveField()[i][j]);
            }
        }
    }

    private int aliveNeighboursCount(int row, int col) {
        int aliveCounter = 0;
        boolean top = row > 0;
        boolean bottom = row < fieldHeight - 1;
        boolean left = col > 0;
        boolean right = col < fieldWidth - 1;
        boolean topLeft = row > 0 && col > 0;
        boolean topRight = row > 0 && col < fieldWidth - 1;
        boolean bottomLeft = row < fieldHeight - 1 && col > 0;
        boolean bottomRight = row < fieldHeight - 1 && col < fieldWidth - 1;

        if (isAliveNeighbour(top, row - 1, col))
            aliveCounter++;
        if (isAliveNeighbour(bottom, row + 1, col))
            aliveCounter++;
        if (isAliveNeighbour(left, row, col - 1))
            aliveCounter++;
        if (isAliveNeighbour(right, row, col + 1))
            aliveCounter++;
        if (isAliveNeighbour(topLeft, row - 1, col - 1))
            aliveCounter++;
        if (isAliveNeighbour(topRight, row - 1, col + 1))
            aliveCounter++;
        if (isAliveNeighbour(bottomLeft, row + 1, col - 1))
            aliveCounter++;
        if (isAliveNeighbour(bottomRight, row + 1, col + 1))
            aliveCounter++;

        return aliveCounter;
    }

    private boolean isAliveNeighbour(boolean neighbourExists, int row, int col) {
        boolean alive = true;
        if (neighbourExists) {
            if (!isAlive(row, col)) {
                alive = false;
            }
        }

        return alive;
    }

    private boolean isAlive(int row, int col) {
        return previousCave.getCaveField()[row][col] == CellStatus.ALIVE;
    }
}
