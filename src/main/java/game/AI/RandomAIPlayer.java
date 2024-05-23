package game.AI;

import game.engine.Board;
import game.engine.CLIController;
import game.engine.Player;
import game.engine.Symbol;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class RandomAIPlayer extends AIPlayer {

    public RandomAIPlayer(Symbol symbol) {
        super(symbol);
    }

    public Point[] getPossibleMoves(Board board) {
        if (board == null){
            System.out.println("Board is null");
            return new Point[0];
        }
        ArrayList<Point> points = new ArrayList<>();
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                if (board.getBoard()[i][j] != Symbol.X && board.getBoard()[i][j] != Symbol.O){
                    points.add(new Point(i, j));
                }
            }
        }
        Point[] pointsArr = new Point[points.size()];
        return points.toArray(pointsArr);
    }
}
