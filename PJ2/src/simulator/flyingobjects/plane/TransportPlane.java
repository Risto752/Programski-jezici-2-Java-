package simulator.flyingobjects.plane;

import map.Map;

public class TransportPlane extends Plane {

       private int maxLoadWeight;

      public TransportPlane(Map map){
          super(map);
          model = "TransportPlane";
    }


    @Override
    public String toString(){
        return "=TP";
    }



    public void loadTransportation(){

            System.out.println("Load is being transported.");

        }


}
