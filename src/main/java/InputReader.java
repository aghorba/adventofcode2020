import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class InputReader {

    private Path input;

    private List<String> data;

    public InputReader(String input) {
        this.input = Paths.get(input);
        readData();
    }

    public InputReader(Path input) {
        this.input = input;
        readData();
    }

    private void readData() {
        try {
            data = Files.readAllLines(input);
        } catch (IOException e) {
            System.out.println("Unable to read data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    protected Path input() {
        return input;
    }

    protected List<String> data() {
        return data;
    }

    protected void resetData(List<String> dataCopy) {
        data = new ArrayList<>(dataCopy);
    }

    protected void changeFileAndReread(Path newFile) {
        input = newFile;
        readData();
    }
}
