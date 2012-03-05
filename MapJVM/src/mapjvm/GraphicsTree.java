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
    private final int FONT_SIZE = 15;
    private final int FONT_PADDING_Y = 18;
    public final int PADDING = 50;
    private int FONT_PADDING_X;
    private int fontX, fontY, ovalX, ovalY, width, height;
    
//////////////////////////////
// Constructors
//////////////////////////////
    
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
        additionalConnections = connections();
    }
    
    //This is only called within this classs
    private GraphicsTree(StringTree strTree, int w)
    {
        st = strTree;
        children = new ArrayList<GraphicsTree>();
        additionalConnections = new HashMap<GraphicsTree, GraphicsTree>();
        FONT_PADDING_X = st.getName().length() * 3;
        width = w;
        height = 5 * FONT_SIZE / 3;
        addChildren();
    }
    
//////////////////////////////
// Functions
//////////////////////////////
    private void addChildren()
    {
        for(StringTree str : st.getChildren())
            children.add(new GraphicsTree(str, FONT_SIZE * str.getName().length()));
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

    public void draw(Graphics g)
    {
        drawLines(g);
        drawNodes(g);
    }

    public void drawLines(Graphics g)
    {
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, FONT_SIZE);
        g.setFont(font);
        g.setColor(Color.black);
        HashMap<GraphicsTree, GraphicsTree> connections = additionalConnections;
        for(GraphicsTree to : connections.keySet())
        {
            GraphicsTree from = connections.get(to);
            if(!to.equals(from))
            {
                if(from.getY() > to.getY())
                    g.drawLine(to.getX() + (to.getWidth() / 2), to.getY() + (to.getHeight() / 2),
                               from.getX() + (from.getWidth() / 2), from.getY());
                else if(from.getY() == to.getY())
                    g.drawLine(to.getX(), to.getY() + (to.getHeight() / 2),
                               from.getX(), from.getY() + (from.getHeight() / 2));
                else
                    g.drawLine(to.getX() + (to.getWidth() / 2), to.getY()+ (to.getHeight() / 2),
                               from.getX() + (from.getWidth() / 2), from.getY() + from.getHeight());
            }
        }
        for(GraphicsTree child : children)
        {
           g.drawLine(ovalX + (width / 2), ovalY + (height / 2),
                      child.ovalX + (child.width / 2), child.ovalY);
           child.drawLines(g);
        }
    }

    public void drawNodes(Graphics g)
    {
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, FONT_SIZE);
        g.setFont(font);
        g.setColor(Color.black);
        HashMap<GraphicsTree, GraphicsTree> connections = additionalConnections;
        for(GraphicsTree to : connections.keySet())
        {
            GraphicsTree from = connections.get(to);
            if(!to.equals(from))
            {
                if(from.getY() > to.getY())
                    g.fillOval(to.getX() - 5 + (to.getWidth() / 2), to.getY() - 5, 10, 10);
                else if(from.getY() == to.getY())
                {
                    if(from.getX() < to.getX())
                        g.fillOval(to.getX() - 5, to.getY() - 5 + (to.getHeight() / 2), 10, 10);
                    else
                        g.fillOval(to.getX() - 5 + to.getWidth(), to.getY() - 5 + (to.getHeight() / 2), 10, 10);
                }
                else
                    g.fillOval(from.getX() + (from.getWidth() / 2)- 5 , from.getY() - 5 + from.getHeight(), 10, 10);
            }
        }
        for(GraphicsTree child : children)
        {
            g.fillOval(child.getX() - 5 + (child.width / 2), child.getY() - 5, 10,10);
            child.drawNodes(g);
        }
        if(st.getType().equals("Object"))
            g.setColor(Color.GREEN);
        else if(st.getType().equals("StackFrame"))
            g.setColor(Color.ORANGE);
        else if(st.getType().equals("Value"))
            g.setColor(Color.GRAY);
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
    
    public ArrayList<GraphicsTree> getChildren()
    {
        return children;
    }
    
    public int getHeight()
    {
        return height;
    }
    
    public String getName()
    {
        return st.getName();
    }
    
    public int getWidth()
    {
        return width;
    }
    
    public int getX()
    {
        return ovalX;
    }
    
    public int getXWindow()
    {
        ArrayList<GraphicsTree> all = allNodes();
        int farX = 0;
        for(GraphicsTree gt : all)
            if(gt.getX() + gt.getWidth() > farX)
                farX = gt.getX() + gt.getWidth();
        return farX + PADDING;
    }
    
    public int getY()
    {
        return ovalY;
    }
    
    public int getYWindow()
    {
        ArrayList<GraphicsTree> all = allNodes();
        int farY = 0;
        for(GraphicsTree gt : all)
            if(gt.getY() + gt.getHeight() > farY)
                farY = gt.getY() + gt.getHeight();
        return farY + PADDING;
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
    
    public void setXY(int X, int Y)
    {
        ovalX = X;
        ovalY = Y;
        fontX = X + FONT_PADDING_X;
        fontY = Y + FONT_PADDING_Y;
    }
}
