import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BootCodeInterpreter extends InputReader {

    private Set<Integer> instructionsExecuted;

    private int accumulator;

    public BootCodeInterpreter(String input) {
        super(input);
    }

    public BootCodeInterpreter(Path input) {
        super(input);
    }

    public static class AccumulatorResult {

        private boolean infiniteLoop;

        private int value;

        public AccumulatorResult(int value, boolean infiniteLoop) {
            this.value = value;
            this.infiniteLoop = infiniteLoop;
        }

        public boolean infiniteLoop() {
            return infiniteLoop;
        }

        public int value() {
            return value;
        }
    }

    public AccumulatorResult accumulatorValue() {
        instructionsExecuted = new HashSet<>();
        accumulator = 0;

        for(int i = 0; i < data().size(); i++) {
            String[] instructionLine = data().get(i).split(" ");
            String operation = instructionLine[0];
            int argument = Integer.parseInt(instructionLine[1]);

            if(instructionsExecuted.contains(i)) {
                return new AccumulatorResult(accumulator, true);
            }

            instructionsExecuted.add(i);
            switch(operation) {
                case "nop":
                    break;
                case "acc":
                    accumulator += argument;
                    break;
                case "jmp":
                    i += argument;
                    // Subtract one to cancel out the ++ on the next run of the loop
                    i--;
                    break;
                default:
                    throw new IllegalArgumentException("Unrecognized instruction: '" + operation + "'");
            }
        }

        return new AccumulatorResult(accumulator, false);
    }

    public int accumulatorValueInfLoopFix() {
       List<Integer> jmpOrNoOpInstructionId = new ArrayList<>();

       for(int i = 0; i < data().size(); i++) {
            String[] instructionLine = data().get(i).split(" ");
            String operation = instructionLine[0];

            if(operation.equals("jmp") || operation.equals("nop")) {
                jmpOrNoOpInstructionId.add(i);
            }
       }

        List<String> dataCopy = new ArrayList<>(data());
        for(int operation : jmpOrNoOpInstructionId) {
            String replaceOp = data().get(operation);
            replaceOp = replaceOp.startsWith("nop")
                    ? replaceOp.replaceAll("nop", "jmp")
                    : replaceOp.replaceAll("jmp", "nop");
            data().set(operation, replaceOp);

            AccumulatorResult result = this.accumulatorValue();
            if (result.infiniteLoop()) {
                resetData(dataCopy);
                continue;
            }

            accumulator = result.value;
            break;
        }

        return accumulator;
    }

}
