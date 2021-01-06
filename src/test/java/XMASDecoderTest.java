import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class XMASDecoderTest extends TestHelper {

    @Test
    void testXMASDecoder() throws IOException {
        Path testFile = createTestFile("xmas_decoder");
        Files.write(testFile, List.of(
                "35",
                "20",
                "15",
                "25",
                "47",
                "40",
                "62",
                "55",
                "65",
                "95",
                "102",
                "117",
                "150",
                "182",
                "127",
                "219",
                "299",
                "277",
                "309",
                "576"
        ));

        XMASDecoder decoder = new XMASDecoder(testFile);
        assertEquals(127, decoder.decodeFirstNumber(5));
    }

    @Test
    void testDayNinePart1() {
        XMASDecoder decoder = new XMASDecoder("src\\main\\resources\\puzzle9_input.txt");
        assertEquals(88311122, decoder.decodeFirstNumber(25));
    }

    @Test
    void testFindEnceyptionWeakness() throws IOException {
        Path testFile = createTestFile("xmas_decoder");
        Files.write(testFile, List.of(
                "35",
                "20",
                "15",
                "25",
                "47",
                "40",
                "62",
                "55",
                "65",
                "95",
                "102",
                "117",
                "150",
                "182",
                "127",
                "219",
                "299",
                "277",
                "309",
                "576"
        ));

        XMASDecoder decoder = new XMASDecoder(testFile);
        assertEquals(62, decoder.findEncryptionWeakness(5));

    }

    @Test
    void testDayNinePart2() {
        XMASDecoder decoder = new XMASDecoder("src\\main\\resources\\puzzle9_input.txt");
        assertEquals(13549369, decoder.findEncryptionWeakness(25));
    }

}
