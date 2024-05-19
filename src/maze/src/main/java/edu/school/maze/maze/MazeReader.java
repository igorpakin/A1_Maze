package edu.school.maze.maze;

import edu.school.maze.caves.exceptions.CaveWrongCharException;
import edu.school.maze.caves.exceptions.CaveWrongSizeException;
import edu.school.maze.maze.entity.Maze;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MazeReader {

    private int width = 0;
    private int height = 0;
    private int[][] rightWallMatrix;
    private int[][] bottomWallMatrix;

    public Maze getMazeFromFile(File file) {
        openFile(file);
        List<List<Integer>> rightWallList = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            List<Integer> innerList = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                innerList.add(rightWallMatrix[i][j]);
            }
            rightWallList.add(innerList);
        }

        List<List<Integer>> bottomWallList = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            List<Integer> innerList = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                innerList.add(bottomWallMatrix[i][j]);
            }
            bottomWallList.add(innerList);
        }

        Maze mazeFromFile = new Maze(height, width);
        mazeFromFile.setRightWall(rightWallList);
        mazeFromFile.setBottomWall(bottomWallList);

        return mazeFromFile;
    }

    private void openFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            readFile(reader);
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }

    private void readFile(BufferedReader reader) {
        try {
            rightWallMatrix = null;
            bottomWallMatrix = null;
            String currentLine = "";
            int rightWallCounter = -1;
            int bottomWallCounter = 0;
            boolean rightWallFlag = true;
            while ((currentLine = reader.readLine()) != null) {
                if (rightWallFlag && currentLine.length() == 0) {
                    rightWallFlag = false;
                    continue;
                }

                if (rightWallCounter == -1) {
                    initMazeArrays(currentLine);
                    rightWallCounter++;
                } else if (rightWallFlag) {
                    lineProcessingRightWall(currentLine, rightWallCounter);
                    rightWallCounter++;
                } else {
                    lineProcessingBottomWall(currentLine, bottomWallCounter);
                    bottomWallCounter++;
                }
            }
            sizeCheck(rightWallCounter, height);
            sizeCheck(bottomWallCounter, height);
        } catch (IOException e) {
            System.out.println("Error reading file");
        } finally {
            closeReader(reader);
        }
    }

    private void lineProcessingRightWall(String currentLine, int rowNum) {
        String[] splitedLine = currentLine.split(" ");
        sizeCheck(splitedLine.length, width);
        for (int i = 0; i < splitedLine.length; i++) {
            if (splitedLine[i].equals("1")) {
                rightWallMatrix[rowNum][i] = 1;
            } else if (splitedLine[i].equals("0")) {
                rightWallMatrix[rowNum][i] = 0;
            } else {
                throw new CaveWrongCharException("Wrong char found!");
            }
        }
    }

    private void lineProcessingBottomWall(String currentLine, int rowNum) {
        String[] splitedLine = currentLine.split(" ");
        sizeCheck(splitedLine.length, width);
        for (int i = 0; i < splitedLine.length; i++) {
            if (splitedLine[i].equals("1")) {
                bottomWallMatrix[rowNum][i] = 1;
            } else if (splitedLine[i].equals("0")) {
                bottomWallMatrix[rowNum][i] = 0;
            } else {
                throw new CaveWrongCharException("Wrong char found!");
            }
        }
    }

    private void sizeCheck(int arrSize, int firstLineSize) {
        if (arrSize != firstLineSize) {
            throw new CaveWrongSizeException("Wrong size found!");
        }
    }

    private void initMazeArrays(String firstLine) {
        readingWidthHeight(firstLine);
        rightWallMatrix = new int[height][width];
        bottomWallMatrix = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                rightWallMatrix[i][j] = 0;
                bottomWallMatrix[i][j] = 0;
            }
        }
    }

    private void readingWidthHeight(String currentLine) {
        String[] firstLineSplited = currentLine.split(" ");
        if (firstLineSplited.length == 2) {
            width = Integer.parseInt(firstLineSplited[0]);
            height = Integer.parseInt(firstLineSplited[1]);
        } else {
            throw new CaveWrongSizeException("Wrong size found!");
        }
    }

    private void closeReader(BufferedReader reader) {
        try {
            reader.close();
        } catch (Exception e) {
            System.out.println("Error closing file");
        }
    }
}

