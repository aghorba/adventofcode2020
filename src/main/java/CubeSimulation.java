import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class CubeSimulation extends InputReader {

    public CubeSimulation(String input) {
        super(input);
    }

    public CubeSimulation(Path input) {
        super(input);
    }

    public long activeCubes(int cycles) {

        Set<List<Integer>> directions = generateMovements(false);
        List<Point> currentGrid = loadInitialInput(false);
        Map<String, Point> gridLookup = createGridLookup(currentGrid);
        int cyclesDone = 0;

        while(cyclesDone != cycles - 1) {
            for (Point currentPoint : currentGrid) {
                int activeNeighbors = 0;

                for (List<Integer> direction : directions) {
                    int nextX = currentPoint.x() + direction.get(0);
                    int nextY = currentPoint.y() + direction.get(1);
                    int nextZ = currentPoint.z() + direction.get(2);

                    Point neighbor = null;
                    String pointKey = new StringBuilder()
                            .append(nextX)
                            .append(nextY)
                            .append(nextZ)
                            .toString();

                    if (gridLookup.containsKey(pointKey)) {
                        neighbor = gridLookup.get(pointKey);

                        if (neighbor.active()) {
                            activeNeighbors++;
                        }
                    } else {
                        neighbor = new Point(nextX, nextY, nextZ, false);
                        gridLookup.put(pointKey, neighbor);
                    }
                }

                if (currentPoint.active()) {
                    if (activeNeighbors == 2 || activeNeighbors == 3) {
                        currentPoint.setActive(true);
                    } else {
                        gridLookup.remove(currentPoint.key());
                        gridLookup.put(currentPoint.key(), new Point(
                                currentPoint.x(), currentPoint.y(), currentPoint.z(), false));
                    }
                } else {
                    if (activeNeighbors == 3) {
                        gridLookup.remove(currentPoint.key());
                        gridLookup.put(currentPoint.key(), new Point(
                                currentPoint.x(), currentPoint.y(), currentPoint.z(), true));
                    }
                }
            }

            currentGrid = updateGrid(gridLookup);
            cyclesDone++;
        }

        return currentGrid.stream()
                .filter(Point::active)
                .count();
    }

    public long activeCubesWithW(int cycles) {
        Set<List<Integer>> directions = generateMovements(true);
        Map<String, PointW> oldGrid = loadInitialInput();
        int cyclesDone = 0;

        /*
        1. Get the starting grid
        2. Put the starting grid into a Map to allow for fast look up
        3. On each cycle
           a. Create a copy of the current grid
           b. Loop on the the current grid
           c. Make changes to the copy of grid (for simultaneous changes)
        4. When a neighbor is not in the current grid, add it to the new grid
        5. After the cycle is done, set the current grid to the new grid that was just modified
        6. Increment cycles done
         */

        while(cyclesDone != cycles - 1) {
            Map<String, PointW> newGrid = new HashMap<>(oldGrid);

            for(PointW pointW : oldGrid.values()) {
                int activeNeighbors = 0;

                for (List<Integer> direction : directions) {
                    int nextX = pointW.x() + direction.get(0);
                    int nextY = pointW.y() + direction.get(1);
                    int nextZ = pointW.z() + direction.get(2);
                    int nextW = pointW.w() + direction.get(3);
                    String key = new StringBuilder()
                            .append(nextX)
                            .append(nextY)
                            .append(nextZ)
                            .append(nextW)
                            .toString();

                    if(oldGrid.containsKey(key)) {
                        if(oldGrid.get(key).active()) {
                            activeNeighbors++;
                        }
                    } else {
                        newGrid.put(key, new PointW(nextX, nextY, nextZ, nextW, false));
                    }
                }

                if(pointW.active()) {
                    if(activeNeighbors < 2 || activeNeighbors > 3) {
                        newGrid.put(pointW.key(), new PointW(pointW.x(), pointW.y(), pointW.z(), pointW.w(), false));
                    }
                } else {
                    if(activeNeighbors == 3) {
                        newGrid.put(pointW.key(), new PointW(pointW.x(), pointW.y(), pointW.z(), pointW.w(), true));
                    }
                }
            }

            oldGrid = newGrid;
            cyclesDone++;
        }

        return oldGrid.values().stream()
                .filter(elem -> elem.active())
                .count();
    }

    private List<Point> updateGrid(Map<String, Point> gridLookup) {
        return new ArrayList<>(gridLookup.values());
    }

    private Map<String, Point> createGridLookup(List<Point> grid) {
        return grid.stream()
                .collect(Collectors.toMap(
                        key -> key.key(),
                        value -> value
                ));
    }

    private List<Point> loadInitialInput(boolean useW) {
        List<Point> startingSlice = new ArrayList<>();
        int y = 0;
        int z = 0;
        int w = 0;
        for(String line : data()) {
            int x = 0;
            for(char cube : line.toCharArray()) {
                if(cube == '.') {
                    if(useW) {
                        startingSlice.add(new PointW(x, y, z, w, false));
                    } else {
                        startingSlice.add(new Point(x, y, z, false));
                    }
                } else if(cube == '#') {
                    if(useW) {
                        startingSlice.add(new PointW(x, y, z, w, true));
                    } else {
                        startingSlice.add(new Point(x, y, z, true));
                    }
                }

                x++;
            }

            y++;
        }

        return startingSlice;
    }

    private Map<String, PointW> loadInitialInput() {
        Map<String, PointW> startingSlice = new HashMap<>();
        int y = 0;
        int z = 0;
        int w = 0;
        for(String line : data()) {
            int x = 0;
            for(char cube : line.toCharArray()) {
                String key = new StringBuilder()
                        .append(x)
                        .append(y)
                        .append(z)
                        .append(w)
                        .toString();
                if(cube == '.') {
                    startingSlice.put(key, new PointW(x, y, z, w, false));
                } else if(cube == '#') {
                    startingSlice.put(key, new PointW(x, y, z, w, true));
                }

                x++;
            }

            y++;
        }

        return startingSlice;
    }

    private Set<List<Integer>> generateMovements(boolean useW) {
        Set<List<Integer>> directions = new HashSet<>();

        if(useW) {
            for(int x = -1; x <= 1; x++) {
                for(int y = -1; y <= 1; y++) {
                    for(int z = -1; z <= 1; z++) {
                        for(int w = -1; w <= 1; w++) {
                            if(x == 0 && y == 0 && z == 0 && w == 0) {
                                continue;
                            }

                            directions.add(List.of(x, y, z, w));
                        }
                    }
                }
            }
        } else {
            List<Integer> movements = List.of(-1, 0, 1);

            for(int i = 0; i < movements.size(); i++) {
                generateMovement(i, new ArrayList<>(), movements, directions, movements.size());
            }
        }

        return directions;
    }

    private void generateMovement(
            int index,
            List<Integer> currentMovements,
            List<Integer> movementValues,
            Set<List<Integer>> allDirections,
            int maxSize) {

        if(index == maxSize) {
            return;
        }

        currentMovements.add(movementValues.get(index));

        if(currentMovements.size() == maxSize) {
            allDirections.add(new ArrayList<>(currentMovements));
            return;
        }

        for(int i = 0; i < movementValues.size(); i++) {
            generateMovement(i, currentMovements, movementValues, allDirections, maxSize);
            if(currentMovements.size() > 0) {
                currentMovements.remove(currentMovements.size() - 1);
            }
        }
    }

    private static class Point {

        private final int x;

        private final int y;

        private final int z;

        private boolean active;

        protected String key;

        public Point(int x, int y, int z, boolean active) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.active = active;
            key = new StringBuilder()
                    .append(x)
                    .append(y)
                    .append(z)
                    .toString();
        }

        public String key() {
            return key;
        }

        public int x() {
            return x;
        }

        public int y() {
            return y;
        }

        public int z() {
            return z;
        }

        public boolean active() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }

        @Override
        public boolean equals(Object p) {
            if(p == null || p.getClass() != this.getClass()) {
                return false;
            }

            if(this == p) {
                return true;
            }

            Point other = (Point) p;

            return  this.x() == other.x()
                    && this.y() == other.y()
                    && this.z() == other.z();
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z);
        }

    }

    private static class PointW extends Point {

        private final int w;

        public PointW(int x, int y, int z, int w, boolean active) {
            super(x, y, z, active);
            this.w = w;
            key = this.key() + w;
        }

        public int w() {
            return w;
        }

        @Override
        public boolean equals(Object p) {
            if(p == null || p.getClass() != this.getClass()) {
                return false;
            }

            if(this == p) {
                return true;
            }

            PointW other = (PointW) p;

            return this.x() == other.x()
                    && this.y() == other.y()
                    && this.z() == other.z()
                    && this.w() == other.w();
        }

        @Override
        public int hashCode() {
            return Objects.hash(super.hashCode(), w);
        }
    }

}
