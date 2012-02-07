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
public class StringTree {
    
    private ArrayList<StringTree> children;
    private String name;

    public StringTree(String stringName)
    {
        name = stringName;
        children = new ArrayList<StringTree>();
    }
    
    public StringTree(String stringName, StringTree st)
    {
        this(stringName);
        addChild(st);
    }
    
    public ArrayList<StringTree> getChildren()
    {
        return children;
    }
    
    public void addChild(StringTree s)
    {
        children.add(s);
    }
    
    public String getName()
    {
        return name;
    }
    
    //TODO : add spacing
    public int longestLine()
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
    }
    
    @Override
    public String toString()
    {
        String rv = "(" + name;
        for(StringTree child : children)
            rv += child;
        return rv + ")";
    }
    
    public void draw(Graphics g)
    {
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 28);
        g.setFont(font);
        g.drawString(name, 100, 100);
        g.drawOval(100, 100, 28, 28);
    }
}
