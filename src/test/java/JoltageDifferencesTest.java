import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JoltageDifferencesTest extends TestHelper {

    @Test
    void testJoltageDifferences() throws IOException {
        Path testFile = createTestFile("joltage_differences");
        Files.write(testFile, List.of(
                "16",
                "10",
                "15",
                "5",
                "1",
                "11",
                "7",
                "19",
                "6",
                "12",
                "4"
        ));

        JoltageDifferences differences = new JoltageDifferences(testFile);
        assertEquals(35, differences.findJoltageDifference());
    }

    @Test
    void testJoltageDifferences2() throws IOException {
        Path testFile = createTestFile("joltage_differences");
        Files.write(testFile, List.of(
                "28",
                "33",
                "18",
                "42",
                "31",
                "14",
                "46",
                "20",
                "48",
                "47",
                "24",
                "23",
                "49",
                "45",
                "19",
                "38",
                "39",
                "11",
                "1",
                "32",
                "25",
                "35",
                "8",
                "17",
                "7",
                "9",
                "4",
                "2",
                "34",
                "10",
                "3"
        ));

        JoltageDifferences differences = new JoltageDifferences(testFile);
        assertEquals(220, differences.findJoltageDifference());
    }

    @Test
    void testJoltageArrangements1() throws IOException {
        Path testFile = createTestFile("joltage_differences");
        Files.write(testFile, List.of(
                "16",
                "10",
                "15",
                "5",
                "1",
                "11",
                "7",
                "19",
                "6",
                "12",
                "4"
        ));

        JoltageDifferences differences = new JoltageDifferences(testFile);
        assertEquals(8, differences.distinctAdapters());
    }

    @Test
    void testJoltageArrangements2() throws IOException {
        Path testFile = createTestFile("joltage_differences");
        Files.write(testFile, List.of(
                "28",
                "33",
                "18",
                "42",
                "31",
                "14",
                "46",
                "20",
                "48",
                "47",
                "24",
                "23",
                "49",
                "45",
                "19",
                "38",
                "39",
                "11",
                "1",
                "32",
                "25",
                "35",
                "8",
                "17",
                "7",
                "9",
                "4",
                "2",
                "34",
                "10",
                "3"
        ));

        JoltageDifferences differences = new JoltageDifferences(testFile);
        assertEquals(19208, differences.distinctAdapters());
    }

    @Test
    void testDayTenPart1() {
        JoltageDifferences differences = new JoltageDifferences("src\\main\\resources\\puzzle10_input.txt");
        assertEquals(1820, differences.findJoltageDifference());
    }

    @Test
    void testDayTenPart2() {
        JoltageDifferences differences = new JoltageDifferences("src\\main\\resources\\puzzle10_input.txt");
        assertEquals(3454189699072L, differences.distinctAdapters());
    }
}
