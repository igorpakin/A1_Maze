package edu.school.maze.maze.operations;

import edu.school.maze.maze.entity.Maze;

import java.util.ArrayList;
import java.util.List;

class WallPoint {
    int i, j;
    boolean wall;

    public WallPoint(int i, int j, boolean wall) {
        this.i = i;
        this.j = j;
        this.wall = wall;
    }
}

public class Path {
    private Point from, to;
    private List<Point> wave, prev_wave;
    private List<List<Integer>> length_vector;
    private int step;
    private int rows, cols;
    private Maze maze;

    public Path(Maze maze) {
        this.maze = maze;
        this.wave = new ArrayList<>();
        this.prev_wave = new ArrayList<>();
        this.length_vector = new ArrayList<>();
        this.step = 0;
    }

    public List<Point> findPath(Point from, Point to) {
        this.from = from;
        this.to = to;
        this.rows = maze.getRows();
        this.cols = maze.getCols();
        inizialization();
        this.prev_wave.add(from);
        while (!prev_wave.isEmpty()) {
            if (newWave())
                break;
        }
        return makeWay();
    }

    private void inizialization() {
        clear();
        this.step = 0;
        for (int i = 0; i < rows; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < cols; j++) {
                row.add(-1);
            }
            length_vector.add(row);
        }
        length_vector.get(from.x).set(from.y, step);
    }

    private boolean noWall(int row, int col, boolean right) {
        if (!rowValid(row) || !colValid(col)) return false;
        if (right) {
            if (maze.getRightWall(row, col) == 0) {
                return true;
            }
        } else {
            if (maze.getBottomWall(row, col) == 0) {
                return true;
            }
        }
        return false;
    }

    private boolean newWave() {
        step++;
        for (Point point : prev_wave) {
            for (WallPoint wallPoint : new WallPoint[]{
                    new WallPoint(point.x + 1, point.y, noWall(point.x, point.y, false)),
                    new WallPoint(point.x - 1, point.y, noWall(point.x - 1, point.y, false)),
                    new WallPoint(point.x, point.y + 1, noWall(point.x, point.y, true)),
                    new WallPoint(point.x, point.y - 1, noWall(point.x, point.y - 1, true))}) {
                if (wallPoint.wall) {
                    int i = wallPoint.i;
                    int j = wallPoint.j;
                    if (safeAccessVector(i, j) == -1) {
                        wave.add(new Point(i, j));
                        length_vector.get(i).set(j, step);
                    }
                    if (i == to.x && j == to.y) return true;
                }
            }
        }
        prev_wave = new ArrayList<>(wave);
        wave.clear();
        return false;
    }
    private boolean rowValid(int row) {
        return row >= 0 && row < rows;
    }


    private boolean colValid(int col) {
        return col >= 0 && col < cols;
    }

    private List<Point> makeWay() {
        List<Point> way = new ArrayList<>();
        int row = to.x;
        int col = to.y;
        while (length_vector.get(row).get(col) != 0) {
            if (safeAccessVector(row, col - 1) + 1 == safeAccessVector(row, col) &&
                    maze.getRightWall(row, col - 1) == 0) {
                col -= 1;
            } else if (safeAccessVector(row, col + 1) + 1 == safeAccessVector(row, col) &&
                    maze.getRightWall(row, col)== 0) {
                col += 1;
            } else if (safeAccessVector(row - 1, col) + 1 == safeAccessVector(row, col) &&
                    maze.getBottomWall(row - 1, col) == 0) {
                row -= 1;
            } else if (safeAccessVector(row + 1, col) + 1 == safeAccessVector(row, col) &&
                    maze.getBottomWall(row, col) == 0) {
                row += 1;
            } else {
                return new ArrayList<>();
            }
            way.add(new Point(row, col));
        }
        way.add(to);
        moveLastToFront(way);
        return way;
    }

    private int safeAccessVector(int i, int j) {
        if (i < 0 || i >= rows || j < 0 || j >= cols) {
            return -3;
        }
        return length_vector.get(i).get(j);
    }

    private void clear() {
        wave.clear();
        prev_wave.clear();
        length_vector.clear();
    }

    private void moveLastToFront(List<Point> list) {
        if (!list.isEmpty()) {
            Point last = list.remove(list.size() - 1);
            list.add(0, last);
        }
    }
}
