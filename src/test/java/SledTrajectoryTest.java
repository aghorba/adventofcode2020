import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SledTrajectoryTest extends TestHelper {

    @Test
    void testSledMapTreeCount() throws IOException {
        Path testFile = createTestFile("sled_trajectory");
        Files.write(testFile, List.of(
                "..##.......",
                "#...#...#..",
                ".#....#..#.",
                "..#.#...#.#",
                ".#...##..#.",
                "..#.##.....",
                ".#.#.#....#",
                ".#........#",
                "#.##...#...",
                "#...##....#",
                ".#..#...#.#"
        ));

        SledTrajectory sledMap = new SledTrajectory(testFile);
        assertEquals(7, sledMap.treesEncountered(3, 1));
    }

    @Test
    void testMultipleSlopeTreeCount() throws IOException {
        Path testFile = createTestFile("sled_trajectory");
        Files.write(testFile, List.of(
                "..##.......",
                "#...#...#..",
                ".#....#..#.",
                "..#.#...#.#",
                ".#...##..#.",
                "..#.##.....",
                ".#.#.#....#",
                ".#........#",
                "#.##...#...",
                "#...##....#",
                ".#..#...#.#"
        ));

        SledTrajectory sledMap = new SledTrajectory(testFile);
        int[][] trajectories = new int[][] {
                {1, 1},
                {3, 1},
                {5, 1},
                {7, 1},
                {1, 2},
        };

        assertEquals(336, sledMap.runMultipleTrajectories(trajectories));
    }

    @Test
    void testDayThreePart1() {
        SledTrajectory sledMap = new SledTrajectory("src\\main\\resources\\puzzle3_input.txt");
        assertEquals(292, sledMap.treesEncountered(3, 1));
    }

    @Test
    void testDayThreePart2() {
        SledTrajectory sledMap = new SledTrajectory("src\\main\\resources\\puzzle3_input.txt");
        int[][] trajectories = new int[][] {
                {1, 1},
                {3, 1},
                {5, 1},
                {7, 1},
                {1, 2},
        };
        assertEquals(9354744432L, sledMap.runMultipleTrajectories(trajectories));
    }

}
