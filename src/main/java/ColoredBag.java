import java.util.List;

public class ColoredBag {

    private final List<ColoredBag> bagsContained;

    private final String bagColor;

    private final int numberOfBags;

    private ColoredBag(ColoredBagBuilder builder) {
        bagsContained = builder.bagsContained;
        bagColor = builder.bagColor;
        numberOfBags = builder.numberOfBags;
    }

    public static ColoredBagBuilder builder() {
        return new ColoredBagBuilder();
    }

    public List<ColoredBag> bagsContained() {
        return bagsContained;
    }

    public String bagColor() {
        return bagColor;
    }

    public int numberOfBags() {
        return numberOfBags;
    }

    public static class ColoredBagBuilder {
        private String bagColor;

        private List<ColoredBag> bagsContained;

        private int numberOfBags;


        public ColoredBagBuilder bagColor(String bagColor) {
            this.bagColor = bagColor;
            return this;
        }

        public ColoredBagBuilder bagsContained(List<ColoredBag> bagsContained) {
            this.bagsContained = bagsContained;
            return this;
        }

        public ColoredBagBuilder numberOfBags(int numberOfBags) {
            this.numberOfBags = numberOfBags;
            return this;
        }

        public ColoredBag build() {
            return new ColoredBag(this);
        }
    }

}
