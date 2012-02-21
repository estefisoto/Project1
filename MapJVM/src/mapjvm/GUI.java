/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mapjvm;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

/**
 *
 * @author mwaldron74
 */
public class GUI extends Frame implements ActionListener{
    private TreeCanvas drawingComponent;
    private ScrollPane scroll;
    public GUI(){
        super("");   
               
        addWindowListener(new closeWindow());
        drawingComponent = new TreeCanvas();
        scroll = new ScrollPane(ScrollPane.SCROLLBARS_ALWAYS);      
        scroll.setBackground(Color.white);
        //scroll.add(drawingComponent);
        scroll.setSize(500,500);
        add(scroll);


        MenuBar bar=new MenuBar();
        Menu file = new Menu("File");
        bar.add(file);
        MenuItem connect = new MenuItem("Connect");
        MenuItem disconnect = new MenuItem("Disconnect");
        MenuItem help = new MenuItem("Help");
        file.add(connect);
        file.add(disconnect);
        file.add(help);
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
        if(e.getActionCommand().equals("Connect"))
        {
            
            drawingComponent.connect();
            drawingComponent.repaint();
            scroll.add(drawingComponent);
            scroll.repaint();

                                  
        }
        else if(e.getActionCommand().equals("Disconnect"))
        {
            drawingComponent.disconnect();
            drawingComponent.repaint();
        }
        else if(e.getActionCommand().equals("Help"))
        {
            System.out.println("Current path"+ System.getProperty("user.dir") );
            File instructions= new File(System.getProperty("user.dir")+"/instructions.txt");
            instructions.setReadOnly();

            try {

		if (instructions.exists()) {

			Process p = Runtime
			   .getRuntime()
			   .exec("gedit "+instructions);
			//p.waitFor();

		} else {

			System.out.println("File does not exist");

		}

		System.out.println("Done");}
                catch (Exception ex) {
		ex.printStackTrace();
	  }


        }


    }
}
