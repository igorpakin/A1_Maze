package edu.school.maze.caves.operations;

import edu.school.maze.caves.entities.Cave;
import edu.school.maze.caves.entities.CellStatus;
import edu.school.maze.caves.exceptions.CaveWrongCharException;
import edu.school.maze.caves.exceptions.CaveWrongSizeException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class CaveReader {

    private int width = 0;
    private int height = 0;
    private CellStatus[][] caveField = null;

    public Cave getCave() {
        return new Cave(width, height, caveField);
    }

    public CaveReader(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            readFile(reader);
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }

    private void readFile(BufferedReader reader) {
        try {
            String currentLine = "";
            int lineCounter = -1;
            while ((currentLine = reader.readLine()) != null) {
                if (lineCounter == -1) {
                    initCaveField(currentLine);
                } else {
                    lineProcessing(currentLine, lineCounter);
                }
                lineCounter++;
            }
            sizeCheck(lineCounter, height);
        } catch (IOException e) {
            System.out.println("Error reading file");
        } finally {
            closeReader(reader);
        }
    }

    private void lineProcessing(String currentLine, int rowNum) {
        String[] splitedLine = currentLine.split(" ");
        sizeCheck(splitedLine.length, width);
        for (int i = 0; i < splitedLine.length; i++) {
            if (splitedLine[i].equals("1")) {
                caveField[rowNum][i] = CellStatus.ALIVE;
            } else if (splitedLine[i].equals("0")) {
                caveField[rowNum][i] = CellStatus.DEAD;
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

    private void initCaveField(String firstLine) {
        readingWidthHeight(firstLine);
        caveField = new CellStatus[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                caveField[i][j] = CellStatus.ALIVE;
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
