package game.engine;

import java.awt.*;

public class HumanPlayer extends Player{

    public HumanPlayer(Symbol symbol) {
        super(symbol);
    }

    @Override
    public boolean play(Board board, Point move) {
        return board.markCell(move.x, move.y, this.getSymbol());
    }
}
