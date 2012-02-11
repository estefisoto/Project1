/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mapjvm;

import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author mwaldron74
 */
public class GraphicsTree {
    private StringTree st;
    private ArrayList<GraphicsTree> children;
    private final int FONT_SIZE = 20;
    private final int PADDING = 20;
    private final int FONT_PADDING_Y = 25;
    private int FONT_PADDING_X;
    private int fontX, fontY, ovalX;
    private int ovalY, width, height;
    
    public GraphicsTree(StringTree strTree)
    {
        st = strTree;
        FONT_PADDING_X = st.getName().length() * 3;
        width = FONT_SIZE * st.getName().length();
        height = 5 * FONT_SIZE / 3;
        fontY = FONT_PADDING_Y + FONT_SIZE;
        fontX = FONT_PADDING_X + FONT_SIZE;
        ovalX = 0;
        ovalY = PADDING;
        children = new ArrayList<GraphicsTree>();
        addChildren();
    }
    
    public GraphicsTree(StringTree strTree, int X, int Y)
    {
        st = strTree;
        FONT_PADDING_X = st.getName().length() * 3;
        width = FONT_SIZE * st.getName().length();
        height = 5 * FONT_SIZE / 3;
        fontY = Y + FONT_PADDING_Y + FONT_SIZE;
        fontX = X + FONT_PADDING_X + FONT_SIZE;
        ovalX = X;
        ovalY = Y + PADDING;
        children = new ArrayList<GraphicsTree>();
    }
    
    //TODO : make sense of child adding algorithm
    private void addChildren()
    {
        for(StringTree child : st.getChildren())
            children.add(new GraphicsTree(child));
        
        
        /*ArrayList<StringTree> nextLevel = st.getChildren();
        int X = 0;
        int Y = 0;
        //Continue until all the nodes have been checked
        while(!nextLevel.isEmpty())
        {
            X = 0;
            for(int i = 0; i < nextLevel.size(); i++)
            {
                StringTree str = nextLevel.remove(0);
                children.add(new GraphicsTree(st, ));
                for(GraphicsTree child : gt.getChildren())
                    nextLevel.add(child);
            }
            //Check if current line is bigger than the biggest
            biggestLine = currentLine > biggestLine ? currentLine : biggestLine;
        }*/
    }
    
    public void draw(Graphics g)
    {
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, FONT_SIZE);
        g.setFont(font);
        g.drawString(st.getName(), fontX, fontY);
        g.drawOval(ovalX, ovalY, width, height);
        for(GraphicsTree child : children)
            child.draw(g);
    }
    
    public int getCenterX()
    {
        return ovalX + width;
    }
    
    public int getCenterY()
    {
        return ovalY + height;
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
    
    //TODO: doesnt work right
    public int longestLine()
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
    }
}
