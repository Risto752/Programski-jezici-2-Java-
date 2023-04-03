package simulator.flyingobjects.plane;

import map.Map;

public class PassengerPlane extends Plane {

           private int numberOfSeats;
           private int maxLuggageWeight;

           public PassengerPlane(Map map){
               super(map);
               model = "PassengerPlane";
           }

    @Override
    public String toString(){
        return "=PP";
    }


    public void passengerTransportation(){

               System.out.println("Passengers are being transported.");

           }


}
