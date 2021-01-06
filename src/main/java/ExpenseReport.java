import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class ExpenseReport extends InputReader {

    public ExpenseReport(String input) {
        super(input);
    }

    public ExpenseReport(Path input) {
        super(input);
    }

    public int fixExpenseReport(int sumToFind) {
        /*

        1721
        979
        366
        299
        675
        1456

        Looking for 2020

        1. Get the current value
        2. Get the difference: 2020 - current value = target value (e.g. 2020 - 1721 = 299)
        3. Check if the target value is in the map
           a. If it is not in the map, put the current value into the map (1721 -> 299)
           b. If the target value IS in the map, return that answer
         */
        Map<Integer, Integer> expenses = new HashMap<>();
        int result = 0;

        for(String reportLine : data()) {
            int currentValue = Integer.parseInt(reportLine);
            int targetValue = sumToFind - currentValue;

            if(expenses.get(targetValue) == null) {
                expenses.put(currentValue, targetValue);
            } else {
                result = currentValue * targetValue;
                break;
            }
        }

        return result;
    }

    public Integer fixExpenseReportThreeSum(int sumToFind) {
        /*
        3 sum:
        2020
        979
        366
        675

        2020 - 979 = 1041
        1041 - 366 = 675
        1041 - 675 = 366

        1. Loop over the input
        2. For each value, do 2020 - value = answer
        3. Call fixExpenseReport with "answer" as the sumToFind
        4. If an answer is found, then multiply the result value by the current value
         */

        int finalResult = 0;
        for(String reportLine : data()) {
            int value = Integer.parseInt(reportLine);
            int targetValue = sumToFind - value;
            int result = fixExpenseReport(targetValue);

            if(result != 0) {
                finalResult = value * result;
                break;
            }
        }

        return finalResult;
    }
}
