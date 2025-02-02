package game.engine;

import java.awt.*;

public abstract class Player {
    private String name;
    private Symbol symbol;
    public Player(Symbol symbol){
        this.symbol = symbol;
    }
    public Player(String name,Symbol symbol){
        this.name = name;
        this.symbol = symbol;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public Symbol getSymbol() {
        return this.symbol;
    }
    public Symbol getOtherSymbol(){
        if(this.symbol == Symbol.X){
            return Symbol.O;
        }
        if(this.symbol == Symbol.O){
            return Symbol.X;
        }
        return null;
    }
    public void setSymbol(Symbol symbol){
        this.symbol = symbol;
    }
    public abstract boolean play(Board board, Point move);
}
