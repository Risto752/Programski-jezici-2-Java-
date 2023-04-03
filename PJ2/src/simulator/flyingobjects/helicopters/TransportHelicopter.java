package simulator.flyingobjects.helicopters;

import map.Map;

public class TransportHelicopter extends Helicopter {

       private int maxLoadWeight;

       public TransportHelicopter(Map map){
           super(map);
           model = "TransportHelicopter";
       }

    @Override
    public String toString(){
        return "=TH";
    }


    public void loadTransportation(){
            System.out.println("Load is being transported.");
        }

}
