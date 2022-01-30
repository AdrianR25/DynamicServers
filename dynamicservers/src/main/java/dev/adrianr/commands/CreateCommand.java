package dev.adrianr.commands;

import java.util.ArrayList;

import com.google.common.collect.Range;

import dev.adrianr.DynamicServers;
import dev.adrianr.common.Allocation;
import dev.adrianr.configuration.ServerTemplate;
import dev.adrianr.http.MojangEndpoint;
import dev.adrianr.pterodactyl.PterodactylApplicationEndpoint;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class CreateCommand extends Command {

	private DynamicServers plugin;


	public CreateCommand(DynamicServers plugin) {
		super("dscreate");
		this.plugin = plugin;		
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		
		// /dscreate <template> <player>
		if (args.length != 2) return; //TODO: Send message

		ServerTemplate template = plugin.getConfigManager().getServerTemplate(args[0]);
		if (template == null) return; //TODO: Send message

		if (MojangEndpoint.getPlayerUUID(args[1]) == null) return; //TODO: Send message
		
		String portRange = template.getAllocationPorts();
		int minPort = Integer.parseInt(portRange.split("-")[0]);
		int maxPort = Integer.parseInt(portRange.split("-")[1]);
		if (minPort > maxPort) return; // TODO: Send message

		int allocationId = getOrCreateAllocationId(template.getServerSettings().getNodeId(), template.getAllocationIp(), minPort, maxPort);
		if (allocationId == -1) return; //TODO: Send message

		// create server

		// wait until it's ready

		// add server to bungeecord

		// execute commands


	}

	private int getOrCreateAllocationId(int node, String ip, int minPort, int maxPort){

		
		// if there is an allocation already created that we can use
		ArrayList<Allocation> allocations = plugin.getApplicationEndpoint().getAllocationsByNode(node);
        int allocationId = getExsistingAllocation(allocations, ip, minPort, maxPort);
        if (allocationId > -1) return allocationId;

		// otherwise create one
		Allocation newAllocation = new Allocation(ip, minPort);
		for (int i = minPort+1; i <= maxPort; i++) {
			if (allocations.contains(newAllocation)){
				newAllocation.setPort(i);
			} else {
				break;
			}
		}

		//create allocation
		if (plugin.getApplicationEndpoint().createAllocation(node, ip, newAllocation.getPort())){
            allocations = plugin.getApplicationEndpoint().getAllocationsByNode(node);
        }

        return getExsistingAllocation(allocations, ip, minPort, maxPort); // this should return the allocation just created (because it exists but hasn't been asigned yet)
		
	}

    private int getExsistingAllocation(ArrayList<Allocation> allocations, String ip, int minPort, int maxPort){
        
        if (allocations.size() > 0){
			for (Allocation allocation : allocations) {
				if (allocation.getIp().equals(ip) && !allocation.isAssigned() && (allocation.getPort() >= minPort && allocation.getPort() <= maxPort)) {
					return allocation.getId();
				}
			}
		}
        return -1;
    }

}
