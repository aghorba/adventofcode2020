import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DockingDataTest extends TestHelper {

    @Test
    void testDockingDataMemoryValuesSum() throws IOException {
        Path testFile = createTestFile("docking_data");
        Files.write(testFile, List.of(
                "mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X",
                "mem[8] = 11",
                "mem[7] = 101",
                "mem[8] = 0"
        ));
        DockingData dockingData = new DockingData(testFile);
        assertEquals(165L, dockingData.memoryValuesSum());
    }

    @Test
    void testDayFourteenPart1() {
        DockingData dockingData = new DockingData("src\\main\\resources\\puzzle14_input.txt");
        assertEquals(15919415426101L, dockingData.memoryValuesSum());
    }

    @Test
    void testDockingDataVersion2() throws IOException {
        Path testFile = createTestFile("docking_data");
        Files.write(testFile, List.of(
                "mask = 000000000000000000000000000000X1001X",
                "mem[42] = 100",
                "mask = 00000000000000000000000000000000X0XX",
                "mem[26] = 1"
        ));
        DockingData dockingData = new DockingData(testFile);
        assertEquals(208L, dockingData.memorySumVersion2());
    }
}
