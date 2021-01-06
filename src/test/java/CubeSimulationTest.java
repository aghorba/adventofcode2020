import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CubeSimulationTest extends TestHelper {

//    @Test
//    void testActiveCubes() throws IOException {
//        Path testFile = createTestFile("cube_simulation");
//        Files.write(testFile, List.of(
//                "..#..",
//                "...#.",
//                ".###.",
//                ".....",
//                "....."
//        ));
//
//        CubeSimulation simulation = new CubeSimulation(testFile);
//        assertEquals(112L, simulation.activeCubes(6));
//    }

    @Test
    void testDaySeventeenPart1() {
        CubeSimulation simulation = new CubeSimulation("src\\main\\resources\\puzzle17_input.txt");
        assertEquals(395, simulation.activeCubes(6));
    }

    @Test
    //TODO: DOES NOT WORK
    void testDaySeventeenPart2() {
        CubeSimulation simulation = new CubeSimulation("src\\main\\resources\\puzzle17_input.txt");
        assertEquals(2296, simulation.activeCubesWithW(6));
    }
}
