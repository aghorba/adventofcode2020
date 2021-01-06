import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class OperationOrder extends InputReader {

    public OperationOrder(String input) {
        super(input);
    }

    public OperationOrder(Path input) {
        super(input);
    }

    public long sum(OperatorPrecedence precedence) {
        long result = 0;

        for(String line : data()) {
            String padWithSpaces = line.replaceAll("\\(", "( ")
                    .replaceAll("\\)", " )");
            result += resolve(0, padWithSpaces.split(" "), precedence).result();
        }

        return result;
    }

    private Result resolve(int index, String[] line, OperatorPrecedence precedence) {
        Deque<String> numbersToEval = new ArrayDeque<>();

        for(int i = index; i < line.length; i++) {
            String current = line[i];

            if(current.equals("(")) {
                Result resolved = resolve(i + 1, line, precedence);
                numbersToEval.addLast(String.valueOf(resolved.result()));
                i = resolved.index();
            } else if(current.equals(")"))  {
                if(precedence.equals(OperatorPrecedence.PLUS_FIRST)) {
                    return new Result(resolveQueueWithPrecedence(numbersToEval), i);
                }

                return new Result(resolveQueue(numbersToEval), i);
            } else {
                numbersToEval.addLast(current);
            }
        }

        if(precedence.equals(OperatorPrecedence.PLUS_FIRST)) {
            return new Result(resolveQueueWithPrecedence(numbersToEval), 0);
        }

        return new Result(resolveQueue(numbersToEval), 0);
    }

    private long resolveQueueWithPrecedence(Deque<String> numbersToEval) {
        /*
        new queue
for loop over precedence (+ first, then *)
	for loop over queue/list
		long prev = i - 1;

		if Character.isDigit
			new queue.addLast
		if !Character.isDigit && char != operator
			new queue.addLast
		if char == operator
			long first = new queue.removeLast
			long second = i + 1;

			switch() {
				...
				new queue.addLast(result)
				i++
			}
	list = new queue

return list.get(0)
         */

        Deque<String> aggregate = new ArrayDeque<>();
        List<String> expression = new ArrayList<>(numbersToEval);
        List<String> operators = List.of("+", "*");
        for(String operator : operators) {
            for(int i = 0; i < expression.size(); i++) {
                String current = expression.get(i);

                if(!expression.contains(current) || !current.equals(operator)) {
                    aggregate.addLast(current);
                } else {
                    long firstDigit = Long.parseLong(aggregate.removeLast());
                    long secondDigit = Long.parseLong(expression.get(i + 1));

                    switch (operator) {
                        case "+":
                            aggregate.addLast(String.valueOf(firstDigit + secondDigit));
                            i++;
                            break;
                        case "*":
                            aggregate.addLast(String.valueOf(firstDigit * secondDigit));
                            i++;
                            break;
                        default:
                            throw new IllegalArgumentException("Unrecognized operator: '" + operator + "'");
                    }
                }
            }

            expression = new ArrayList<>(aggregate);
            aggregate.clear();
        }

        return Long.parseLong(expression.get(0));
    }

    private long resolveQueue(Deque<String> numbersToEval) {
        long result = 0;

        while(!numbersToEval.isEmpty()) {
            if(numbersToEval.size() == 1) {
                break;
            }

            long firstDigit = Long.parseLong(numbersToEval.removeFirst());
            String operator = numbersToEval.removeFirst();
            long secondDigit = Long.parseLong(numbersToEval.removeFirst());

            switch (operator) {
                case "+":
                    result = (firstDigit + secondDigit);
                    numbersToEval.addFirst(String.valueOf(result));
                    break;
                case "*":
                    result = (firstDigit * secondDigit);
                    numbersToEval.addFirst(String.valueOf(result));
                    break;
                default:
                    throw new IllegalArgumentException("Unrecognized operator: '" + operator + "'");
            }
        }

        return result;
    }

    private static class Result {

        private long result;

        private int index;

        public Result(long result, int index) {
            this.result = result;
            this.index = index;
        }

        public long result() {
            return result;
        }

        public int index() {
            return index;
        }

    }
}
