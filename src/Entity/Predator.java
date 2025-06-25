package Entity;

import Game.Position;
import Game.World;

import java.util.ArrayList;
import java.util.List;

public class Predator extends Creature{
    private int damage;

    public Predator(Position position, int speed, int hp, int damage) {
        super(position, speed, hp);
        this.damage = damage;
    }

    public void attack(Herbivore target, World world){
        target.setHp(target.getHp() - damage);
        if(target.getHp() <= 0){
            world.removeEntity(target.getPosition());
            setHp(Math.min(getHp() + 20, getMaxHp()));
        }
    }

    @Override
    public void makeMove(World world) {
        if (getHp() <= 0) return;

        setHp(getHp() - 2);
        if (getHp() <= 0) {
            world.removeEntity(this.position);
            return;
        }

        Position nearestHerb = world.findNearestHerbivore(this.position);
        if (nearestHerb == null) {
            moveRandom(world);
            return;
        }

        if (this.position.isNeighbour(nearestHerb)) {
            Entity target = world.getEntity(nearestHerb);
            if (target instanceof Herbivore) {
                attack((Herbivore) target, world);
            }
            return;
        }

        Position nextSToHerb = world.findClosestHerbivore(this.position, nearestHerb);
        if (nextSToHerb == null) return;

        List<Position> path = world.findPath(this.position, nextSToHerb);
        if (path.isEmpty()) {
            moveRandom(world);
            return;
        }

        int currenttick = world.getTickCounter();
        int moveSteps = (currenttick % 5 == 0) ? (getSpeed() + 2) : getSpeed();
//        System.out.println("tick=" + currenttick + " moveSteps=" + moveSteps + " path.size=" + path.size());
        int steps = Math.min(moveSteps, path.size());
        for (int i = 0; i < steps; i++) {
            Position nextStep = path.get(i); //Было
            boolean moved = world.moveEntity(this, nextStep);
            if (!moved) break;

        }
    }
//        List<Position> path = world.findPath(this.position, nearestHerb); //Было
//        if (path.isEmpty()) return; //Было
//            Position nextStep = path.get(i); //Было // path.get(0)
//            world.moveEntity(this, nextStep); // Было

//            if (nextStep.equals(nearestHerb) || this.position.isNeighbour(nearestHerb)){
//                Entity target = world.getEntity(nearestHerb);
//                if(target instanceof Herbivore){
//                    attack((Herbivore) target, world);
//                }
//                return;

//        Position nextStep = world.findClosestHerbivore(this.position,nearestHerb);
//        if (nextStep != null) {
//            world.moveEntity(this, nextStep);
//        }

//    Position nextStep = world.findClosestHerbivore(this.position, nearestHerb);
//        if (nextStep != null) {
//        world.moveEntity(this, nextStep);
//    }


    @Override
    public char getSymbol() {
        return 'P';
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
