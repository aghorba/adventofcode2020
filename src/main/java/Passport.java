import java.util.Optional;

public class Passport {
    /*
        byr (Birth Year)
        iyr (Issue Year)
        eyr (Expiration Year)
        hgt (Height)
        hcl (Hair Color)
        ecl (Eye Color)
        pid (Passport ID)
        cid (Country ID) - OPTIONAL. Not required.
     */

    private final int birthYear;

    private final Optional<Integer> countryId;

    private final int expirationYear;

    private final String eyeColor;

    private final String hairColor;

    private final String height;

    private final int issueYear;

    private final String passportId;

    private Passport(PassportBuilder builder) {
        birthYear = builder.birthYear;
        countryId = Optional.ofNullable(builder.countryId);
        expirationYear = builder.expirationYear;
        eyeColor = builder.eyeColor;
        hairColor = builder.hairColor;
        height = builder.height;
        issueYear = builder.issueYear;
        passportId = builder.passportId;
    }

    public static PassportBuilder builder() {
        return new PassportBuilder();
    }

    public int birthYear() {
        return birthYear;
    }

    public Optional<Integer> countryId() {
        return countryId;
    }

    public int expirationYear() {
        return expirationYear;
    }

    public String eyeColor() {
        return eyeColor;
    }

    public String hairColor() {
        return hairColor;
    }

    public String height() {
        return height;
    }

    public int issueYear() {
        return issueYear;
    }

    public String passportId() {
        return passportId;
    }

    public static class PassportBuilder {

        private int birthYear;

        private Integer countryId;

        private int expirationYear;

        private String eyeColor;

        private String hairColor;

        private String height;

        private int issueYear;

        private String passportId;

        private PassportBuilder() {

        }

        public PassportBuilder birthYear(int birthYear) {
            this.birthYear = birthYear;
            return this;
        }

        public PassportBuilder countryId(Integer countryId) {
            this.countryId = countryId;
            return this;
        }

        public PassportBuilder expirationYear(int expirationYear) {
            this.expirationYear = expirationYear;
            return this;
        }

        public PassportBuilder eyeColor(String eyeColor) {
            this.eyeColor = eyeColor;
            return this;
        }

        public PassportBuilder hairColor(String hairColor) {
            this.hairColor = hairColor;
            return this;
        }

        public PassportBuilder height(String height) {
            this.height = height;
            return this;
        }

        public PassportBuilder issueYear(int issueYear) {
            this.issueYear = issueYear;
            return this;
        }

        public PassportBuilder passportId(String passportId) {
            this.passportId = passportId;
            return this;
        }

        public Passport build() {
            return new Passport(this);
        }

    }
}
