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
import java.awt.Scrollbar;

/**
 *
 * @author estefisoto
 */
public class MapJVM {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Frame layout=new Frame("");
        layout.addWindowListener(new closeWindow());
        Canvas container= new Canvas();
        container.setSize(800,800);
        layout.add(container);
       
        MenuBar bar=new MenuBar();
        Menu options = new Menu("Options");
        bar.add(options);
        MenuItem connect=new MenuItem("Connect to JVM");
        options.add(connect);
        layout.setMenuBar(bar);
        
      //  Scrollbar vertical= new Scrollbar();
        
      //  Scrollbar horizontal= new Scrollbar(Scrollbar.HORIZONTAL,1000,50,500,5000);
      //  layout.add(vertical);
      //  layout.add(horizontal);
        
        
        layout.pack();
        layout.setVisible(true); 
    }
}
