import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SeatingSystemTest extends TestHelper {

    @Test
    void testGetOccupiedSeats() throws IOException {
        Path testFile = createTestFile("seating_system");
        Files.write(testFile, List.of(
                "L.LL.LL.LL",
                "LLLLLLL.LL",
                "L.L.L..L..",
                "LLLL.LL.LL",
                "L.LL.LL.LL",
                "L.LLLLL.LL",
                "..L.L.....",
                "LLLLLLLLLL",
                "L.LLLLLL.L",
                "L.LLLLL.LL"
        ));
        SeatingSystem seatingSystem = new SeatingSystem(testFile);
//        assertEquals(37, seatingSystem.occupiedSeats());
        assertEquals(37, seatingSystem.occupiedSeats(
                false,
                seatsOccupied -> seatsOccupied == 0,
                seatsOccupied -> seatsOccupied >= 4));
    }

    @Test
    void testGetOccupiedSeatsModifiedRules() throws IOException {
        Path testFile = createTestFile("seating_system");
        Files.write(testFile, List.of(
                "L.LL.LL.LL",
                "LLLLLLL.LL",
                "L.L.L..L..",
                "LLLL.LL.LL",
                "L.LL.LL.LL",
                "L.LLLLL.LL",
                "..L.L.....",
                "LLLLLLLLLL",
                "L.LLLLLL.L",
                "L.LLLLL.LL"
        ));
        SeatingSystem seatingSystem = new SeatingSystem(testFile);
//        assertEquals(26, seatingSystem.occupiedSeatsModifiedRules());
        assertEquals(26, seatingSystem.occupiedSeats(
                true,
                seatsOccupied -> seatsOccupied == 0,
                seatsOccupied -> seatsOccupied >= 5));
    }

    @Test
    void testDayElevenPart1() {
        SeatingSystem seatingSystem = new SeatingSystem("src\\main\\resources\\puzzle11_input.txt");
//        assertEquals(2299, seatingSystem.occupiedSeats());
        assertEquals(2299, seatingSystem.occupiedSeats(
                false,
                seatsOccupied -> seatsOccupied == 0,
                seatsOccupied -> seatsOccupied >= 4));
    }

    @Test
    void testDayElevenPart2() {
        SeatingSystem seatingSystem = new SeatingSystem("src\\main\\resources\\puzzle11_input.txt");
//        assertEquals(2047, seatingSystem.occupiedSeats());
        assertEquals(2047, seatingSystem.occupiedSeats(
                true,
                seatsOccupied -> seatsOccupied == 0,
                seatsOccupied -> seatsOccupied >= 5));
    }
}
