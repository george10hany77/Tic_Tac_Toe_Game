package game.Main;

import game.engine.CLIController;

public class Main {
    /**
     * Main class to play the Tic-Tac-Toe game.
     */
    public static void main(String[] args) {
        System.out.println("Tic-Tac-Toe!");
        CLIController cliController = new CLIController();
        cliController.startGame();
    }

}
