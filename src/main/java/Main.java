public class Main {

    public static void main(String[] args) {
//        dayOne();
//        dayTwo();
//        dayThree();
//        dayFour();
//        dayFive();
//        daySix();
//        daySeven();
//        dayEight();
//        dayNine();
//        dayTen();
//        dayEleven();
//        dayTwelve();
//        dayThirteen();
//        dayFourteen();
//        dayFifteen();
//        daySixteen();
//        daySeventeen();
//        dayEighteen();
        dayNineteen();
    }

    public static void dayOne() {
        ExpenseReport report = new ExpenseReport("src\\main\\resources\\puzzle1_input.txt");
        System.out.println("Answer: " + report.fixExpenseReport(2020));
        System.out.println("Answer: " + report.fixExpenseReportThreeSum(2020));
    }

    public static void dayTwo() {
        PasswordValidator validator = new PasswordValidator("src\\main\\resources\\puzzle2_input.txt");
        System.out.println("Answer: " + validator.validPasswords());
        System.out.println("Answer: " + validator.validPasswordPositions());
    }

    public static void dayThree() {
        SledTrajectory sledMap = new SledTrajectory("src\\main\\resources\\puzzle3_input.txt");
        System.out.println("Answer: " + sledMap.treesEncountered(3, 1));

        int[][] trajectories = new int[][] {
                {1, 1},
                {3, 1},
                {5, 1},
                {7, 1},
                {1, 2},
        };
        System.out.println("Answer: " + sledMap.runMultipleTrajectories(trajectories));
    }

    public static void dayFour() {
        PassportProcessor processor = new PassportProcessor("src\\main\\resources\\puzzle4_input.txt");
        System.out.println("Answer: " + processor.numValidPassports());
        System.out.println("Answer: " + processor.numValidPassportsWithStrictRules());
    }

    private static void dayFive() {
        BoardingPassDecoder decoder = new BoardingPassDecoder("src\\main\\resources\\puzzle5_input.txt");
        System.out.println("Answer: " + decoder.highestSeatId());
        System.out.println("Answer: " + decoder.selfSeatId());
    }

    private static void daySix() {
        CustomsQuestionnaire questionnaire = new CustomsQuestionnaire("src\\main\\resources\\puzzle6_input.txt");
        System.out.println("Answer: " + questionnaire.yesCount());
        System.out.println("Answer: " + questionnaire.allYesCount());
    }

    private static void daySeven() {
        BaggageProcessor baggageProcessor = new BaggageProcessor("src\\main\\resources\\puzzle7_input.txt");
        System.out.println("Answer: " + baggageProcessor.numBagColors());
        System.out.println("Answer: " + baggageProcessor.bagsWithinGoldBag());
    }

    private static void dayEight() {
        BootCodeInterpreter interpreter = new BootCodeInterpreter("src\\main\\resources\\puzzle8_input.txt");
        System.out.println("Answer: " + interpreter.accumulatorValue());
        System.out.println("Answer: " + interpreter.accumulatorValueInfLoopFix());
    }

    private static void dayNine() {
        XMASDecoder decoder = new XMASDecoder("src\\main\\resources\\puzzle9_input.txt");
        System.out.println("Answer: " + decoder.decodeFirstNumber(25));
        System.out.println("Answer: " + decoder.findEncryptionWeakness(25));
    }

    private static void dayTen() {
        JoltageDifferences differences = new JoltageDifferences("src\\main\\resources\\puzzle10_input.txt");
        System.out.println("Answer: " + differences.findJoltageDifference());
        System.out.println("Answer: " + differences.distinctAdapters());
    }

    private static void dayEleven() {
        SeatingSystem seatingSystem = new SeatingSystem("src\\main\\resources\\puzzle11_input.txt");
        // System.out.println("Answer: " + seatingSystem.occupiedSeats());
        // System.out.println("Answer: " + seatingSystem.occupiedSeatsModifiedRules());
        System.out.println("Answer: " + seatingSystem.occupiedSeats(
                false,
                seatsOccupied -> seatsOccupied == 0,
                seatsOccupied -> seatsOccupied >= 4));
        System.out.println("Answer: " + seatingSystem.occupiedSeats(
                true,
                seatsOccupied -> seatsOccupied == 0,
                seatsOccupied -> seatsOccupied >= 5));
    }

    private static void dayTwelve() {
        ShipNavigation navigator = new ShipNavigation("src\\main\\resources\\puzzle12_input.txt");
        System.out.println("Answer: " + navigator.manhattanDistance());
        System.out.println("Answer: " + navigator.manhattanDistanceWithWaypoint());
    }

    private static void dayThirteen() {
        ShuttleSearch shuttleSearch = new ShuttleSearch("src\\main\\resources\\puzzle13_input.txt");
        System.out.println("Answer: " + shuttleSearch.busID());
        System.out.println("Answer: " + shuttleSearch.earliestTimestamp());
    }

    private static void dayFourteen() {
        DockingData dockingData = new DockingData("src\\main\\resources\\puzzle14_input.txt");
        System.out.println("Answer: " + dockingData.memoryValuesSum());
        System.out.println("Answer: " + dockingData.memorySumVersion2());
    }

    private static void dayFifteen() {
        NumbersGame game = new NumbersGame("src\\main\\resources\\puzzle15_input.txt");
        System.out.println("Answer: " + game.spokenNumber(2020));
        System.out.println("Answer: " + game.spokenNumber(30000000));
    }

    private static void daySixteen() {
        TicketScanner scanner = new TicketScanner("src\\main\\resources\\puzzle16_input.txt");
        System.out.println("Answer: " + scanner.ticketScanErrorRate());
        System.out.println("Answer: " + scanner.selfTicketValue(FieldFilter.DEPARTURE));
    }

    private static void daySeventeen() {
        CubeSimulation simulation = new CubeSimulation("src\\main\\resources\\puzzle17_input.txt");
        System.out.println("Answer: " + simulation.activeCubes(6));
        //TODO: DOES NOT WORK
        System.out.println("Answer: " + simulation.activeCubesWithW(6));
    }

    private static void dayEighteen() {
        OperationOrder op = new OperationOrder("src\\main\\resources\\puzzle18_input.txt");
        System.out.println("Answer: " + op.sum(OperatorPrecedence.LEFT_TO_RIGHT));
        System.out.println("Answer: " + op.sum(OperatorPrecedence.PLUS_FIRST));
    }

    private static void dayNineteen() {
        MessageValidation messageValidation = new MessageValidation("src\\main\\resources\\puzzle19_input.txt");
        System.out.println("Answer: " + messageValidation.validMessages());
    }
}
