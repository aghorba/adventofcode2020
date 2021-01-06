import java.nio.file.Path;
import java.util.*;

public class MessageValidation extends InputReader {

    public MessageValidation(String input) {
        super(input);
    }

    public MessageValidation(Path input) {
        super(input);
    }

    public long validMessages() {
        /*

        0: 4 1 5
        1: 2 3 | 3 2
        2: 4 4 | 5 5
        3: 4 5 | 5 4
        4: "a"
        5: "b"

         */
        long numValid = 0;
        Map<Integer, Rule> adjacencyLists = buildGraph();
        Set<String> codesMatchingRuleZero = generateCodesFrom(adjacencyLists, 0);
        boolean startMessageParsing = false;
        for(String line : data()) {
            if(startMessageParsing) {
                if(codesMatchingRuleZero.contains(line)) {
                    numValid++;
                }

                continue;
            }

            if(line.equals("")) {
                startMessageParsing = true;
            }
        }

        return numValid;
    }

    private Set<String> generateCodesFrom(Map<Integer, Rule> adjacencyLists, int startingRule) {
        List<List<String>> possibleCodes = new ArrayList<>();

        traverseGraph(new HashSet<>(), possibleCodes, new StringBuilder(), adjacencyLists, startingRule);

        // Initially load with the first part of each possible code
        List<String> formFullCodes = new ArrayList<>();
        for(String partialCode : possibleCodes.get(0)) {
            formFullCodes.add(partialCode);
        }

        for(int i = 1; i < possibleCodes.size(); i++) {
            List<String> incompleteCode =  possibleCodes.get(i);

            for(String partialCode : incompleteCode) {
                for(int k = 0; k < formFullCodes.size(); k++) {
                    String incomplete = formFullCodes.get(k);
                    String updated = incomplete + partialCode;
                    formFullCodes.set(k, updated);
                }
            }
        }

        return new HashSet<>(formFullCodes);
    }

    private void traverseGraph(
            Set<Integer> visited,
            List<List<String>> codes,
            List<String> currentCode,
            Map<Integer, Rule> adjacencyLists,
            int ruleNum) {

        visited.add(ruleNum);

        String messageCharacter = adjacencyLists.get(ruleNum).messageCharacter();
        if(messageCharacter != null) {
            currentCode.add(messageCharacter);
            return;
        }

        for(List<Integer> ruleNums : adjacencyLists.get(ruleNum).ruleReferences()) {
            for(int rule : ruleNums) {
                if(!visited.contains(rule)) {
                    traverseGraph(visited, codes, currentCode, adjacencyLists, rule);
                    visited.remove(rule);
//                    if(currentCode.length() > 0) {
//                        currentCode.setLength(currentCode.length() - 1);
//                    }
                }
            }
        }

//        codes.add(currentCode.toString());
    }

    private Map<Integer, Rule> buildGraph() {
        Map<Integer, Rule> adjacencyLists = new HashMap<>();

        for(String line : data()) {
            if(line.equals("")) {
                break;
            }

            String[] ruleNumAndDescription = line.split(": ");
            Integer ruleNum = Integer.parseInt(ruleNumAndDescription[0]);
            List<List<Integer>> ruleReferences = new ArrayList<>();
            String[] rules = ruleNumAndDescription[1].split(" ");

            List<Integer> ruleSet = new ArrayList<>();
            Rule fullRule = new Rule();
            for(String rule : rules) {
                if(rule.equals("|")) {
                    ruleReferences.add(new ArrayList<>(ruleSet));
                    ruleSet.clear();
                    continue;
                }

                if(rule.contains("a") || rule.contains("b")) {
                    fullRule.setMessageCharacter(rule.replaceAll("\"", ""));
                    continue;
                }

                ruleSet.add(Integer.parseInt(rule));
            }

            ruleReferences.add(ruleSet);
            fullRule.setRuleReferences(ruleReferences);
            adjacencyLists.put(ruleNum, fullRule);
        }

        return adjacencyLists;
    }

    private static class Rule {

        private List<List<Integer>> ruleReferences;

        private String messageCharacter;

        public void setRuleReferences(List<List<Integer>> ruleReferences) {
            this.ruleReferences = ruleReferences;
        }

        public void setMessageCharacter(String messageCharacter) {
            this.messageCharacter = messageCharacter;
        }

        public String messageCharacter() {
            return messageCharacter;
        }

        public List<List<Integer>> ruleReferences() {
            return ruleReferences;
        }

    }
}
