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
            int[] bestMove = getBestMoveWithDepth(board);
            if (bestMove.length == 0) return false;
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

    public int[] getBestMove2(Board board) throws CloneNotSupportedException {
        if (board.isFull()) return new int[0];
        Point[] moves = getPossibleMoves(board);
        GPoint[] gPoints = new GPoint[moves.length];
        for (int i = 0; i < moves.length; i++) {
            gPoints[i] = new GPoint(new Point(moves[i].x, moves[i].y), 0);
        }
        for (int i = 0; i < moves.length; i++) {
            Board clone = board.clone();
            clone.markCell(moves[i].x, moves[i].y, this.getSymbol());
            Symbol winner = clone.checkWinner();
            if (winner == Symbol.X) gPoints[i].score += -10;
            if (winner == Symbol.O) gPoints[i].score += 10;
            if (board.isFull()) gPoints[i].score += -1;
            miniMax2(clone, gPoints, i, false);
        }
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

    public int miniMax2 (Board board, GPoint[] gPoints, int index, boolean isAI) throws CloneNotSupportedException {
        Symbol winner = board.checkWinner();
        if (winner == Symbol.X) return -10;
        if (winner == Symbol.O) return 10;
        if (board.isFull()) return -1;

        Point[] moves = getPossibleMoves(board);
        for (int i = 0; i < moves.length; i++) {
            if (i > 0){
                // to remove the previous play
                board.getBoard()[moves[i-1].x][moves[i-1].y] = null;
            }
            if (isAI)
                board.markCell(moves[i].x, moves[i].y, this.getSymbol());
            else
                board.markCell(moves[i].x, moves[i].y, this.getOtherSymbol());
            Board clone = board.clone();
            int score = miniMax2(clone, gPoints, index, !isAI);
            gPoints[index].score += score;
        }

        return 0;
    }

    public int[] getBestMoveWithDepth(Board board) throws CloneNotSupportedException {
        if (board.isFull()) return new int[0];
        Point[] moves = getPossibleMoves(board);
        GPoint[] gPoints = new GPoint[moves.length];
        for (int i = 0; i < moves.length; i++) {
            gPoints[i] = new GPoint(new Point(moves[i].x, moves[i].y), 0);
        }
        for (int i = 0; i < moves.length; i++) {
            Board clone = board.clone();
            clone.markCell(moves[i].x, moves[i].y, this.getSymbol());
            Symbol winner = clone.checkWinner();
            int depthEquation = (int) (10 + 100*(Math.exp(-2*0)));
            if (winner == Symbol.X) gPoints[i].score += -depthEquation;
            if (winner == Symbol.O) gPoints[i].score += depthEquation;
            if (board.isFull()) gPoints[i].score += -1;
            miniMax3(clone, gPoints, i, false, 1);
        }
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

    public int miniMax3 (Board board, GPoint[] gPoints, int index, boolean isAI, int depth) throws CloneNotSupportedException {
        Symbol winner = board.checkWinner();
        int depthEquation = (int) (10 + 100*(Math.exp(-2*(depth-1))));
        if (winner == Symbol.X) return -depthEquation;
        if (winner == Symbol.O) return depthEquation;
        if (board.isFull()) return -1;

        Point[] moves = getPossibleMoves(board);
        for (int i = 0; i < moves.length; i++) {
            if (i > 0){
                // to remove the previous play
                board.getBoard()[moves[i-1].x][moves[i-1].y] = null;
            }
            if (isAI)
                board.markCell(moves[i].x, moves[i].y, this.getSymbol());
            else
                board.markCell(moves[i].x, moves[i].y, this.getOtherSymbol());
            Board clone = board.clone();
            int score = miniMax3(clone, gPoints, index, !isAI, depth+1);
            gPoints[index].score += score;
        }

        return 0;
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
