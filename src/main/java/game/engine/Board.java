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
//            System.out.println("Invalid indices !");
            return false;
        }
        if (symbol == Symbol.X || symbol == Symbol.O){
            if (board[row][col] != Symbol.X && board[row][col] != Symbol.O){
                board[row][col] = symbol;
            }else {
//                System.out.println("Occupied !");
                return false;
            }
            return true;
        }else {
//            System.out.println("Not X nor O !");
            return false;
        }
    }
    public Board clone() throws CloneNotSupportedException{
        Board newBoard = new Board(size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == Symbol.X)
                    newBoard.getBoard()[i][j] = Symbol.X;
                else if (board[i][j] == Symbol.O)
                    newBoard.getBoard()[i][j] = Symbol.O;
                else
                    newBoard.getBoard()[i][j] = null;
            }
        }
        return newBoard;
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

    // For debugging issues remove .toString and put .name() ,but you will
    // sacrifice the marvelous colors in the terminal :(
    public String toString(){
        String ans = "";
        int count = 1;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == Symbol.X) {
                    ans += " " + Symbol.X.toString() + " ";
                }else if (board[i][j] == Symbol.O){
                    ans += " " + Symbol.O.toString() + " ";
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

    public Symbol checkWinner() {
        // Check rows
        for (int i = 0; i < size; i++) {
            if (board[i][0] != null && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                return board[i][0];
            }
        }

        // Check columns
        for (int i = 0; i < size; i++) {
            if (board[0][i] != null && board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                return board[0][i];
            }
        }

        // Check diagonals
        if (board[0][0] != null && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            return board[0][0];
        }
        if (board[0][2] != null && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            return board[0][2];
        }

        return null;
    }

}