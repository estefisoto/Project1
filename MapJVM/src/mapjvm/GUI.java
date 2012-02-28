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
import java.io.IOException;
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

        addWindowListener(new closeWindow());
        drawingComponent = new TreeCanvas();
        scroll = new ScrollPane(ScrollPane.SCROLLBARS_ALWAYS);
        scroll.setBackground(Color.white);
        //scroll.add(drawingComponent);
        scroll.setSize(500, 500);
        add(scroll);


        MenuBar bar = new MenuBar();
        Menu file = new Menu("File");
        bar.add(file);
        MenuItem browse = new MenuItem("Browse for Java Class");
        MenuItem connect = new MenuItem("Connect");
        MenuItem disconnect = new MenuItem("Disconnect");
        MenuItem help = new MenuItem("Help");
        file.add(browse);
        file.add(connect);
        file.add(disconnect);
        file.add(help);
        browse.addActionListener(this);
        connect.addActionListener(this);
        disconnect.addActionListener(this);
        help.addActionListener(this);
        setMenuBar(bar);
        Dialog x = new Dialog(this);
        x.setModal(true);


        pack();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Connect")) {
            try {
                drawingComponent.connect();
            } catch (IOException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalConnectorArgumentsException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IncompatibleThreadStateException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (AbsentInformationException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotLoadedException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
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


        } else if (e.getActionCommand().equals("Browse for Java Class")) {
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
                System.out.println(file);
                System.out.println(classPath);

                className = file.getName();
              


                try {
                   Runtime.getRuntime().exec("java -cp ~"+classPath+" -agentlib:jdwp=transport=dt_socket,server=y,address=8000,suspend=n " + className+"."+className);
                } catch (IOException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                    JDialog error =new JDialog(this, ex.getMessage() );
                    error.setVisible(true);

                }




            }
        }
    }
}
