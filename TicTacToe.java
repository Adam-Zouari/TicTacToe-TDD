import java.util.*;

public class TicTacToe {
    private char[][] board;
    private char currentPlayer;
    private boolean gameOver;
    private int turn; 
    private GererDB db;
    private TicTacToeSave save;


    //Version pour les tests définis dans TicTacToeTest.java
    public TicTacToe() {
        board = new char[3][3];
        for (int i = 0; i < 3; i++) {
            Arrays.fill(board[i], '-');
        }
        currentPlayer = 'X';
        gameOver = false;
        turn=0;
        save = new TicTacToeSave(db);
        if(!save.CreateDatabase("Tic-Tac-Toe")){
            System.err.println("Database error!");
        }
    }

     //Version pour les tests définis dans TicTacToeTest2.java
     public TicTacToe(GererDB db) {
        this.db=db;
        board = new char[3][3];
        for (int i = 0; i < 3; i++) {
            Arrays.fill(board[i], '-');
        }
        currentPlayer = 'X';
        gameOver = false;
        turn=0;
        save = new TicTacToeSave(db);
        if(!save.CreateDatabase("Tic-Tac-Toe")){
            System.err.println("Database error!");
        }
    }

    public void play(int x, int y) {
        if (x < 0 || x > 2) {
            throw new RuntimeException("X out of bounds");
        }
        if (y < 0 || y > 2) {
            throw new RuntimeException("Y out of bounds");
        }
        if (board[x][y] != '-') {
            throw new RuntimeException("Position already occupied");
        }
        if (gameOver) {
            throw new RuntimeException("Game is over");
        }
        
        board[x][y] = currentPlayer;
        if (checkWin(x, y)) {
            gameOver = true;
            System.out.println("Player " + currentPlayer + " wins!");
        } else if (isBoardFull()) {
            gameOver = true;
            System.out.println("Game is a draw!");
        } else {
            
            //Enlever ce commentaire uniquement pour les tests définis dans TicTacToeTest2.java

          /*  if(!save.saveMove(new Data(turn, x, y, currentPlayer))){
                throw new RuntimeException("Failed to save move!");
            }*/

            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            turn++;
            }
        }

    public boolean checkWin(int x, int y) {
        if (board[x][0] == currentPlayer && board[x][1] == currentPlayer && board[x][2] == currentPlayer) {
            return true;
        }
        if (board[0][y] == currentPlayer && board[1][y] == currentPlayer && board[2][y] == currentPlayer) {
            return true;
        }
        if (x == y && board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) {
            return true;
        }
        if (x + y == 2 && board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer) {
            return true;
        }
        return false;
    }

    public boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') {
                    return false;
                }
            }
        }
        return true;
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }

    public char[][] getBoard() {
        return board;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean initializeGame(){
        if(!save.initializeDB()){
            System.err.println("Initialization error!");
            return false;
        }
        for (int i = 0; i < 3; i++) {
            Arrays.fill(board[i], '-');
        }
        currentPlayer = 'X';
        gameOver = false;
        turn=0;
        return true;
    }

    public TicTacToeSave getSave() {
        return save;
    }

    public GererDB getDB() {
        return db;
    }
}