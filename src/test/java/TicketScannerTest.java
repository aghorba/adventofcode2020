import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TicketScannerTest extends TestHelper {

    @Test
    void testTicketScanErrorRate() throws IOException {
        Path testFile = createTestFile("ticket_scanner");
        Files.write(testFile, List.of(
                "class: 1-3 or 5-7",
                "row: 6-11 or 33-44",
                "seat: 13-40 or 45-50",
                "",
                "your ticket:",
                "7,1,14",
                "",
                "nearby tickets:",
                "7,3,47",
                "40,4,50",
                "55,2,20",
                "38,6,12"
        ));

        TicketScanner scanner = new TicketScanner(testFile);
        assertEquals(71, scanner.ticketScanErrorRate());
    }

    @Test
    void testDaySixteenPart1() {
        TicketScanner scanner = new TicketScanner("src\\main\\resources\\puzzle16_input.txt");
        assertEquals(29759, scanner.ticketScanErrorRate());
    }

    @Test
    void testOwnTicketMultiply() throws IOException {
        Path testFile = createTestFile("ticket_scanner");
        Files.write(testFile, List.of(
                "class: 0-1 or 4-19",
                "row: 0-5 or 8-19",
                "seat: 0-13 or 16-19",
                "",
                "your ticket:",
                "11,12,13",
                "",
                "nearby tickets:",
                "3,9,18",
                "15,1,5",
                "5,14,9"
        ));

        TicketScanner scanner = new TicketScanner(testFile);
        assertEquals(1716, scanner.selfTicketValue(FieldFilter.NONE));
    }

    @Test
    void testDaySixteenPart2() {
        TicketScanner scanner = new TicketScanner("src\\main\\resources\\puzzle16_input.txt");
        assertEquals(1307550234719L, scanner.selfTicketValue(FieldFilter.DEPARTURE));
    }

}
