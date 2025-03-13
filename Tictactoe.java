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
        String playerStatus = "";
        boolean endGame=false;
        setBoard();

        do
        {
            displayUpdated();
            makeMove(input);
            input = changeTurn(input);
            movesMade++;
        }while(!endGame);

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
        for(int i=0; i<rows;i++){
            for(int j=0;i<columns;j++){
                board[i][j] = " ";
            }
        }

    }

    public void displayUpdated(){
        System.out.println("\n");
        for (int i=0; i<rows;i++){
            for (int j=0;j<columns;j++){
                System.out.println("["+ board[i][j] + "]");
            }
            System.out.println("\n");
        }
        System.out.println("\n");


    }

    public String changeTurn(String turn){
        if (turn ==p1){
            turn = p2;
        } else{
            turn = p1;
        }
        return turn; 
    }

    
  
}