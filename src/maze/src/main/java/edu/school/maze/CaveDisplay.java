package edu.school.maze;

import edu.school.maze.caves.entities.Cave;
import edu.school.maze.caves.entities.CellStatus;
import edu.school.maze.caves.operations.CaveGeneration;
import edu.school.maze.caves.operations.CaveReader;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.File;

public class CaveDisplay extends Application {
    private GridPane grid;
    private CellStatus[][] cave;
    private Button nextStepButton;
    private CaveGeneration caveGeneration;
    private Button autoModeButton;

    private int speed;

    private boolean autoMode = false;

    private Stage stage = new Stage();

    @Override
    public void start(Stage stage) throws Exception {

    }

    public void show(int speed, File file) {
        grid = new GridPane();
        this.speed = speed;
        nextStepButton = new Button("Next Step");
        nextStepButton.setOnAction(event -> nextStep());
        autoModeButton = new Button("Auto Mode: Off");
        autoModeButton.setOnAction(event -> toggleAutoMode());

        HBox buttonBox = new HBox(10, nextStepButton, autoModeButton);
        CaveReader caveReader = new CaveReader(file);
        Cave readedCave = caveReader.getCave();
        cave = readedCave.getCaveField();
        caveGeneration = new CaveGeneration(readedCave, 4, 3, 1000L);
        updateGrid();

        Scene scene = new Scene(new HBox(grid, buttonBox), cave[0].length * 50 + 300, cave.length * 50);
        stage.setScene(scene);
        stage.show();

        if (autoMode) {
            autoModeButton.fire();
        }
    }

    private void updateGrid() {
        grid.getChildren().clear();
        for (int i = 0; i < caveGeneration.getPreviousCave().getCaveHeight(); i++) {
            for (int j = 0; j < caveGeneration.getPreviousCave().getCaveWidth(); j++) {
                Rectangle cell = new Rectangle(50, 50);
                cell.setStroke(Color.BLACK);
                cell.setFill(cave[i][j].equals(CellStatus.ALIVE) ? Color.BLACK : Color.WHITE);
                grid.add(cell, j, i);
            }
        }
    }

    public void nextStep() {
        caveGeneration.generateStep();
        cave = caveGeneration.getPreviousCave().getCaveField();
        updateGrid();
    }

    private void toggleAutoMode() {
        autoMode = !autoMode;
        autoModeButton.setText("Auto Mode: " + (autoMode ? "On" : "Off"));
        if (autoMode) {
            autoModeButton.setText("Auto Mode: On");
            new Thread(() -> {
                while (autoMode) {
                    try {
                        Thread.sleep(speed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Platform.runLater(() -> nextStepButton.fire());
                }
            }).start();
        } else {
            autoModeButton.setText("Auto Mode: Off");
        }
    }

}
