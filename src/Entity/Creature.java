package Entity;

import Game.Position;
import Game.World;

import java.util.Collections;
import java.util.List;

public abstract class
Creature extends Entity{
    private int speed;
    private int hp;
    protected int maxHp;

    public Creature(Position position, int speed, int hp) {
        super(position);
        this.speed = speed;
        this.hp = hp;
        this.maxHp = hp;
    }

    public abstract void makeMove(World world);

    public void moveRandom(World world) {
        for (int i = 0; i < getSpeed(); i++) {
            List<Position> neibours = world.getNeighbours(this.position);
            Collections.shuffle(neibours);

            boolean moved = false;
            for (Position pos : neibours) {
                if (world.getEntity(pos) == null) {
                    world.moveEntity(this, pos);
                    moved = true;
                    break;
                }
            }
            if (!moved) break;
        }
    }

    void takeDamage(int damage){
        this.hp -= damage;
    }

    boolean isAlive(){
        return hp <= 0;
    }

    public int getSpeed() {
        return speed;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMaxHp() {
        return maxHp;
    }
}
