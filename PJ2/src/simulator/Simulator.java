package simulator;

import map.Map;
import simulator.flyingobjects.UnmannedAircraft;
import simulator.flyingobjects.helicopters.FireProtectingHelicopter;
import simulator.flyingobjects.helicopters.PassengerHelicopter;
import simulator.flyingobjects.helicopters.TransportHelicopter;
import simulator.flyingobjects.plane.*;


import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Simulator extends Thread {

    private Map map;
    private int simulatorTimer;
    private EventFiles events;
    public static boolean flightGranted = true;


     public static boolean enemyAirplane = false, javaAirplane = false;
     public static final int SECOND = 1000;

    public Simulator(Map map, int simulatorTimer,EventFiles events) {
        this.map = map;
        this.simulatorTimer = simulatorTimer;
        this.events = events;
    }



    static {

        try {

            Handler handler = new FileHandler("logs" + File.separator + "simulator.log");
            Logger.getLogger(Simulator.class.getName()).addHandler(handler);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }





    @Override
    public void run() {

        FileWatcher fileWatcher = new FileWatcher();
        fileWatcher.start();



        while (true) {

            Random rand = new Random();

            generateMilitaryAirplane(rand);
            generateCivilAirplane(rand);

            chaseEnemyAirplanes();

            try {
                Thread.sleep(simulatorTimer * SECOND);
            } catch (InterruptedException e) {
                log(Level.SEVERE, Simulator.class.getName(), e);
            }


        }

    }


    private void chaseEnemyAirplanes(){
        events.processFiles(map);
    }


    private void generateCivilAirplane(Random rand) {

        if (!enemyAirplane && flightGranted) {

            switch (rand.nextInt(7)) {

                case 0: {
                    new TransportPlane(map).start();
                    break;
                }
                case 1: {
                    new PassengerPlane(map).start();
                    break;
                }
                case 2: {
                    new FireProtectingPlane(map).start();
                    break;
                }
                case 3: {
                    new TransportHelicopter(map).start();
                    break;
                }
                case 4: {
                    new PassengerHelicopter(map).start();
                    break;
                }
                case 5: {
                    new FireProtectingHelicopter(map).start();
                    break;
                }
                case 6: {
                    new UnmannedAircraft(map).start();
                    break;
                }
            }

        }
    }


    private void generateMilitaryAirplane(Random rand) {

        if (enemyAirplane) {

            switch (rand.nextInt(2)) {

                case 0: {
                    new Bombarder(map, enemyAirplane).start();
                    break;
                }
                case 1: {
                    new Fighter(map, enemyAirplane).start();
                    break;
                }
            }
        }
        if (javaAirplane) {

            switch (rand.nextInt(2)) {

                case 0: {
                    new Bombarder(map, !javaAirplane).start();
                    break;
                }
                case 1: {
                    new Fighter(map, !javaAirplane).start();
                    break;
                }


            }

        }

    }


    private static void log(Level level, String name, Exception ex){

        Logger logger = Logger.getLogger(name);
        logger.log(level, ex.fillInStackTrace().toString());

    }



}