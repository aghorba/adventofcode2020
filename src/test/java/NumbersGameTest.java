import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NumbersGameTest extends TestHelper {

    @Test
    void testNumbersGame() throws IOException {
        Path testFile = createTestFile("numbers_game");
        Files.write(testFile, List.of(
                "0,3,6"
        ));

        NumbersGame game = new NumbersGame(testFile);
        assertEquals(436, game.spokenNumber(2020));
        assertEquals(175594, game.spokenNumber(30000000));
    }

    @Test
    void testNumbersGame2() throws IOException {
        Path testFile = createTestFile("numbers_game");
        Files.write(testFile, List.of(
            "1,3,2"
        ));

        NumbersGame game = new NumbersGame(testFile);
        assertEquals(1, game.spokenNumber(2020));
        assertEquals(2578, game.spokenNumber(30000000));
    }

    @Test
    void testNumbersGame3() throws IOException {
        Path testFile = createTestFile("numbers_game");
        Files.write(testFile, List.of(
            "2,1,3"
        ));

        NumbersGame game = new NumbersGame(testFile);
        assertEquals(10, game.spokenNumber(2020));
        assertEquals(3544142, game.spokenNumber(30000000));
    }

    @Test
    void testNumbersGame4() throws IOException {
        Path testFile = createTestFile("numbers_game");
        Files.write(testFile, List.of(
            "1,2,3"
        ));

        NumbersGame game = new NumbersGame(testFile);
        assertEquals(27, game.spokenNumber(2020));
        assertEquals(261214, game.spokenNumber(30000000));
    }

    @Test
    void testNumbersGame5() throws IOException {
        Path testFile = createTestFile("numbers_game");
        Files.write(testFile, List.of(
            "2,3,1"
        ));

        NumbersGame game = new NumbersGame(testFile);
        assertEquals(78, game.spokenNumber(2020));
        assertEquals(6895259, game.spokenNumber(30000000));
    }

    @Test
    void testNumbersGame6() throws IOException {
        Path testFile = createTestFile("numbers_game");
        Files.write(testFile, List.of(
            "3,2,1"
        ));

        NumbersGame game = new NumbersGame(testFile);
        assertEquals(438, game.spokenNumber(2020));
        assertEquals(18, game.spokenNumber(30000000));
    }

    @Test
    void testNumbersGame7() throws IOException {
        Path testFile = createTestFile("numbers_game");
        Files.write(testFile, List.of(
            "3,1,2"
        ));

        NumbersGame game = new NumbersGame(testFile);
        assertEquals(1836, game.spokenNumber(2020));
        assertEquals(362, game.spokenNumber(30000000));
    }

    @Test
    void testDayFifteenPart1() {
        NumbersGame game = new NumbersGame("src\\main\\resources\\puzzle15_input.txt");
        assertEquals(614, game.spokenNumber(2020));
    }

    @Test
    void testDayFifteenPart2() {
        NumbersGame game = new NumbersGame("src\\main\\resources\\puzzle15_input.txt");
        assertEquals(1065, game.spokenNumber(30000000));
    }

}
