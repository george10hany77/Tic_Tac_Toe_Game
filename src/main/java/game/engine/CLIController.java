package game.engine;

import java.awt.*;
import java.util.Scanner;

public class CLIController extends GameController {
    private Player player = new Player(Symbol.X); // the first move will be for X;
    private Board board = new Board(3); // init the board;
    GameStatus status = GameStatus.IN_PROGRESS;;
    private Scanner sc = new Scanner(System.in);
    @Override
    public void startGame() {
        gameLoop();
    }

    public void gameLoop(){
        GameStatus gameStatus = null;
        while (getStatus() == GameStatus.IN_PROGRESS) {
            System.out.println(board);
            String input = "";
            Point point = null;
            do {
                System.out.println("Enter cell position: ");
                input = sc.nextLine();
                point = convertNumToPoint(Integer.parseInt(input));
            } while (!isValidInput(input) || point == null || !makeMove(point.x, point.y));
            gameStatus = getStatus();
        }
        System.out.println(board);
        if (gameStatus != null)
            System.out.println("\n" +gameStatus.getName() + " !!!");
    }

    private Point convertNumToPoint(int num){
        int size = board.getSize();
        if (num < 1 || num > Math.pow(size, 2))
            return null;

        int row = (Integer)((num - 1)/(size));
        int col = (num - 1) % size;

        return new Point(row, col);
    }

    private boolean isValidInput(String in){
        if (in.length() != 1)
            return false;
        return Character.isDigit(in.charAt(0));
    }

    @Override
    public boolean makeMove(int row, int col) {
        boolean moveRes = board.markCell(row, col, player.getSymbol());
        if(!moveRes) // if not successful don't switch players
            return false;
        switchPlayer();
        return true;
    }

    @Override
    public void checkStatus() {
        // Check the rows
        for (int i = 0; i < board.getSize(); i++) {
            boolean isAllXs = true, isAllOs = true;
            for (int j = 0; j < board.getSize(); j++) {
                Symbol currSymbol = board.getBoard()[i][j];
                if (currSymbol != null) {
                    if (currSymbol == Symbol.X) {
                        isAllOs = false;
                    }
                    if (currSymbol == Symbol.O) {
                        isAllXs = false;
                    }
                }else { // there is an empty space
                    isAllXs = false;
                    isAllOs = false;
                    break;
                }
            }
            if (isAllXs && !isAllOs){
                status = GameStatus.PLAYER_X_WINS;
                return;
            } else if (isAllOs && !isAllXs) {
                status = GameStatus.PLAYER_O_WINS;
                return;
            }
        }

        // Check the columns
        for (int i = 0; i < board.getSize(); i++) {
            boolean isAllXs = true, isAllOs = true;
            for (int j = 0; j < board.getSize(); j++) {
                Symbol currSymbol = board.getBoard()[j][i];
                if (currSymbol != null) {
                    if (currSymbol == Symbol.X) {
                        isAllOs = false;
                    }
                    if (currSymbol == Symbol.O) {
                        isAllXs = false;
                    }
                }else {
                    isAllXs = false;
                    isAllOs = false;
                    break;
                }
            }
            if (isAllXs && !isAllOs){
                status = GameStatus.PLAYER_X_WINS;
                return;
            } else if (isAllOs && !isAllXs) {
                status = GameStatus.PLAYER_O_WINS;
                return;
            }
        }

        // Check the diagonals
        for (int n = 0; n < 2; n++) {
            boolean isAllXs = true, isAllOs = true;
            // Right diagonal
            for (int i = 0; n == 0 && i < board.getSize(); i++) {
                Symbol currSymbol = board.getBoard()[i][i];
                if (currSymbol != null) {
                    if (currSymbol == Symbol.X) {
                        isAllOs = false;
                    }
                    if (currSymbol == Symbol.O) {
                        isAllXs = false;
                    }
                }else {
                    isAllXs = false;
                    isAllOs = false;
                    break;
                }
            }
            if (isAllXs && !isAllOs){
                status = GameStatus.PLAYER_X_WINS;
                return;
            } else if (isAllOs && !isAllXs) {
                status = GameStatus.PLAYER_O_WINS;
                return;
            }
            // Left diagonal
            for (int i = board.getSize()-1; n == 1 && i >= 0; i--) {
                Symbol currSymbol = board.getBoard()[i][i];
                if (currSymbol != null) {
                    if (currSymbol == Symbol.X) {
                        isAllOs = false;
                    }
                    if (currSymbol == Symbol.O) {
                        isAllXs = false;
                    }
                }else {
                    isAllXs = false;
                    isAllOs = false;
                    break;
                }
            }
            if (isAllXs && !isAllOs){
                status = GameStatus.PLAYER_X_WINS;
                return;
            } else if (isAllOs && !isAllXs) {
                status = GameStatus.PLAYER_O_WINS;
                return;
            }
        }
        if (board.isFull()){
            status = GameStatus.DRAW;
        }else {
            status = GameStatus.IN_PROGRESS;
        }
    }

    @Override
    public void switchPlayer() {
        if (player.getSymbol() == Symbol.X){
            player.setSymbol(Symbol.O);
        }else if (player.getSymbol() == Symbol.O){
            player.setSymbol(Symbol.X);
        }
    }

    @Override
    public GameStatus getStatus() {
        checkStatus();
        return status;
    }

    @Override
    public Player getPlayer() {
        return this.player;
    }

    @Override
    public Board getBoard() {
        return this.board;
    }
}
