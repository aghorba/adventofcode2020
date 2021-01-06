import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BootCodeInterpreterTest extends TestHelper {

    @Test
    void testBootCodeAccumulator() throws IOException {
        Path testFile = createTestFile("boot_code_interpreter");
        Files.write(testFile, List.of(
                "nop +0",
                "acc +1",
                "jmp +4",
                "acc +3",
                "jmp -3",
                "acc -99",
                "acc +1",
                "jmp -4",
                "acc +6"
        ));

        BootCodeInterpreter interpreter = new BootCodeInterpreter(testFile);
        assertEquals(5, interpreter.accumulatorValue().value());
    }

    @Test
    void testDayEightPart1() {
        BootCodeInterpreter interpreter = new BootCodeInterpreter("src\\main\\resources\\puzzle8_input.txt");
        assertEquals(1317, interpreter.accumulatorValue().value());
    }

    @Test
    void testBootCodeLoopFix() throws IOException {
        Path testFile = createTestFile("boot_code_interpreter");
        Files.write(testFile, List.of(
                "nop +0",
                "acc +1",
                "jmp +4",
                "acc +3",
                "jmp -3",
                "acc -99",
                "acc +1",
                "jmp -4",
                "acc +6"
        ));

        BootCodeInterpreter interpreter = new BootCodeInterpreter(testFile);
        assertEquals(8, interpreter.accumulatorValueInfLoopFix());
    }

    @Test
    void testDayEightPart2() {
        BootCodeInterpreter interpreter = new BootCodeInterpreter("src\\main\\resources\\puzzle8_input.txt");
        assertEquals(1033, interpreter.accumulatorValueInfLoopFix());
    }
}
