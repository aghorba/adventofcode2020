import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BoardingPassDecoder extends InputReader {

    public BoardingPassDecoder(String input) {
        super(input);
    }

    public BoardingPassDecoder(Path input) {
        super(input);
    }

    public int highestSeatId() {
        List<Integer> seatIds = getSeatIds();

        int max = 0;
        for(int seatId : seatIds) {
            if(seatId > max) {
                max = seatId;
            }
        }

        return max;
    }

    public int selfSeatId() {
        List<Integer> seatIds = getSeatIds();
        Collections.sort(seatIds);

        int selfId = 0;
        for(int i = 1; i < seatIds.size(); i++) {
            int currentId = seatIds.get(i);
            int previousId = seatIds.get(i - 1);

            if(currentId - previousId > 1) {
                selfId = currentId - 1;
                break;
            }
        }

        return selfId;
    }

    private List<Integer> getSeatIds() {
        return data().stream()
                .map(boardingPass -> {
                    String rowId = boardingPass.substring(0, 7);
                    String columnId = boardingPass.substring(7);
                    int row = binarySearchRow(rowId);
                    int column = binarySearchColumn(columnId);

                    return (row * 8) + column;
                })
                .collect(Collectors.toList());
    }

    private int binarySearchRow(String rowId) {
        int lowRow = 0;
        int highRow = 127;
        int rowIdIndex = 0;

        while(lowRow < highRow && rowIdIndex < rowId.length()) {
            int midIndex = lowRow + (highRow - lowRow) / 2;

            if(rowId.charAt(rowIdIndex) == 'F') {
                highRow = midIndex;
            } else if(rowId.charAt(rowIdIndex) == 'B') {
                lowRow = midIndex + 1;
            }

            rowIdIndex++;
        }

        return rowId.charAt(rowIdIndex - 1) == 'F'
                ? lowRow
                : highRow;
    }

    private int binarySearchColumn(String columnId) {
        int lowColumn = 0;
        int highColumn = 7;
        int columnIdIndex = 0;

        while(lowColumn < highColumn && columnIdIndex < columnId.length()) {
            int midColumn = lowColumn + (highColumn - lowColumn) / 2;

            if(columnId.charAt(columnIdIndex) == 'L') {
                highColumn = midColumn;
            } else if(columnId.charAt(columnIdIndex) == 'R') {
                lowColumn = midColumn + 1;
            }

            columnIdIndex++;
        }

        return columnId.charAt(columnIdIndex - 1) == 'L'
                ? lowColumn
                : highColumn;
    }
}
