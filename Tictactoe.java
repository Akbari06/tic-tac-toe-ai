import java.util.Scanner;
public class Tictactoe{
    private Scanner kb;
    private String[][] board;
    private String p1,p2;
    private int movesMade;
    private int rows,columns;

    public Tictactoe(){
        kb = new Scanner(System.in);
        board = new String[3][3];
        p1 = "O";
        p2 = "X";
        movesMade = 0;
        rows = board.length;
        columns = board[0].length;
    }

    public static void main(String[] args) {
        Tictactoe ttt = new Tictactoe();
        ttt.run();
    }

    public void run(){
        System.out.println("\n\n");
        String input = p1;
        String playAgainStatus = "";
        boolean endGame=false;
        setBoard();

        do
        {
            displayUpdated();
            makeMove(input);
            input = changeTurn(input);
            movesMade++;
            if (movesMade==9 && winner().equals(" ")){
                displayUpdated();
                System.out.println("TIE");
                playAgainStatus = askToPlayAgain();

            }
            else if (movesMade <= 9 && !winner().equals(" ")){
                displayUpdated();
                System.out.println("WINNER: \'" + winner() + "\'");
                playAgainStatus = askToPlayAgain();
            }

            if(playAgainStatus.equalsIgnoreCase("no")){
                endGame=true;
            }
            else if(playAgainStatus.equalsIgnoreCase("yes")){
                playAgainStatus="";
                movesMade=0;
                input=p1;
                setBoard();
            }
        }while(!endGame);
        System.out.println("\n\n Thanks for playing, hope to see you again soon!!!");

    }

    public String askToPlayAgain(){
        String statusCall = " ";
        while (!statusCall.equalsIgnoreCase("yes") && 
              !statusCall.equalsIgnoreCase("no")){
            System.out.println("\n Would you like to play again? (yes/no) ");
            statusCall = kb.next();
        }
        return statusCall;
    }

    public void makeMove(String move){
        boolean moveMade = false;
        int playerRow=0, playerColumn=0;
        do { 
            System.out.println("Player with \'"+move+"\' enter a row number(1,2,3): ");
            playerRow = kb.nextInt();
            System.out.println("Player with \'"+move+"\' enter a column number(1,2,3): ");
            playerColumn = kb.nextInt();

            if ((playerRow >= 1 && playerRow <= rows) && 
            (playerColumn >= 1 && playerColumn <= columns) && 
            board[playerRow-1][playerColumn-1].equals(" ")){
                board[playerRow-1][playerColumn-1] = move;
                moveMade = true;
            }
            else{
                System.out.println("\n invalid input!");
            }
        } while (!moveMade);

    }

    public void setBoard(){
        System.out.println("\nWelcome to tictactoe");
        for(int i=0; i<rows; i++){
            for(int j=0; j<columns; j++){
                board[i][j] = " ";
            }
        }
    }

    public void displayUpdated(){
        System.out.println("\n");
        for (int i=0; i<rows; i++){
            for (int j=0; j<columns; j++){
                System.out.print("[" + board[i][j] + "]");
            }
            System.out.println("\n");
        }
        System.out.println("\n");
    }

    public String changeTurn(String turn){
        if (turn.equals(p1)){
            turn = p2;
        } else{
            turn = p1;
        }
        return turn; 
    }

    public String winner(){
        // check rows
        for (int i=0; i<rows; i++){
            if(!board[i][0].equals(" ") && board[i][0].equals(board[i][1]) && board[i][0].equals(board[i][2])){
                return board[i][0];
            }
        }
        // check columns
        for (int i=0; i<columns; i++){
            if(!board[0][i].equals(" ") && board[0][i].equals(board[1][i]) && board[0][i].equals(board[2][i])){
                return board[0][i];
            }
        }
        // check diagonals
        if (!board[1][1].equals(" ") && 
            ((board[0][0].equals(board[1][1]) && board[0][0].equals(board[2][2])) || 
             (board[0][2].equals(board[1][1]) && board[0][2].equals(board[2][0])))) {
            return board[1][1];
        }

        return " ";
    }
}