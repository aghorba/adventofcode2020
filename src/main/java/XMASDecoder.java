import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class XMASDecoder extends InputReader {

    public XMASDecoder(String input) {
        super(input);
    }

    public XMASDecoder(Path input) {
        super(input);
    }

    public int decodeFirstNumber(int preambleLength) {
        /*
        1. Read first 25 numbers
        2. Check if 26th number can be created by a sum of 2 numbers within the first 25
           a. If it can, remove the 1st of the 25 numbers and add in the 26th number and repeat.
           b. If it can't, return that number
         */
        List<Integer> dataSet = data().stream()
                .map(Integer::parseInt)
                .limit(preambleLength)
                .collect(Collectors.toList());
        Deque<Integer> queue = new ArrayDeque<>(dataSet);
        Integer decodedNum = null;
        int comparisonIndex = preambleLength;
        while(decodedNum == null && comparisonIndex < data().size()) {
            int targetSum = Integer.parseInt(data().get(comparisonIndex));

            if(twoSum(queue, targetSum)) {
                queue.removeFirst();
                queue.addLast(targetSum);
                comparisonIndex++;
            } else {
                decodedNum = targetSum;
            }
        }

        return decodedNum;
    }

    public int findEncryptionWeakness(int preambleLength) {
        int invalidNumber = decodeFirstNumber(preambleLength);
        Deque<Integer> slidingWindow = new ArrayDeque<>();
        int sum = 0;
        boolean sumFound = false;

        for(String num : data()) {
            int value = Integer.parseInt(num);

            if(value == invalidNumber) {
                continue;
            }

            slidingWindow.addLast(value);
            sum += value;

            if(sum == invalidNumber) {
                break;
            } else if(sum > invalidNumber) {
                /*
                1. Keep removing from the queue until the sum matches or goes below the invalidNumber
                 */
                while(!slidingWindow.isEmpty()) {
                    sum -= slidingWindow.removeFirst();

                    if(sum == invalidNumber) {
                        sumFound = true;
                        break;
                    } else if(sum < invalidNumber) {
                        break;
                    }
                }
            }

            if(sumFound) {
                break;
            }
        }

        List<Integer> contiguousList = slidingWindow.stream()
                .collect(Collectors.toList());
        Collections.sort(contiguousList);

        return contiguousList.get(0) + contiguousList.get(contiguousList.size() - 1);
    }

    private boolean twoSum(Deque<Integer> numList, int targetSum) {
        Set<Integer> visited = new HashSet<>();
        for(int currentValue : numList) {
            int complement = targetSum - currentValue;

            if(visited.contains(complement)) {
                return true;
            }

            visited.add(currentValue);
        }

        return false;
    }
}
