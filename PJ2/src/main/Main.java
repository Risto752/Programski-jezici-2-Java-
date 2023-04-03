package main;



import backup.ZipDirectory;
import gui.AlertFiles;
import gui.Window;
import radar.Radar;

import simulator.EventFiles;
import simulator.Simulator;

import map.Map;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main {


     static private  int rowsOfMap, columnsOfMap;
     static private int simulatorTimer, radarTimer;


    static {

        try {

            Handler handler = new FileHandler("logs" + File.separator + "parameters.log");
            Logger.getLogger(Main.class.getName()).addHandler(handler);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }




    public static void main(String []args){

        EventFiles events = new EventFiles();
        AlertFiles alert = new AlertFiles();

        readSimulatorParameters();
        readRadarParameters();

        Map map = new Map(rowsOfMap,columnsOfMap);

        Simulator simulator = new Simulator(map, simulatorTimer,events);
        simulator.start();

        Radar radar = new Radar(map,radarTimer,events,alert);
        radar.start();

       Thread guiThread = new Thread(new Window(map,alert,events));
        guiThread.start();

        ZipDirectory zip = new ZipDirectory();
        zip.start();


    }







    private static void readSimulatorParameters(){

        try (BufferedReader br = new BufferedReader(new FileReader("config.properties.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("=");

                switch (parts[0]){
                    case "rowsOfMap" : {
                        rowsOfMap = Integer.parseInt(parts[1]);
                        if(rowsOfMap <= 0){
                            throw new NumberFormatException("Unjeli ste nepredvidjenu vrijednosti za parametre, vrijednosti parametara ce biti setovane na default vrijednosti");
                        }
                        break;
                    }
                    case "columnsOfMap" : {
                        columnsOfMap = Integer.parseInt(parts[1]);
                        if(columnsOfMap <= 0){
                            throw new NumberFormatException("Unjeli ste nepredvidjenu vrijednosti za parametre, vrijednosti parametara ce biti setovane na default vrijednosti");
                        }
                        break;
                    }
                    case "simulatorTimer" : {
                        simulatorTimer = Integer.parseInt(parts[1]);
                        if(simulatorTimer <= 0){
                            throw new NumberFormatException("Unjeli ste nepredvidjenu vrijednosti za parametre, vrijednosti parametara ce biti setovane na default vrijednosti");
                        }
                        break;
                    }
                }

            }
        } catch (IOException | NumberFormatException e) {
            log(Level.WARNING, Main.class.getName(),e);
            rowsOfMap = 100;
            columnsOfMap = 100;
            simulatorTimer = 10;
        }
    }


    private static void readRadarParameters(){

        try (BufferedReader br = new BufferedReader(new FileReader("radar.properties.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("=");

                        radarTimer = Integer.parseInt(parts[1]);
                        if(radarTimer <= 0){
                            throw new NumberFormatException("Unjeli ste nepredvidjenu vrijednosti za parametre, vrijednosti parametara ce biti setovane na default vrijednosti");
                        }

            }
        } catch (IOException | NumberFormatException e) {

            log(Level.WARNING, Main.class.getName(), e);
            radarTimer = 1;

        }


    }

    private static void log(Level level, String name, Exception ex){

        Logger logger = Logger.getLogger(name);
        logger.log(level, ex.fillInStackTrace().toString());

    }



}
