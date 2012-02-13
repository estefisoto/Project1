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
        StringTree main = new StringTree("main");
        StringTree BSTa = new StringTree("BST A");
        StringTree BSTb = new StringTree("BST B");
        StringTree chAL = new StringTree("child A Left");
        StringTree chBL = new StringTree("child B Left");
        StringTree chBR = new StringTree("child B Right");
        StringTree LL0 = new StringTree("Linked List (0)");
        StringTree LL1 = new StringTree("Linked List (1)");
        StringTree LL2 = new StringTree("Linked List (2)");
        main.addChild(BSTa);
        main.addChild(BSTb);
        main.addChild(LL0);
        LL0.addChild(LL1);
        LL1.addChild(LL2);
        BSTa.addChild(chAL);
        main.addConnection(BSTa, BSTb);
        BSTb.addChild(chBL);
        BSTb.addChild(chBR);
        nodes.add(main);
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
