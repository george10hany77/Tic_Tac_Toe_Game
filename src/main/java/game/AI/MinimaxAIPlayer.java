package game.AI;

import game.engine.Board;
import game.engine.Symbol;

import java.awt.*;

public class MinimaxAIPlayer extends AIPlayer{

    public MinimaxAIPlayer(Symbol symbol) {
        super(symbol);
    }

    public Point[] getPossibleMoves(Board board) {
        return new Point[0];
    }
}
