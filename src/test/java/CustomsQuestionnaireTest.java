import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomsQuestionnaireTest extends TestHelper {

    @Test
    void testYesCount() throws IOException {
        Path testFile = createTestFile("customs_questionnaire");
        Files.write(testFile, List.of(
                "abc",
                "",
                "a",
                "b",
                "c",
                "",
                "ab",
                "ac",
                "",
                "a",
                "a",
                "a",
                "a",
                "",
                "b",
                ""
        ));

        CustomsQuestionnaire questionnaire = new CustomsQuestionnaire(testFile);
        assertEquals(11, questionnaire.yesCount());
    }

    @Test
    void testAllYesCount() throws IOException {
        Path testFile = createTestFile("customs_questionnaire");
        Files.write(testFile, List.of(
                "abc",
                "",
                "a",
                "b",
                "c",
                "",
                "ab",
                "ac",
                "",
                "a",
                "a",
                "a",
                "a",
                "",
                "b",
                ""
        ));

        CustomsQuestionnaire questionnaire = new CustomsQuestionnaire(testFile);
        assertEquals(6, questionnaire.allYesCount());
    }

    @Test
    void testDaySixPart1() {
        CustomsQuestionnaire questionnaire = new CustomsQuestionnaire("src\\main\\resources\\puzzle6_input.txt");
        assertEquals(6437, questionnaire.yesCount());
    }

    @Test
    void testDaySixPart2() {
        CustomsQuestionnaire questionnaire = new CustomsQuestionnaire("src\\main\\resources\\puzzle6_input.txt");
        assertEquals(3229, questionnaire.allYesCount());
    }
}
