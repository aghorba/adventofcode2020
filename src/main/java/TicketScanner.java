import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class TicketScanner extends InputReader {

    public TicketScanner(String input) {
        super(input);
    }

    public TicketScanner(Path input) {
        super(input);
    }

    public int ticketScanErrorRate() {
        /*
        parsing the ranges:
        1. Check if the current line contains ":" and does NOT contain the word "ticket"
        2. Then split on ": ".
            a. Index 0 will be the field name
            b. Index 1 will be the range, e.g. "31-538 or 546-960"
        3. Split index 1 on " or " and calculate the ranges (store as hashmap)
        4. If line does NOT contain ":" and is NOT an empty string, split on ","
        5. Iterate through each fields ranges to check if each number matches or not
           a. If any number does not match ANY fields values, the ticket is invalid
         */
        Map<String, FieldRange> fieldsAndRanges = new HashMap<>();
        int errorRate = 0;
        for(int i = 0; i < data().size(); i++) {
            String ticketLine = data().get(i);

            if(ticketLine.contains("your ticket")) {
                i++;
                continue;
            }

            if(ticketLine.contains(":") && !ticketLine.contains("ticket")) {
                String[] fieldAndRange = ticketLine.split(": ");
                Map<Integer, Integer> ranges = Arrays.stream(fieldAndRange[1].split(" or "))
                        .collect(Collectors.toMap(
                                key -> Integer.parseInt(key.split("-")[0]),
                                value -> Integer.parseInt(value.split("-")[1])
                        ));
                FieldRange field = new FieldRange(fieldAndRange[0], ranges);

                fieldsAndRanges.put(field.fieldName(), field);
            } else if(!ticketLine.contains(":") && !ticketLine.equals("")) {
                List<Integer> ticket = findInvalidTicketValues(ticketLine, fieldsAndRanges);
                if(ticket.size() > 0) {
                    errorRate += ticket.stream()
                            .reduce(0, Integer::sum);
                }
            }
        }

        return errorRate;
    }

    public long selfTicketValue(FieldFilter filter) {
        Map<String, FieldRange> fieldsAndRanges = new HashMap<>();
        List<Integer> ownTicket = null;
        List<List<Integer>> validTickets = new ArrayList<>();
        for(int i = 0; i < data().size(); i++) {
            String ticketLine = data().get(i);

            if(ticketLine.contains("your ticket")) {
                i++;
                ownTicket = Arrays.stream(data().get(i).split(","))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
                continue;
            }

            if(ticketLine.contains(":") && !ticketLine.contains("ticket")) {
                String[] fieldAndRange = ticketLine.split(": ");
                Map<Integer, Integer> ranges = Arrays.stream(fieldAndRange[1].split(" or "))
                        .collect(Collectors.toMap(
                                key -> Integer.parseInt(key.split("-")[0]),
                                value -> Integer.parseInt(value.split("-")[1])
                        ));
                FieldRange field = new FieldRange(fieldAndRange[0], ranges);

                fieldsAndRanges.put(field.fieldName(), field);
            } else if(!ticketLine.contains(":") && !ticketLine.equals("")) {
                List<Integer> ticket = findInvalidTicketValues(ticketLine, fieldsAndRanges);

                if(ticket.size() == 0) {
                    validTickets.add(Arrays.stream(ticketLine.split(","))
                            .map(Integer::parseInt)
                            .collect(Collectors.toList()));
                }
            }
        }

        // Check the ranges against each value in a ticket vertically (instead of horizontally)
        Map<String, Integer> fieldIndexes = new HashMap<>();
        int maxFields = fieldsAndRanges.keySet().size();
        // Ranges are specified by "x or y". At least numRanges should match to be valid for a field
        int numRanges = 1;
        // Keep looping until all the field indexes are set
        while(fieldIndexes.keySet().size() != maxFields) {
            Map<String, FieldIndex> matchedValues = new HashMap<>();
            for(Map.Entry<String, FieldRange> entry : fieldsAndRanges.entrySet()) {
                String fieldName = entry.getKey();

                if(fieldIndexes.containsKey(fieldName)) {
                    continue;
                }

                FieldRange rangeValues = entry.getValue();
                Integer index = null;
                int validatedRanges = 0;
                int columnsMatched = 0;
                for(int fieldIndex = 0; fieldIndex < maxFields; fieldIndex++) {
                    if(fieldIndexes.containsValue(fieldIndex)) {
                        continue;
                    }

                    for(List<Integer> validTicket : validTickets) {
                        Integer ticketField = validTicket.get(fieldIndex);
                        long validRanges = rangeValues.ranges().entrySet().stream()
                                .filter(rangeEntry ->  ticketField >= rangeEntry.getKey()
                                        && ticketField <= rangeEntry.getValue())
                                .count();

                        if(validRanges != numRanges) {
                            validatedRanges = 0;
                            break;
                        } else {
                            validatedRanges++;
                        }
                    }

                    // Every value matched for this field's column
                    if(validatedRanges == validTickets.size()) {
                        validatedRanges = 0;
                        index = fieldIndex;
                        columnsMatched++;
                    }
                }

                if(columnsMatched > 0) {
                    matchedValues.put(fieldName, new FieldIndex(columnsMatched, index));
                }
            }

            for(Map.Entry<String, FieldIndex> entry : matchedValues.entrySet()) {
                FieldIndex value = entry.getValue();
                // Look for the column that matched exactly 1 of the fields
                if(value.matchedCount() == 1) {
                    fieldIndexes.put(entry.getKey(), value.index());
                }
            }
        }

        long multiplyResult = 1;
        for(Map.Entry<String, Integer> entry : fieldIndexes.entrySet()) {
            if(filter.equals(FieldFilter.DEPARTURE)) {
                if(!entry.getKey().startsWith(FieldFilter.DEPARTURE.name().toLowerCase())) {
                    continue;
                }
            }

            multiplyResult *= ownTicket.get(entry.getValue());
        }

        return multiplyResult;
    }

    private List<Integer> findInvalidTicketValues(String ticketLine, Map<String, FieldRange> fieldsAndRanges) {
        return Arrays.stream(ticketLine.split(","))
                .map(Integer::parseInt)
                .filter(number ->
                        fieldsAndRanges.values().stream()
                                .map(fieldRange -> fieldRange.ranges().entrySet()
                                        .stream()
                                        .filter(entry -> number < entry.getKey()
                                                || number > entry.getValue())
                                        .collect(Collectors.toList()))
                                .filter(list -> list.size() == 2)
                                .count() == fieldsAndRanges.keySet().size())
                .collect(Collectors.toList());
    }

    private static class FieldRange {
        private String fieldName;

        private Map<Integer, Integer> ranges;

        public FieldRange(String fieldName, Map<Integer, Integer> ranges) {
            this.fieldName = fieldName;
            this.ranges = ranges;
        }

        public String fieldName() {
            return fieldName;
        }

        public Map<Integer, Integer> ranges() {
            return ranges;
        }
    }

    private static class FieldIndex {
        private int matchedCount;
        private int index;

        public FieldIndex(int matchedCount, int index) {
            this.matchedCount = matchedCount;
            this.index = index;
        }

        public int matchedCount() {
            return matchedCount;
        }

        public int index() {
            return index;
        }
    }

}
