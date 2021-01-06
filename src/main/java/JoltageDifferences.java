import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class JoltageDifferences extends InputReader {

    private List<Integer> joltAdapterValues;

    private int deviceJoltage;

    private Map<Integer, Long> arrangementsFromAdapter;

    private Set<Integer> joltAdapterValuesSet;

    public JoltageDifferences(String input) {
        super(input);
        initialize();
    }

    public JoltageDifferences(Path input) {
        super(input);
        initialize();
    }

    public int findJoltageDifference() {
        int joltageDiffOf1 = 0;
        int joltageDiffOf3 = 0;
        int currentComparisonJoltage = 0;
        for(int i = 0; i < joltAdapterValues.size(); i++) {
            int currentJoltage = joltAdapterValues.get(i);
            int joltageDifference = currentJoltage - currentComparisonJoltage;

            switch(joltageDifference) {
                case 1:
                    joltageDiffOf1++;
                    currentComparisonJoltage = currentJoltage;
                    break;
                case 2:
                    break;
                case 3:
                    joltageDiffOf3++;
                    currentComparisonJoltage = currentJoltage;
                    break;
                default:
                    break;
            }
        }

        return joltageDiffOf1 * (joltageDiffOf3 + 1);
    }

    public long distinctAdapters() {
        return calcDistinctAdapterArrangements(0, 0);
    }

    private long calcDistinctAdapterArrangements(int joltage, long distinctAdapters) {

        if(arrangementsFromAdapter.containsKey(joltage)) {
            return arrangementsFromAdapter.get(joltage);
        }

        Set<Integer> possibleAdapters = Set.of(joltage + 1, joltage + 2, joltage + 3);
        long answers = 0;
        for(int possibleAdapter : possibleAdapters) {
            if(possibleAdapter == deviceJoltage) {
                return 1;
            }

            if(joltAdapterValuesSet.contains(possibleAdapter)) {
                long result = calcDistinctAdapterArrangements(possibleAdapter, distinctAdapters);
                arrangementsFromAdapter.put(possibleAdapter, result);
                answers += distinctAdapters + result;
            }
        }

        return answers;
    }

    private void initialize() {
        arrangementsFromAdapter = new HashMap<>();
        joltAdapterValues = convertDataToInt();
        deviceJoltage = joltAdapterValues.get(joltAdapterValues.size() - 1) + 3;
        joltAdapterValuesSet = createSet();
    }

    private List<Integer> convertDataToInt() {
        return data().stream()
                .map(Integer::parseInt)
                .sorted()
                .collect(Collectors.toList());
    }

    private Set<Integer> createSet() {
        return joltAdapterValues.stream()
                .collect(Collectors.toSet());
    }

}
