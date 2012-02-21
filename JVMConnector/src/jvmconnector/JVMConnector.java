/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jvmconnector;

import com.sun.jdi.*;
import com.sun.jdi.connect.AttachingConnector;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author stephen
 */
public class JVMConnector {

    /**
     * @param args the command line arguments
     */
    
    
                                        
    public static void main(String[] args) throws IOException, IllegalConnectorArgumentsException, InterruptedException, IncompatibleThreadStateException, AbsentInformationException, ClassNotLoadedException {
        ArrayList<Value> seen = new ArrayList<Value> ();
        
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
        System.out.println(portArg);
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
                    System.out.println(main_tr);
                    List<StackFrame> sf_list = main_tr.frames();
                    System.out.println(sf_list.size() + " Stack Frames");
                    for(StackFrame sf : sf_list)
                    {
                        ArrayList<StringTree> Trees = new ArrayList<StringTree>();
                        StringTree parent = new StringTree("main", "StackFrame");
                        try {
                                List<LocalVariable> llv = sf.visibleVariables();
                                for(LocalVariable l : llv)
                                {
                                   if(l.type() instanceof ReferenceType)
                                   {
                                       ReferenceType rt = (ReferenceType) l.type();
                                       List<ObjectReference> lor = rt.instances(0);
                                       for(ObjectReference or: lor)
                                       {
                                            if(or.type() instanceof ClassType)
                                            {
                                                ClassType ct = (ClassType) or.type();
                                                List<Field> lf = ct.allFields();
                                                for(Field f : lf)
                                                {
                                                    if(f.type() instanceof ReferenceType)
                                                    {
                                                        //System.out.println("goodbye " + f);
                                                        Value v = or.getValue(f);
                                                        dfs(v, parent, seen);
                                                    }
                                                }
                                            }
                                       }
                                   }
                                }
                            }
                        catch (AbsentInformationException aie) {
                                        System.out.println("No info for " + sf);
                                    }
                }
            }
        }
    }

    private static void dfs(Value v, StringTree parent, ArrayList<Value> seen) throws ClassNotLoadedException 
    {
        if(v == null)
            return;
        if(seen.contains(v))
            return;
        if(v.type() instanceof PrimitiveType)
        {
            seen.add(v);
            StringTree z = new StringTree("name", "value");
            parent.addConnection(parent, z);
            return;
        }
        if(v instanceof ObjectReference)
        {
            System.out.println("DFS");
            seen.add(v);
            ObjectReference o = (ObjectReference) v;
            if(o.type() instanceof ClassType)
            {
                ClassType ct = (ClassType) o.type();
                if(ct.toString().startsWith("class java.lang"))
                    return;
                for(Field f : ct.allFields())
                {
                    System.out.println("Hello " + f);
                    Value u = o.getValue(f);
                    StringTree z = new StringTree(ct.name(), "new");
                    parent.addConnection(parent, z);
                    dfs(u, z, seen);
                }
            }
        }
    }
}



