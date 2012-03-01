/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mapjvm;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.sun.jdi.*;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JFileChooser;

/**
 *
 * @author mwaldron74
 */
public class GUI extends Frame implements ActionListener {

    private TreeCanvas drawingComponent;
    private ScrollPane scroll;

    public GUI() {
        super("");


        drawingComponent = new TreeCanvas();
        scroll = new ScrollPane(ScrollPane.SCROLLBARS_ALWAYS);
        scroll.setBackground(Color.white);
        //scroll.add(drawingComponent);
        scroll.setSize(500, 500);
        add(scroll);


        MenuBar bar = new MenuBar();
        Menu file = new Menu("File");
        bar.add(file);
        MenuItem browse = new MenuItem("Choose Java Class");
        MenuItem connect = new MenuItem("Connect");
        MenuItem saveImg = new MenuItem("Save Image As...");
        MenuItem disconnect = new MenuItem("Disconnect");
        MenuItem help = new MenuItem("Help");
        file.add(browse);
        file.add(connect);
        file.add(saveImg);
        file.add(disconnect);
        file.add(help);
        browse.addActionListener(this);
        saveImg.addActionListener(this);
        connect.addActionListener(this);
        disconnect.addActionListener(this);
        help.addActionListener(this);
        setMenuBar(bar);
        Dialog x = new Dialog(this);
        x.setModal(true);
        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent event) {
                System.exit(0);
            }
        });

        pack();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Connect")) {
            try {
                drawingComponent.connect();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            } catch (IllegalConnectorArgumentsException ex) {
                System.out.println("IllegalConnector"+ex.getMessage());
            } catch (InterruptedException ex) {
                System.out.println("InterruptedException"+ex.getMessage());
            } catch (IncompatibleThreadStateException ex) {
               System.out.println(ex.getMessage());
            } catch (AbsentInformationException ex) {
               System.out.println(ex.getMessage());
            } catch (ClassNotLoadedException ex) {
                System.out.println(ex.getMessage());
            }

            drawingComponent.repaint();
            scroll.add(drawingComponent);
            scroll.repaint();
        } else if (e.getActionCommand().equals("Disconnect")) {
            drawingComponent.disconnect();
            drawingComponent.repaint();
        } else if (e.getActionCommand().equals("Help")) {
            System.out.println("Current path" + System.getProperty("user.dir"));
            File instructions = new File(System.getProperty("user.dir") + "/instructions.txt");
            instructions.setReadOnly();

            try {

                if (instructions.exists()) {

                    Runtime.getRuntime().exec("gedit " + instructions);


                } else {

                    System.out.println("File does not exist");

                }

                System.out.println("Done");
            } catch (Exception ex) {
                ex.printStackTrace();
            }


        } else if (e.getActionCommand().equals("Choose Java Class")) {
            //New file chooser
            final JFileChooser fc = new JFileChooser();
            //New Dialog

            //Set return val to Open file browse in frame
            int returnVal = fc.showOpenDialog(this);
            //Declare string to hold line from file
            String classPath = new String();
            String className = new String();

            //If a new file was selected from browser
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                //Set file equal to the sellected file
                File file = fc.getSelectedFile();
                classPath = fc.getCurrentDirectory().toString();
                System.out.println(classPath);
                int remove=0;
                remove = file.getName().indexOf(".");
                className = file.getName().substring(0,remove);
                System.out.println(className);


                try {
                    Runtime.getRuntime().exec("java -cp ~" +classPath+" -agentlib:jdwp=transport=dt_socket,server=y,address=8000,suspend=n " + className + "." + className);
                    try {
                        System.out.println("java -cp ~" +classPath+" -agentlib:jdwp=transport=dt_socket,server=y,address=8000,suspend=n " + className + "." + className);
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        System.err.println("May not connect appropriately");
                    }
                } catch (IOException ex) {
                    System.out.println("COULD NOT CONNECT TO JVM!!");

                }
            }
        }
        else if (e.getActionCommand().equals("Save Image As...")) {

            BufferedImage img= new BufferedImage(drawingComponent.getWidth(),drawingComponent.getHeight(),BufferedImage.TYPE_INT_RGB);
            Graphics g= img.createGraphics();
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, img.getWidth(), img.getHeight());
            drawingComponent.paint(img.createGraphics());
          

            final JFileChooser fc = new JFileChooser();
            //Set return val equal to showSavedialog
            int returnVal = fc.showSaveDialog(this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
              File outputfile =fc.getSelectedFile() ;
                try {
                    ImageIO.write(img, "png", outputfile);
                } catch (IOException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }




        }
    }
}

}
