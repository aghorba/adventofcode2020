import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShipNavigationTest extends TestHelper {

    @Test
    void testGetManhattanDistance() throws IOException {
        Path testFile = createTestFile("ship_navigation");
        Files.write(testFile, List.of(
                "F10",
                "N3",
                "F7",
                "R90",
                "F11"
        ));

        ShipNavigation navigator = new ShipNavigation(testFile);
        assertEquals(25, navigator.manhattanDistance());
    }

    @Test
    void testDayTwelvePart1() {
        ShipNavigation  navigator = new ShipNavigation("src\\main\\resources\\puzzle12_input.txt");
        assertEquals(998, navigator.manhattanDistance());
    }

    @Test
    void testWaypointLocation() throws IOException {
        Path testFile = createTestFile("ship_navigation");
        Files.write(testFile, List.of(
                "F10",
                "N3",
                "F7",
                "R90",
                "F11"
        ));

        ShipNavigation navigator = new ShipNavigation(testFile);
        assertEquals(286, navigator.manhattanDistanceWithWaypoint());
    }

    @Test
    void testDayTwelvePart2() {
        ShipNavigation  navigator = new ShipNavigation("src\\main\\resources\\puzzle12_input.txt");
        assertEquals(71586, navigator.manhattanDistanceWithWaypoint());
    }
}
