import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class SeatingSystem extends InputReader {

    public SeatingSystem(String input) {
        super(input);
    }

    public SeatingSystem(Path input) {
        super(input);
    }

    public int occupiedSeats(
            boolean modifiedRules,
            Predicate<Integer> emptySeatRule,
            Predicate<Integer> occupiedSeatRule) {
        /*
        1. Save the current grid to compare to for changes
        2. Make changes in a new version of the grid but base the checks on the old (previous) grid
        3. Apply each of the rules
         */

        int occupiedSeats = 0;
        List<String> oldGrid = new ArrayList<>(data());
        boolean seatStateFrozen = false;

        /*
            All decisions are based on the number of occupied seats adjacent to a given
    seat (one of the eight positions immediately up, down, left, right, or diagonal from the seat).
    The following rules are applied to every seat simultaneously:

        If a seat is empty (L) and there are no occupied seats adjacent to it, the seat becomes occupied.
        If a seat is occupied (#) and four or more seats adjacent to it are also occupied,
        he seat becomes empty.
        Otherwise, the seat's state does not change.
         */
        while(!seatStateFrozen) {
            List<String> newGrid = new ArrayList<>(oldGrid);
            for(int row = 0; row < oldGrid.size(); row++) {
                for(int column = 0; column < oldGrid.get(0).length(); column++) {
                    char currentSeat = oldGrid.get(row).charAt(column);
                    boolean seatChange = false;

                    if(currentSeat == 'L') {
                        seatChange = changeAdjacentSeat(
                                oldGrid,
                                newGrid,
                                row,
                                column,
                                currentSeat,
                                modifiedRules,
                                emptySeatRule);

                    } else if(currentSeat == '#') {
                        occupiedSeats++;
                        seatChange = changeAdjacentSeat(
                                oldGrid,
                                newGrid,
                                row,
                                column,
                                currentSeat,
                                modifiedRules,
                                occupiedSeatRule);
                    }

                    if(seatChange) {
                        occupiedSeats++;
                    }
                }
            }

            if(oldGrid.equals(newGrid)) {
                seatStateFrozen = true;
            } else {
                occupiedSeats = 0;
                oldGrid = newGrid;
            }
        }

        return occupiedSeats;
    }

    private boolean changeAdjacentSeat(
            List<String> oldGrid,
            List<String> newGrid,
            int row,
            int column,
            char seat,
            boolean modifiedRules,
            Predicate<Integer> seatCheck) {
        int[][] directions = {
                {-1, 0}, // up
                {1, 0}, // down
                {0, -1}, // left
                {0, 1}, // right
                {-1, -1}, // top left
                {-1, 1}, // top right
                {1, -1}, // bottom left
                {1, 1} // bottom right
        };
        int seatsOccupied = 0;

        for(int[] direction : directions) {
            int nextRow = row + direction[0];
            int nextColumn = column + direction[1];

            if(modifiedRules) {
                while(nextRow >= 0
                        && nextRow < oldGrid.size()
                        && nextColumn >= 0
                        && nextColumn < oldGrid.get(0).length()
                        && oldGrid.get(nextRow).charAt(nextColumn) == '.') {
                    nextRow += direction[0];
                    nextColumn += direction[1];
                }
            }

            if(nextRow >= 0
                    && nextRow < oldGrid.size()
                    && nextColumn >= 0
                    && nextColumn < oldGrid.get(0).length()
                    && oldGrid.get(nextRow).charAt(nextColumn) == '#') {
                seatsOccupied++;
            }
        }

        char updatedSeat = seat == '#'
                ? 'L'
                : '#';
        if(seatCheck.test(seatsOccupied)) {
            StringBuilder currentRow = new StringBuilder(newGrid.get(row));
            currentRow.setCharAt(column, updatedSeat);

            newGrid.set(row, currentRow.toString());
            return true;
        }

        return false;
    }
}
