import java.nio.file.Path;
import java.util.*;

public class NumbersGame extends InputReader {

    public NumbersGame(String input) {
        super(input);
    }

    public NumbersGame(Path input) {
        super(input);
    }

    public int spokenNumber(int lastTurn) {
        /*
        1. Read all the starting numbers first
        2. Then each turn after the starting numbers follows these rules:
            a. If that was the first time the number has been spoken, the current player says 0.
            b. Otherwise, the number had been spoken before;
            the current player announces how many turns apart the number is from
            when it was previously spoken.
        3. When a number has been spoken before:
           i. Most recent turn it was spoken - previous turn before that when the number was spoken = answer

         */
        int lastNumberSpoken = Integer.MIN_VALUE;
        int turn = 0;
        int previousTurn = -1;
        String[] startingNumbers = data().get(0).split(",");
        Map<Integer, SpokenNumber> spokenNumbers = new HashMap<>();
        for(String number : startingNumbers) {
            int value = Integer.parseInt(number);
            SpokenNumber newNumber = new SpokenNumber(value, turn);

            spokenNumbers.put(value, newNumber);
            lastNumberSpoken = value;
            previousTurn++;
            turn++;
        }

        /*
        Turn 4 (previous 3): Last number spoken -> 6
                6 was not spoken after the starting numbers. Therefore, the spoken number is 0
        Turn 5: Last number spoken -> 0
                0 was spoken before, now calculate it's most recent turn - the previous turn to that
                spoken number = 4 - 1 = 3
        Turn 6: Last number spoken -> 3
                3 was spoken before
                spoken number = 5 - 2 = 3
        Turn 7: Last number spoken -> 3
                3 was spoken before
                spoken number = 6 - 5 = 1
        Turn 8: Last number spoken -> 1
                1 was never spoken before. Now speak 0
        Turn 9: Last number spoken -> 0
                0 was spoken before
                spoken number = 8 - 4 = 4
        Turn 10: Last number spoken -> 4
                 4 not spoken before. Now speak 0.
         */
        while(turn != lastTurn) {
              SpokenNumber lastNumber = spokenNumbers.get(lastNumberSpoken);

              if(!lastNumber.spokenBefore()) {
                  // Update the turns it has been spoken on
                  updateNumber(spokenNumbers, lastNumber.value(), previousTurn);
                  lastNumberSpoken = 0;
                  // Update that zero was spoken
                  updateNumberIfSpoken(spokenNumbers, 0, turn);
              } else {
                  int nextNumberToSpeak = lastNumber.mostRecentTurnSpoken()
                          - lastNumber.turnPreviousToMostRecent();
                  lastNumberSpoken = nextNumberToSpeak;
                  // Update that this number was just spoken
                  updateNumberIfSpoken(spokenNumbers, nextNumberToSpeak, turn);
              }

            previousTurn++;
            turn++;
        }

        return lastNumberSpoken;
    }

    private void updateNumber(
            Map<Integer, SpokenNumber> spokenNumbers,
            int numberSpoken,
            int turnSpoken) {
        SpokenNumber spokenNumber = spokenNumbers.get(numberSpoken);
        spokenNumber.setTurnPreviousToMostRecent(spokenNumber.mostRecentTurnSpoken());
        spokenNumber.setMostRecentTurnSpoken(turnSpoken);
        spokenNumber.spokenBefore(true);
        spokenNumbers.put(numberSpoken, spokenNumber);
    }

    private void updateNumberIfSpoken(
            Map<Integer, SpokenNumber> spokenNumbers,
            int numberSpoken,
            int turnSpoken) {
        // Update that this number was just spoken if spoken before
        if(spokenNumbers.containsKey(numberSpoken)) {
            updateNumber(spokenNumbers, numberSpoken, turnSpoken);
        } else {
            // First time this number is being spoken
            spokenNumbers.put(numberSpoken, new SpokenNumber(numberSpoken, turnSpoken));
        }
    }

    private static class SpokenNumber {
        private int value;
        private boolean spokenBefore;
        private Integer mostRecentTurnSpoken;
        private Integer turnPreviousToMostRecent;

        public SpokenNumber(int value, int turnSpoken) {
            this.value = value;
            mostRecentTurnSpoken = turnSpoken;
            spokenBefore = false;
        }

        public int value() {
            return value;
        }

        public boolean spokenBefore() {
            return spokenBefore;
        }

        public void spokenBefore(boolean spokenBefore) {
            this.spokenBefore = spokenBefore;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public int mostRecentTurnSpoken() {
            return mostRecentTurnSpoken;
        }

        public void setMostRecentTurnSpoken(int mostRecentTurnSpoken) {
            this.mostRecentTurnSpoken = mostRecentTurnSpoken;
        }

        public int turnPreviousToMostRecent() {
            return turnPreviousToMostRecent;
        }

        public void setTurnPreviousToMostRecent(int turnPreviousToMostRecent) {
            this.turnPreviousToMostRecent = turnPreviousToMostRecent;
        }
    }

}
