import java.nio.file.Path;

public class SledTrajectory extends InputReader {

    public SledTrajectory(String input) {
        super(input);
    }

    public SledTrajectory(Path input) {
        super(input);
    }

    public int treesEncountered(int xIncrement, int yIncrement) {
        /*
        1. Loop over input data
        2. Track the X position (Y position will be determined with the loop)
        3. Start at the 2nd line (first line never has an answer)
        4. Start with initial X = 3 (representing index 3). Check if a '.' or a '#'
           a. If a '#', increment treeCount
        5. Increment X += 3.
           a. Check if X goes passed the line length. If X + 3 > line length, then set X = (X + 3) - line length
         */

        int xPosition = xIncrement;
        int treeCount = 0;
        int rowStart = yIncrement;
        for(int i = rowStart; i < data().size(); i += yIncrement) {
            String mapLine = data().get(i);

            if(mapLine.charAt(xPosition) == '#') {
                treeCount++;
            }

            xPosition += xIncrement;
            if(xPosition >= mapLine.length()) {
                xPosition = xPosition - mapLine.length();
            }
        }

        return treeCount;
    }

    /**
     * Provide an array of arrays representing the trajectories. Each subarray should be 2 elements long representing<br>
     * the following: <br>
     *     Index 0: X trajectory
     *     Index 1: Y trajectory
     * @param trajectories array of arrays
     * @return Result of the number of trees encountered for each trajectory multiplied together
     */
    public long runMultipleTrajectories(int[][] trajectories) {
        long total = 1;

        for(int[] trajectory : trajectories) {
            total *= treesEncountered(trajectory[0], trajectory[1]);
        }

        return total;
    }
}
