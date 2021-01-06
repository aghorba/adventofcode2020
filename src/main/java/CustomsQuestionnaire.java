import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomsQuestionnaire extends InputReader {

    public CustomsQuestionnaire(String input) {
        super(input);
    }

    public CustomsQuestionnaire(Path input) {
        super(input);
    }

    public int yesCount() {
        int yesCount = 0;
        Map<Character, Integer> counts = new HashMap<>();
        for(String questionnaireLine : data()) {
            if(questionnaireLine.equals("")) {
                yesCount += counts.entrySet().stream()
                        .map(answer -> answer.getValue())
                        .reduce(0, Integer::sum);
                counts.clear();
                continue;
            }

            for(char answer : questionnaireLine.toCharArray()) {
                counts.putIfAbsent(answer, 1);
            }
        }

        // Last questionnaire
        yesCount += counts.entrySet().stream()
                .map(answer -> answer.getValue())
                .reduce(0, Integer::sum);

        return yesCount;
    }

    public int allYesCount() {
        int yesCount = 0;
        Map<Character, Integer> answerCounts = new HashMap<>();
        int batchSize = 0;
        for(String questionnaireLine : data()) {
            if(questionnaireLine.equals("")) {
                int finalBatchCount = batchSize;
                yesCount += answerCounts.entrySet().stream()
                        .map(entry -> entry.getValue())
                        .filter(count -> count.equals(finalBatchCount))
                        .count();
                batchSize = 0;
                answerCounts.clear();
                continue;
            }

            for(char answer : questionnaireLine.toCharArray()) {
                Integer count = answerCounts.getOrDefault(answer, 0);
                answerCounts.put(answer, ++count);
            }

            batchSize++;
        }

        // Last questionnaire
        int finalBatchCount = batchSize;
        yesCount += answerCounts.entrySet().stream()
                .map(entry -> entry.getValue())
                .filter(count -> count.equals(finalBatchCount))
                .count();

        return yesCount;
    }

}
