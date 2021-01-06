import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpenseReportTest extends TestHelper {

    @Test
    void testSumFind() throws IOException {
        Path testFile = createTestFile("sumFind");
        Files.write(testFile, List.of("1721", "979", "366", "299", "675", "1456"));

        ExpenseReport report = new ExpenseReport(testFile);

        assertEquals(514579, report.fixExpenseReport(2020));
    }

    @Test
    void testThreeSumFind() throws IOException {
        Path testFile = createTestFile("sumFind");
        Files.write(testFile, List.of("1721", "979", "366", "299", "675", "1456"));

        ExpenseReport report = new ExpenseReport(testFile);

        assertEquals(241861950, report.fixExpenseReportThreeSum(2020));
    }

    @Test
    void testDayOnePart1() {
        ExpenseReport report = new ExpenseReport("src\\main\\resources\\puzzle1_input.txt");
        assertEquals(877971, report.fixExpenseReport(2020));
    }

    @Test
    void testDayOnePart2() {
        ExpenseReport report = new ExpenseReport("src\\main\\resources\\puzzle1_input.txt");
        assertEquals(203481432, report.fixExpenseReportThreeSum(2020));
    }

}

