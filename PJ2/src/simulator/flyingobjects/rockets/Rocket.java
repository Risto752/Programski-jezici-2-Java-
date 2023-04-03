package simulator.flyingobjects.rockets;




public abstract class Rocket extends Thread  {

    private int reach, speed, rocketID, height;
    String pointOfEntry = "";



    @Override
    public void run(){

        while(true){

            standardMotion();

        }



    }


    private void standardMotion(){

        switch (pointOfEntry) {

            case "SOUTH": {
                moveNorth();
                break;
            }
            case "WEST": {
                moveEast();
                break;
            }
            case "NORTH": {
                moveSouth();
                break;
            }
            case "EAST": {
                moveWest();
                break;
            }
        }


    }

    private void moveNorth(){
    }
    private void moveEast(){
    }
    private void moveSouth(){
    }
    private void moveWest(){
    }

}



