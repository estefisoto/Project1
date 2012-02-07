/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mapjvm;

import java.awt.Canvas;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author mwaldron74
 */
public class GUI extends Frame implements ActionListener{
    
    public GUI(){
        super("");
        addWindowListener(new closeWindow());
        Canvas container= new Canvas();
        container.setSize(800,800);
        add(container);
       
        MenuBar bar=new MenuBar();
        Menu file = new Menu("File");
        bar.add(file);
        MenuItem connect = new MenuItem("Connect to JVM");
        MenuItem close = new MenuItem("Close");
        file.add(connect);
        file.add(close);
        connect.addActionListener(this);
        close.addActionListener(this);
       setMenuBar(bar);
        
      //  Scrollbar vertical= new Scrollbar();
        
      //  Scrollbar horizontal= new Scrollbar(Scrollbar.HORIZONTAL,1000,50,500,5000);
      //  layout.add(vertical);
      //  layout.add(horizontal);
        
        
        pack();
        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Connect to JVM"))
            System.out.println("Connect");
        else if(e.getActionCommand().equals("Close"))
            System.out.println("Close");
    }
}