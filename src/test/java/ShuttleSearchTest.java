import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShuttleSearchTest extends TestHelper {

    @Test
    void testShuttleSearch() throws IOException {
        Path testFile = createTestFile("shuttle_search");
        Files.write(testFile, List.of(
                "939",
                "7,13,x,x,59,x,31,19"
        ));
        ShuttleSearch shuttleSearch = new ShuttleSearch(testFile);
        assertEquals(295, shuttleSearch.busID());
    }

    @Test
    void testDayThirteenPart1() {
        ShuttleSearch shuttleSearch = new ShuttleSearch("src\\main\\resources\\puzzle13_input.txt");
        assertEquals(207, shuttleSearch.busID());
    }

    @Test
    void testEarliestTimestamp() throws IOException {
        Path testFile = createTestFile("shuttle_search");
        Files.write(testFile, List.of(
                "939",
                "7,13,x,x,59,x,31,19"
        ));
        ShuttleSearch shuttleSearch = new ShuttleSearch(testFile);
        assertEquals(1068781, shuttleSearch.earliestTimestamp());
    }

    @Test
    void testEarliestTimestamp2() throws IOException {
        Path testFile = createTestFile("shuttle_search");
        Files.write(testFile, List.of(
                "939",
                "17,x,13,19"
        ));
        ShuttleSearch shuttleSearch = new ShuttleSearch(testFile);
        assertEquals(3417, shuttleSearch.earliestTimestamp());
    }

    @Test
    void testEarliestTimestamp3() throws IOException {
        Path testFile = createTestFile("shuttle_search");
        Files.write(testFile, List.of(
                "939",
                "67,7,59,61"
        ));
        ShuttleSearch shuttleSearch = new ShuttleSearch(testFile);
        assertEquals(754018, shuttleSearch.earliestTimestamp());
    }

    @Test
    void testEarliestTimestamp4() throws IOException {
        Path testFile = createTestFile("shuttle_search");
        Files.write(testFile, List.of(
                "939",
                "67,x,7,59,61"
        ));
        ShuttleSearch shuttleSearch = new ShuttleSearch(testFile);
        assertEquals(779210, shuttleSearch.earliestTimestamp());
    }

    @Test
    void testEarliestTimestamp5() throws IOException {
        Path testFile = createTestFile("shuttle_search");
        Files.write(testFile, List.of(
                "939",
                "67,7,x,59,61"
        ));
        ShuttleSearch shuttleSearch = new ShuttleSearch(testFile);
        assertEquals(1261476, shuttleSearch.earliestTimestamp());
    }

    @Test
    void testEarliestTimestamp6() throws IOException {
        Path testFile = createTestFile("shuttle_search");
        Files.write(testFile, List.of(
                "939",
                "1789,37,47,1889"
        ));
        ShuttleSearch shuttleSearch = new ShuttleSearch(testFile);
        assertEquals(1202161486, shuttleSearch.earliestTimestamp());
    }

    @Test
    void testDayThirteenPart2() {
        ShuttleSearch shuttleSearch = new ShuttleSearch("src\\main\\resources\\puzzle13_input.txt");
        assertEquals(530015546283687L, shuttleSearch.earliestTimestamp());
    }

}
