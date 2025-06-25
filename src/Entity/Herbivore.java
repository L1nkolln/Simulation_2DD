package Entity;

import Game.Position;
import Game.World;

import java.util.List;

public class Herbivore extends Creature{

    public Herbivore(Position position, int speed, int hp) {
        super(position, speed, hp);
    }

    void eat(World world, Position pos){
        Entity target = world.getEntity(pos);
        if(target instanceof Grass){
            world.removeEntity(pos);
            setHp(Math.min(getHp() + 15, getMaxHp()));
        }
    }

    @Override
    public void makeMove(World world) {
      if (getHp() <= 0) {
          return;
      }

      setHp(getHp() - 1);
      if (getHp() <= 0) {
          world.removeEntity(this.position);
          return;
      }

      Position nearestGrass = world.findNearestGrass(this.position);
      if (nearestGrass == null) {
          moveRandom(world);
          return;
      }

      if (this.position.equals(nearestGrass)) {
          eat(world, this.position);
          return;
      }

      List<Position> path = world.findPath(this.position, nearestGrass);
      if (path.isEmpty()) {
          moveRandom(world);
          return;
      }

      int steps = Math.min(getSpeed(), path.size());

      for (int i = 0; i < steps; i++) {
          Position nextStep = path.get(i);
          if (nextStep.equals(nearestGrass)) {
              world.moveEntity(this, nextStep);
              eat(world, nextStep);
              return;
          }

          boolean moved = world.moveEntity(this, nextStep);
          if (!moved) break;

      }
    }

    @Override
    public char getSymbol() {
        return 'H';
    }
}
