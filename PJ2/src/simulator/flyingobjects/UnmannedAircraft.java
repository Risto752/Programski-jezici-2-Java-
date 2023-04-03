package simulator.flyingobjects;

import map.Map;

public class UnmannedAircraft extends Airplane {

            public UnmannedAircraft(Map map){
                super(map);
                model = "UnmannedAircraft";
            }

    @Override
    public String toString(){
        return "=U";
    }



    public void shootingTerrain(){

                System.out.println("Terrain is being recorded");

            }
}
