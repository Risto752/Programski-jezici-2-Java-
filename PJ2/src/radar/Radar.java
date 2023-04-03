package radar;


import gui.AlertFiles;

import map.Map;

import simulator.EventFiles;
import simulator.flyingobjects.plane.MilitaryPlane;

import java.io.*;
import java.time.LocalDateTime;

import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import static simulator.Simulator.SECOND;

public class Radar extends Thread {

    private Map map;
    private int radarTimer;
    private EventFiles events;
    public static final File file = new File("map.txt");
    private AlertFiles alert;


    public Radar(Map map, int radarTimer, EventFiles events, AlertFiles alert) {

        this.map = map;
        this.radarTimer = radarTimer;
        this.events = events;
        this.alert = alert;

    }

    static {

        try {

            Handler handler = new FileHandler("logs" + File.separator + "radar.log");
            Logger.getLogger(Radar.class.getName()).addHandler(handler);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }




    private void radarSweep(FileWriter fileWriter, AlertFiles alert) {

        for (int i = 0; i < map.getRows(); i++)
            for (int j = 0; j < map.getColumns(); j++) {
                map.getMap()[i][j].updateMapTxt(fileWriter,events,alert);
            }


    }



    public static File recordEvent(MilitaryPlane x, int coordinateX, int coordinateY){

        File file = new File("events"+ File.separator +"Hours_" + LocalDateTime.now().getHour()+"_   Minutes_"+LocalDateTime.now().getMinute()+"_   Seconds_"+LocalDateTime.now().getSecond()+"_.txt");
        StringBuilder stringBuilderEvents = new StringBuilder();
        String temp =  coordinateX+"="+coordinateY;
        stringBuilderEvents.append(temp);

        try {
            file.createNewFile();

            FileWriter fw = new FileWriter(file);

            fw.write(stringBuilderEvents.append(x.eventsInformation()).toString());
            fw.close();


        } catch (IOException e) {
            log(Level.SEVERE, Radar.class.getName(), e);
        }

       return file;

    }



    @Override
    public void run() {


       while(true) {

           try {
               Thread.sleep(radarTimer*SECOND);
           } catch (InterruptedException e) {
               log(Level.SEVERE, Radar.class.getName(), e);
           }

           try {
               file.createNewFile();

               FileWriter fileWriter = new FileWriter(file);
            synchronized (file) {
                radarSweep(fileWriter,alert);
            }
               fileWriter.close();
           } catch (IOException  e) {
               log(Level.SEVERE, Radar.class.getName(), e);
           }


       }

    }

    private static void log(Level level, String name, Exception ex){

        Logger logger = Logger.getLogger(name);
        logger.log(level, ex.fillInStackTrace().toString());

    }

}





