/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mapjvm;

import java.awt.Color;
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
    private int PADDING_X = 0, PADDING_Y=1;
    private final int FONT_PADDING_Y = 22;
    private int FONT_PADDING_X;
    private int fontX, fontY, ovalX;
    private int ovalY, width, height;
    private int x1,x2,y1,y2;
    
    public GraphicsTree(StringTree strTree)
    {
        st = strTree;
        FONT_PADDING_X = st.getName().length() * 5;
       //Width and height and correctly calculated
        width = FONT_SIZE * st.getName().length();
        height = 5 * FONT_SIZE / 3;
        fontY = FONT_PADDING_Y;
        fontX = FONT_PADDING_X;
        ovalX = PADDING_X;
        ovalY = PADDING_Y;
        x1=ovalX+(width/2);
        y1=ovalY+height;
        x2=ovalX+(width/2);
        y2=ovalY;
        children = new ArrayList<GraphicsTree>();
        addChildren();
        
        
    }
    
    public GraphicsTree(StringTree strTree, int X, int Y)
    {
        st = strTree;
        FONT_PADDING_X = st.getName().length() * 5;
        width = FONT_SIZE * st.getName().length();
        height = 5 * FONT_SIZE / 3;
        PADDING_Y=Y;
        fontY = Y + FONT_PADDING_Y;
        fontX = X + FONT_PADDING_X; 
        ovalX = X;
        ovalY = Y;
        x1=ovalX+(width/2);
        y1=ovalY+height;
        x2=ovalX+(width/2);
        y2=ovalY;
        children = new ArrayList<GraphicsTree>();
        addChildren();
    }
  
    //TODO : make sense of child adding algorithm
    private void addChildren()
    {
        PADDING_Y += 100;
        PADDING_X+=ovalX;
        for(StringTree child : st.getChildren())
        {   
            GraphicsTree graphChild=new GraphicsTree(child, PADDING_X, PADDING_Y);
            children.add(graphChild);
            if(child.getNumChildren()>0)
            PADDING_X += graphChild.getchW()+ 50*child.getNumChildren() ;
            else
            PADDING_X += FONT_SIZE * child.getName().length() + 50;
        }
        
        
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
        g.setColor(Color.black);
        g.drawString(st.getName(), fontX, fontY);
        g.drawOval(ovalX, ovalY, width, height);
        
        for(GraphicsTree child : children)
        {
           child.draw(g);
           g.drawLine(x1, y1, child.x2, child.y2);
        }
        
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
    
    public int getchW()
    {
        int total=0;
        for(GraphicsTree child:children)
            total+=child.getWidth();
        
        return total;
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
