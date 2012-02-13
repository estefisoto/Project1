/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mapjvm;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author mwaldron74
 */

public class StringTree {
    
    private ArrayList<StringTree> children;
    public HashMap<StringTree, StringTree> additionalConnections;
    private String name;
    
    public StringTree(String stringName)
    {
        name = stringName;
        children = new ArrayList<StringTree>();
        additionalConnections = new HashMap<StringTree, StringTree>();
    }
    
    public StringTree(String stringName, StringTree st)
    {
        this(stringName);
        addChild(st);
    }
    
    public void addConnection(StringTree from, StringTree to)
    {
        additionalConnections.put(from, to);
    }
    
    public ArrayList<StringTree> getChildren()
    {
        return children;
    }
    
    public int getNumChildren()
    {
        return children.size();
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