package edu.school.maze;

import java.io.File;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.ValueSource;

import edu.school.maze.maze.operations.Path;
import edu.school.maze.maze.operations.PerfectMaze;
import edu.school.maze.maze.operations.Point;
import edu.school.maze.caves.entities.Cave;
import edu.school.maze.caves.entities.CellStatus;
import edu.school.maze.caves.operations.CaveGeneration;
import edu.school.maze.caves.operations.CaveReader;
import edu.school.maze.maze.entity.Maze;
import edu.school.maze.maze.MazeReader;

@DisplayName("MazeTest")
public class MazeAndCaveTests {

        @ParameterizedTest(name = "{index} - correct open file {0}")
        @ValueSource(strings = {
                        "/home/ipakin/A1_Maze_Java-1/src/maze/src/test/java/edu/school/maze/maze_examples/maze_example_1.txt",
                        "/home/ipakin/A1_Maze_Java-1/src/maze/src/test/java/edu/school/maze/maze_examples/maze_example_2.txt",
                        "/home/ipakin/A1_Maze_Java-1/src/maze/src/test/java/edu/school/maze/maze_examples/maze_example_3.txt" })
        void correctOpenFileMaze(String input) {
                File file = new File(input);
                Assertions.assertDoesNotThrow(() -> new MazeReader().getMazeFromFile(file));
        }

        @Test
        void testGeneration() {
                Maze maze = new Maze();
                PerfectMaze perfectMaze = new PerfectMaze(maze);
                Assertions.assertDoesNotThrow(() -> perfectMaze.generate(10, 10));
                Assertions.assertDoesNotThrow(() -> perfectMaze.getMaze());
                Assertions.assertEquals(10, maze.getRows());
                Assertions.assertEquals(10, maze.getCols());
        }

        @Test
        void testPointPath() {
                Maze maze = new Maze();
                PerfectMaze perfectMaze = new PerfectMaze(maze);
                perfectMaze.generate(10, 10);
                perfectMaze.getMaze();

                Point point1 = new Point(1, 2);
                Point point2 = new Point(4, 5);
                Path path = new Path(maze);
                Assertions.assertDoesNotThrow(() -> path.findPath(point1, point2));
                Assertions.assertEquals(point2.getX(), 4);
                Assertions.assertEquals(point2.getY(), 5);
                Assertions.assertEquals(point2.toString(), "Point{x=4, y=5}");

        }

        @ParameterizedTest(name = "{index} - correct open file {0}")
        @ValueSource(strings = {
                        "/home/ipakin/A1_Maze_Java-1/src/maze/src/test/java/edu/school/maze/cave_examples/example_1.txt",
                        "/home/ipakin/A1_Maze_Java-1/src/maze/src/test/java/edu/school/maze/cave_examples/example_2.txt",
                        "/home/ipakin/A1_Maze_Java-1/src/maze/src/test/java/edu/school/maze/cave_examples/example_3.txt" })
        void correctOpenFileCave(String input) {
                File file = new File(input);
                Assertions.assertDoesNotThrow(() -> new CaveReader(file));
        }

        @ParameterizedTest(name = "{index} - incorrect open file {0}")
        @ValueSource(strings = {
                        "/home/ipakin/A1_Maze_Java-1/src/maze/src/test/java/edu/school/maze/cave_examples/incorrect_example_1.txt",
                        "/home/ipakin/A1_Maze_Java-1/src/maze/src/test/java/edu/school/maze/cave_examples/incorrect_example_2.txt",
                        "/home/ipakin/A1_Maze_Java-1/src/maze/src/test/java/edu/school/maze/cave_examples/incorrect_example_3.txt",
                        "/home/ipakin/A1_Maze_Java-1/src/maze/src/test/java/edu/school/maze/cave_examples/incorrect_example_4.txt",
                        "/home/ipakin/A1_Maze_Java-1/src/maze/src/test/java/edu/school/maze/cave_examples/incorrect_example_5.txt"
        })
        void incorrectOpenFileCave(String input) {
                File file = new File(input);
                Assertions.assertThrows(Exception.class, () -> new CaveReader(file));
        }

        @Test
        void testGetCave() {
                int width = 4;
                int height = 4;
                CellStatus[][] field = new CellStatus[width][height];
                field[0][0] = CellStatus.DEAD;
                field[0][1] = CellStatus.ALIVE;
                field[0][2] = CellStatus.DEAD;
                field[0][3] = CellStatus.ALIVE;

                field[1][0] = CellStatus.ALIVE;
                field[1][1] = CellStatus.DEAD;
                field[1][2] = CellStatus.DEAD;
                field[1][3] = CellStatus.ALIVE;

                field[2][0] = CellStatus.DEAD;
                field[2][1] = CellStatus.ALIVE;
                field[2][2] = CellStatus.DEAD;
                field[2][3] = CellStatus.DEAD;

                field[3][0] = CellStatus.DEAD;
                field[3][1] = CellStatus.DEAD;
                field[3][2] = CellStatus.ALIVE;
                field[3][3] = CellStatus.ALIVE;
                Cave correctCave = new Cave(width, height, field);
                File file = new File(
                                "/home/ipakin/A1_Maze_Java-1/src/maze/src/test/java/edu/school/maze/cave_examples/simple_example.txt");
                Assertions.assertNotEquals(null, correctCave);
                Assertions.assertNotEquals(Integer.class, correctCave);
                Cave correctCave2 = new Cave(width, height, field).copyCave(correctCave);
                Assertions.assertEquals(correctCave, correctCave2);
                Cave correctCave3 = correctCave;
                Assertions.assertEquals(correctCave3.hashCode(),
                                correctCave.hashCode());
        }

        @ParameterizedTest(name = "{index} - correct step gen {0}")
        @ValueSource(strings = {
                        "/home/ipakin/A1_Maze_Java-1/src/maze/src/test/java/edu/school/maze/cave_examples/example_1.txt",
                        "/home/ipakin/A1_Maze_Java-1/src/maze/src/test/java/edu/school/maze/cave_examples/example_2.txt",
                        "/home/ipakin/A1_Maze_Java-1/src/maze/src/test/java/edu/school/maze/cave_examples/example_3.txt" })
        void testGeneration(String input) {
                File file = new File(input);
                CaveReader caveReader = new CaveReader(file);
                CaveGeneration caveGeneration = new CaveGeneration(caveReader.getCave(), 3, 4, 500L);
                Assertions.assertDoesNotThrow(() -> caveGeneration.generateStep());
        }

        @ParameterizedTest(name = "{index} - incorrect gen {0}")
        @ValueSource(strings = {
                        "/home/ipakin/A1_Maze_Java-1/src/maze/src/test/java/edu/school/maze/cave_examples/example_1.txt"
        })
        void testIncorrectGeneration(String input) {
                File file = new File(input);
                CaveReader caveReader = new CaveReader(file);
                Assertions.assertThrows(Exception.class, () -> new CaveGeneration(caveReader.getCave(), 9,
                                4, 5L));
                Assertions.assertThrows(Exception.class, () -> new CaveGeneration(caveReader.getCave(), -1,
                                4, 5L));
                Assertions.assertThrows(Exception.class, () -> new CaveGeneration(caveReader.getCave(), 4,
                                9, 5L));
                Assertions.assertThrows(Exception.class, () -> new CaveGeneration(caveReader.getCave(), 9,
                                -1, 5L));
        }

}
