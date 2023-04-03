package simulator.flyingobjects.plane;

import map.Map;

public class FireProtectingPlane extends Plane {

           private int maxWaterCapacity;

          public FireProtectingPlane(Map map){
               super(map);
               model = "FireProtectingPlane";
           }

    @Override
    public String toString(){
        return "=FP";
    }


    public void fireFighting(){

               System.out.println("Fire is being extinguished.");

           }

}
