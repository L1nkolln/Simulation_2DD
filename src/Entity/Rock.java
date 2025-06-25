package Entity;

import Game.Position;

public class Rock extends Entity{

    public Rock(Position position) {
        super(position);
    }

    @Override
    public char getSymbol() {
        return 'R';
    }
}
