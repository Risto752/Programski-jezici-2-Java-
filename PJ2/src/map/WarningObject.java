package map;


import java.io.Serializable;

public class WarningObject implements Serializable {

            private String crashedPlane1, crashedPlane2 ;
            private int coordinateX, coordinateY;
            private String timeOfCollision;
            private static int id = 0;
            private int warningId;

            WarningObject(String crashedPlane1,String crashedPlane2, int coordinateX, int coordinateY, String timeOfCollision){

                warningId = ++id;
                this.crashedPlane1 = crashedPlane1;
                this.crashedPlane2 = crashedPlane2;
                this.coordinateX = coordinateX;
                this.coordinateY = coordinateY;
                this.timeOfCollision = timeOfCollision;


            }


             int getWarningId(){
                return warningId;
            }

            public int getCoordinateX(){
                return coordinateX;
            }
            public int getCoordinateY(){
                return coordinateY;
            }
            public String getTimeOfCollision(){
                return timeOfCollision;
            }
            public String getCrashedPlane1(){
                return crashedPlane1;
            }
            public String getCrashedPlane2(){
                return crashedPlane2;
            }

            @Override
        public String toString(){

                return "Crashed Airplane number 1: "+ crashedPlane1 +"Crashed Airplane number 2" + crashedPlane2 + " X: "+ coordinateX +" Y: "+ coordinateY +" Time of collision: "+ timeOfCollision;

            }


}
