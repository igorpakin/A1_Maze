package edu.school.maze.maze.operations;

import edu.school.maze.maze.entity.Maze;

import java.util.*;

public class PerfectMaze {
    private int x;
    private int y;
    private Maze perfectMaze;
    private List<Integer> sideLine;
    private int counter;
    private List<List<Integer>> vertWalls;
    private List<List<Integer>> horWalls;

    public PerfectMaze(Maze maze) {
        this.perfectMaze = maze;
        this.sideLine = new ArrayList<>();
        this.vertWalls = new ArrayList<>();
        this.horWalls = new ArrayList<>();
        initElementsDefault();
    }

    public void generate(int rows, int cols) {
        perfectMaze.clear();
        counter = 1;
        x = rows;
        y = cols;
        prepareVectorsToWork();
        initElementsDefault();
        initMazeWithoutLastLine();
        lastLine();
        setWalls();
    }

    private void prepareVectorsToWork() {
        sideLine.clear();
        vertWalls.clear();
        horWalls.clear();
        vertWalls = new ArrayList<>(x);
        for (int i = 0; i < x; i++) {
            vertWalls.add(new ArrayList<>(y));
            for (int j = 0; j < y; j++) {
                vertWalls.get(i).add(0);
            }
        }
        horWalls = new ArrayList<>(x);
        for (int i = 0; i < x; i++) {
            horWalls.add(new ArrayList<>(y));
            for (int j = 0; j < y; j++) {
                horWalls.get(i).add(0);
            }
        }
    }

    private void initElementsDefault() {
        for (int i = 0; i < y; i++) {
            sideLine.add(0);
        }
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                vertWalls.get(i).set(j, 0);
                horWalls.get(i).set(j, 0);
            }
        }
    }

    private void initMazeWithoutLastLine() {
        for (int i = 0; i < x - 1; i++) {
            initUniqueLine();
            buildVertWalls(i);
            buildHorWalls(i);
            checkHorWalls(i);
            newLine(i);
        }
    }


    private void initUniqueLine() {
        for (int i = 0; i < y; i++) {
            if (sideLine.get(i) == 0) {
                sideLine.set(i, counter);
                counter++;
            }
        }
    }

    private void buildVertWalls(int line) {
        for (int j = 0; j < y - 1; j++) {
            if (randomBool() || sideLine.get(j).equals(sideLine.get(j + 1))) {
                vertWalls.get(line).set(j, 1);
            } else {
                unionCells(j);
            }
        }
        vertWalls.get(line).set(y - 1, 1);
    }

    private void unionCells(int col) {
        for (int j = 0; j < y; j++) {
            if (sideLine.get(j).equals(sideLine.get(col + 1))) {
                sideLine.set(j, sideLine.get(col));
            }
        }
    }

    private void buildHorWalls(int line) {
        for (int j = 0; j < y; j++) {
            if (randomBool() && checkForUnique(sideLine.get(j)) != 1) {
                horWalls.get(line).set(j, 1);
            }
        }
    }

    private int checkForUnique(int elem) {
        int counterUniqCells = 0;
        for (int i = 0; i < y; i++) {
            if (sideLine.get(i).equals(elem)) {
                counterUniqCells++;
            }
        }
        return counterUniqCells;
    }

    private void checkHorWalls(int line) {
        for (int j = 0; j < y; j++) {
            if (countHorWalls(sideLine.get(j), line) == 0) {
                horWalls.get(line).set(j, 0);
            }
        }
    }

    private int countHorWalls(int elem, int line) {
        int countHorWalls = 0;
        for (int j = 0; j < y - 1; j++) {
            if (sideLine.get(j).equals(elem) && horWalls.get(line).get(j) == 0) {
                countHorWalls++;
            }
        }
        return countHorWalls;
    }

    private void newLine(int line) {
        for (int j = 0; j < y; j++) {
            if (horWalls.get(line).get(j) == 1) {
                sideLine.set(j, 0);
            }
        }
    }

    private void lastLine() {
        initUniqueLine();
        buildVertWalls(x - 1);
        checkLastLine();
    }

    private void checkLastLine() {
        for (int i = 0; i < y - 1; i++) {
            if (!sideLine.get(i).equals(sideLine.get(i + 1))) {
                vertWalls.get(x - 1).set(i, 0);
                unionCells(i);
            }
            horWalls.get(x - 1).set(i, 1);
        }
        horWalls.get(x - 1).set(y - 1, 1);
    }

    private boolean randomBool() {
        Random random = new Random();
        return random.nextBoolean();
    }

    private void setWalls() {
        perfectMaze.setRightWall(vertWalls);
        perfectMaze.setBottomWall(horWalls);
        perfectMaze.setRows(x);
        perfectMaze.setCols(y);
    }

    public Maze getMaze() {
        return perfectMaze;
    }
}
