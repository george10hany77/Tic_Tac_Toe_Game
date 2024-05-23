package game.engine;

/**
 * Main class to play the Tic-Tac-Toe game.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Tic-Tac-Toe!");
        CLIController cliController = new CLIController();
        cliController.startGame();
    }
}
