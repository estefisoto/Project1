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
        
        List<ObjectReference> attempt = null;

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
                        try {
                                List<LocalVariable> llv = sf.visibleVariables();
                                for (LocalVariable lv : llv) 
                                {
                                    //System.out.println(lv.typeName());
                                    //System.out.println(lv);
                                    if(lv.type() instanceof ReferenceType)
                                    {
                                        ReferenceType rt = (ReferenceType) lv.type();
                                        List<ObjectReference> objref_l = rt.instances(0);
                                        for(ObjectReference objref : objref_l)
                                        {                                       
                                            List<ObjectReference> referringObj_l = objref.referringObjects(0);
                                            System.out.println("******" + objref);
                                            for(ObjectReference referring_obj : referringObj_l)
                                            {
                                                System.out.println("^^ referred to by: " + referring_obj);
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
}
