package Game;

import java.util.Objects;

public class Position {
    public final int x;
    public final int y;

    public Position(Integer width, Integer height) {
        this.x = width;
        this.y = height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Objects.equals(x, position.x) && Objects.equals(y, position.y);
    }

    public boolean isNeighbour(Position position) {
        int dx = Math.abs(this.x - position.x);
        int dy = Math.abs(this.y - position.y);
        return (dx + dy == 1);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

}