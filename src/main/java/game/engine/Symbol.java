package game.engine;

public enum Symbol {
    X(Colors.RED), O(Colors.CYAN);

    Colors SymbolColor;
    Symbol(Colors c) {
        SymbolColor = c;
    }
    public String toString(){
        return SymbolColor.colorText(this.name());
    }
    /*
    public String formatSymbolColor(){
        return SymbolColor.colorText(this.name());
    }
     */
}
