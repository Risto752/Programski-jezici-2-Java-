package simulator.flyingobjects.plane;

import map.Map;

        public class Fighter extends MilitaryPlane {


                private String side = "";


                public Fighter(Map map, boolean isEnemyAirplane){
                        super(map, isEnemyAirplane);
                        model = "Fighter";
                }

                public Fighter(Map map,int enemyPositionX, int enemyPositionY, String enemyPointOfEntry, String enemyHeight, String side){
                        super(map,enemyPositionX,enemyPositionY,enemyPointOfEntry,enemyHeight, side);
                        this.side = side;
                }

                @Override
                public void run() {

                        while (!exitMap) {

                                if (!getIsEnemyAirplane() && !side.equals("")) {

                                        if (isOnEdge) {
                                                destroyEnemyOnEdge();

                                        } else {
                                                destoryEnemy();
                                        }
                                } else {

                                        standardMotion();

                                }

                        }
                }


                private void destoryEnemy(){


                        switch (pointOfEntry) {


                                case "SOUTH": {
                                        if (side.equals("left")) {
                                                map.getMap()[coordinateX + 1][coordinateY].checkForEnemy();
                                        }
                                        if (side.equals("right")) {
                                                map.getMap()[coordinateX - 1][coordinateY].checkForEnemy();
                                        }
                                        moveNorth();
                                        break;
                                }
                                case "WEST": {
                                        if (side.equals("left")) {
                                                map.getMap()[coordinateX][coordinateY - 1].checkForEnemy();
                                        }
                                        if (side.equals("right")) {
                                                map.getMap()[coordinateX][coordinateY + 1].checkForEnemy();
                                        }
                                        moveEast();
                                        break;
                                }
                                case "NORTH": {
                                        if (side.equals("left")) {
                                                map.getMap()[coordinateX - 1][coordinateY].checkForEnemy();
                                        }
                                        if (side.equals("right")) {
                                                map.getMap()[coordinateX + 1][coordinateY].checkForEnemy();
                                        }
                                        moveSouth();
                                        break;
                                }
                                case "EAST": {
                                        if (side.equals("left")) {
                                                map.getMap()[coordinateX][coordinateY + 1].checkForEnemy();
                                        }
                                        if (side.equals("right")) {
                                                map.getMap()[coordinateX][coordinateY - 1].checkForEnemy();
                                        }
                                        moveWest();
                                        break;
                                }
                        }


                }


              private  void destroyEnemyOnEdge(){



                        switch (pointOfEntry) {

                                case "SOUTH": {
                                        if ((coordinateY + 1) != map.getColumns())
                                                map.getMap()[coordinateX][coordinateY + 1].checkForEnemy();
                                        moveNorth();
                                        break;
                                }
                                case "WEST": {
                                        if ((coordinateX + 1) != map.getRows())
                                                map.getMap()[coordinateX + 1][coordinateY].checkForEnemy();
                                        moveEast();
                                        break;
                                }
                                case "NORTH": {
                                        if ((coordinateY - 1) != -1)
                                                map.getMap()[coordinateX][coordinateY - 1].checkForEnemy();
                                        moveSouth();
                                        break;
                                }
                                case "EAST": {
                                        if ((coordinateX - 1) != -1)
                                                map.getMap()[coordinateX - 1][coordinateY].checkForEnemy();
                                        moveWest();
                                        break;
                                }
                        }





                }



                @Override
                public String toString(){
                        if(getIsEnemyAirplane())
                        return "=F*";
                        else
                                return "=F";
                }



                @Override
                public void targetAttack(){
                        System.out.println("Shooting targets in air and on the ground.");
                }


        }
