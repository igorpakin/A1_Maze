module edu.school.maze {
    requires javafx.controls;
    requires javafx.fxml;

    opens edu.school.maze to javafx.fxml;

    exports edu.school.maze;
}