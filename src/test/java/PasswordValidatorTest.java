import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PasswordValidatorTest extends TestHelper {

    @Test
    void testPasswordValidator() throws IOException {
        Path testFile = createTestFile("password_validator");
        Files.write(testFile, List.of(
                "1-3 a: abcde",
                "1-3 b: cdefg",
                "2-9 c: ccccccccc"
        ));

        PasswordValidator validator = new PasswordValidator(testFile);
        assertEquals(2, validator.validPasswords());
    }

    @Test
    void testPasswordPositionValidator() throws IOException {
        Path testFile = createTestFile("password_position_validator");
        Files.write(testFile, List.of(
                "1-3 a: abcde",
                "1-3 b: cdefg",
                "2-9 c: ccccccccc"
        ));

        PasswordValidator positionValidator = new PasswordValidator(testFile);
        assertEquals(1, positionValidator.validPasswordPositions());
    }

    @Test
    void testDayTwoPart1() {
        PasswordValidator validator = new PasswordValidator("src\\main\\resources\\puzzle2_input.txt");
        assertEquals(524, validator.validPasswords());
    }

    @Test
    void testDayTwoPart2() {
        PasswordValidator validator = new PasswordValidator("src\\main\\resources\\puzzle2_input.txt");
        assertEquals(485, validator.validPasswordPositions());
    }

}
