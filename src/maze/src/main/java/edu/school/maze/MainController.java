package edu.school.maze;

import edu.school.maze.maze.MazeReader;
import edu.school.maze.maze.entity.Maze;
import edu.school.maze.maze.operations.Path;
import edu.school.maze.maze.operations.Point;
import edu.school.maze.maze.operations.PerfectMaze;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class MainController {

    @FXML
    private Button mazeFromFile;

    @FXML
    private Button perfectMaze;

    @FXML
    private Button findPath;

    @FXML
    private Button loadMaze;

    @FXML
    private Group mazeView;

    @FXML
    private TextField fromX;

    @FXML
    private TextField fromY;

    @FXML
    private TextField toX;

    @FXML
    private TextField toY;

    @FXML
    private TextField speed;

    @FXML
    private TextField mazeCols;

    @FXML
    private TextField mazeRows;

    private final int WALL_THICKNESS = 2;

    private double CELL_SIZE;

    private final FileChooser fileChooser = new FileChooser();

    private final CaveDisplay caveDisplay = new CaveDisplay();

    private final MazeReader mazeReader = new MazeReader();

    private Maze maze = new Maze();

    @FXML
    void onMazeFromFileButtonClick(ActionEvent event) throws Exception {
        if (!checkSpeed()) {
            showErrorAlert("Некорректная скорость");
        } else
            caveDisplay.show(Integer.parseInt(speed.getText()) * 1000, getFile());
    }

    @FXML
    void onLoadMazeButtonClick(ActionEvent event) throws Exception {
        maze = mazeReader.getMazeFromFile(getFile());
        drawMaze(maze);
    }

    @FXML
    void onPerfectMazeButtonClick(ActionEvent event) {
        if (!checkMaze()) {
            showErrorAlert("Некорректный размер лабиринта");
        } else {
            PerfectMaze perfectMaze = new PerfectMaze(maze);
            perfectMaze.generate(Integer.parseInt(mazeRows.getText()), Integer.parseInt(mazeCols.getText()));
            maze = perfectMaze.getMaze();
            drawMaze(maze);
        }
    }

    @FXML
    void onFindPathButtonClick(ActionEvent event) {
        if (!checkPath()) {
            showErrorAlert("Некорректные координаты");
        } else {
            Path pathFinder = new Path(maze);
            List<Point> solutionPath = pathFinder.findPath(
                    new Point(Integer.parseInt(fromX.getText()) - 1, Integer.parseInt(fromY.getText()) - 1),
                    new Point(Integer.parseInt(toX.getText()) - 1, Integer.parseInt(toY.getText()) - 1));
            drawPath(solutionPath);
        }
    }

    private void drawMaze(Maze maze) {
        mazeView.getChildren().clear();
        CELL_SIZE = 500.0 / Math.max(maze.getRows(), maze.getCols());
        for (int i = 0; i < maze.getRows(); i++) {
            Rectangle leftWall = new Rectangle(0, i * CELL_SIZE, WALL_THICKNESS, CELL_SIZE);
            leftWall.setFill(Color.BLACK);
            mazeView.getChildren().add(leftWall);

            if (i == 0) {
                Rectangle topWall = new Rectangle(0, 0, maze.getCols() * CELL_SIZE, WALL_THICKNESS);
                topWall.setFill(Color.BLACK);
                mazeView.getChildren().add(topWall);
            }

            for (int j = 0; j < maze.getCols(); j++) {
                if (j < maze.getCols() - 1 && maze.getRightWallMatrix().get(i).get(j) == 1) {
                    Rectangle wall = new Rectangle((j + 1) * CELL_SIZE - WALL_THICKNESS, i * CELL_SIZE, WALL_THICKNESS,
                            CELL_SIZE);
                    wall.setFill(Color.BLACK);
                    mazeView.getChildren().add(wall);
                }

                if (i < maze.getRows() - 1 && maze.getBottomWallMatrix().get(i).get(j) == 1) {
                    Rectangle wall = new Rectangle(j * CELL_SIZE, (i + 1) * CELL_SIZE - WALL_THICKNESS, CELL_SIZE,
                            WALL_THICKNESS);
                    wall.setFill(Color.BLACK);
                    mazeView.getChildren().add(wall);
                }
            }

            Rectangle rightWall = new Rectangle(maze.getCols() * CELL_SIZE - WALL_THICKNESS, i * CELL_SIZE,
                    WALL_THICKNESS, CELL_SIZE);
            rightWall.setFill(Color.BLACK);
            mazeView.getChildren().add(rightWall);

            if (i == maze.getRows() - 1) {
                Rectangle bottomWall = new Rectangle(0, maze.getRows() * CELL_SIZE - WALL_THICKNESS,
                        maze.getCols() * CELL_SIZE, WALL_THICKNESS);
                bottomWall.setFill(Color.BLACK);
                mazeView.getChildren().add(bottomWall);
            }
        }
    }

    private File getFile() {
        fileChooser.setTitle("Выберите файл");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Текстовые файлы (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        return selectedFile;
    }

    private void drawPath(List<Point> solutionPath) {
        for (int i = 0; i < solutionPath.size() - 1; i++) {
            Point current = solutionPath.get(i);
            Point next = solutionPath.get(i + 1);

            double startX = current.getY() * CELL_SIZE + CELL_SIZE / 2;
            double startY = current.getX() * CELL_SIZE + CELL_SIZE / 2;
            double endX = next.getY() * CELL_SIZE + CELL_SIZE / 2;
            double endY = next.getX() * CELL_SIZE + CELL_SIZE / 2;

            if (current.getY() == next.getX()) {
                if (endY > startY) {
                    startY += WALL_THICKNESS / 2;
                    endY -= WALL_THICKNESS / 2;
                } else {
                    startY -= WALL_THICKNESS / 2;
                    endY += WALL_THICKNESS / 2;
                }
            } else {
                if (endX > startX) {
                    startX += WALL_THICKNESS / 2;
                    endX -= WALL_THICKNESS / 2;
                } else {
                    startX -= WALL_THICKNESS / 2;
                    endX += WALL_THICKNESS / 2;
                }
            }

            Line line = new Line(startX, startY, endX, endY);
            line.setStroke(Color.RED);
            line.setStrokeWidth(2);
            mazeView.getChildren().add(line);
        }
    }

    private boolean checkMaze() {
        try {
            int mazeColsInt = Integer.parseInt(mazeCols.getText());
            int mazeRowsInt = Integer.parseInt(mazeRows.getText());
            if (mazeColsInt < 1 || mazeColsInt > 50 || mazeRowsInt < 1 || mazeRowsInt > 50) {
                return false;
            } else
                return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    private boolean checkSpeed() {
        try {
            int speedInt = Integer.parseInt(speed.getText());
            if (speedInt < 1) {
                return false;
            } else
                return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    private boolean checkPath() {
        try {
            int fromXInt = Integer.parseInt(fromX.getText());
            int fromYInt = Integer.parseInt(fromY.getText());
            int toXInt = Integer.parseInt(toX.getText());
            int toYInt = Integer.parseInt(toY.getText());
            if (fromXInt > maze.getRows() || fromYInt > maze.getCols() || toXInt > maze.getRows()
                    || toYInt > maze.getCols()) {
                return false;
            } else
                return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}