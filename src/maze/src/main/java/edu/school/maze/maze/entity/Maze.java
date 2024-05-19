package edu.school.maze.maze.entity;

import java.util.ArrayList;
import java.util.List;

public class Maze {
    private int rows;
    private int cols;
    private List<List<Integer>> rightWallMatrix;
    private List<List<Integer>> bottomWallMatrix;

    public Maze() {
        this.rows = 0;
        this.cols = 0;
        this.rightWallMatrix = new ArrayList<>();
        this.bottomWallMatrix = new ArrayList<>();
    }

    public Maze(int cols, int rows) {
        this.rows = rows;
        this.cols = cols;
        this.rightWallMatrix = new ArrayList<>(rows);
        for (int i = 0; i < rows; i++) {
            this.rightWallMatrix.add(new ArrayList<>());
        }
        this.bottomWallMatrix = new ArrayList<>(rows);
        for (int i = 0; i < rows; i++) {
            this.bottomWallMatrix.add(new ArrayList<>());
        }
    }

    public void clear() {
        this.rows = 0;
        this.cols = 0;
        this.rightWallMatrix.clear();
        this.bottomWallMatrix.clear();
    }

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }

    public List<List<Integer>> getRightWallMatrix() {
        return rightWallMatrix;
    }

    public List<List<Integer>> getBottomWallMatrix() {
        return bottomWallMatrix;
    }

    public int getRightWall(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            throw new IllegalArgumentException("Invalid row or column index");
        }
        return rightWallMatrix.get(row).get(col);
    }

    public int getBottomWall(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            throw new IllegalArgumentException("Invalid row or column index");
        }
        return bottomWallMatrix.get(row).get(col);
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setRightWall(List<List<Integer>> rightWallMatrix) {
        this.rightWallMatrix = rightWallMatrix;
    }

    public void setBottomWall(List<List<Integer>> bottomWallMatrix) {
        this.bottomWallMatrix = bottomWallMatrix;
    }
}
