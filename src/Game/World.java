package Game;

import Entity.Entity;
import Entity.Grass;
import Entity.Rock;
import Entity.Tree;
import Entity.Creature;
import Entity.Herbivore;
import Entity.Predator;
import java.util.*;


public class World {
    private final int width;
    private final int height;
    private Map<Position, Entity> world;
    private int tickCounter = 0;

    public World(int width, int height) {
        this.width = width;
        this.height = height;
        this.world = new HashMap<>();
        this.tickCounter = 0;
    }

    boolean isInZone(Position pos) {
        int x = pos.getX();
        int y = pos.getY();
        return (x >= 0 && x < width && y >= 0 && y < height);
    }

    public Entity getEntity(Position pos) {
        return world.get(pos);
    }

    public Entity removeEntity(Position pos) {
        return world.remove(pos);
    }

    public boolean addEntity(Entity entity) {
        Position pos = entity.getPosition();
        if (!isInZone(pos) || world.containsKey(pos)) {
            return false;
        }
        world.put(pos, entity);
        entity.setPosition(pos);
        return true;
    }

    public boolean moveEntity(Entity entity, Position pos) {
        if (!isInZone(pos)) {
            return false;
        }

        Entity target = world.get(pos);
        if (target != null) {
            if (target instanceof Rock || target instanceof Tree || target instanceof Creature) {
                return false;
            }
            if (entity instanceof Herbivore || target instanceof Grass) {
            }else {
                return false;
            }
        }

        Position oldPos = entity.getPosition();
        world.remove(oldPos);
        world.put(pos, entity);
        entity.setPosition(pos);
        return true;
    }

    public void render(){
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Entity e = getEntity(new Position(x, y));
                if (e == null) {
                    System.out.print(".");
                } else {
                    System.out.print(e.getSymbol());
                }
            }
            System.out.println();
        }
    }

    public List<Position> getNeighbours(Position pos) {
        List<Position> neighbours = new ArrayList<>();
        int x = pos.getX();
        int y = pos.getY();

        int [] dx = {0, 0, -1, 1};
        int [] dy = {1, -1, 0, 0};

        for (int i = 0; i < 4; i++) {
            int newX = x + dx[i];
            int newY = y + dy[i];
            Position neighbor = new Position(newX, newY);
            if (isInZone(neighbor)) {
                neighbours.add(neighbor);
            }
        }
        return neighbours;
    }

    public Position findNearestGrass(Position start) {
        Set<Position> visited = new HashSet<>();
        Queue<Position> queue = new LinkedList<>();
        queue.add(start);
        visited.add(start);
        while (!queue.isEmpty()) {
            Position current = queue.poll();
            Entity entity = getEntity(current);
            if (entity instanceof Grass){
                return current;
            }
            for (Position neighbor : getNeighbours(current)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
        return null;
    }

    public Position findNearestHerbivore(Position start) {
        Set<Position> visited = new HashSet<>();
        Queue<Position> queue = new LinkedList<>();
        queue.add(start);
        visited.add(start);
        while (!queue.isEmpty()) {
            Position current = queue.poll();
            Entity entity = getEntity(current);
            if (entity instanceof Herbivore){
                return current;
            }
            for (Position neighbor : getNeighbours(current)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
        return null;
    }

    public List<Position> findPath(Position start, Position goal) {
        Queue<Position> queue = new LinkedList<>();
        Set<Position> visited = new HashSet<>();
        Map<Position, Position> cameFrom = new HashMap<>();

        queue.add(start);
        visited.add(start);
        while (!queue.isEmpty()) {
            Position current = queue.poll();

            if (current.equals(goal)) {
                List<Position> path = new ArrayList<>();
                Position step = goal;
                while (!step.equals(start)) {
                    path.add(step);
                    step = cameFrom.get(step);
                }
                Collections.reverse(path);
                return path;
            }
            for (Position neighbor : getNeighbours(current)) {
                if (visited.contains(neighbor)) continue;
                Entity e = getEntity(neighbor);
                if (e instanceof Rock || e instanceof Tree || e instanceof Creature) continue;

                visited.add(neighbor);
                cameFrom.put(neighbor, current);
                queue.add(neighbor);
            }
        }
        return Collections.emptyList();
    }

    public Position findClosestHerbivore(Position start, Position goal) {
        for (Position neighbour : getNeighbours(goal)) {
            if (isInZone(neighbour) && getEntity(neighbour) == null) {
                List<Position> path = findPath(start, neighbour);
                if (!path.isEmpty()) {
                    return path.get(0);
                }
            }
        }
        return null;
    }

    public void turnAction(){
        List<Entity> entities = new ArrayList<>(getWorld().values());
        for (Entity entity : entities) {
            if (entity instanceof Creature) {
                ((Creature) entity).makeMove(this);
            }
        }
        tickCounter++;
    }

    public static World initAction() {
        World world = new World(10,10);

        world.addEntity(new Grass(new Position(3,3)));
        world.addEntity(new Grass(new Position(6,6)));
        world.addEntity(new Grass(new Position(4,7)));
        world.addEntity(new Grass(new Position(7,4)));
        world.addEntity(new Grass(new Position(0,9)));
        world.addEntity(new Grass(new Position(9,0)));
        world.addEntity(new Grass(new Position(2,5)));
        world.addEntity(new Grass(new Position(5,2)));

        world.addEntity(new Rock(new Position(4,4)));
        world.addEntity(new Rock(new Position(6,2)));
        world.addEntity(new Rock(new Position(2,1)));
        world.addEntity(new Rock(new Position(7,7)));

        world.addEntity(new Tree(new Position(3,6)));
        world.addEntity(new Tree(new Position(5,5)));
        world.addEntity(new Tree(new Position(6,3)));
        world.addEntity(new Tree(new Position(5,7)));

        world.addEntity(new Herbivore(new Position(1,1), 2, 100));
        world.addEntity(new Herbivore(new Position(1,8), 2, 100));
        world.addEntity(new Herbivore(new Position(8,1), 2, 100));
        world.addEntity(new Herbivore(new Position(8,8), 2, 100));

        world.addEntity(new Predator(new Position(0,4),1,100,50));
        world.addEntity(new Predator(new Position(9,5),1,100,50));

        return world;
    }

    public Map<Position, Entity> getWorld() {
        return world;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getTickCounter() {
        return tickCounter;
    }
}
