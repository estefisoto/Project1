/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mapjvm;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author esoto.student
 */
public class GraphicsTree {
    protected StringTree st;
    private ArrayList<GraphicsTree> children;
    private final int FONT_SIZE = 20;
    private int PADDING_X = 0;
    private int PADDING_Y = 0;
    private final int FONT_PADDING_Y = 22;
    private int FONT_PADDING_X;
    private int fontX, fontY, ovalX;
    private int ovalY, width, height;
    public int finalX,finalY;
    private HashMap<GraphicsTree, GraphicsTree> additionalConnections;
    
    public GraphicsTree(StringTree strTree)
    {
        st = strTree;
        FONT_PADDING_X = st.getName().length() * 5;
       //Width and height and correctly calculated
        width = FONT_SIZE * st.getName().length();
        height = 5 * FONT_SIZE / 3;
        ovalX = 20;
        ovalY = 20;
        fontY = ovalY + FONT_PADDING_Y;
        fontX = ovalX + FONT_PADDING_X;
        children = new ArrayList<GraphicsTree>();
        finalX=0;
        finalY=0;
        addChildren();
        //TODO:
        //additionalConnections = connections();
    }
    
    public GraphicsTree(StringTree strTree, int X, int Y)
    {
        st = strTree;
        FONT_PADDING_X = st.getName().length() * 5;
        width = FONT_SIZE * st.getName().length();
        height = 5 * FONT_SIZE / 3;
        PADDING_Y = Y;
        fontY = Y + FONT_PADDING_Y;
        fontX = X + FONT_PADDING_X; 
        ovalX = X;
        ovalY = Y;
        children = new ArrayList<GraphicsTree>();
        finalX=0;
        finalY=0;
        addChildren();
        additionalConnections = new HashMap<GraphicsTree, GraphicsTree>();
    }
    
    public HashMap<GraphicsTree, GraphicsTree> connections()
    {
        HashMap<GraphicsTree, GraphicsTree> allConnections = new HashMap<GraphicsTree, GraphicsTree>();
        ArrayList<GraphicsTree> from = new ArrayList<GraphicsTree>(), to = new ArrayList<GraphicsTree>();
        for(StringTree key : st.additionalConnections.keySet())
            for(GraphicsTree gt : allNodes())
                if(gt.st.equals(key))
                    from.add(gt);
        for(StringTree value : st.additionalConnections.values())
            for(GraphicsTree gt : allNodes())
                if(gt.st.equals(value))
                    to.add(gt);
        for(int i = 0; i < from.size(); i++)
        {
            allConnections.put(from.get(i), to.get(i));
        }
        return allConnections;
    }
  
    private void addChildren()
    {
        PADDING_Y += 150;
        PADDING_X += ovalX;
        for(StringTree child : st.getChildren())
        {   
            GraphicsTree graphChild=new GraphicsTree(child, PADDING_X, PADDING_Y);
            
            graphChild.finalY=PADDING_Y;
            
            children.add(graphChild);
            if(child.getNumChildren() > 0)
                PADDING_X += graphChild.getchW() +(50 * child.getNumChildren()) ;
            else
                PADDING_X += FONT_SIZE * child.getName().length() + 50;
            if(finalY<graphChild.finalY )
                finalY= graphChild.finalY;
        }
        if(PADDING_X> finalX)
            finalX=PADDING_X;
        
    }
    
    private ArrayList<GraphicsTree> allNodes()
    {
        ArrayList<GraphicsTree> rv = new ArrayList<GraphicsTree>();
        rv.add(this);
        for(GraphicsTree child: children)
            for(GraphicsTree gt : child.allNodes())
                rv.add(gt);
        return rv;
    }
    
    public void draw(Graphics g)
    {
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, FONT_SIZE);
        g.setFont(font);
        g.setColor(Color.black);
        if(additionalConnections != null)
        {
            for(GraphicsTree from : additionalConnections.keySet())
            {
                GraphicsTree to = additionalConnections.get(from);
                g.drawLine(from.getCenterX(), from.getCenterY(), to.getCenterX(), to.getCenterY());
            }
        }
        for(GraphicsTree child : children)
        {
           g.drawLine(getCenterX(), getCenterY(), child.getCenterX(), child.getCenterY());
           child.draw(g);
        }
        if(st.getType().equals("Object"))
            g.setColor(Color.GREEN);
        else if(st.getType().equals("StackFrame"))
            g.setColor(Color.ORANGE);
        else
            g.setColor(Color.RED);        
        g.fillOval(ovalX, ovalY, width, height);
       
        g.setColor(Color.BLACK);
        g.drawOval(ovalX  , ovalY, width, height);
        g.drawString(st.getName(), fontX, fontY);
    }
    
    public int getCenterX()
    {
        return ovalX + (width / 2);
    }
    
    public int getCenterY()
    {
        return ovalY + (height / 2);
    }
    
    public int getWidth()
    {
        return width;
    }
    
    public int getHeight()
    {
        return height;
    }
    
    public ArrayList<GraphicsTree> getChildren()
    {
        return children;
    }
    
    public int getchW()
    {
        int total=0;
        for(GraphicsTree child:children)
            total+=child.getWidth();
        
        return total;
    }
    
    /*public int longestLine()
    {
        ArrayList<GraphicsTree> nextLevel = children;
        int currentLine;
        int biggestLine = width;
        //Continue until all the nodes have been checked
        while(!nextLevel.isEmpty())
        {
            currentLine = 0;
            for(int i = 0; i < nextLevel.size(); i++)
            {
                GraphicsTree gt = nextLevel.remove(0);
                currentLine += gt.getWidth();
                for(GraphicsTree child : gt.getChildren())
                    nextLevel.add(child);
            }
            //Check if current line is bigger than the biggest
            biggestLine = currentLine > biggestLine ? currentLine : biggestLine;
        }
        return biggestLine;
    }*/
}
