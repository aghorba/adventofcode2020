import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardingPassDecoderTest extends TestHelper {

    @Test
    void testDecodeBoardingPasses() throws IOException {
        Path testFile = createTestFile("boarding_pass_decoder");
        Files.write(testFile, List.of(
           "FBFBBFFRLR"
        ));

        BoardingPassDecoder decoder = new BoardingPassDecoder(testFile);
        assertEquals(357, decoder.highestSeatId());

        Files.write(testFile, List.of(
                "BFFFBBFRRR"
        ));

        decoder.changeFileAndReread(testFile);
        assertEquals(567, decoder.highestSeatId());

        Files.write(testFile, List.of(
                "FFFBBBFRRR"
        ));

        decoder.changeFileAndReread(testFile);
        assertEquals(119, decoder.highestSeatId());

        Files.write(testFile, List.of(
                "BBFFBBFRLL"
        ));

        decoder.changeFileAndReread(testFile);
        assertEquals(820, decoder.highestSeatId());
    }

    @Test
    void testDayFivePart1() {
        BoardingPassDecoder decoder = new BoardingPassDecoder("src\\main\\resources\\puzzle5_input.txt");
        assertEquals(944, decoder.highestSeatId());
    }

    @Test
    void testDayFivePart2() {
        BoardingPassDecoder decoder = new BoardingPassDecoder("src\\main\\resources\\puzzle5_input.txt");
        assertEquals(554, decoder.selfSeatId());
    }

}
