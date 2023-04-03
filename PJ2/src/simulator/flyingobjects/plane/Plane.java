package simulator.flyingobjects.plane;


import simulator.flyingobjects.Airplane;
import map.Map;

public abstract class Plane extends Airplane {

    public Plane(Map map,int enemyPositionX, int enemyPositionY, String enemyPointOfEntry, String enemyHeight, String side){
        super(map,enemyPositionX,enemyPositionY,enemyPointOfEntry,enemyHeight, side);
    }

    Plane(Map map){
        super(map);
    }
}
