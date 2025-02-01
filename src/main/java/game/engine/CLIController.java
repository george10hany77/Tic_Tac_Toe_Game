package game.engine;

import game.AI.AIPlayer;
import game.AI.MinimaxAIPlayer;
import game.AI.RandomAIPlayer;

import java.awt.*;
import java.util.Random;
import java.util.Scanner;

public class CLIController extends GameController {
    private Player player; // the first move will be for X;
    private AIPlayer playerAI; // the first AI move will be for O;

    public Board board;
    GameStatus status = GameStatus.IN_PROGRESS;
    private Scanner sc = new Scanner(System.in);

    public CLIController(){
        board = new Board(3); // init the board;
        String input;
        do {
            System.out.println("Human to Human : 'H' --- Human to AI : 'A' ");
            input = sc.nextLine();
        }while (input.length() != 1 || (input.charAt(0) != 'H' && input.charAt(0) != 'A'));
        player = new HumanPlayer(Symbol.X);
        if (input.equals("A")){
            do {
                System.out.println("Random AI 'R' --- Minimax AI : 'M' ");
                input = sc.nextLine();
            }while (input.length() != 1 || (input.charAt(0) != 'R' && input.charAt(0) != 'M'));
            if (input.equals("R"))
                playerAI = new RandomAIPlayer(Symbol.O);
            else playerAI = new MinimaxAIPlayer(Symbol.O);
        }
    }
    @Override
    public void startGame() {
        if (playerAI == null) { // that means the AI player is not initialized ,so it is only humans.
            gameLoop();
        }else {
            gameLoopAI2();
        }
    }

    public void gameLoopAI2() {
        GameStatus gameStatus = null;
        while (getStatus() == GameStatus.IN_PROGRESS) {
            if (board.isFull()) {
                break;
            }
            boolean AIPlayed = false;
            do {
                System.out.println("AI will Make a Move !");
                AIPlayed = playerAI.play(board);
                if (getStatus() != GameStatus.IN_PROGRESS)
                    break;
                System.out.println(board);
            } while (!AIPlayed);
            String input = "";
            Point point = null;
            do {
                System.out.println("Enter cell position: ");
                input = sc.nextLine();
                if (isValidInput(input))
                    point = convertNumToPoint(Integer.parseInt(input));
            } while (!isValidInput(input) || point == null || !player.play(board, point));
            gameStatus = getStatus();
        }
        System.out.println(board);
        if (gameStatus != null)
            System.out.println("\n" + status.getName() + " !!!");
    }

    public void gameLoopAI(){
        GameStatus gameStatus = null;
        while (getStatus() == GameStatus.IN_PROGRESS) {
            System.out.println("AI will Make a Move !");
            playerAI.play(board);
            if (getStatus() != GameStatus.IN_PROGRESS)
                break;
            System.out.println(board);
            String input = "";
            Point point = null;
            do {
                System.out.println("Enter cell position: ");
                input = sc.nextLine();
                if (isValidInput(input))
                    point = convertNumToPoint(Integer.parseInt(input));
            } while (!isValidInput(input) || point == null || !player.play(board, point));
            gameStatus = getStatus();
        }
        System.out.println(board);
        if (gameStatus != null)
            System.out.println("\n" +status.getName() + " !!!");
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
                if (isValidInput(input))
                    point = convertNumToPoint(Integer.parseInt(input));
            } while (!isValidInput(input) || point == null || !makeMove(point.x, point.y));
            gameStatus = getStatus();
        }
        System.out.println(board);
        if (gameStatus != null)
            System.out.println("\n" +status.getName() + " !!!");
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
        boolean isMoveDone = board.markCell(row, col, player.getSymbol());
        if(!isMoveDone) // if not successful don't switch players
            return false;
        switchPlayer();
        return true;
    }

//    public boolean makeAIMove(){
//        Random random = new Random();
//        Point[] possiblePoints = playerAI.getPossibleMoves(board);
//        if (possiblePoints.length == 0)
//            return false;
//        Point move = possiblePoints[random.nextInt(possiblePoints.length)];
//        return board.markCell(move.x, move.y, playerAI.getSymbol());
//    }

//    public boolean makeHumanMove(Point move){
//        return board.markCell(move.x, move.y, player.getSymbol());
//    }


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
            // Left diagonal
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
            // Right diagonal
            for (int i = 0; n == 1 && i < board.getSize(); i++) {
                Symbol currSymbol = board.getBoard()[(board.getSize()-1) - i][i];
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

    public void switchAIPlayer() {
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
        return board;
    }
}
