/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mapjvm;

import java.awt.Canvas;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author mwaldron74
 */
public class GUI extends Frame implements ActionListener{
    private TreeCanvas container;
    public GUI(){
        super("");
        addWindowListener(new closeWindow());
        container = new TreeCanvas();
        container.setSize(1300,400);
        add(container);
       
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
        //    Scrollbar vertical= new Scrollbar();
        //    Scrollbar horizontal= new Scrollbar(Scrollbar.HORIZONTAL,1000,50,500,5000);
        //    add(vertical);
        //    add(horizontal);
        Dialog x = new Dialog(this);
        x.setModal(true);
        pack();
        setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Connect"))
        {    
            container.connect();
            container.repaint();
        }
        else if(e.getActionCommand().equals("Disconnect"))
        {
            container.disconnect();
            container.repaint();
        }
        else if(e.getActionCommand().equals("Help"))
            System.out.println("Help");
    }
}
