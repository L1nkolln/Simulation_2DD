package Entity;

import Game.Position;

public class Tree extends Entity{

    public Tree(Position position) {
        super(position);
    }

    @Override
    public char getSymbol() {
        return 'T';
    }
}
