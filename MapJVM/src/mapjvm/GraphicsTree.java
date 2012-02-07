/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mapjvm;

import java.awt.Font;
import java.awt.Graphics;

/**
 *
 * @author mwaldron74
 */
public class GraphicsTree {
    private StringTree st;
    private final int FONT_SIZE = 20;
    private int fontX = 0, fontY = 0, ovalX = 0;
    private int ovalY = 0, width = 0, height = 0;
    public GraphicsTree(StringTree strTree)
    {
        st = strTree;
    }
    
    public void draw(Graphics g)
    {
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, FONT_SIZE);
        g.setFont(font);
        g.drawString(st.getName(), 100, 100);
        g.drawOval(70, 65, 25*st.getName().length(), 50);
    }
    
    //TODO : add spacing
    /*public int longestLine()
    {
        ArrayList<StringTree> nextLevel = children;
        int currentLine ;
        int biggestLine = name.length();
        //Continue until all the nodes have been checked
        while(!nextLevel.isEmpty())
        {
            currentLine = 0;
            for(int i = 0; i < nextLevel.size(); i++)
            {
                StringTree st = nextLevel.remove(0);
                currentLine += st.getName().length();
                for(StringTree child : st.getChildren())
                    nextLevel.add(child);
            }
            //Check if current line is bigger than the biggest
            biggestLine = currentLine > biggestLine ? currentLine : biggestLine;
        }
        return biggestLine;
    }*/
}
