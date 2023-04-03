package simulator;

import map.Map;
import simulator.flyingobjects.plane.Fighter;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;

import java.io.*;
import java.util.ArrayList;

import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EventFiles {

        private ArrayList<File> files = new ArrayList<>();
        private ArrayList<File> filesGui = new ArrayList<>();

    static {

        try {

            Handler handler = new FileHandler("logs" + File.separator + "eventfiles.log");
            Logger.getLogger(EventFiles.class.getName()).addHandler(handler);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



        public synchronized void processFilesGui(JLabel label, DefaultListModel<String> model){

            if(!filesGui.isEmpty()){

                for(File f : filesGui){

                    try(BufferedReader br = new BufferedReader(new FileReader(f))){

                        String line;
                        while((line = br.readLine()) != null){

                            String[] enemyInfo = line.split("=");

                            label.setText("Strana vojna letjelica je detektovana u vazdušnom prostoru na koordinatama X: " + enemyInfo[0]+" Y: " + enemyInfo[1]);
                            label.setForeground(ColorUIResource.RED);
                            model.addElement("Strana vojna letjelica je detektovana u vazdušnom prostoru na koordinatama X: " + enemyInfo[0]+" Y: " + enemyInfo[1]);

                        }
                    } catch (IOException e) {
                        log(Level.SEVERE, EventFiles.class.getName(), e);
                    }


                }
            }

            filesGui.clear();

        }

        public synchronized void addFile(File f){
                files.add(f);
                filesGui.add(f);
        }

        synchronized void processFiles(Map map){

            if(!files.isEmpty()){

                for(File f : files){

                    try(BufferedReader br = new BufferedReader(new FileReader(f))){

                        String line;
                        while((line = br.readLine()) != null){

                            String[] enemyInfo = line.split("=");

                            int enemyPositionX = Integer.parseInt(enemyInfo[0]);
                            int enemyPositionY = Integer.parseInt(enemyInfo[1]);

                            String pointOfEntry = enemyInfo[2];
                            String height = enemyInfo[3];

                            new Fighter(map,enemyPositionX,enemyPositionY,pointOfEntry,height,"left").start();
                            new Fighter(map,enemyPositionX,enemyPositionY,pointOfEntry,height,"right").start();

                        }
                    } catch (IOException e) {
                        log(Level.SEVERE, EventFiles.class.getName(), e);
                    }


                }
                files.clear();
            }
        }


    private static void log(Level level, String name, Exception ex){

        Logger logger = Logger.getLogger(name);
        logger.log(level, ex.fillInStackTrace().toString());

    }

}
