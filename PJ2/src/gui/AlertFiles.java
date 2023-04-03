package gui;

import map.WarningObject;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AlertFiles {

        private ArrayList<File> alertFiles = new ArrayList<>();
       private  ArrayList<String> alertStorage = new ArrayList<>();


    static {

        try {

            Handler handler = new FileHandler("logs" + File.separator +"alert.log");
            Logger.getLogger(AlertFiles.class.getName()).addHandler(handler);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


       ArrayList<String> getAlertStorage(){
           return alertStorage;
       }

        public synchronized void addFile(File f){

            alertFiles.add(f);

        }

        synchronized void processAlertFiles(){

            if(!alertFiles.isEmpty()){

                for(File f : alertFiles){

                    WarningObject warning;

                    try {
                        FileInputStream fis = new FileInputStream(f);
                        ObjectInputStream in = new ObjectInputStream(fis);

                        warning = (WarningObject)in.readObject();
                        alertStorage.add(warning.toString());

                        JFrame popUp = new JFrame();
                        JLabel label = new JLabel("Sudar se desio na koordinatama X: " +warning.getCoordinateX()+" Y: "+warning.getCoordinateY()+" imedju (aviona 1)" + warning.getCrashedPlane1()+" i (aviona 2) "+warning.getCrashedPlane2()+" Vrijeme sudara: " + warning.getTimeOfCollision());

                        popUp.setSize(800,100);
                        popUp.getContentPane().add(label, BorderLayout.CENTER);
                        popUp.setVisible(true);


                    } catch (ClassNotFoundException | IOException e) {

                       log(Level.SEVERE, AlertFiles.class.getName(), e);

                    }

                }

                alertFiles.clear();

            }



        }

    private static void log(Level level, String name, Exception ex){

        Logger logger = Logger.getLogger(name);
        logger.log(level, ex.fillInStackTrace().toString());

    }



}
