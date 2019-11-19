package sample.GameEntity.GameTile;

import java.util.LinkedList;
import java.util.Queue;

public class Road {
    public Queue<Integer> road = new LinkedList<>();
    public Road(){
        road.add(250350);
        road.add(250050);
        road.add(450050);
        road.add(450450);
        road.add(350450);
        road.add(350650);
        road.add(750650);
        road.add(750450);
        road.add(650450);
        road.add(650050);
        road.add(850050);
        road.add(850350);
        road.add(1150350);
    }
}
