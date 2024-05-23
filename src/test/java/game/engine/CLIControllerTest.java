package game.engine;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for CLIController functionality.
 */
public class CLIControllerTest {

    // Tests the initialization of the CLIController.
    @Test
    public void testCLIControllerInitialization() {
        GameController controller = new CLIController();
        assertNotNull(controller);
        assertEquals(GameStatus.IN_PROGRESS, controller.getStatus());
        assertEquals(Symbol.X, controller.getPlayer().getSymbol());
        assertNotNull(controller.getBoard());
    }

    // Tests making a valid move on the board.
    @Test
    public void testMakeMoveValid() {
        GameController controller = new CLIController();
        assertTrue(controller.makeMove(0, 0));
    }

    // Tests making an invalid move on the board.
    @Test
    public void testMakeMoveInvalid() {
        GameController controller = new CLIController();
        assertTrue(controller.makeMove(0, 0));
        assertFalse(controller.makeMove(0, 0));
    }

    // Tests switching players.
    @Test
    public void testSwitchPlayer() {
        GameController controller = new CLIController();
        assertEquals(Symbol.X, controller.getPlayer().getSymbol());
        controller.switchPlayer();
        assertEquals(Symbol.O, controller.getPlayer().getSymbol());
    }

    // Tests the winning conditions.
    @Test
    public void testWinningConditions() {
        // Test horizontal winning condition for Player X
        CLIController controller = new CLIController();
        controller.makeMove(0, 0); // X
        controller.makeMove(1, 0); // O
        controller.makeMove(0, 1); // X
        controller.makeMove(1, 1); // O
        controller.makeMove(0, 2); // X
        controller.checkStatus();
        assertEquals(GameStatus.PLAYER_X_WINS, controller.getStatus());

        // Test vertical winning condition for Player O
        controller = new CLIController();
        controller.makeMove(0, 0); // X
        controller.makeMove(0, 1); // O
        controller.makeMove(1, 0); // X
        controller.makeMove(1, 1); // O
        controller.makeMove(2, 2); // X
        controller.makeMove(2, 1); // O
        controller.checkStatus();
        assertEquals(GameStatus.PLAYER_O_WINS, controller.getStatus());

        // Test Left diagonal winning condition for Player X
        controller = new CLIController();
        controller.makeMove(0, 0); // X
        controller.makeMove(0, 1); // O
        controller.makeMove(1, 1); // X
        controller.makeMove(0, 2); // O
        controller.makeMove(2, 2); // X
        controller.checkStatus();
        assertEquals(GameStatus.PLAYER_X_WINS, controller.getStatus());

        // Test Right diagonal winning condition for Player X
        controller = new CLIController();
        controller.makeMove(0, 2); // X
        controller.makeMove(0, 1); // O
        controller.makeMove(1, 1); // X
        controller.makeMove(0, 0); // O
        controller.makeMove(2, 0); // X
        System.out.println(controller.getBoard());
        controller.checkStatus();
        assertEquals(GameStatus.PLAYER_X_WINS, controller.getStatus());

    }

    // Tests the draw condition.
    @Test
    public void testDrawCondition() {
        CLIController controller = new CLIController();
        controller.makeMove(0, 0); // X
        controller.makeMove(1, 0); // O
        controller.makeMove(0, 1); // X
        controller.makeMove(1, 1); // O
        controller.makeMove(1, 2); // X
        controller.makeMove(0, 2); // O
        controller.makeMove(2, 0); // X
        controller.makeMove(2, 1); // O
        controller.makeMove(2, 2); // X
        controller.checkStatus();
        assertEquals(GameStatus.DRAW, controller.getStatus());
    }

    // Tests the board full condition.
    @Test
    public void testBoardFull() {
        GameController controller = new CLIController();
        // Make moves to fill the board
        controller.makeMove(0, 0); // X
        controller.makeMove(0, 1); // O
        controller.makeMove(0, 2); // X
        controller.makeMove(1, 0); // O
        controller.makeMove(1, 1); // X
        controller.makeMove(1, 2); // O
        controller.makeMove(2, 0); // X
        controller.makeMove(2, 1); // O
        // Check if the board is not yet full
        assertFalse(controller.getBoard().isFull());
        // The last move to fill the board
        assertTrue(controller.makeMove(2, 2)); // X
        // Check if the board is full
        assertTrue(controller.getBoard().isFull());
    }

}
