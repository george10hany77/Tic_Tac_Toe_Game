package game.AI;

import game.engine.Board;
import game.engine.Symbol;

import java.awt.*;

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
            int[] bestMove = getBestMove(board.clone());
            return board.markCell(bestMove[0], bestMove[1], this.getSymbol());
        }catch (Exception e){
            System.out.println("EXCEPTION");
            return false;
        }
    }
    @Override
    public Point[] getPossibleMoves(Board board) {
        return new Point[0];
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
