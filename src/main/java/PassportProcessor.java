import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;

public class PassportProcessor extends InputReader {

    private final List<String> passportFields = List.of(
            "byr",
            "iyr",
            "eyr",
            "hgt",
            "hcl",
            "ecl",
            "pid",
            "cid"
    );

    public PassportProcessor(String input) {
        super(input);
    }

    public PassportProcessor(Path input) {
        super(input);
    }

    public int numValidPassports() {
        /*
        The expected fields are as follows:

            byr (Birth Year)
            iyr (Issue Year)
            eyr (Expiration Year)
            hgt (Height)
            hcl (Hair Color)
            ecl (Eye Color)
            pid (Passport ID)
            cid (Country ID) - OPTIONAL. Not required.

            Passports are separated by blank lines.

       1. Check if current line is an empty string. Skip if true (indicates end of passport)
       2. Split each line by space and then each split line by :
       3. Validate each field is present minus Country ID (which is optional)
          a. If a field is missing, mark as invalid
          b. If all fields (or only Country ID is missing), mark as valid
         */
        Map<String, String> passport = new HashMap<>();
        int numValid = 0;
        for(String passportLine : data()) {
            if(passportLine.equals("")) {
                if(validatePassport(passport)) {
                    numValid++;
                }

                passport.clear();
                continue;
            }

            Arrays.stream(passportLine.split(" "))
                    .map(fields -> fields.split(":"))
                    .forEach(keyValues -> passport.put(keyValues[0], keyValues[1]));
        }

        // Check last passport
        if(validatePassport(passport)) {
            numValid++;
        }

        return numValid;
    }

    public int numValidPassportsWithStrictRules() {
        /*
            You can continue to ignore the cid field, but each other field has strict rules about
            what values are valid for automatic validation:

            byr (Birth Year) - four digits; at least 1920 and at most 2002.
            iyr (Issue Year) - four digits; at least 2010 and at most 2020.
            eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
            hgt (Height) - a number followed by either cm or in:
                If cm, the number must be at least 150 and at most 193.
                If in, the number must be at least 59 and at most 76.
            hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
            ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
            pid (Passport ID) - a nine-digit number, including leading zeroes.
            cid (Country ID) - ignored, missing or not.
        */
        Map<String, String> passport = new HashMap<>();
        int numValid = 0;
        for(String passportLine : data()) {
            if(passportLine.equals("")) {
                // Do initial validation
                if(validatePassport(passport)) {
                    // Perform strict rules validation
                    if(validatePassportRules(passport)) {
                        numValid++;
                    }
                }

                passport.clear();
                continue;
            }

            Arrays.stream(passportLine.split(" "))
                    .map(fields -> fields.split(":"))
                    .forEach(keyValues -> passport.put(keyValues[0], keyValues[1]));
        }

        // Check last passport
        if(validatePassport(passport)) {
            if(validatePassportRules(passport)) {
                numValid++;
            }
        }

        return numValid;
    }

    private boolean validatePassport(Map<String, String> passport) {
        if(passport.keySet().size() < 7) {
            return false;
        } else if(passport.keySet().size() == 7 && passport.get("cid") != null) {
            return false;
        }

        return true;
    }

    private boolean validatePassportRules(Map<String, String> passport) {
        Passport.PassportBuilder builder = Passport.builder();
        for(String field : passportFields) {
            switch(field) {
                case "byr":
                    builder.birthYear(Integer.parseInt(passport.get(field)));
                    break;
                case "iyr":
                    builder.issueYear(Integer.parseInt(passport.get(field)));
                    break;
                case "eyr":
                    builder.expirationYear(Integer.parseInt(passport.get(field)));
                    break;
                case "hgt":
                    builder.height(passport.get(field));
                    break;
                case "hcl":
                    builder.hairColor(passport.get(field));
                    break;
                case "ecl":
                    builder.eyeColor(passport.get(field));
                    break;
                case "pid":
                    builder.passportId(passport.get(field));
                    break;
            }
        }

        Passport passportToCheck = builder.build();
        List<Boolean> validationResults = List.of(
                validateField(passportToCheck.birthYear(), birthYear -> birthYear >= 1920 && birthYear <= 2002),
                validateField(passportToCheck.issueYear(), issueYear -> issueYear >= 2010 && issueYear <= 2020),
                validateField(passportToCheck.expirationYear(),
                        expirationYear -> expirationYear >= 2020 && expirationYear <= 2030),
                validateField(passportToCheck.height(),
                        height -> {
                            boolean valid = true;
                            if(Pattern.matches("\\d+cm", height)) {
                                int cmHeight = Integer.parseInt(height.split("cm")[0]);

                                if(cmHeight < 150 || cmHeight > 193) {
                                    valid = false;
                                }
                            } else if(Pattern.matches("\\d+in", height)) {
                                int inHeight = Integer.parseInt(height.split("in")[0]);

                                if(inHeight < 59 || inHeight > 76) {
                                    valid = false;
                                }
                            } else {
                                valid = false;
                            }

                            return valid;
                        }),
                validateField(passportToCheck.hairColor(),
                        hairColor -> hairColor.charAt(0) == '#' && Pattern.matches("#[0-9|a-f]{6}", hairColor)),
                validateField(passportToCheck.eyeColor(), eyeColor -> List.of("amb", "blu", "brn", "gry",
                        "grn", "hzl", "oth").stream()
                        .filter(color -> eyeColor.equals(color))
                        .count() == 1),
                validateField(passportToCheck.passportId(), passportId -> Pattern.matches("\\d{9}", passportId))
        );

        return validationResults.stream()
                .filter(valid -> valid)
                .count() == 7;
    }

    private <T> boolean validateField(T field, Function<T, Boolean> validationFunction) {
        return validationFunction.apply(field);
    }
}
