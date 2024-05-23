package game.engine;

public enum GameStatus {
    IN_PROGRESS("In Progress"), PLAYER_X_WINS("Player X Wins"), PLAYER_O_WINS("Player O Wins"), DRAW("Draw");
    private String name;
    GameStatus(String s) {
        name = s;
    }
    public String getName(){
        return name;
    }
}
