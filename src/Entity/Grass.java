package Entity;

import Game.Position;

public class Grass extends Entity{

    public Grass(Position position) {
        super(position);
    }

    @Override
    public char getSymbol() {
        return 'G';
    }
}
