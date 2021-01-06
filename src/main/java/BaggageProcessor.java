import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class BaggageProcessor extends InputReader {

    private Map<String, List<ColoredBag>> adjacencyLists;

    public BaggageProcessor(String input) {
        super(input);
        initialize();
    }

    public BaggageProcessor(Path input) {
        super(input);
        initialize();
    }



    public int numBagColors() {
        /*
        BWB -> SGB       - 1
        MYB -> SBW, etc. - 1
        DOB -> BWB, MYB  - 1
        LRB -> BWB, MYB  - 1

        1. Parse the input and build up a graph (adjacency lists)
        2. Then use each node as a starting point and traverse till we reach the shiny gold bag.
        3. Once the shiny gold bag has been reached, add 1 to the total
         */

        int bagsContainingGold = 0;
        for (String bagColor : adjacencyLists.keySet()) {
            // Ignore self
            if (bagColor.startsWith("shiny gold")) {
                continue;
            }

            bagsContainingGold += traverseBags(bagColor, new HashSet<>(), 0);
        }

        return bagsContainingGold;
    }

    public int bagsWithinGoldBag() {
        int bagsWithinGold = 0;


        for(ColoredBag coloredBag : adjacencyLists.get("shiny gold bags")) {
            bagsWithinGold += coloredBag.numberOfBags() + (coloredBag.numberOfBags() *
                    traverseBags(coloredBag, 0, new HashSet<>()));
        }
        /*
        shiny gold bags -> 1 dark olive bag, 2 vibrant plum bags
                        ->
         */
        return bagsWithinGold;
    }

    private int traverseBags(ColoredBag bag, int bagCount, Set<ColoredBag> visited) {
        visited.add(bag);

        int totalBagCount = 0;
        for(ColoredBag adjacentBag : adjacencyLists.get(bag.bagColor())) {
            if(!visited.contains(adjacentBag.bagColor())) {
                totalBagCount += adjacentBag.numberOfBags() + adjacentBag.numberOfBags() *
                        traverseBags(adjacentBag, adjacentBag.numberOfBags(), visited);
            }
        }

        return totalBagCount;
    }

    private int traverseBags(String bagColor, Set<String> visited, int bagsContainingGold) {
        visited.add(bagColor);

        if(bagColor.equals("shiny gold bags")) {
            return bagsContainingGold + 1;
        }

        for(ColoredBag adjacentBag : adjacencyLists.get(bagColor)) {
            if(!visited.contains(adjacentBag.bagColor())) {
                bagsContainingGold = traverseBags(adjacentBag.bagColor(), visited, bagsContainingGold);
            }
        }

        return bagsContainingGold;
    }

    private void initialize() {
        adjacencyLists = new HashMap<>();
        parseRules();
    }

    private void parseRules() {
        /*
        Parsing the input:
        1. A period represents the end of a rule
        2. Template: <bag color> bags contain < "no" | [0-9] > <color> <,> <color> ... . (end of rule)
         */

        /*
        "dark orange contain 3 bright white , 4 muted yellow"
        "bright white contain 1 shiny gold "
        "striped cyan contain 1 bright black , 4 posh cyan , 2 striped purple , 1 pale fuchsia"
        "faded blue contain no other "

          a. "dark orange bags contain 3 bright white bags, 4 muted yellow bags."
          b. "bright white bags contain 1 shiny gold bag."
          c. "faded blue bags contain no other bags.",
          d. "striped cyan bags contain 1 bright black bag, 4 posh cyan bags, 2 striped purple bags, 1 pale fuchsia bag."

        Split on " bags contain "
        [0]: bag color
        [1]: < "no" | [0-9] > <color> <,> <color> ...

        a. [0]: "dark orange"
           [1]: "3 bright white bags, 4 muted yellow bags."
        b. [0]: "bright white"
           [1]: "1 shiny gold bag."
        c. [0]: "faded blue"
           [1]: "no other bags."
        d. [0]: "striped cyan"
           [1]: "1 bright black bag, 4 posh cyan bags, 2 striped purple bags, 1 pale fuchsia bag."

           "1 bright black bag, 4 posh cyan bags, 2 striped purple bags, 1 pale fuchsia bag."
           ->
           "1 bright black"
           "4 posh cyan"
           "2 striped purple"
           "1 pale fuchsia bag."

           Conditions:
           1. Contains "no other" -> 0
           2. Does NOT contain "," -> split on " bag"
           3. Does contain "," -> split on " bag[s?], "

         */
        data().stream()
                .map(rule -> rule.replaceAll("\\.", ""))
                .map(rule -> rule.split(" contain "))
                .forEach(rule -> {
                    String bagColor = rule[0];
                    String description = rule[1];

                    if ((description.contains("no other"))) {
                        adjacencyLists.put(bagColor, new ArrayList<>());
                    } else {
                        List<ColoredBag> bagsContained = Arrays.stream(description.split(", "))
                                .map(bagRule -> bagRule.split(" ", 2))
                                .map(numBagAndColor -> {
                                    int numberOfBags = Integer.parseInt(numBagAndColor[0]);
                                    return ColoredBag.builder()
                                            .numberOfBags(numberOfBags)
                                            .bagColor(numberOfBags == 1
                                                    ? numBagAndColor[1].replaceAll("bag", "bags")
                                                    : numBagAndColor[1])
                                            .build();
                                })
                                .collect(Collectors.toList());
                        adjacencyLists.put(bagColor, bagsContained);
                    }
                });
    }
}
