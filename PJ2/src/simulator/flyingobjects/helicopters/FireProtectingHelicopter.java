package simulator.flyingobjects.helicopters;

import map.Map;

public class FireProtectingHelicopter extends Helicopter {

            private int maxWaterCapacity;

            public FireProtectingHelicopter(Map map){

                super(map);
                model = "FireProtectingHelicopter";
            }

            @Override
           public String toString(){
                return "=FH";
            }


            public void fireFighting(){
                System.out.println("Fire is being extinguished.");
            }


}
