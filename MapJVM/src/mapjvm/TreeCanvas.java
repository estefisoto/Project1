/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mapjvm;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author esoto.student
 */
public class TreeCanvas extends Canvas {

    ArrayList<StringTree> nodes= new  ArrayList<StringTree>();

    //DefaultSize
     public TreeCanvas() {
        //Set size and color of canvas
        this.setSize(1000,1000);
        this.setBackground(Color.white);
        
        //TODO : Delete this test code
        nodes.add(new StringTree("HELLO WORLD"));
    }
     @Override
    //Need this function in order to paint on canvas
    public void paint(Graphics g)
    {
       //For loop that draws all shapes currently in the vector
       for (int i = 0; i < nodes.size(); i++) {
           StringTree s = nodes.get(i);
           (new GraphicsTree(s)).draw(g);
    }



    }


}
