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
    private String typeof;

    public StringTree()
    {
        name = "nothing";
        typeof = "nothing";
        children = new ArrayList<StringTree>();
        additionalConnections = new HashMap<StringTree, StringTree>();
    }

    public StringTree(String stringName, String type)
    {
        name = stringName;
        typeof = type;
        children = new ArrayList<StringTree>();
        additionalConnections = new HashMap<StringTree, StringTree>();
    }
    
    public StringTree(String stringName, String type, StringTree st)
    {
        this(stringName, type);
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
    
    public String getType()
    {
        return typeof;
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