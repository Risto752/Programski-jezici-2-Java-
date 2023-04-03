package map;


import gui.AlertFiles;
import simulator.EventFiles;
import simulator.flyingobjects.Airplane;
import simulator.flyingobjects.plane.MilitaryPlane;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import static radar.Radar.recordEvent;



public class Field {

        private int coordinateX,coordinateY;
        private ArrayList<Airplane> airplanesOnField = new ArrayList<>();
        private ArrayList<WarningObject> collisions = new ArrayList<>();


         Field(int coordinateX, int coordinateY){
                this.coordinateX = coordinateX;
                this.coordinateY = coordinateY;
        }


    static {

        try {

            Handler handler = new FileHandler("logs" + File.separator + "field.log");
            Logger.getLogger(Field.class.getName()).addHandler(handler);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



        public synchronized void updateMapTxt(FileWriter fileWriter, EventFiles events, AlertFiles alert) {

             recordCollision(alert);

            StringBuilder stringBuilder = new StringBuilder();
            String coordinatesString =   coordinateX + "=" + coordinateY;

            stringBuilder.append(coordinatesString);

            for (Airplane x : airplanesOnField) {

                if(x instanceof MilitaryPlane && ((MilitaryPlane) x).getIsEnemyAirplane() && !((MilitaryPlane) x).isAlreadyDetected){

                    events.addFile(recordEvent((MilitaryPlane)x,coordinateX,coordinateY));

                    ((MilitaryPlane) x).isAlreadyDetected = true;
                }
                stringBuilder.append(x.toString());
            }
            stringBuilder.append(System.lineSeparator());


            try {

                fileWriter.write(stringBuilder.toString());

            } catch (IOException e) {

               log(Level.SEVERE, Field.class.getName(), e);

            }


        }

        private void recordCollision(AlertFiles alert){

             for(WarningObject x : collisions) {

                 File file = new File("alert"+ File.separator + x.getWarningId());

                 try {
                     file.createNewFile();
                     FileOutputStream fos = new FileOutputStream(file);
                     ObjectOutputStream out = new ObjectOutputStream(fos);

                     out.writeObject(x);
                     alert.addFile(file);

                     out.close();

                 } catch (IOException e) {

                     log(Level.SEVERE, Field.class.getName(), e);

                 }

             }

             collisions.clear();


        }



        public synchronized void addAirplaneToField(Airplane airplane){
                checkForCollision(airplane);
                airplanesOnField.add(airplane);
        }

        public synchronized void removeAirplanefromField(Airplane airplane){
                airplanesOnField.remove(airplane);
        }


        public synchronized void checkForEnemy(){

             for(Airplane x : airplanesOnField){

                 if(x instanceof MilitaryPlane && ((MilitaryPlane) x).isAlreadyDetected) {
                     x.exitMap = true;
                 }


             }


        }


        private  void checkForCollision(Airplane airplane){
                for(Airplane x : airplanesOnField){
                        if(airplane.getHeight() == x.getHeight() ){
                                airplane.collision = true;
                                collisions.add(new WarningObject(airplane.toString(),x.toString(),coordinateX,coordinateY, LocalDateTime.now().toString()));
                               removeAirplanefromField(x);
                                x.collision = true;
                                return;
                        }
                }
        }


    private static void log(Level level, String name, Exception ex){

        Logger logger = Logger.getLogger(name);
        logger.log(level, ex.fillInStackTrace().toString());

    }


}
