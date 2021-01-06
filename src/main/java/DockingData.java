import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DockingData extends InputReader {

    public DockingData(String input) {
        super(input);
    }

    public DockingData(Path input) {
        super(input);
    }

    public long memoryValuesSum() {
        Map<Integer, Long> memory = new HashMap<>();
        char[] mask = null;
        for(String dockDataLine : data()) {
            if(dockDataLine.startsWith("mask")) {
                mask = parseMask(dockDataLine);
            } else {
                String[] memLineSplit = dockDataLine.split(" = ");
                int address = Integer.parseInt(parseAddress(memLineSplit[0]));
                char[] value = decimalToBinary(memLineSplit[1]);
                long valueToWrite = applyMask(mask, value);
                memory.put(address, valueToWrite);
            }
        }

        return memory.entrySet().stream()
                .map(entry -> entry.getValue())
                .reduce(0L, Long::sum);
    }

    public long memorySumVersion2() {
        Map<Long, Long> memory = new HashMap<>();
        char[] mask = null;
        for(String dockDataLine : data()) {
            if(dockDataLine.startsWith("mask")) {
                mask = parseMask(dockDataLine);
            } else {
                String[] memLineSplit = dockDataLine.split(" = ");
                char[] address = decimalToBinary(parseAddress(memLineSplit[0]));
                List<String> addresses = calculateAddresses(mask, address);
                addresses.stream()
                        .forEach(addr -> memory.put(
                                Long.parseLong(addr, 2), Long.valueOf(memLineSplit[1])));
            }
        }

        return memory.entrySet().stream()
                .map(entry -> entry.getValue())
                .reduce(0L, Long::sum);
    }

    private List<String> calculateAddresses(char[] mask, char[] address) {
        int floatingBits = 0;
        for(int i = 0; i < mask.length; i++) {
            if(mask[i] != '0') {
                address[i] = mask[i];
            }

            if(mask[i] == 'X') {
                floatingBits++;
            }
        }

        List<String> addresses = new ArrayList<>();
        int permutations = (int) Math.pow(2.0, floatingBits * 1.0);

        for(int i = 0; i <= permutations; i++) {
            String stringToPad = Integer.toBinaryString(i);
            String binary = padZeroes(floatingBits - stringToPad.length(), stringToPad);
            StringBuilder addressPermutation = new StringBuilder();
            int binaryStringIndex = 0;
            for(char addressBit : address) {
                if(addressBit == 'X') {
                    addressPermutation.append(binary.charAt(binaryStringIndex));
                    binaryStringIndex++;
                    continue;
                }

                addressPermutation.append(addressBit);
            }

            addresses.add(addressPermutation.toString());
        }

        return addresses;
    }

    private String parseAddress(String s) {
        return s.replaceAll("mem", "")
                .replaceAll("\\[", "")
                .replaceAll("\\]", "");
    }

    private long applyMask(char[] mask, char[] value) {
        for(int i = 0; i < mask.length; i++) {
            if(mask[i] != 'X') {
                value[i] = mask[i];
            }
        }

        return Long.parseLong(String.valueOf(value), 2);
    }

    private char[] decimalToBinary(String s) {
        String binaryRepresentation = Long.toBinaryString(Long.parseLong(s));
        int zeroesToPad = 36 - binaryRepresentation.length();

        return padZeroes(zeroesToPad, binaryRepresentation).toCharArray();
    }

    private String padZeroes(int zeroesToPad, String stringToPad) {
        StringBuilder zeroPad = new StringBuilder();

        for(int i = 0; i < zeroesToPad; i++) {
            zeroPad.append("0");
        }

        zeroPad.append(stringToPad);
        return zeroPad.toString();
    }

    private char[] parseMask(String dockDataLine) {
        return dockDataLine.split(" = ")[1].toCharArray();
    }

}
