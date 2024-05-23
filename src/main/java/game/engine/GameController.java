package game.engine;

/**
 * Abstract class representing the controller for the Tic-Tac-Toe game.
 * This class defines the common framework and operations for different game
 * controllers (e.g., CLI, GUI, AI).
 */
public abstract class GameController {

    /**
     * Starts the game.
     */
    public abstract void startGame();

    /**
     * Handles a player's move.
     * 
     * @param row the row index of the move
     * @param col the column index of the move
     * @return true if the move is valid and executed, false otherwise
     */
    public abstract boolean makeMove(int row, int col);

    /**
     * Checks the current state of the game and updates the game status accordingly.
     */
    public abstract void checkStatus();

    /**
     * Switches the current player.
     */
    public abstract void switchPlayer();

    /**
     * Gets the current status of the game.
     * 
     * @return the current game status
     */
    public abstract GameStatus getStatus();

    /**
     * Gets the current player.
     * 
     * @return the current player
     */
    public abstract Player getPlayer();

    /**
     * Gets the current game board
     * 
     * @return the current game board
     */
    public abstract Board getBoard();
}
