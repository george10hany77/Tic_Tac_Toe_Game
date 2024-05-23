package game.engine;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for the Board class functionality.
 */
public class BoardTest {

    private Board board;

    @Before
    public void setUp() {
        board = new Board(3); // Assuming a constructor that sets up a 3x3 board
    }

    /**
     * Tests that the initial board is empty.
     */
    @Test
    public void testInitialBoardIsEmpty() {
        Symbol[][] expected = new Symbol[3][3]; // A new board is expected to be empty
        assertArrayEquals("Board should be empty at the start.", expected, board.getBoard());
    }

    /**
     * Tests marking a cell on the board.
     */
    @Test
    public void testMarkCell() {
        assertTrue("Marking an empty cell should return true.", board.markCell(0, 0, Symbol.X));
        assertEquals("Cell should be marked with X.", Symbol.X, board.getBoard()[0][0]);
        assertFalse("Marking an already marked cell should return false.", board.markCell(0, 0, Symbol.O));
    }

    /**
     * Tests whether the board is full.
     */
    @Test
    public void testIsFull() {
        // Mark all cells
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board.markCell(i, j, Symbol.X);
            }
        }
        assertTrue("Board should be full.", board.isFull());
    }

    /**
     * Tests the boardToString method of the Board class.
     */
    @Test
    public void testBoardToString() {
        Board board = new Board(3);
        // Make some moves on the board
        board.markCell(0, 0, Symbol.X);
        board.markCell(1, 1, Symbol.O);
        board.markCell(2, 2, Symbol.X);

        // Define the expected string representation of the board
        String expectedBoardString = " X |[2]|[3]\n---|---|---\n[4]| O |[6]\n---|---|---\n[7]|[8]| X ";

        // Compare the expected string with the actual string representation of the
        // board
        assertEquals(expectedBoardString, board.boardToString());
    }
}
