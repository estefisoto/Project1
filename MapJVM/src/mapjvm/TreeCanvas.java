/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mapjvm;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JComponent;
/**
 *
 * @author esoto.student
 */
public final class TreeCanvas extends JComponent {

    ArrayList<StringTree> nodes = new ArrayList<StringTree>();

    //DefaultSize
    public TreeCanvas() {
        this.setSize(500,500);
        this.setPreferredSize(new Dimension(500,500));
        this.setBackground(Color.white);        
    }

    public void connect() {
        //TODO : Delete this test code
        StringTree main = new StringTree("main", "StackFrame");
        StringTree BSTa = new StringTree("BST A", "Object");
        StringTree BSTb = new StringTree("BST B", "Object");
        StringTree chAL = new StringTree("child A Left", "Object");
        StringTree chBL = new StringTree("child B Left", "Object");
        StringTree chBR = new StringTree("child B Right", "Object");
        StringTree LL0 = new StringTree("Linked List (0)", "Object");
        StringTree LL1 = new StringTree("Linked List (1)", "Object");
        StringTree LL2 = new StringTree("Linked List (2)", "Object");
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
        
        //TODO: add method to find canvas max size(of graphics tree)
        //this.setPreferredSize(new Dimension(nodes.get(0).getWidth(),nodes.get(0).getHeight()));
        this.setPreferredSize(new Dimension(10000,10000));
        
    }

    public void disconnect() {
        nodes.clear();
    }

    @Override
    //Need this function in order to paint on canvas
    public void paint(Graphics g) {
        //For loop that draws all shapes currently in the vector
        for (int i = 0; i < nodes.size(); i++) {
            StringTree s = nodes.get(i);
            (new GraphicsTree(s)).draw(g);
        }
    }
}
