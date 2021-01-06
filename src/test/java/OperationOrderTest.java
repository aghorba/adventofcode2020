import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OperationOrderTest extends TestHelper {

    @Test
    void testOperationOrder1() throws IOException {
        Path testFile = createTestFile("operation_order");
        Files.write(testFile, List.of(
                "1 + (2 * 3) + (4 * (5 + 6))"
        ));

        OperationOrder op = new OperationOrder(testFile);
        assertEquals(51, op.sum(OperatorPrecedence.LEFT_TO_RIGHT));
    }

    @Test
    void testOperationOrder2() throws IOException {
        Path testFile = createTestFile("operation_order");
        Files.write(testFile, List.of(
                "2 * 3 + (4 * 5)"
        ));

        OperationOrder op = new OperationOrder(testFile);
        assertEquals(26, op.sum(OperatorPrecedence.LEFT_TO_RIGHT));
    }

    @Test
    void testOperationOrder3() throws IOException {
        Path testFile = createTestFile("operation_order");
        Files.write(testFile, List.of(
                "5 + (8 * 3 + 9 + 3 * 4 * 3)"
        ));

        OperationOrder op = new OperationOrder(testFile);
        assertEquals(437, op.sum(OperatorPrecedence.LEFT_TO_RIGHT));
    }

    @Test
    void testOperationOrder4() throws IOException {
        Path testFile = createTestFile("operation_order");
        Files.write(testFile, List.of(
                "5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))"
        ));

        OperationOrder op = new OperationOrder(testFile);
        assertEquals(12240, op.sum(OperatorPrecedence.LEFT_TO_RIGHT));
    }

    @Test
    void testOperationOrder5() throws IOException {
        Path testFile = createTestFile("operation_order");
        Files.write(testFile, List.of(
                "((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2"
        ));

        OperationOrder op = new OperationOrder(testFile);
        assertEquals(13632, op.sum(OperatorPrecedence.LEFT_TO_RIGHT));
    }

    @Test
    void testAlternatePrecedence1() throws IOException {
        Path testFile = createTestFile("operation_order");
        Files.write(testFile, List.of(
                "5 + (8 * 3 + 9 + 3 * 4 * 3)"
        ));

        OperationOrder op = new OperationOrder(testFile);
        assertEquals(1445, op.sum(OperatorPrecedence.PLUS_FIRST));
    }

    @Test
    void testAlternatePrecedence2() throws IOException {
        Path testFile = createTestFile("operation_order");
        Files.write(testFile, List.of(
                "1 + 2 * 3 + 4 * 5 + 6"
        ));

        OperationOrder op = new OperationOrder(testFile);
        assertEquals(231, op.sum(OperatorPrecedence.PLUS_FIRST));
    }

    @Test
    void testAlternatePrecedence3() throws IOException {
        Path testFile = createTestFile("operation_order");
        Files.write(testFile, List.of(
                "1 + (2 * 3) + (4 * (5 + 6))"
        ));

        OperationOrder op = new OperationOrder(testFile);
        assertEquals(51, op.sum(OperatorPrecedence.PLUS_FIRST));
    }

    @Test
    void testAlternatePrecedence4() throws IOException {
        Path testFile = createTestFile("operation_order");
        Files.write(testFile, List.of(
                "2 * 3 + (4 * 5)"
        ));

        OperationOrder op = new OperationOrder(testFile);
        assertEquals(46, op.sum(OperatorPrecedence.PLUS_FIRST));
    }

    @Test
    void testAlternatePrecedence5() throws IOException {
        Path testFile = createTestFile("operation_order");
        Files.write(testFile, List.of(
                "5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))"
        ));

        OperationOrder op = new OperationOrder(testFile);
        assertEquals(669060, op.sum(OperatorPrecedence.PLUS_FIRST));
    }

    @Test
    void testAlternatePrecedence6() throws IOException {
        Path testFile = createTestFile("operation_order");
        Files.write(testFile, List.of(
                "((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2"
        ));

        OperationOrder op = new OperationOrder(testFile);
        assertEquals(23340, op.sum(OperatorPrecedence.PLUS_FIRST));
    }

}
