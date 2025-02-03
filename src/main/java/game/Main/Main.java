package game.Main;

import game.engine.CLIController;
import game.engine.Colors;
import game.engine.GameStatus;

public class Main {
    /**
     * Main class to play the Tic-Tac-Toe game.
     */
    public static void main(String[] args) {
        System.out.println(Colors.PURPLE.colorText("Tic-Tac-Toe!\n"));
        System.out.println(GameStatus.DRAW.ordinal());
        CLIController cliController = new CLIController();
        cliController.startGame();
    }

}
