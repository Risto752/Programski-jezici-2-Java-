package gui;


import map.Map;
import simulator.EventFiles;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;


import static radar.Radar.file;
import static simulator.Simulator.flightGranted;


public class Window extends JFrame implements Runnable {

      private JButton[][] buttons;

      private JLabel label = new JLabel();
      private DefaultListModel<String> model = new DefaultListModel<>();

      private AlertFiles alert;
      private EventFiles events;

    static {

        try {

            Handler handler = new FileHandler("logs" + File.separator + "window.log");
            Logger.getLogger(Window.class.getName()).addHandler(handler);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


        public Window(Map map, AlertFiles alert, EventFiles events){

             JPanel matrix;
             JPanel bottom = new JPanel(new FlowLayout());
             JPanel up = new JPanel(new FlowLayout());


             JButton flightRestriction = new JButton("ZABRANA LETENJA");
             JButton flightPermitted = new JButton("DOZVOLA LETENJA");
             JButton showCollisions = new JButton("PRIKAŽI SVE SUDARE");



            this.alert = alert;
            this.events = events;


            JFrame eventWindow = new JFrame();
            eventWindow.setSize(600,400);
            eventWindow.setVisible(true);


            JList<String> eventList = new JList<>( model );
            JScrollPane eventScrollPane = new JScrollPane();
            eventScrollPane.setViewportView(eventList);
            eventWindow.getContentPane().add(eventScrollPane,BorderLayout.CENTER);


            setSize(600, 400);
            setVisible(true);
            setDefaultCloseOperation(EXIT_ON_CLOSE);

            matrix = new JPanel(new GridLayout(map.getRows(),map.getColumns()));
            buttons = new JButton[map.getRows()][map.getColumns()];
            for(int i = 0 ; i < map.getRows(); i++)
                for(int j = 0; j < map.getColumns(); j++){

                buttons[i][j] = new JButton();
               matrix.add(buttons[i][j]);

            }


            up.add(flightRestriction);
            up.add(showCollisions);
            up.add(flightPermitted);


            bottom.add(label);

            getContentPane().add(matrix,BorderLayout.CENTER);
            getContentPane().add(bottom,BorderLayout.SOUTH);
            getContentPane().add(up,BorderLayout.NORTH);

            flightRestriction.addActionListener(e -> flightGranted = false);
            flightPermitted.addActionListener(e -> flightGranted = true);

            showCollisions.addActionListener(e -> {

                JFrame window = new JFrame();
                window.setSize(750,750);

                JScrollPane scrollPane = new JScrollPane();
                JList list = new JList(alert.getAlertStorage().toArray());
                scrollPane.setViewportView(list);

                window.getContentPane().add(scrollPane,BorderLayout.CENTER);

                window.setVisible(true);


            });

        }



        private void readMapTxt(){



            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                Font f = new Font("Verdana",Font.BOLD,1);
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split("=");
                    if(parts.length >= 3) {
                      JButton button = buttons[Integer.parseInt(parts[0])][Integer.parseInt(parts[1])];
                      button.setText(parts[2]);
                      button.setFont(f);
                     if(parts[2].equals("TP") || parts[2].equals("PP") || parts[2].equals("FP")) {
                         button.setBackground(ColorUIResource.black);
                     }
                     if( parts[2].equals("TH") || parts[2].equals("PH") || parts[2].equals("FH") ) {
                         button.setBackground(ColorUIResource.MAGENTA);
                     }
                     if(parts[2].equals("U") ){
                         button.setBackground(ColorUIResource.ORANGE);
                     }
                     if(parts[2].equals("B*") || parts[2].equals("F*")){
                         button.setBackground(ColorUIResource.RED);
                     }
                     if(parts[2].equals("B") || parts[2].equals("F")){
                         button.setBackground(Color.BLUE);
                     }

                    }else if(parts.length == 2){
                        buttons[Integer.parseInt(parts[0])][Integer.parseInt(parts[1])].setBackground(ColorUIResource.WHITE);
                    }

                }
            } catch (IOException e) {

                log(Level.SEVERE, Window.class.getName(),e);

            }


}


        @Override
        public void run(){


            while(true){

            synchronized (file){
                readMapTxt();
            }

                alert.processAlertFiles();
                events.processFilesGui(label,model);

            }




        }


    private static void log(Level level, String name, Exception ex){

        Logger logger = Logger.getLogger(name);
        logger.log(level, ex.fillInStackTrace().toString());

    }


}
