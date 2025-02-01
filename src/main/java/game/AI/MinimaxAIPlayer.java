package game.AI;

import game.engine.Board;
import game.engine.Symbol;

import java.awt.*;
import java.util.ArrayList;

class GPoint{
    public Point point;
    public int score;
    public GPoint(Point point, int score){
        this.point = point;
        this.score = score;
    }
}

public class MinimaxAIPlayer extends AIPlayer {

    public MinimaxAIPlayer(Symbol symbol) {
        super(symbol);
    }

    @Override
    public boolean play(Board board, Point move) {
        return false;
    }

    @Override
    public boolean play(Board board) {
        try {
            int[] bestMove = getBestMove2(board.clone());
            return board.markCell(bestMove[0], bestMove[1], this.getSymbol());
        }catch (Exception e){
            System.out.println("EXCEPTION");
            return false;
        }
    }

    @Override
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

    private int[] getBestMove(Board board) {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = new int[2];

        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                if (board.getBoard()[i][j] == null) {
                    board.markCell(i, j, this.getSymbol());
                    int moveScore = minimax(board, 0, false);
                    board.markCell(i, j, null);  // Undo move

                    if (moveScore > bestScore) {
                        bestScore = moveScore;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    }
                }
            }
        }
        return bestMove;
    }

    public int miniMax2 (Board board, GPoint[] gPoints, int index, boolean isAI) throws CloneNotSupportedException {
        Symbol winner = board.checkWinner();
        if (winner == Symbol.X) return -10;
        if (winner == Symbol.O) return 10;
        if (board.isFull()) return 0;

        Point[] moves = getPossibleMoves(board);
        for (int i = 0; i < moves.length; i++) {
            Board newBoard = board.clone();
            if (isAI)
                newBoard.markCell(moves[i].x, moves[i].y, this.getSymbol());
            else
                newBoard.markCell(moves[i].x, moves[i].y, this.getOtherSymbol());
            gPoints[index].score += miniMax2(newBoard, gPoints, index, !isAI);
        }

        return 0;
    }

    public int[] getBestMove2(Board board) throws CloneNotSupportedException {
        Point[] moves = getPossibleMoves(board);
        GPoint[] gPoints = new GPoint[moves.length];
        for (int i = 0; i < moves.length; i++) {
            gPoints[i] = new GPoint(new Point(), 0);
        }
        miniMax2(board, gPoints, 0, true);
        int maxScore = Integer.MIN_VALUE;
        GPoint maxGpoint = null;
        for (int i = 0; i < gPoints.length; i++) {
            if (gPoints[i].score > maxScore) {
                maxScore = gPoints[i].score;
                maxGpoint = gPoints[i];
            }
        }
        int[] res = {maxGpoint.point.x, maxGpoint.point.y};
        return res;
    }

    private int minimax(Board board, int depth, boolean isMaximizing) {
        Symbol winner = board.checkWinner();
        if (winner == Symbol.X) return -10;
        if (winner == Symbol.O) return 10;
        if (board.isFull()) return 0;

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < board.getSize(); i++) {
                for (int j = 0; j < board.getSize(); j++) {
                    if (board.getBoard()[i][j] == null) {
                        board.markCell(i, j, this.getSymbol());
                        int score = minimax(board, depth + 1, false);
                        board.markCell(i, j, null);  // Undo move
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < board.getSize(); i++) {
                for (int j = 0; j < board.getSize(); j++) {
                    if (board.getBoard()[i][j] == null) {
                        board.markCell(i, j, Symbol.X);
                        int score = minimax(board, depth + 1, true);
                        board.markCell(i, j, null);  // Undo move
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
            return bestScore;
        }
    }
}
