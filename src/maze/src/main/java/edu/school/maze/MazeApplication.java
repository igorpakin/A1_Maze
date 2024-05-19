package edu.school.maze;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/*!
    @mainpage
        @section section1 Реализация проекта Maze
            Необходимо реализовать программу Maze, позволяющую генерировать и отрисовывать идеальные лабиринты и пещеры.
        @section section2 Генерация идеального лабиринта
            Добавить возможность автоматической генерации идеального лабиринта. 
            Идеальным считается лабиринт, в котором из каждой точки можно попасть в любую другую точку ровно одним способом.
        @section section3 Решение лабиринта
            Добавить возможность показать решение любого лабиринта, который сейчас изображен на экране.
        @section section4 Дополнительно. Генерация пещер
            Добавить генерацию пещер с использованием клеточного автомата

*/
public class MazeApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MazeApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 860, 650);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}