import java.awt.*;
import java.awt.event.*;

public class Board extends MouseAdapter {
    private static final int squareLength = 200;
    private Square[][] board;
    private Square winner;
    private TicTacToeAPI api;
    private String currentTurn;
    private int rows, cols, movesMade;
    private boolean winnerFound;
    private String playerChoice;
    private boolean playerMoveDone;

    public Board() {
        board = new Square[3][3];
        api = new TicTacToeAPI();
        rows = board.length;
        cols = board[0].length;
        currentTurn = " ";
        winner = null;
        winnerFound = false;
        movesMade = 0;
        playerChoice = "";
        playerMoveDone = false;
        
        // Initialize the board with empty squares
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = new Square(i + 1, j + 1, new Piece(" ", -1, -1));
            }
        }
    }

    public void update() {
        if (currentTurn.equals(" "))
            currentTurn = api.changeTurns(currentTurn);
            
        if (playerMoveDone) {
            if (board != null) {
                computerMove();
                currentTurn = api.changeTurns(currentTurn);
                playerMoveDone = false;
            }
        }
        
        winner = api.winner(board, rows, cols);
        if (winner != null && !winner.getPiece().getValue().equals(" ")) {
            winnerFound = true;
        }
        if (winner == null && movesMade == 9) {
            winnerFound = true;
        }
    }

    public void draw(Graphics g) {
        // Set background for the entire game area
        g.setColor(new Color(240, 240, 240));  // Light gray background
        g.fillRect(0, 0, 800, 700);  // Assuming window size
        
        // Draw the board
        g.setFont(new Font("Arial", Font.BOLD, 140));
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // Draw square background
                g.setColor(Color.WHITE);
                g.fillRect(i * squareLength + 100, j * squareLength + 90, squareLength, squareLength);
                
                // Draw square border
                ((Graphics2D) g).setStroke(new BasicStroke(4));
                g.setColor(Color.BLACK);
                g.drawRect(i * squareLength + 100, j * squareLength + 90, squareLength, squareLength);
                
                // Draw X or O
                if (board[i][j].getPiece().getValue().equals("X")) {
                    g.setColor(new Color(41, 128, 185)); // Blue for X
                } else if (board[i][j].getPiece().getValue().equals("O")) {
                    g.setColor(new Color(231, 76, 60));  // Red for O
                } else {
                    g.setColor(Color.WHITE); // No piece
                }
                
                g.drawString(board[i][j].getPiece().getValue(), 
                             i * squareLength + 135, 
                             j * squareLength + 200);
            }
        }
        
        // Draw game status or winner
        if (winnerFound) {
            if (winner != null) {
                api.drawWinningPath(g, winner, squareLength);
            }
            
            if (movesMade == 9 && winner == null) {
                api.gameResult(g, "TIE");
            } else if (winner != null) {
                api.gameResult(g, winner.getPiece().getValue());
            }
            
            askToPlayAgain(g);
        } else {
            // Game title
            g.setColor(new Color(44, 62, 80)); // Dark blue
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("Tic-Tac-Toe", 260, 50);
            
            // Current turn indicator
            g.setFont(new Font("Arial", Font.PLAIN, 24));
            if (currentTurn.equals("X")) {
                g.setColor(new Color(41, 128, 185)); // Blue for X
                g.drawString("Your turn (X)", 330, 80);
            } else if (currentTurn.equals("O")) {
                g.setColor(new Color(231, 76, 60));  // Red for O
                g.drawString("Computer's turn (O)", 300, 80);
            }
        }
    }

    public void askToPlayAgain(Graphics g) {
        // Semi-transparent overlay
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, 800, 700);
        
        // Play again panel
        g.setColor(new Color(236, 240, 241)); // Light gray
        g.fillRoundRect(200, 250, 400, 200, 20, 20);
        
        g.setFont(new Font("Arial", Font.BOLD, 32));
        g.setColor(new Color(44, 62, 80)); // Dark blue
        g.drawString("Play Again?", 300, 300);

        // Yes button
        g.setColor(new Color(46, 204, 113)); // Green
        g.fillRoundRect(250, 350, 120, 60, 15, 15);
        g.setColor(Color.WHITE);
        g.drawString("Yes", 280, 390);

        // No button
        g.setColor(new Color(231, 76, 60)); // Red
        g.fillRoundRect(430, 350, 120, 60, 15, 15);
        g.setColor(Color.WHITE);
        g.drawString("No", 465, 390);
    }

    public void resetGame() {
        movesMade = 0;
        currentTurn = " ";
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = new Square(i + 1, j + 1, new Piece(" ", -1, -1));
            }
        }
        winnerFound = false;
        winner = null;
        playerChoice = "";
        playerMoveDone = false;
    }

    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();
        
        if (winnerFound) {
            // Check if play again buttons are clicked
            if (mx >= 250 && mx <= 370 && my >= 350 && my <= 410) {
                // Yes button clicked
                resetGame();
            } else if (mx >= 430 && mx <= 550 && my >= 350 && my <= 410) {
                // No button clicked
                System.exit(0);
            }
        } else {
            // Handle game board clicks
            if (currentTurn.equals("X")) {
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        int x = i * squareLength + 100;
                        int y = j * squareLength + 90;
                        
                        if (mx >= x && mx <= x + squareLength && 
                            my >= y && my <= y + squareLength) {
                            if (board[i][j].getPiece().getValue().equals(" ")) {
                                board[i][j].setPiece(new Piece(currentTurn, j + 1, i + 1));
                                movesMade++;
                                currentTurn = api.changeTurns(currentTurn);
                                playerMoveDone = true;
                            }
                        }
                    }
                }
            }
        }
    }

    public void computerMove() {
        Square[][] tempBoard = copyBoard(board);
        Square tempWinner = null;
        String comp = "O";
        String playMove = "X";
        
        /**
         * Take the winning move
         * */
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (tempBoard[j][i].getPiece().getValue().equals(" ")) {
                    tempBoard[j][i].setPiece(new Piece(comp, i + 1, j + 1));
                    tempWinner = api.winner(tempBoard, 3, 3);
                    if (tempWinner != null && winner == null) {
                        board[j][i].setPiece(new Piece(comp, i + 1, j + 1));
                        movesMade++;
                        return;
                    } else if (winner == null) {
                        tempBoard[j][i].setPiece(new Piece(" ", i + 1, j + 1));
                        tempWinner = null;
                    }
                }
            }
        }
        
        /**
         * Stop the opponent from winning
         * */
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (tempBoard[j][i].getPiece().getValue().equals(" ")) {
                    tempBoard[j][i].setPiece(new Piece(playMove, i + 1, j + 1));
                    tempWinner = api.winner(tempBoard, rows, cols);
                    if (tempWinner != null && winner == null) {
                        board[j][i].setPiece(new Piece(comp, i + 1, j + 1));
                        movesMade++;
                        return;
                    } else {
                        tempBoard[j][i].setPiece(new Piece(" ", i + 1, j + 1));
                        tempWinner = null;
                    }
                }
            }
        }
        
        /**
         * Select center if open
         * */
        if (board[1][1].getPiece().getValue().equals(" ")) {
            board[1][1].setPiece(new Piece(comp, 2, 2));
            movesMade++;
            return;
        }
        
        /**
         * Select any corners
         * */
        int[] corners = {0, 2};
        for (int i : corners) {
            for (int j : corners) {
                if (board[i][j].getPiece().getValue().equals(" ")) {
                    board[i][j].setPiece(new Piece(comp, j + 1, i + 1));
                    movesMade++;
                    return;
                }
            }
        }
        
        /**
         * Select any open square
         * */
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j].getPiece().getValue().equals(" ")) {
                    board[i][j].setPiece(new Piece(comp, j + 1, i + 1));
                    movesMade++;
                    return;
                }
            }
        }
    }

    public Square[][] copyBoard(Square[][] b) {
        Square[][] toReturn = new Square[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Piece p = b[i][j].getPiece();
                int row = b[i][j].getRow();
                int col = b[i][j].getCol();
                toReturn[i][j] = new Square(row, col, new Piece(p.getValue(), p.getRow(), p.getCol()));
            }
        }
        return toReturn;
    }
}