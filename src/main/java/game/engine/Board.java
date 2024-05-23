package game.engine;

public class Board {
    private Symbol[][] board;
    private int size;

    public Board(int size){
        this.size = size;
        board = new Symbol[size][size];
    }

    private boolean isValidRowsAndCols(int r, int c){
        return r >= 0 && r < size && c >= 0 && c < size;
    }

    public boolean markCell(int row, int col, Symbol symbol){
        if (!isValidRowsAndCols(row, col)){
            System.out.println("Invalid indices !");
            return false;
        }
        if (symbol == Symbol.X || symbol == Symbol.O){
            if (board[row][col] != Symbol.X && board[row][col] != Symbol.O){
                board[row][col] = symbol;
            }else {
                System.out.println("Occupied !");
                return false;
            }
            return true;
        }else {
            System.out.println("Not X nor O !");
            return false;
        }
    }

    public boolean isFull(){
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == null){
                    return false;
                }
            }
        }
        return true;
    }

    public Symbol[][] getBoard(){
        return board;
    }

    public int getSize(){
        return size;
    }

    public void printBoard(){
        System.out.println(boardToString());
    }

    public String boardToString(){
        String ans = "";
        int count = 1;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == Symbol.X) {
                    ans += " X ";
                }else if (board[i][j] == Symbol.O){
                    ans += " O ";
                }
                else {
                    ans += "[" + count + "]";
                }
                count++;
                if (j != size-1){
                    ans += "|";
                }
            }
            if (i != size-1) {
                ans += "\n---|---|---\n";
            }
        }
        return ans;
    }

    public String toString(){
        String ans = "";
        int count = 1;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == Symbol.X) {
                    ans += " X ";
                }else if (board[i][j] == Symbol.O){
                    ans += " O ";
                }
                else {
                    ans += "[" + count + "]";
                }
                count++;
                if (j != size-1){
                    ans += "|";
                }
            }
            if (i != size-1) {
                ans += "\n---|---|---\n";
            }
        }
        return ans;
    }
}