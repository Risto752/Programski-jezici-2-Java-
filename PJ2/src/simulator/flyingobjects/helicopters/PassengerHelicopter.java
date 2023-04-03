package simulator.flyingobjects.helicopters;

import map.Map;

public class PassengerHelicopter extends Helicopter {

            private int numberOfSeats;

            public PassengerHelicopter(Map map){
                super(map);
                model = "PassengerHelicopter";
            }

    @Override
    public String toString(){
        return "=PH";
    }


    public void passengersTrasport(){

                System.out.println("Passengers are being transported");

            }


}
