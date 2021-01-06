import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class ShuttleSearch extends InputReader {

    public ShuttleSearch(String input) {
        super(input);
    }

    public ShuttleSearch(Path input) {
        super(input);
    }

    public int busID() {
        long earliestTimeEstimate = Long.parseLong(data().get(0));
        List<Integer> buses = Arrays.stream(data().get(1).split(","))
                .filter(bus -> !bus.equals("x"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        long earliestTime = Integer.MAX_VALUE;
        int selectedBusId = -1;
        for(int busID : buses) {
            int lowEnd = (int) Math.floor(earliestTimeEstimate / (busID * 1.0));
            int highEnd = (int) Math.ceil(earliestTimeEstimate / (busID * 1.0));
            long lowEndTime = busID * lowEnd;
            long highEndTime = busID * highEnd;

            if(lowEndTime > earliestTimeEstimate || highEndTime > earliestTimeEstimate) {
                long lowEndDifference = lowEndTime - earliestTimeEstimate;
                long highEndDifference = highEndTime - earliestTimeEstimate;

                if(lowEndDifference > 0 && lowEndDifference < highEndDifference) {
                    if(lowEndDifference < earliestTime) {
                        earliestTime = lowEndDifference;
                        selectedBusId = busID;
                    }
                } else {
                    if(highEndDifference < earliestTime) {
                        earliestTime = highEndDifference;
                        selectedBusId = busID;
                    }
                }
            }
        }

        return selectedBusId * (int) earliestTime;
    }

    public long earliestTimestamp() {
        Map<Integer, Integer> offsetAndBusId = new LinkedHashMap<>();
        String[] busIDs = data().get(1).split(",");

        for(int i = 0; i < busIDs.length; i++) {
            String busID = busIDs[i];

            if(!busID.equals("x")) {
                offsetAndBusId.put(i, Integer.parseInt(busID));
            }
        }

        long incrementLength = offsetAndBusId.get(0);
        Set<Long> foundMulitples = new HashSet<>();
        foundMulitples.add(Long.valueOf(offsetAndBusId.get(0)));
        for(long i = 0; i < Long.MAX_VALUE; i += incrementLength) {
            long timestamp = i;
            boolean allConditionsPassed = true;
            for(Map.Entry<Integer, Integer> entry : offsetAndBusId.entrySet()) {
                long nextTimestamp = timestamp + entry.getKey();
                int currentBusID = entry.getValue();

                if(nextTimestamp % currentBusID != 0) {
                    allConditionsPassed = false;
                    break;
                } else {
                    if(!foundMulitples.contains(currentBusID)) {
                        foundMulitples.add(Long.valueOf(currentBusID));
                        incrementLength = foundMulitples.stream()
                                .reduce((a, b) -> a * b)
                                .get();
                    }
                }
            }

            if(allConditionsPassed) {
                return timestamp;
            }
        }

        return -1L;
    }
}
