/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mapjvm;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JComponent;
import java.util.HashMap;
import com.sun.jdi.*;
import com.sun.jdi.connect.AttachingConnector;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author esoto.student
 */
public final class TreeCanvas extends JComponent {

    ArrayList<GraphicsTree> nodes = new ArrayList<GraphicsTree>();


    //DefaultSize
    public TreeCanvas() {
        this.setSize(500,500);
        this.setPreferredSize(new Dimension(500,500));
        this.setBackground(Color.white);        
    }

    public void connect()throws IOException, IllegalConnectorArgumentsException, InterruptedException, IncompatibleThreadStateException, AbsentInformationException, ClassNotLoadedException  {

       
    HashMap<Value,StringTree> DFSLookup = new HashMap<Value, StringTree>();
  

    StringTree start = new StringTree();
  

    VirtualMachineManager vmm = Bootstrap.virtualMachineManager();
    AttachingConnector socket = null;
    VirtualMachine vm = null;
    List<AttachingConnector> aclist = vmm.attachingConnectors();
    for (AttachingConnector attachingconnector : aclist)
    {
        if(attachingconnector.transport().name().equals("dt_socket"))
        {
            socket = attachingconnector;
            System.out.println("Connector Found");
            break;
        }
    }

    Map parametersMap = socket.defaultArguments();
    Connector.IntegerArgument portArg = (Connector.IntegerArgument)parametersMap.get("port");
    portArg.setValue(8000);
    vm = socket.attach(parametersMap);
    vm.suspend();
    System.out.println("Attached to Process '" + vm.name() + "'");

    List<ThreadReference> ltr = vm.allThreads();
    for(ThreadReference thread_ref : ltr)
    {
        if(thread_ref.name().equalsIgnoreCase("main"))
        {
            ThreadReference main_tr = thread_ref;
            List<StackFrame> sf_list = main_tr.frames();
            System.out.println(sf_list.size() + " Stack Frames");
            start = new StringTree(thread_ref.name(), "StackFrame");

            for(StackFrame sf : sf_list)
                {
                    String stackname = null;
                    if(sf.toString().lastIndexOf("io") != -1)
                        stackname = sf.toString().substring(sf.toString().lastIndexOf("io") + 3, sf.toString().indexOf("thread")-8);
                    else
                        stackname = sf.toString().substring(sf.toString().indexOf(".")+1,sf.toString().indexOf(":"));
                    StringTree start2 = new StringTree(stackname, "StackFrame");
                    start.addChild(start2);
                    try 
                    {
                        List<LocalVariable> llv = sf.visibleVariables();
                        for(LocalVariable l : llv)
                        {
                            Value v = sf.getValue(l);
                            if(!DFSLookup.containsKey(v))
                            {
                                dfs(v, start, start2, DFSLookup);
                            }
                        }

                    } catch (AbsentInformationException aie) {
                         System.out.println("No info for " + sf);
                    }
                }
            }
        }
        GraphicsTree g = new GraphicsTree(start);
        nodes.add(g);
        this.setPreferredSize(new Dimension(g.getXWindow(), g.getYWindow()));
    }


    public void disconnect() {
        nodes.clear();
    }

    @Override
    //Need this function in order to paint on canvas
    public void paint(Graphics g) {
        //For loop that draws all shapes currently in the vector
        for (int i = 0; i < nodes.size(); i++) {
            nodes.get(i).draw(g);
        }
    }


    private void dfs(Value v, StringTree topParent, StringTree parent,HashMap<Value,StringTree> Lookup ) throws ClassNotLoadedException
    {
        StringTree z ;

        if(v == null)
        {
            return;
        }
        if(Lookup.containsKey(v)) {
            
            topParent.addConnection(parent, Lookup.get(v));
            return;
            
        }
        if(v.type() instanceof PrimitiveType)
        {
            z = new StringTree(v.toString(), "Value");
            parent.addChild(z);
            return;
        }
        if(v instanceof ObjectReference)
        {
            ObjectReference o = (ObjectReference) v;
            if(o.type() instanceof ClassType)
            {
                ClassType ct = (ClassType) o.type();

                int s = ct.name().toString().lastIndexOf(".") + 1;

               if(ct.toString().startsWith("class java.lang"))
                {
                    
                    z = new StringTree(ct.name().substring(s), "Value");
                    Lookup.put(v,z);
                    parent.addChild(z);
                    return;
                }
             
                z = new StringTree(ct.name().substring(s), "Object");
                Lookup.put(v,z);
                parent.addChild(z);
                for(Field f : ct.allFields())
                {
                    Value u = o.getValue(f);
                    dfs(u, topParent, z, Lookup);
                }
            } else if (v instanceof ArrayReference){
                ArrayReference aref = (ArrayReference) v;
                z = new StringTree(aref.toString().substring(aref.toString().lastIndexOf(".")+1,aref.toString().lastIndexOf(" ")), "Object");
                Lookup.put(v,z);
                parent.addChild(z);
                for(Value val : aref.getValues())
                    if(!(val.type().toString().equals("byte"))){
                        System.out.println(val.type());
                        dfs(val, topParent, z, Lookup);
                    }
            }
        }

    }
}


