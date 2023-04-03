package simulator.flyingobjects.plane;

import map.Map;



public abstract class MilitaryPlane extends Plane {

        boolean carryWeapons = true;
       private boolean isEnemyAirplane;
       public boolean isAlreadyDetected = false;

        MilitaryPlane(Map map, boolean isEnemyAirplane){
           super(map);
           this.isEnemyAirplane = isEnemyAirplane;
        }

         MilitaryPlane(Map map,int enemyPositionX, int enemyPositionY, String enemyPointOfEntry, String enemyHeight, String side){
           super(map,enemyPositionX,enemyPositionY,enemyPointOfEntry,enemyHeight,side);
           this.isEnemyAirplane = false;
        }


        public boolean getIsEnemyAirplane(){
            return isEnemyAirplane;
        }

         abstract void targetAttack();


        public String eventsInformation(){
            String type = isEnemyAirplane ? "enemyAirplane":"javaAirplane";
            return "=" + pointOfEntry +"=" + getHeight() +"=" + model + "Type="+ type+"AirplaneID="+airplaneId;
        }


        @Override
        public void run(){



            while (!exitMap) {

                standardMotion();

                }
            }


        }






