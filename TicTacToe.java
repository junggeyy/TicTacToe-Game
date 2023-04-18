import java.util.*;


public class TicTacToe {
    static ArrayList<Integer> playerMoves = new ArrayList<>();
    static ArrayList<Integer> computerMoves = new ArrayList<>();

    static ArrayList<Integer> validCells = new ArrayList<>();
    public static void main(String[] args) throws InterruptedException {
        Scanner input = new Scanner(System.in);

        String[][] boardParts = {{"|", "---", "|", "---", "|", "---", "|"},
                {"|", "   ", "|", "   ", "|", "   ", "|"},
                {"|", "---", "|", "---", "|", "---", "|"},
                {"|", "   ", "|", "   ", "|", "   ", "|"},
                {"|", "---", "|", "---", "|", "---", "|"},
                {"|", "   ", "|", "   ", "|", "   ", "|"},
                {"|", "---", "|", "---", "|", "---", "|"}
        };
        gameBoard(boardParts);        //display the first game board
        int[] cellPlaces = {11, 12, 13, 21, 22, 23, 31, 32, 33};    //array of valid cell place
        for (int i: cellPlaces){
            validCells.add(i);      //creating a validCells list to compare user's input
        }
        System.out.println("---------------------------------------");
        System.out.println("Who do you want to start the game?");
        System.out.println("1. User");
        System.out.println("2. Computer");
        System.out.println("----------------------------------------");
        int firstCall = input.nextInt();
        while(true){        //check if user entered any other number
            if(firstCall==1 || firstCall==2){
                break;
            }
            else{
                System.out.println("Please enter a valid number(1 or 2):");
                firstCall = input.nextInt();
            }
        }
        while(true ){
            if (firstCall==1) {
                UserFirst(input, cellPlaces, boardParts);
            }
            else {
               firstComputerMove(boardParts, cellPlaces, input);  //first computer move
            }
        }
    }

    public static void gameBoard(String[][] boardParts) {
        for (int i = 0; i <= 6; i++) {
            for (int j = 0; j <= 6; j++) {
                System.out.print(boardParts[i][j]);
            }
            System.out.println();
        }
    }

    public static void updatePosition(String[][] boardParts, int position, String mark) {
        switch (position) {
            case 11:
                boardParts[1][1] = " " + mark + " ";
                break;
            case 12:
                boardParts[1][3] = " " + mark + " ";
                break;
            case 13:
                boardParts[1][5] = " " + mark + " ";
                break;
            case 21:
                boardParts[3][1] = " " + mark + " ";
                break;
            case 22:
                boardParts[3][3] = " " + mark + " ";
                break;
            case 23:
                boardParts[3][5] = " " + mark + " ";
                break;
            case 31:
                boardParts[5][1] = " " + mark + " ";
                break;
            case 32:
                boardParts[5][3] = " " + mark + " ";
                break;
            case 33:
                boardParts[5][5] = " " + mark + " ";
                break;
        }
    }

    public static String winningConditions() {
        List<Integer> row1 = Arrays.asList(11, 12, 13);
        List<Integer> row2 = Arrays.asList(21, 22, 23);
        List<Integer> row3 = Arrays.asList(31, 32, 33);
        List<Integer> col1 = Arrays.asList(11, 21, 31);
        List<Integer> col2 = Arrays.asList(12, 22, 32);
        List<Integer> col3 = Arrays.asList(13, 23, 33);
        List<Integer> cross1 = Arrays.asList(11, 22, 33);
        List<Integer> cross2 = Arrays.asList(31, 22, 13);

        List<List<Integer>> winning = new ArrayList<>();
        winning.add(row1);
        winning.add(row2);
        winning.add(row3);
        winning.add(col1);
        winning.add(col2);
        winning.add(col3);
        winning.add(cross1);
        winning.add(cross2);

        for (List<Integer> i : winning) {
            if (playerMoves.containsAll(i)) {
                return "Congratulations! You won.";
            } else if (computerMoves.containsAll(i)) {
                return "Sorry! The computer won.";
            }
        }
        if (playerMoves.size() + computerMoves.size() == 9) {    //if all the squares are filled without any win
            return "Game Over!";
        }
        return "";
    }

    public static int userInt;     //using this class variable to take user value from userFirst() to ifUserFirst()-AI
    public static void userMove(Scanner input,int[] cellPlaces, String [][] boardParts) throws InterruptedException {
        System.out.print("Enter a cell number(11-first row, first column): ");
        userInt = input.nextInt();
        while(!validCells.contains(userInt)){     //check if entered input is valid
            System.out.println("Invalid cell!!");
            System.out.println("Enter number again: ");
            userInt = input.nextInt();
        }

        while (playerMoves.contains(userInt) || computerMoves.contains(userInt) ) {     //check if cell is occupied
            System.out.println("Cell already taken!");
            System.out.print("Enter another cell number:");
            userInt = input.nextInt();
        }
        String mark = "X";     //user's mark is X
        playerMoves.add(userInt);     //records player moves in list
        updatePosition(boardParts, userInt, mark);   //updates the gameBoard with mark(s)

        String winner = winningConditions();    //check for winner each loop
        if (winner.length() > 0) {
            gameBoard(boardParts);
            System.out.println(winner);
            System.out.println("Do you want to play again?(y/n)");
            String playMore = input.next();
            if(playMore.equalsIgnoreCase("y")){
                TicTacToe.computerMoves.clear();
                TicTacToe.playerMoves.clear();
                main(null);
            }
            else {
                System.exit(0);
            }
        }
        gameBoard(boardParts);
        computerMove(input, cellPlaces, boardParts);
    }

    public static void computerMove(Scanner input, int[] cellPlaces,String[][] boardParts) throws InterruptedException {
        int computerPlace = smartMove();
        if(computerPlace == -1) {
            int randomIndex = new Random().nextInt(cellPlaces.length);  //random index of cellPlaces for computer
            computerPlace = cellPlaces[randomIndex];         //taking the cell place number with random index
            while (playerMoves.contains(computerPlace) || computerMoves.contains(computerPlace)) {
                randomIndex = new Random().nextInt(cellPlaces.length);  //random index initializer
                computerPlace = cellPlaces[randomIndex];
            }
       }
        String mark = "O";     //computer's mark is O;
        computerMoves.add(computerPlace);       //records computers move in list
        updatePosition(boardParts, computerPlace, mark); //update the gameBoard
        for (int i = 0; i < 1; i++) {       //to make board show up after 5 seconds
            System.out.print("Loading..");
            Thread.sleep(5000);
            System.out.println();
        }
        String winner = winningConditions();       //check for winner each loop
        if (winner.length() > 0) {
            gameBoard(boardParts);
            System.out.println(winner);
            System.out.println("Do you want to play again?(y/n)");
            String playMore = input.next();
            if(playMore.equalsIgnoreCase("y")){
                TicTacToe.computerMoves.clear();
                TicTacToe.playerMoves.clear();
                main(null);
            }
            else {
                System.exit(0);
            }

        }
        gameBoard(boardParts);      //prints board after each move
        userMove(input, cellPlaces, boardParts);
        }
    public static void firstComputerMove(String[][] boardParts, int[] cellPlaces, Scanner input) throws InterruptedException {
        int[] cellPlaces01 = {11, 13, 22, 31, 33};       //center and corner cells
        String mark="0";
        int randomIndex = new Random().nextInt(cellPlaces01.length);  //random index of cellPlaces for computer
        int computerPlace = cellPlaces01[randomIndex];         //taking the cell place number with random index
        computerMoves.add(computerPlace);       //records computers move in list
        updatePosition(boardParts, computerPlace, mark); //update the gameBoard
        gameBoard(boardParts);
        userMove(input, cellPlaces, boardParts);
    }
    public static void UserFirst(Scanner input,int[] cellPlaces, String [][] boardParts) throws InterruptedException {  //user
        System.out.print("Enter a cell number(11-first row, first column): ");
        userInt = input.nextInt();
        while(!validCells.contains(userInt)){     //check if entered input is valid
            System.out.println("Invalid cell!!");
            System.out.println("Enter number again: ");
            userInt = input.nextInt();
        }
        String mark = "X";     //user's mark is X
        playerMoves.add(userInt);     //records player moves in list
        updatePosition(boardParts, userInt, mark);   //updates the gameBoard with mark(s)
        gameBoard(boardParts);
        ifUserFirst(input, cellPlaces, boardParts, userInt);
    }   //user
    public static void ifUserFirst(Scanner input,int[] cellPlaces, String[][] boardParts, int firstMove) throws InterruptedException {      //computer
        int[] cellPlaces01 = {11, 13, 31, 33};
        String mark="0";
        int computerPlace = 22;
        if(firstMove==22) {
            int randomIndex = new Random().nextInt(cellPlaces01.length);  //random index of cellPlaces for computer
            computerPlace = cellPlaces01[randomIndex];         //taking the cell place number with random index
        }
        computerMoves.add(computerPlace);       //records computers move in list
        updatePosition(boardParts, computerPlace, mark); //update the gameBoard
        for(int i = 0; i<1;i++) {//to make board show up after 5 seconds
            System.out.print("Loading..");
            Thread.sleep(5000);
            System.out.println();
        }
        gameBoard(boardParts);
        userMove(input, cellPlaces, boardParts);
    }
    public static int smartMove() {
        //for computer winning
        //for row1
        if(computerMoves.contains(11) && computerMoves.contains(12)){
            if(!playerMoves.contains(13) && !computerMoves.contains(13)){
                return 13;
            }
        }
        if(computerMoves.contains(11) && computerMoves.contains(13)){
            if(!playerMoves.contains(12) && !computerMoves.contains(12)){
                return 12;
            }
        }
        if(computerMoves.contains(13) && computerMoves.contains(12)){
            if(!playerMoves.contains(11) && !computerMoves.contains(11)){
                return 11;
            }
        }
        //for row2
        if(computerMoves.contains(21) && computerMoves.contains(22)){
            if(!playerMoves.contains(23) && !computerMoves.contains(23)){
                return 23;
            }
        }
        if(computerMoves.contains(21) && computerMoves.contains(23)){
            if(!playerMoves.contains(22) && !computerMoves.contains(22)){
                return 22;
            }
        }
        if(computerMoves.contains(22) && computerMoves.contains(23)){
            if(!playerMoves.contains(21) && !computerMoves.contains(21)){
                return 21;
            }
        }
        //for row3
        if(computerMoves.contains(31) && computerMoves.contains(32)){
            if(!playerMoves.contains(33) && !computerMoves.contains(33)){
                return 33;
            }
        }
        if(computerMoves.contains(31) && computerMoves.contains(33)){
            if(!playerMoves.contains(32) && !computerMoves.contains(32)){
                return 32;
            }
        }
        if(computerMoves.contains(32) && computerMoves.contains(33)){
            if(!playerMoves.contains(31) && !computerMoves.contains(31)){
                return 31;
            }
        }
        //for col1
        if(computerMoves.contains(11) && computerMoves.contains(21)){
            if(!playerMoves.contains(31) && !computerMoves.contains(31)){
                return 31;
            }
        }
        if(computerMoves.contains(11) && computerMoves.contains(31)){
            if(!playerMoves.contains(21) && !computerMoves.contains(21)){
                return 21;
            }
        }
        if(computerMoves.contains(21) && computerMoves.contains(31)){
            if(!playerMoves.contains(11) && !computerMoves.contains(11)){
                return 11;
            }
        }
        //for col2
        if(computerMoves.contains(12) && computerMoves.contains(22)){
            if(!playerMoves.contains(32) && !computerMoves.contains(32)){
                return 32;
            }
        }
        if(computerMoves.contains(12) && computerMoves.contains(32)){
            if(!playerMoves.contains(22) && !computerMoves.contains(22)){
                return 22;
            }
        }
        if(computerMoves.contains(22) && computerMoves.contains(32)){
            if(!playerMoves.contains(12) && !computerMoves.contains(12)){
                return 12;
            }
        }
        //for col3
        if(computerMoves.contains(13) && computerMoves.contains(23)){
            if(!playerMoves.contains(33) && !computerMoves.contains(33)){
                return 33;
            }
        }
        if(computerMoves.contains(13) && computerMoves.contains(33)){
            if(!playerMoves.contains(23) && !computerMoves.contains(23)){
                return 23;
            }
        }
        if(computerMoves.contains(23) && computerMoves.contains(33)){
            if(!playerMoves.contains(13) && !computerMoves.contains(13)){
                return 13;
            }
        }
        //for cross1
        if(computerMoves.contains(11) && computerMoves.contains(33)){
            if(!playerMoves.contains(22) && !computerMoves.contains(22)){
                return 22;
            }
        }
        if(computerMoves.contains(11) && computerMoves.contains(22)){
            if(!playerMoves.contains(33) && !computerMoves.contains(33)){
                return 33;
            }
        }
        if(computerMoves.contains(22) && computerMoves.contains(33)){
            if(!playerMoves.contains(11) && !computerMoves.contains(11)){
                return 11;
            }
        }
        //for cross2
        if(computerMoves.contains(13) && computerMoves.contains(31)){
            if(!playerMoves.contains(22) && !computerMoves.contains(22)){
                return 22;
            }
        }
        if(computerMoves.contains(13) && computerMoves.contains(22)){
            if(!playerMoves.contains(31) && !computerMoves.contains(31)){
                return 31;
            }
        }
        if(computerMoves.contains(22) && computerMoves.contains(31)){
            if(!playerMoves.contains(13) && !computerMoves.contains(13)){
                return 13;
            }
        }
        //for blocking user
        //for row1
        if(playerMoves.contains(11) && playerMoves.contains(12)){
            if(!playerMoves.contains(13) && !computerMoves.contains(13)){
                return 13;
            }
        }
        if(playerMoves.contains(11) && playerMoves.contains(13)){
            if(!playerMoves.contains(12) && !computerMoves.contains(12)){
                return 12;
            }
        }
        if(playerMoves.contains(12) && playerMoves.contains(13)){
            if(!playerMoves.contains(11) && !computerMoves.contains(11)){
                return 11;
            }
        }
        //for row2
        if(playerMoves.contains(21) && playerMoves.contains(22)){
            if(!playerMoves.contains(23) && !computerMoves.contains(23)){
                return 23;
            }
        }
        if(playerMoves.contains(21) && playerMoves.contains(23)){
            if(!playerMoves.contains(22) && !computerMoves.contains(22)){
                return 22;
            }
        }
        if(playerMoves.contains(22) && playerMoves.contains(23)){
            if(!playerMoves.contains(21) && !computerMoves.contains(21)){
                return 21;
            }
        }
        //for row3
        if(playerMoves.contains(31) && playerMoves.contains(32)){
            if(!playerMoves.contains(33) && !computerMoves.contains(33)){
                return 33;
            }
        }
        if(playerMoves.contains(31) && playerMoves.contains(33)){
            if(!playerMoves.contains(32) && !computerMoves.contains(32)){
                return 32;
            }
        }
        if(playerMoves.contains(32) && playerMoves.contains(33)){
            if(!playerMoves.contains(31) && !computerMoves.contains(31)){
                return 31;
            }
        }
        //for col1
        if(playerMoves.contains(11) && playerMoves.contains(21)){
            if(!playerMoves.contains(31) && !computerMoves.contains(31)){
                return 31;
            }
        }
        if(playerMoves.contains(11) && playerMoves.contains(31)){
            if(!playerMoves.contains(21) && !computerMoves.contains(21)){
                return 21;
            }
        }
        if(playerMoves.contains(21) && playerMoves.contains(31)){
            if(!playerMoves.contains(11) && !computerMoves.contains(11)){
                return 11;
            }
        }
        //for col2
        if(playerMoves.contains(12) && playerMoves.contains(22)){
            if(!playerMoves.contains(32) && !computerMoves.contains(32)){
                return 32;
            }
        }
        if(playerMoves.contains(12) && playerMoves.contains(32)){
            if(!playerMoves.contains(22) && !computerMoves.contains(22)){
                return 22;
            }
        }
        if(playerMoves.contains(22) && playerMoves.contains(32)){
            if(!playerMoves.contains(12) && !computerMoves.contains(12)){
                return 12;
            }
        }
        //for col3
        if(playerMoves.contains(13) && playerMoves.contains(23)){
            if(!playerMoves.contains(33) && !computerMoves.contains(33)){
                return 33;
            }
        }
        if(playerMoves.contains(13) && playerMoves.contains(33)){
            if(!playerMoves.contains(23) && !computerMoves.contains(23)){
                return 23;
            }
        }
        if(playerMoves.contains(23) && playerMoves.contains(33)){
            if(!playerMoves.contains(13) && !computerMoves.contains(13)){
                return 13;
            }
        }
        //for cross1
        if(playerMoves.contains(11) && playerMoves.contains(33)){
            if(!playerMoves.contains(22) && !computerMoves.contains(22)){
                return 22;
            }
        }
        if(playerMoves.contains(11) && playerMoves.contains(22)){
            if(!playerMoves.contains(33) && !computerMoves.contains(33)){
                return 33;
            }
        }
        if(playerMoves.contains(22) && playerMoves.contains(33)){
            if(!playerMoves.contains(11) && !computerMoves.contains(11)){
                return 11;
            }
        }
        //for cross2
        if(playerMoves.contains(13) && playerMoves.contains(31)){
            if(!playerMoves.contains(22) && !computerMoves.contains(22)){
                return 22;
            }
        }
        if(playerMoves.contains(13) && playerMoves.contains(22)){
            if(!playerMoves.contains(31) && !computerMoves.contains(31)){
                return 31;
            }
        }
        if(playerMoves.contains(22) && playerMoves.contains(31)){
            if(!playerMoves.contains(13) && !computerMoves.contains(13)){
                return 13;
            }
        }
        return -1;
    }
}
