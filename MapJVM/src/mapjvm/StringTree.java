/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mapjvm;

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
        children = null;
    }
    
    public StringTree(String stringName, StringTree st)
    {
        name = stringName;
        children = st.getChildren();
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
        while(nextLevel != null)
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
}
