package simulator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import static simulator.Simulator.*;


public class FileWatcher extends Thread {


                    @Override
                public void run(){
                    while(true){

                        readMilitaryParameters();

                        try {
                            Thread.sleep(SECOND);
                        } catch (InterruptedException e) {
                            log(Level.SEVERE, FileWatcher.class.getName(),e);
                        }
                    }
                }



    static {

        try {

            Handler handler = new FileHandler("logs" + File.separator + "filewatcher.log");
            Logger.getLogger(FileWatcher.class.getName()).addHandler(handler);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



          private static void readMilitaryParameters(){

        try (BufferedReader br = new BufferedReader(new FileReader("config.properties.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("=");

                switch (parts[0]){
                    case "enemyAirplane" :{
                        enemyAirplane = Boolean.parseBoolean(parts[1]);
                        break;
                    }
                    case "javaAirplane" : {
                        javaAirplane = Boolean.parseBoolean(parts[1]);
                        break;
                    }
                }

            }
        } catch (IOException e) {
            log(Level.SEVERE, FileWatcher.class.getName(),e);
        }
    }


    private static void log(Level level, String name, Exception ex){

        Logger logger = Logger.getLogger(name);
        logger.log(level, ex.fillInStackTrace().toString());

    }



}
