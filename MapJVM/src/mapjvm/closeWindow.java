/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mapjvm;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author estefisoto
 */
class closeWindow extends WindowAdapter {

    public closeWindow() {
    }
    
    @Override
        public void windowClosing(WindowEvent e)
        {
            System.exit(0);
        }
    
}
