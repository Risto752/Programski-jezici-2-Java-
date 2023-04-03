package simulator.flyingobjects;


import map.Map;
import simulator.Height;
import simulator.people.Person;

import java.io.File;
import java.io.IOException;
import java.util.*;

import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import static simulator.Simulator.*;


public abstract class Airplane extends Thread {

        protected Map map;
        private Height height;
        private int  speed;
        protected boolean isOnEdge = false;

        protected   String model = "";
        protected    int airplaneId;
        private static int id = 0;

        private  HashMap<String, Integer> characteristics;
        private ArrayList<Person> peopleOnBoard;

        protected int  coordinateX, coordinateY;
        protected String pointOfEntry = "";
        public boolean collision = false;
        public boolean exitMap = false;
        private boolean turnaround = true;
        private  int northExit, southExit, eastExit, westExit, minDistance;
        private boolean executeOnce = true;



    static {

        try {

            Handler handler = new FileHandler("logs" + File.separator + "airplane.log");
            Logger.getLogger(Airplane.class.getName()).addHandler(handler);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }






    public Airplane(Map map){

        this.map = map;

        airplaneId = ++id;

        Random rand = new Random();
        speed = rand.nextInt(3) + 1;

        setHeightOfFlight(rand);

        setStartingCoordinates();

    }



    public Airplane(Map map,int enemyPositionX, int enemyPositionY, String enemyPointOfEntry, String enemyHeight, String side){



        this.map = map;
        pointOfEntry = enemyPointOfEntry;
        Random rand = new Random();
        speed = rand.nextInt(3) + 1;

            setHeight(enemyHeight);

            setChasingEnemyCoordinates(enemyPositionX,enemyPositionY,side);


        }



    @Override
    public void run() {



        while (!exitMap) {

            if (enemyAirplane || !flightGranted) {

                escapeRoutine();

            } else {

                standardMotion();

            }
        }
    }

        private void setHeightOfFlight(Random rand){


            switch (rand.nextInt(3)){

                case 0 :{
                    height = Height.LOW;
                    break;
                }
                case 1 :{
                    height = Height.MEDIUM;
                    break;
                }
                case 2 : {
                    height = Height.HIGH;
                    break;
                }

            }





        }



        private void setHeight(String enemyHeight){

            switch (enemyHeight) {

                case "HIGH": {
                    this.height = Height.HIGH;
                    break;
                }
                case "MEDIUM": {
                    this.height = Height.MEDIUM;
                    break;
                }
                case "LOW": {
                    this.height = Height.LOW;
                    break;
                }
            }



        }





        private void setChasingEnemyCoordinates(int enemyPositionX,int enemyPositionY ,String side){

            switch (pointOfEntry){

                case "SOUTH" : {
                    coordinateY = 0;

                    if(side.equals("left")){

                        if(--enemyPositionX != -1){
                            coordinateX = enemyPositionX;
                        }else{
                            coordinateX = 0;
                            isOnEdge = true;
                        }
                    }
                    if(side.equals("right")){

                        if(++enemyPositionX != map.getRows()){
                            coordinateX = enemyPositionX;
                        }else{
                            coordinateX = map.getRows() - 1;
                            isOnEdge = true;
                        }


                    }
                    break;
                }
                case "NORTH" : {

                    coordinateY = map.getColumns() - 1;

                    if(side.equals("left")){

                        if(++enemyPositionX != map.getRows()){
                            coordinateX = enemyPositionX;
                        }else{
                            coordinateX = map.getRows()-1;
                            isOnEdge = true;
                        }
                    }
                    if(side.equals("right")){

                        if(--enemyPositionX != -1){
                            coordinateX = enemyPositionX;
                        }else{
                            coordinateX = 0;
                            isOnEdge = true;
                        }
                    }
                    break;
                }
                case "WEST" : {
                    coordinateX = 0;

                    if(side.equals("left")){

                        if(++enemyPositionY != map.getColumns()){
                            coordinateY = enemyPositionY;
                        }else{
                            coordinateY = map.getColumns()-1;
                            isOnEdge = true;
                        }
                    }
                    if(side.equals("right")){

                        if(--enemyPositionY != -1){
                            coordinateY = enemyPositionY;
                        }else{
                            coordinateY = 0;
                            isOnEdge = true;
                        }


                    }
                    break;
                }
                case "EAST" : {
                    coordinateX = map.getRows() - 1;

                    if(side.equals("left")){

                        if(--enemyPositionY != -1){
                            coordinateY = enemyPositionY;
                        }else{
                            coordinateY = 0;
                            isOnEdge = true;
                        }
                    }
                    if(side.equals("right")){

                        if(++enemyPositionY != map.getColumns()){
                            coordinateY = enemyPositionY;
                        }else{
                            coordinateY = map.getColumns() - 1;
                            isOnEdge = true;
                        }


                    }
                    break;
                }
            }


        }





        private void escapeRoutine(){

            if(executeOnce) {
                findShortestPath();
            }

            if( minDistance == northExit){
                escapeNorthExit();
            }
            else if(minDistance == southExit){
                escapeSouthExit();
            }
            else if(minDistance == eastExit){
                escapeEastExit();
            }
            else{
                escapeWestExit();
            }

        }





        protected void  standardMotion(){

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



        private void setStartingCoordinates(){

           Random rand = new Random();
           switch (rand.nextInt(4)){

               case 0 : {
                   coordinateY = 0;
                   coordinateX = rand.nextInt(map.getRows());
                   pointOfEntry = "SOUTH";
                   break;
               }
               case 1 : {
                   coordinateX = 0;
                   coordinateY = rand.nextInt(map.getColumns());
                   pointOfEntry = "WEST";
                   break;
               }
               case 2 : {
                   coordinateY = map.getColumns()-1;
                   coordinateX = rand.nextInt(map.getRows());
                   pointOfEntry = "NORTH";
                   break;
               }
               case 3 : {
                   coordinateX = map.getRows()-1;
                   coordinateY = rand.nextInt(map.getColumns());
                   pointOfEntry = "EAST";
                   break;
               }
           }

        }
        private void recordMapState(){
            map.getMap()[coordinateX][coordinateY].addAirplaneToField(this);

            try {
                Thread.sleep(speed*SECOND);
            } catch (InterruptedException e) {
                log(Level.SEVERE, Airplane.class.getName(), e);
            }
            map.getMap()[coordinateX][coordinateY].removeAirplanefromField(this);
        }

        protected void moveNorth(){
            recordMapState();
            coordinateY++;
            if (coordinateY == map.getColumns() || collision) {
                exitMap = true;
            }
        }

        protected  void moveEast(){

            recordMapState();
            coordinateX++;
            if (coordinateX == map.getRows() || collision) {
                exitMap = true;
            }

        }

        protected void moveSouth(){

            recordMapState();
            coordinateY--;
            if (coordinateY == -1 || collision) {
                exitMap = true;
            }

        }

        protected void moveWest(){
            recordMapState();
            coordinateX--;
            if (coordinateX == -1 || collision) {
                exitMap = true;
            }
        }

        private void findShortestPath(){



            northExit = map.getColumns() - coordinateY;
            southExit = coordinateY;
            eastExit = map.getRows() - coordinateX;
            westExit = coordinateX;


            minDistance = northExit;
            if(southExit < minDistance)
                minDistance = southExit;
            if(eastExit < minDistance)
                minDistance = eastExit;
            if(westExit < minDistance)
                minDistance = westExit;

            executeOnce = false;

        }


        private void escapeNorthExit(){

            if(pointOfEntry.equals("NORTH" )  &&   turnaround){
                moveEast();
                turnaround = false;
            }else {
                moveNorth();
            }

        }

        private void escapeSouthExit(){
            if(pointOfEntry.equals("SOUTH") && turnaround){
                moveWest();
                turnaround = false;
            }else {
                moveSouth();
            }
        }

        private void escapeEastExit(){
            if(pointOfEntry.equals("EAST") && turnaround){
                moveNorth();
                turnaround = false;
            }else {
                moveEast();

            }
        }
        private void escapeWestExit(){

            if(pointOfEntry.equals("WEST") && turnaround){
                moveSouth();
                turnaround = false;
            }else {
                moveWest();
            }


        }


        public Height getHeight(){
                return height;
        }

    private static void log(Level level, String name, Exception ex){

        Logger logger = Logger.getLogger(name);
        logger.log(level, ex.fillInStackTrace().toString());

    }

}


