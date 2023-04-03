package simulator.flyingobjects.plane;

import map.Map;

public class Bombarder extends MilitaryPlane {

    public Bombarder(Map map, boolean isEnemyAirplane){
        super(map, isEnemyAirplane);
        model = "Bombarder";
    }
    @Override
    public String toString(){
        if(getIsEnemyAirplane())
        return "=B*";
        else
            return"=B";
    }


    @Override
     public  void targetAttack() {
            System.out.println("Bombing targets on ground.");
     }

}
