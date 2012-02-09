/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jvmconnector;

import com.sun.jdi.Bootstrap;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.VirtualMachineManager;
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
    public static void main(String[] args) throws IOException, IllegalConnectorArgumentsException {
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
        if(socket != null)
        {
            Map parametersMap = socket.defaultArguments();
            Connector.IntegerArgument portArg = (Connector.IntegerArgument)parametersMap.get("port");
            System.out.println(portArg);
            portArg.setValue(8000);
            vm = socket.attach(parametersMap);
            System.out.println("Attached to Process '" + vm.name() + "'");
        }
    }
}
