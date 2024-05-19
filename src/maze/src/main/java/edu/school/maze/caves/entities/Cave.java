package edu.school.maze.caves.entities;

import java.util.Arrays;
import java.util.Objects;

public class Cave {
    private int fieldWidth = 0;
    private int fieldHeight = 0;
    private CellStatus[][] field = null;

    public Cave(int fieldWidth, int fieldHeight, CellStatus[][] field) {
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        this.field = copyField(field, fieldWidth, fieldHeight);
    }

    public int getCaveWidth() {
        return fieldWidth;
    }

    public int getCaveHeight() {
        return fieldHeight;
    }

    public CellStatus[][] getCaveField() {
        return field;
    }

    public void setCaveCell(int row, int col, CellStatus status) {
        field[row][col] = status;
    }

    public Cave copyCave(Cave otherField) {
        fieldWidth = otherField.getCaveWidth();
        fieldHeight = otherField.getCaveHeight();
        CellStatus[][] newField = new CellStatus[fieldHeight][fieldWidth];
        for (int i = 0; i < fieldHeight; i++) {
            for (int j = 0; j < fieldWidth; j++) {
                newField[i][j] = otherField.getCaveField()[i][j];
            }
        }
        return new Cave(fieldWidth, fieldHeight, newField);
    }

    public CellStatus[][] copyField(CellStatus[][] otherField, int fieldWidth, int fieldHeight) {
        CellStatus[][] newField = new CellStatus[fieldHeight][fieldWidth];
        for (int i = 0; i < fieldHeight; i++) {
            for (int j = 0; j < fieldWidth; j++) {
                newField[i][j] = otherField[i][j];
            }
        }
        return newField;
    }

    @Override
    public boolean equals(Object otherCave) {
        if (this == otherCave) {
            return true;
        }
        if (otherCave == null || getClass() != otherCave.getClass()) {
            return false;
        }

        Cave other = (Cave) otherCave;
        return fieldWidth == other.fieldWidth &&
                fieldHeight == other.fieldHeight &&
                Arrays.deepEquals(field, other.field);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fieldWidth, fieldHeight, Arrays.hashCode(field));
    }

}
