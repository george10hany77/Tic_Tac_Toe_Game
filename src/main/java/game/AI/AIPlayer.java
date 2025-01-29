package game.AI;

import game.engine.Board;
import game.engine.Player;
import game.engine.Symbol;

import java.awt.*;

public abstract class AIPlayer extends Player {


    public AIPlayer(Symbol symbol) {
        super(symbol);
    }
    public abstract boolean play(Board board);
    public abstract Point[] getPossibleMoves(Board board);
}
