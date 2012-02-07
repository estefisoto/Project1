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
    
    @Override
    public String toString()
    {
        String rv = "(" + name;
        for(StringTree child : children)
            rv += child;
        return rv + ")";
    }
    
    
    
}
