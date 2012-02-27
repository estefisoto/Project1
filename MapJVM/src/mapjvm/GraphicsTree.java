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
public class GraphicsTree{
    protected StringTree st;
    private ArrayList<GraphicsTree> children;
    private HashMap<GraphicsTree, GraphicsTree> additionalConnections;
    private final int FONT_SIZE = 20;
    private final int FONT_PADDING_Y = 22;
    private final int PADDING = 20;
    private int FONT_PADDING_X;
    private int fontX, fontY, ovalX, ovalY, width, height;
    //This constructor is called in TreeCanvas
    public GraphicsTree(StringTree strTree)
    {
        st = strTree;
        children = new ArrayList<GraphicsTree>();
        additionalConnections = new HashMap<GraphicsTree, GraphicsTree>();
        FONT_PADDING_X = st.getName().length() * 5;
        width = FONT_SIZE * st.getName().length();
        height = 5 * FONT_SIZE / 3;
        addChildren();
        setGraphics();
    }
    
    //This is only called within this classs
    private GraphicsTree(StringTree strTree, int w)
    {
        st = strTree;
        children = new ArrayList<GraphicsTree>();
        additionalConnections = new HashMap<GraphicsTree, GraphicsTree>();
        FONT_PADDING_X = st.getName().length() * 5;
        width = w;
        height = 5 * FONT_SIZE / 3;
        addChildren();
    }
    
    private void setGraphics()
    {
        int center = getCenterX();
        setXY(center - (width / 2), 0);
        ArrayList<GraphicsTree> nextLevel = new ArrayList<GraphicsTree>();
        ArrayList<GraphicsTree> nextLevelCopy = new ArrayList<GraphicsTree>();
        for(GraphicsTree g : children)
        {
            nextLevel.add(g);
            nextLevelCopy.add(g);
        }
        int Y = 0;
        int currentLine;
        //Continue until all the nodes have been checked
        while(!nextLevel.isEmpty())
        {
            Y += PADDING + height;
            currentLine = 0;
            
            //Calculate current line size
            int i = 0;
            int size = nextLevel.size();
            while(i < size)
            {
                GraphicsTree gt = nextLevel.remove(0);
                currentLine += FONT_SIZE * gt.getName().length();
                for(GraphicsTree child : gt.getChildren())
                    nextLevel.add(child);
                i++;
            }
            
            i = 0;
            size = nextLevelCopy.size();
            int X = PADDING + center - (currentLine / 2);
            while(i < size)
            {
                GraphicsTree gt = nextLevelCopy.remove(0);
                gt.setXY(X, Y);
                X += gt.getWidth() + PADDING;
                for(GraphicsTree child : gt.getChildren())
                    nextLevelCopy.add(child);
                i++;
            }
        }
    }
    
    private void addChildren()
    {
        for(StringTree str : st.getChildren())
            children.add(new GraphicsTree(str, FONT_SIZE * str.getName().length()));
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
        for(int i = 0; i < from.size() && i < to.size(); i++)
        {
            allConnections.put(from.get(i), to.get(i));
        }
        return allConnections;
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
        for(GraphicsTree from : connections().keySet())
        {     
            GraphicsTree to = connections().get(from);
            
            g.drawLine(from.ovalX + (from.width / 2) - 10, from.ovalY + (from.height / 2), 
                       to.ovalX + (to.width / 2) - 10, to.ovalY + (to.width /2));
            
        }
        for(GraphicsTree child : children)
        {
           g.drawLine(ovalX + (width / 2) + 10, ovalY + (height / 2), 
                      child.ovalX + (child.width / 2) + 10, child.ovalY + (child.height / 2));
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
    
    private int getCenterX()
    {
        ArrayList<StringTree> nextLevel = new ArrayList<StringTree>();
        for(StringTree s : st.getChildren())
            nextLevel.add(s);
        int currentLine;
        int biggestLine = width;
        //Continue until all the nodes have been checked
        while(!nextLevel.isEmpty())
        {
            currentLine = 0;
            int i = 0;
            int size = nextLevel.size();
            while(i < size)
            {
                StringTree st = nextLevel.remove(0);
                currentLine += PADDING + FONT_SIZE * st.getName().length();
                for(StringTree child : st.getChildren())
                    nextLevel.add(child);
                i++;
            }
            //Check if current line is bigger than the biggest
            biggestLine = currentLine > biggestLine ? currentLine : biggestLine;
        }
        return biggestLine / 2;
    }
    
    public void setXY(int X, int Y)
    {
        ovalX = X;
        ovalY = Y;
        fontX = X + FONT_PADDING_X;
        fontY = Y + FONT_PADDING_Y;
    }
    
    public int getWidth()
    {
        return width;
    }
    
    public int getHeight()
    {
        return height;
    }
    
    public String getName()
    {
        return st.getName();
    }
    
    public ArrayList<GraphicsTree> getChildren()
    {
        return children;
    }
}
