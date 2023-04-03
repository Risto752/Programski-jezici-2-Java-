package backup;

import java.io.*;
import java.time.LocalDateTime;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipDirectory extends Thread {


     private static final int SECOND = 1000;
     private static final int BLOCK = 1024;



     static {

        try {

          Handler handler = new FileHandler("logs" + File.separator + "zip.log");
            Logger.getLogger(ZipDirectory.class.getName()).addHandler(handler);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    @Override
    public void run(){

        while(true){


            zipDirectories();


            try {
                Thread.sleep(60*SECOND);
            } catch (InterruptedException e) {

               log(Level.SEVERE,ZipDirectory.class.getName(),e);

            }

        }



    }


    private void zipDirectories(){


        try {

            File events = new File("events");
            File[] filesEvents = events.listFiles();

            File alert = new File("alert");
            File[] filesAlerts = alert.listFiles();

            File zipFile = new File("backup_"+ LocalDateTime.now().getYear()+"_-"+LocalDateTime.now().getMonth()+"_date-"+LocalDateTime.now().getDayOfMonth()+"_hour-"+LocalDateTime.now().getHour()+"_minute-"+LocalDateTime.now().getMinute()+".zip");
                    zipFile.createNewFile();

            FileOutputStream fos = new FileOutputStream(zipFile);
            ZipOutputStream zipOut = new ZipOutputStream(fos);

            if(filesEvents != null) {
                zipDirectory(filesEvents, zipOut);
            }


            if(filesAlerts != null) {
                zipDirectory(filesAlerts, zipOut);
            }

            zipOut.closeEntry();
            zipOut.close();


        } catch (IOException e) {

            log(Level.SEVERE, ZipDirectory.class.getName(),e);

        }

    }

    private void zipDirectory(File[] files, ZipOutputStream zipOut) throws IOException {

        for (File file : files) {

            byte[] buffer = new byte[BLOCK];

            FileInputStream fis = new FileInputStream(file);
            zipOut.putNextEntry(new ZipEntry(file.getName()));

            int lenght;

            while ((lenght = fis.read(buffer)) > 0) {

                zipOut.write(buffer, 0, lenght);

            }

            fis.close();

        }


    }


    private static void log(Level level, String name, Exception ex){

        Logger logger = Logger.getLogger(name);
        logger.log(level, ex.fillInStackTrace().toString());

    }




}
