import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageValidationTest extends TestHelper {

    @Test
    void testNumValidMessages() throws IOException {
        Path testFile = createTestFile("message_validation");
        Files.write(testFile, List.of(
                "0: 4 1 5",
                "1: 2 3 | 3 2",
                "2: 4 4 | 5 5",
                "3: 4 5 | 5 4",
                "4: \"a\"",
                "5: \"b\"",
                "",
                "ababbb",
                "bababa",
                "abbbab",
                "aaabbb",
                "aaaabbb"
        ));

        MessageValidation messageValidation = new MessageValidation(testFile);
        assertEquals(2L, messageValidation.validMessages());
    }
}
