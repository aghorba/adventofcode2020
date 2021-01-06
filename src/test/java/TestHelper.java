import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

public class TestHelper {

    protected Path createTestFile(String prefix) throws IOException {
        return Files.createTempFile(prefix + "_", UUID.randomUUID().toString());
    }

}
