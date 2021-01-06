import java.nio.file.Path;

public class PasswordValidator extends InputReader {

    public PasswordValidator(String input) {
        super(input);
    }

    public PasswordValidator(Path input) {
        super(input);
    }

    public int validPasswords() {
        /*
        1. Split each line by space
        1-3 a: abcde -> 1-3, a:, abcde

        2. Split the index 0 by "-" to get the range of characters
            1-3 -> 1, 3

        3. Get index 0 of the string at index 1 of the first split array
            split[1].charAt(0) -> a
        4. Loop through index 2 of the first split array to double check the count for the password
         */

        return (int) data().stream()
                .map(line -> line.split(" "))
                .map(splitLine -> {
                    String[] rangeSplit = splitLine[0].split("-");
                    int min = Integer.parseInt(rangeSplit[0]);
                    int max = Integer.parseInt(rangeSplit[1]);
                    char letter = splitLine[1].charAt(0);

                    int letterCount = 0;
                    for(char passwordChar : splitLine[2].toCharArray()) {
                        if(passwordChar == letter) {
                            letterCount++;
                        }
                    }

                    return letterCount >= min && letterCount <= max;
                })
                .filter(valid -> valid)
                .count();
    }

    public int validPasswordPositions() {
        return (int) data().stream()
                .map(line -> line.split(" "))
                .map(splitLine -> {
                    String[] positionsSplit = splitLine[0].split("-");
                    int firstPosition = Integer.parseInt(positionsSplit[0]) - 1;
                    int secondPosition = Integer.parseInt(positionsSplit[1]) - 1;
                    char letter = splitLine[1].charAt(0);
                    String password = splitLine[2];

                    int numTimesCharFound = 0;
                    if(letter == password.charAt(firstPosition)) {
                        numTimesCharFound++;
                    }

                    if(letter == password.charAt(secondPosition)) {
                        numTimesCharFound++;
                    }

                    return numTimesCharFound == 1;
                })
                .filter(valid -> valid)
                .count();
    }
}
